package ui.gui;

import model.Categories;
import model.Item;
import model.ShoppingList;
import model.exception.AvoidDuplicateException;
import model.exception.NotInTheListException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;

public class ShoppingTab extends Tab implements PropertyChangeListener, ListSelectionListener {
    private ShoppingList shoppingList;
    private JPanel topPanel;
    private JFormattedTextField budgetField;
    private NumberFormat budgetFormat;

    private JPanel centerPanel;
    private DefaultListModel listModel;
    private JList list;

    private JButton addButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton boughtButton;

    String[] categories = {"Food", "Fruit And Vegetables", "Drinks", "Necessities", "Others"};


    // EFFECTS: create a new tab with the shoppinglist displayed
    public ShoppingTab(Main controller, ShoppingList shoppingList) {
        super(controller);
        this.shoppingList = shoppingList;
        setLayout(new BorderLayout());

        topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(new Color(122, 189, 194));
        createBudget();
        createReset();

        createPaneList();
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        centerPanel.setBackground(new Color(181, 241, 227, 207));

        createAddButton();

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

    private void createReset() { // TODO
        refreshButton = new JButton("Refresh");
        try {
            Image img = ImageIO.read(getClass().getResource("images/refreshIcon.png"));
            Image newImage = img.getScaledInstance(20,
                    20, Image.SCALE_DEFAULT);
            refreshButton.setIcon(new ImageIcon(newImage));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        refreshButton.addActionListener(e -> {
            if (e.getSource() == refreshButton) {
                createPaneList();
            }
        });
        topPanel.add(refreshButton);
    }

    // EFFECTS: when click on the add button, show a new dialogue window to allow inputs
    private void createAddButton() {
        addButton = new JButton("Add");
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/ui/gui/images/addIcon.png"));
            Image newImage = myPicture.getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            addButton.setIcon(new ImageIcon(newImage));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        addButton.addActionListener(e -> {
            JTextField itemName = new JTextField();
            JTextField itemAmount = new JFormattedTextField("1");
            JComboBox categoryList = new JComboBox(categories);
            JPanel panel = new JPanel(new GridLayout(3, 2));

            panel.add(new JLabel("Item's name: "));
            panel.add(itemName);
            panel.add(new JLabel("Item's amount: "));
            panel.add(itemAmount);
            panel.add(new JLabel("Choose a Category: "));
            panel.add(categoryList);

            processCommand(e, panel, itemName, itemAmount, categoryList);
        });
        addButton.setBackground(new Color(122, 189, 194));
    }


    // EFFECTS: when click on the add button, process to add items to toBuy list
    private void processCommand(ActionEvent e, JPanel panel, JTextField itemName,
                                JTextField itemAmount, JComboBox categoryList) {
        if (e.getSource() == addButton) {
            try {
                BufferedImage myPicture = ImageIO.read(new File("src/main/ui/gui/images/groceryIcon.png"));
                Image newImage = myPicture.getScaledInstance(70, 70, Image.SCALE_DEFAULT);
                ImageIcon img = new ImageIcon(newImage);
                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Adding a new items to buy...", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, img);
                if (result == JOptionPane.OK_OPTION) {
                    String name = itemName.getText();
                    int amount = Integer.parseInt(itemAmount.getText());
                    String category = (String) categoryList.getSelectedItem();
                    Categories type = shoppingList.convertToCategory(category);
                    Item newItem = new Item(name, amount, type, LocalDate.now());
                    try {
                        shoppingList.addItem(newItem);
                    } catch (AvoidDuplicateException avoidDuplicateException) {
                        checkException(name);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            addInputToList(itemName, itemAmount);
            deleteButton.setEnabled(true);
            boughtButton.setEnabled(true);

        }
    }


    // EFFECTS: add the input new item to the list
    private void addInputToList(JTextField itemName, JTextField itemAmount) {
        int index = list.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }
        listModel.insertElementAt(itemAmount.getText() + "  " + itemName.getText(), index);
    }

    // EFFECTS: check the exception for adding items to shopping list
    private void checkException(String name) {
        if (name == null | name.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "You haven't enter the name for the item!",
                    "Oops...",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "You already added the item to shopping list!",
                    "Oops...",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // have multiple panel as list displayed as the To buy list
    private void createPaneList() {
        centerPanel = new JPanel(new BorderLayout());
        JPanel selection = createSelectionBar();
        JScrollPane toBuyPanel = createToBuyList();

        centerPanel.add(selection, BorderLayout.PAGE_START);
        centerPanel.add(toBuyPanel, BorderLayout.CENTER);

    }

    // EFFECTS: create list of items that needed to buy in the shopping list
    private JScrollPane createToBuyList() {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane toBuyPanel = new JScrollPane(list);

        toBuyPanel.setBackground(new Color(248, 255, 194, 210));
        return toBuyPanel;
    }

    // EFFECTS: create the example bar that display the basic element of list
    private JPanel createSelectionBar() {
        JPanel example = new JPanel(new GridLayout(1, 2));
        createBoughtButton();
        createDeleteButton();
        example.add(boughtButton);
        example.add(deleteButton);
        centerPanel.add(example, BorderLayout.PAGE_START);
        return example;
    }

    // EFFECTS: do delete from the shopping list
    private void createDeleteButton() {
        deleteButton = new JButton("Delete");
        if (shoppingList.getToBuy().isEmpty()) {
            deleteButton.setEnabled(false);
        }
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                listModel.remove(index);
//                String itemName = (String) list.getSelectedValue();
                Item i = shoppingList.getToBuy().get(index);
                try {
                    shoppingList.deleteItem(i);
                } catch (NotInTheListException notInTheListException) {
                    notInTheListException.printStackTrace();
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);

                if (shoppingList.getToBuy().isEmpty()) {
                    deleteButton.setEnabled(false);
                }
            }
        });
    }

    // EFFECTS: create a bought button that delete items from shopping list
    private void createBoughtButton() {
        boughtButton = new JButton("Bought");
        if (shoppingList.getToBuy().isEmpty()) {
            boughtButton.setEnabled(false);
        }
        boughtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                listModel.remove(index);
                Item i = shoppingList.getToBuy().get(shoppingList.getToBuy().size() - index - 1);
                if (e.getSource() == boughtButton) {
                    try {
                        shoppingList.markItem(i);
                    } catch (NotInTheListException notInTheListException) {
                        notInTheListException.printStackTrace();
                    }
                }
                if (shoppingList.getToBuy().isEmpty()) {
                    boughtButton.setEnabled(false);
                }
            }
        });
    }



