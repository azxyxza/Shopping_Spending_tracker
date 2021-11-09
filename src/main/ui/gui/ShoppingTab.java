package ui.gui;

import model.Categories;
import model.Item;
import model.ShoppingList;
import model.exception.AvoidDuplicateException;
import model.exception.NotInTheListException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.time.LocalDate;

public class ShoppingTab extends Tab implements PropertyChangeListener {
    //    private JSplitPane splitPane;
    private ShoppingList shoppingList;
    private JPanel topPanel;
    private JPanel centerPanel;

    private JLabel budgetLabel;
    private static String budgetString = "Set the budget: ";
    private JFormattedTextField budgetField;
    private NumberFormat budgetFormat;
    private JButton addButton;
    private JButton refreshButton; // TODO: reset the shopping list

    ImageIcon icon = createImageIcon("images/middle.gif");
    String[] categories = {"Food", "Fruit And Vegetables", "Drinks", "Necessities", "Others"};
    private NumberFormat amountFormat;
    private JFormattedTextField itemAmount;


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

    private void createReset() {
        refreshButton = new JButton("Refresh");
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
            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Adding a new items to buy...", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String name = itemName.getText();
                int amount = Integer.parseInt(itemAmount.getText());
                String category = (String) categoryList.getSelectedItem();
                Categories type = shoppingList.convertToCategory(category);
                Item newItem = new Item(name, amount, type, LocalDate.now());
                try {
                    shoppingList.addItem(newItem);
                } catch (AvoidDuplicateException avoidDuplicateException) {
                    if (name.equals("")) {
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
            }
        }
    }


    // have multiple panel as list displayed as the To buy list
    private void createPaneList() {
        centerPanel = new JPanel(new BorderLayout());
        JPanel example = new JPanel(new GridLayout(1, 8));

        // index 0
        JCheckBox check = new JCheckBox();
        // index 1
        JLabel item = new JLabel("Item's name");
        // index 4
        JLabel amount = new JLabel("Quantity");
        // index 5
        JButton deleteButton = new JButton("Delete");
        example.add(check, 0); // 0
        example.add(item, 1); // 1
        example.add(amount, 2); // 2
        example.add(deleteButton);
        centerPanel.add(example, BorderLayout.NORTH);

        JPanel toBuyPanel = new JPanel(new GridLayout(shoppingList.getToBuy().size(), 1));
        toBuyPanel.setBackground(new Color(248, 255, 194, 210));
        for (Item i : shoppingList.getToBuy()) {
            // TODO: tobuy item's is not displayed because it's not running for the first time
            toBuyPanel.add(createSinglePanel(i));
        }
        centerPanel.add(toBuyPanel, BorderLayout.CENTER);
    }


    private JPanel createSinglePanel(Item i) {
        JPanel panel = new JPanel(new GridLayout(1, 8));
        // 0: check whether bought
        JCheckBox checkBox = new JCheckBox();
        checkBox.setMnemonic(KeyEvent.VK_C);
        checkBox.setSelected(false);
        doCheckItemToBought(checkBox, i);

        // 1: item name;
        JLabel item = new JLabel(i.getName());

        // 2: editable text field for amount
        JFormattedTextField amount = new JFormattedTextField(i.getAmount());

        // 9: delete button
        JButton deleteButton = new JButton("Delete");
        doDelete(deleteButton, i);

        panel.add(checkBox, 0);
        panel.add(item, 1);
        panel.add(amount, 2);
        panel.add(deleteButton, 3);

        return panel;
    }

    // EFFECTS: when the check box is selected, add the items to the bought list
    private void doCheckItemToBought(JCheckBox checkBox, Item i) {
        checkBox.addActionListener(e -> {
            if (e.getSource() == checkBox) {
                try {
                    shoppingList.markItem(i);
                } catch (NotInTheListException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // EFFECTS: when clicking delete button, remove the items from tobuy list
    private void doDelete(JButton deleteButton, Item i) {
        deleteButton.addActionListener(e -> {
            if (e.getSource() == deleteButton) {
                try {
                    shoppingList.deleteItem(i);
                } catch (NotInTheListException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    /**
     * have a budget text at the top of the page
     */
    private void createBudget() {
        //Create the labels.
        budgetLabel = new JLabel(budgetString);
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


    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ShoppingTab.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}

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
