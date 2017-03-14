/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtreetable;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import jtreetable.JTreeTable.Setup;

/**
 *
 * @author Naruto
 */
public class Example extends javax.swing.JFrame {

    private JTreeTable treeTable;
    
    /**
     * Creates new form Example
     */
    public Example() {
        initComponents();
        init();
    }
    
    private void init(){
        List<Setup> sets = new ArrayList<>();
        sets.add(new Setup("Fruits", String.class, false, 120, "Fruits"));
        sets.add(new Setup("In cart", Boolean.class, true, 120, "Cart"));
        treeTable = new JTreeTable(sets);
        
        getContentPane().add(treeTable.getSrollPane(), BorderLayout.CENTER);
        
        JTreeTable.TreeElement teCommon = new JTreeTable.TreeElement("Common", null, null);
        treeTable.addBranch(teCommon, null, new Object[]{"A color", false});
        JTreeTable.TreeElement teChild01 = new JTreeTable.TreeElement("Pear", null, null);
        treeTable.addBranch(teCommon, teChild01, new Object[]{"Yellow color", false});
        JTreeTable.TreeElement teChild02 = new JTreeTable.TreeElement("Apple", null, null);
        treeTable.addBranch(teCommon, teChild02, new Object[]{"Yellow color", false});
        
        JTreeTable.TreeElement teExotic = new JTreeTable.TreeElement("Exotic", null, null);
        treeTable.addBranch(teExotic, null, new Object[]{"A color", false});
        JTreeTable.TreeElement teChild03 = new JTreeTable.TreeElement("Mango", null, null);
        treeTable.addBranch(teExotic, teChild03, new Object[]{"Green color", false});
        JTreeTable.TreeElement teChild04 = new JTreeTable.TreeElement("Kiwi", null, null);
        treeTable.addBranch(teExotic, teChild04, new Object[]{"Maroon color", false});
        JTreeTable.TreeElement teChild05 = new JTreeTable.TreeElement("Banana", null, null);
        treeTable.addBranch(teExotic, teChild05, new Object[]{"Yellow color", false});
        
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Example.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Example.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Example.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Example.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Example().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}