    /**
     * have a budget text at the top of the page
     */
    private void createBudget() {
        //Create the labels.
        String budgetString = "Set the budget: ";
        JLabel budgetLabel = new JLabel(budgetString);
        //Create the text fields and set them up.
        budgetField = new JFormattedTextField(budgetFormat);
        budgetField.setValue(shoppingList.getBudget());
        budgetField.setColumns(10);
        budgetField.addPropertyChangeListener("value", this);

        //Tell accessibility tools about label/textfield pairs.
        budgetLabel.setLabelFor(budgetField);
        budgetFormat = NumberFormat.getNumberInstance();

        //Lay out the labels in a panel.
        JPanel budgetPane = new JPanel();
        budgetPane.setBackground(new Color(122, 189, 194));
        budgetPane.add(budgetLabel);
        budgetPane.add(budgetField);
        topPanel.add(budgetPane);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        double newBudget;
        if (source == budgetField) {
            newBudget = ((Number) budgetField.getValue()).doubleValue();
            shoppingList.setBudget(newBudget);
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        if (list.getSelectedIndex() == -1) {
            //No selection, disable fire button.
            deleteButton.setEnabled(false);
            boughtButton.setEnabled(false);
        } else {
            //Selection, enable the fire button.
            deleteButton.setEnabled(true);
            boughtButton.setEnabled(true);
        }
    }


}


//
//    private JPanel createSinglePanel(Item i) {
//        JPanel panel = new JPanel(new GridLayout(1, 8));
//        // 0: check whether bought
//        JCheckBox checkBox = new JCheckBox();
//        checkBox.setMnemonic(KeyEvent.VK_C);
//        checkBox.setSelected(false);
//        doCheckItemToBought(checkBox, i);
//
//        // 1: item name;
//        JLabel item = new JLabel(i.getName());
//
//        // 2: editable text field for amount
//        JFormattedTextField amount = new JFormattedTextField(i.getAmount());
//
//        // 9: delete button
//        JButton deleteButton = new JButton("Delete");
//        doDelete(deleteButton, i);
//
//        panel.add(checkBox, 0);
//        panel.add(item, 1);
//        panel.add(amount, 2);
//        panel.add(deleteButton, 3);
//
//        return panel;
//    }


/**
 * private void initLeftSplit() {
 * choicePanel = new JPanel();
 * choicePanel.setBackground(Color.BLUE);
 * choicePanel.setLayout(new GridLayout(4, 1));
 * <p>
 * addTextField();
 * }
 * <p>
 * private void addTextField() {
 * JTextField budget = new JTextField();
 * JButton budgetPanel = new JButton();
 * budgetPanel.add(budget);
 * //        budget.setPreferredSize(new Dimension(choicePanel.getWidth(), choicePanel.getHeight() / 4));
 * //        budget.setActionCommand("Budget");
 * //        budget.addActionListener(this);
 * choicePanel.add(budgetPanel);
 * <p>
 * JFormattedTextField numToBuy = new JFormattedTextField();
 * JButton numToBuyPanel = new JButton();
 * numToBuyPanel.add(numToBuy);
 * //        numToBuy.setActionCommand("Needs to buy");
 * //        numToBuy.addActionListener(this);
 * choicePanel.add(numToBuyPanel);
 * <p>
 * JFormattedTextField numBought = new JFormattedTextField();
 * //        numBought.setActionCommand("Have bought");
 * //        numBought.addActionListener(this);
 * JButton numBoughtPanel = new JButton();
 * numBoughtPanel.add(numBought);
 * choicePanel.add(numBoughtPanel);
 * <p>
 * JFormattedTextField time = new JFormattedTextField(
 * java.util.Calendar.getInstance().getTime());
 * //        time.setActionCommand("Current date");
 * //        time.addActionListener(this);
 * JButton timePanel = new JButton();
 * timePanel.add(time);
 * choicePanel.add(timePanel);
 * //choicePanel.add(time);
 * <p>
 * //addLabelToText(budget, numToBuy, numBought, time);
 * }
 * <p>
 * <p>
 * private void addLabelToText(JTextField budget, JFormattedTextField numToBuy,
 * JFormattedTextField numBought, JFormattedTextField time) {
 * JLabel budgetLabel = new JLabel("Budget: ");
 * budgetLabel.setLabelFor(budget);
 * budgetLabel.setVisible(true);
 * <p>
 * JLabel numToBuyLabel = new JLabel("Need to buy: ");
 * numToBuyLabel.setLabelFor(numToBuy);
 * <p>
 * JLabel numBoughtLabel = new JLabel("Have bought: ");
 * numBoughtLabel.setLabelFor(numBought);
 * <p>
 * JLabel timeLabel = new JLabel("Today is: ");
 * timeLabel.setLabelFor(time);
 * <p>
 * //        GridBagLayout gridbag = new GridBagLayout();
 * //        GridBagConstraints c = new GridBagConstraints();
 * <p>
 * //        JLabel[] labels = {budgetLabel, numToBuyLabel, numBoughtLabel, timeLabel};
 * //        JTextField[] textFields = {budget, numToBuy, numBought, time};
 * //        addLabelTextRows(labels, textFields, gridbag, choicePanel);
 * //        c.gridwidth = GridBagConstraints.REMAINDER; //last
 * //        c.anchor = GridBagConstraints.WEST;
 * //        c.weightx = 1.0;
 * choicePanel.add(budget);
 * choicePanel.add(numToBuy);
 * choicePanel.add(numBought);
 * choicePanel.add(time);
 * <p>
 * <p>
 * }
 * <p>
 * //    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields,
 * //                                  GridBagLayout gridbag, Container choicePanel) {
 * //        GridBagConstraints c = new GridBagConstraints();
 * //        c.anchor = GridBagConstraints.EAST;
 * //        int numLabels = labels.length;
 * //
 * //        for (int i = 0; i < numLabels; i++) {
 * //            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
 * //            c.fill = GridBagConstraints.NONE;      //reset to default
 * //            c.weightx = 0.0;                       //reset to default
 * //            choicePanel.add(labels[i], c);
 * //
 * //            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
 * //            c.fill = GridBagConstraints.HORIZONTAL;
 * //            c.weightx = 1.0;
 * //            choicePanel.add(textFields[i], c);
 * //        }
 * //    }
 * <p>
 * //    private void initRightSplit() {
 * //        listScrollPane = new JScrollPane();
 * //        choicePanel.setLayout(new GridLayout(1, 3));
 * //    }
 * <p>
 * //    private void createSplitPane() {
 * //        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
 * //                choicePanel, listScrollPane);
 * //        splitPane.setOneTouchExpandable(true);
 * //        splitPane.setDividerLocation(200);
 * //
 * //        //Provide minimum sizes for the two components in the split pane.
 * //        Dimension minimumSize = new Dimension(100, 50);
 * //        choicePanel.setMinimumSize(minimumSize);
 * //        listScrollPane.setMinimumSize(minimumSize);
 * //
 * //        //Provide a preferred size for the split pane.
 * //        splitPane.setPreferredSize(new Dimension(500, 500));
 * //        topPanel.add(splitPane);
 * //    }
 *
 * @Override public void actionPerformed(ActionEvent e) {
 * <p>
 * }
 * }
 */
