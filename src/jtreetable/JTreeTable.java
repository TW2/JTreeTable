/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtreetable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Naruto
 */
public class JTreeTable extends JTable {
    
    private static DefaultTableModel dtm;
    private List<Setup> sets = new ArrayList<>();
    private JScrollPane scrollpane;
    
    private String[] headers;
    private Class[] types;
    private boolean[] canEdit;
    
    private Map<TreeElement, Object[]> rows = new HashMap<>();

    public JTreeTable(List<Setup> sets) {
        this.sets = sets;
        init();
    }
    
    private void init(){
        setDoubleBuffered(true);
        
        scrollpane = new JScrollPane();
        scrollpane.setViewportView(this);
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        headers = new String[sets.size()+1];
        headers[0] = "Element";
        for(int i=1; i<sets.size()+1; i++){
            headers[i] = sets.get(i-1).getColumnName();
        }
        
        types = new Class[sets.size()+1];
        types[0] = TreeElement.class;
        for(int i=1; i<sets.size()+1; i++){
            types[i] = sets.get(i-1).getColumnClass();
        }
        
        canEdit = new boolean[sets.size()+1];
        canEdit[0] = false;
        for(int i=1; i<sets.size()+1; i++){
            canEdit[i] = sets.get(i-1).isColumnCanEdit();
        }
       
        dtm = new DefaultTableModel(
                null,
                headers
        ){
            @Override
            public Class getColumnClass(int columnIndex) {return types [columnIndex];}
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {return canEdit [columnIndex];}
        };
        
        setModel(dtm);
        setRowHeight(30);
        
        TableColumn column;
        column = getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column.setIdentifier("TreeElement");
        for(int i=1; i<sets.size()+1; i++){
            column = getColumnModel().getColumn(i);
            column.setPreferredWidth(sets.get(i-1).getColumnSize());
            column.setIdentifier(sets.get(i-1).getColumnIdentifier());
        }
        
        setDefaultRenderer(TreeElement.class, new TreeElementRenderer());
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                treeTableMouseClicked(e);                
            }
        });
        
    }
    
    public void treeTableMouseClicked(MouseEvent e){
        int row = rowAtPoint(e.getPoint());
        int col = columnAtPoint(e.getPoint());
        Object obj = getValueAt(row, col);

        if(obj instanceof TreeElement){
            TreeElement te = (TreeElement)obj; 
            
            if(te.isExpanded() == true && te.isLeaf() == false){
                te.setExpanded(false);
            }else if(te.isLeaf() == false){
                te.setExpanded(true);
            }
            
            
            if(te.isExpanded() == true && te.getChildren().isEmpty() == false && te.isLeaf() == false){
                int index = 1;
                for(int i=0; i< te.getChildren().size(); i++){
                    TreeElement child = te.getChildren().get(i);                    
                    if(child != null && child.getParent() != null && child.getParent().equals(te)){
                        dtm.insertRow(row+index, rows.get(te.getChildren().get(i)));
                        index++;
                    }
                }                
            }else if(te.isExpanded() == false && te.getChildren().isEmpty() == false && te.isLeaf() == false){
                //On réunit dans un tableau tous les sous éléments de te.
                List<Integer> ints = new ArrayList<>();
                for(int i=0; i<dtm.getRowCount(); i++){
                    //Repérage du parent
                    TreeElement search = (TreeElement)getValueAt(i, 0);
                    if(search.equals(te)){
                        for(int j=0; j<dtm.getRowCount(); j++){
                            //On choppe les enfants
                            TreeElement child = (TreeElement)getValueAt(j, 0);
                            if(child != null && child.getParent() != null && child.getParent().equals(search)){
                                ints.add(j);
                            }
                        }
                    }
                }
                //On itère sur nos positions
                for(int i = ints.size()-1; i>=0; i--){
                    int index = ints.get(i);
                    dtm.removeRow(index);
                }
            }

            repaint();
        }
        
    }
    
    public void addBranch(TreeElement parent, TreeElement child, Object[] row){
        if(child != null && parent != null){
            Object[] objElements = new Object[row.length+1];
            objElements[0] = child;
            for(int i=1; i<objElements.length; i++){
                objElements[i] = row[i-1];
            }
            
            parent.addChild(parent, child);
            parent.setLeaf(false);

            rows.put(child, objElements);
        }else if(parent != null){
            Object[] objElements = new Object[row.length+1];
            objElements[0] = parent;
            for(int i=1; i<objElements.length; i++){
                objElements[i] = row[i-1];
            }

            rows.put(parent, objElements);
            dtm.addRow(objElements);
        }        
    }
    
    public JScrollPane getScrollPane(){
        return scrollpane;
    }

    public static class TreeElement {
        private String title;
        private ImageIcon eXimageIcon;
        private ImageIcon cSimageIcon;
        private static List<TreeElement> children = new ArrayList<>();
        private TreeElement parent = null;
        
        private boolean expanded = false;
        private boolean leaf = true;

        public TreeElement() {
        }

        public TreeElement(String title, ImageIcon expanded, ImageIcon collapsed) {
            this.title = title;
            this.eXimageIcon = expanded;
            this.cSimageIcon = collapsed;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setExpandedImageIcon(ImageIcon eXimageIcon) {
            this.eXimageIcon = eXimageIcon;
        }

        public ImageIcon getExpandedImageIcon() {
            return eXimageIcon;
        }
        
        public void setCollapsedImageIcon(ImageIcon cSimageIcon) {
            this.cSimageIcon = cSimageIcon;
        }

        public ImageIcon getCollapsedImageIcon() {
            return cSimageIcon;
        }

        public void setChildren(List<TreeElement> children) {
            TreeElement.children = children;
        }

        public List<TreeElement> getChildren() {
            return children;
        }
        
        public void addChild(TreeElement parent, TreeElement elem){
            children.add(elem);
            elem.setParent(parent);
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setParent(TreeElement parent) {
            this.parent = parent;
        }

        public TreeElement getParent() {
            return parent;
        }
        
        
        
    }
    
    public static class Setup {        
        private String columnName;
        private Class columnClass;
        private boolean columnCanEdit;
        private int columnSize;
        private Object columnIdentifier;

        public Setup() {
        }

        public Setup(String columnName, Class columnClass, boolean columnCanEdit, int columnSize, Object columnIdentifier) {
            this.columnName = columnName;
            this.columnClass = columnClass;
            this.columnCanEdit = columnCanEdit;
            this.columnSize = columnSize;
            this.columnIdentifier = columnIdentifier;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnClass(Class columnClass) {
            this.columnClass = columnClass;
        }

        public Class getColumnClass() {
            return columnClass;
        }

        public void setColumnCanEdit(boolean columnCanEdit) {
            this.columnCanEdit = columnCanEdit;
        }

        public boolean isColumnCanEdit() {
            return columnCanEdit;
        }

        public void setColumnSize(int columnSize) {
            this.columnSize = columnSize;
        }

        public int getColumnSize() {
            return columnSize;
        }

        public void setColumnIdentifier(Object columnIdentifier) {
            this.columnIdentifier = columnIdentifier;
        }

        public Object getColumnIdentifier() {
            return columnIdentifier;
        }
        
    } 
    
    public class TreeElementRenderer extends JLabel implements TableCellRenderer {

        public TreeElementRenderer() {
            init();
        }

        private void init(){
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {

            setBackground(Color.white);

            if(value instanceof TreeElement){
                TreeElement val = (TreeElement)value;
                if(val.getExpandedImageIcon() != null && val.isExpanded() == true){
                    setIcon(val.getExpandedImageIcon());
                }else if(val.getCollapsedImageIcon() != null && val.isExpanded()== false){
                    setIcon(val.getCollapsedImageIcon());
                }else if(val.isExpanded() == true){
                    setIcon(new ImageIcon(getExpanded()));
                }else if(val.isExpanded() == false){
                    setIcon(new ImageIcon(getCollapsed()));
                }
                setText(val.getTitle());
            }

            return this;
        }

        private Image getCollapsed(){
            BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            int[] pX = new int[]{5,10,5};
            int[] pY = new int[]{0,5,10};
            g.setColor(Color.blue);
            g.fillPolygon(pX, pY, 3);
            g.dispose();
            return img;
        }

        private Image getExpanded(){
            BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            int[] pX = new int[]{0,10,5};
            int[] pY = new int[]{5,5,10};
            g.setColor(Color.blue);
            g.fillPolygon(pX, pY, 3);
            g.dispose();
            return img;
        }
    }
    
    
    
}
