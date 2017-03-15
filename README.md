# JTreeTable
A simple JTreeTable with a smart configuration.

How to use it :
-

First write this in your class :
```Java
private JTreeTable treeTable;
```

Then in your init method, write something like that :
```Java
//Initialize a Setup list to add it in JTreeTable instance.
List<Setup> sets = new ArrayList<>();

//Add Setup to your list
//Format for your column: name, class, can edit, size and identifier
sets.add(new Setup("Fruits", String.class, false, 120, "Fruits"));
sets.add(new Setup("In cart", Boolean.class, true, 120, "Cart"));

//Create your instance
treeTable = new JTreeTable(sets);

//Add the preconfigured scrollpane object
getContentPane().add(treeTable.getSrollPane(), BorderLayout.CENTER);

//Add a TreeElement parent object
//Format of TreeElement instance: display name, expand image (can be null) and collapse image (can be null)
//Format of addBranch: parent, child (can be null if parent) and all your object of the row
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
```

You can test this Example in the package.
