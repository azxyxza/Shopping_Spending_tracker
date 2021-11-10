package ui.gui;


import model.Item;
import model.ShoppingList;
import model.Spending;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/**This is the spending panel that display the spending summary and transaction list*/
public class SpendingTab extends Tab implements PropertyChangeListener {
    private Spending spending;
    private ShoppingList shoppingList;
    //create two panel and whole is a split pane
    private JPanel summary;
    private JPanel transactionPanel;
    private JScrollPane transaction;
    private JSplitPane splitPane;

    //Values for the fields
    private double income;
    private double expense;
    private double balance;

    //Labels to identify the fields
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;

    //Strings for the labels
    private static String incomeString = "Income: ";
    private static String expenseString = "Expense: ";
    private static String balanceString = "Balance: ";

    //Fields for data entry
    private JFormattedTextField incomeField;
    private JFormattedTextField expenseField;
    private JFormattedTextField balanceField;

    //Formats to format and parse numbers
    private NumberFormat incomeFormat;
    private NumberFormat expenseFormat;
    private NumberFormat balanceFormat;

    // EFFECTS: construct a spending tab with a split pane
    //          with top being the summary and bottom being the transaction
    public SpendingTab(Main controller, ShoppingList shoppingList, Spending spending) {
        super(controller);
        setUpFormats();
        summary = new JPanel();
        summary.setBackground(Color.PINK);
        transactionPanel = new JPanel();
        transactionPanel.setBackground(Color.CYAN);

        this.shoppingList = shoppingList;
        this.spending = spending;

        // top summary panel
        spending.trackExpense(spending.getTransactions());
        income = spending.getIncome();
        expense = spending.getExpense();
        balance = spending.getBalance();
        balance = computeBalance();
        createLabels();
        createTextField();
        setLabelTextLayout();

        // bottom transaction panel
        createTransactions();

        transaction = new JScrollPane(transactionPanel);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                summary, transaction);
        splitPane.setOneTouchExpandable(true);
        splitPane.setPreferredSize(new Dimension(Main.WIDTH - 50, Main.HEIGHT - 150));
        add(splitPane);
    }

    private void createTransactions() { // TODO: panel not displayed
        transactionPanel = new JPanel(new BorderLayout());
        JPanel title = new JPanel(new GridLayout(1, 8));
        // index 0
        JLabel item = new JLabel("Item's name");
        // index 1
        JLabel amount = new JLabel("Quantity");
        // index 8
        JFormattedTextField expense = new JFormattedTextField("Cost");
        // index 9
        JButton deleteButton = new JButton("Delete");
        title.add(item, 0); // 0
        title.add(amount, 1); // 1
        title.add(expense, 2);
        title.add(deleteButton, 3); // 8

        JPanel transactionHolder = new JPanel();
        for (Transaction t : spending.getTransactions()) {
            transactionHolder = createSingleTransaction(t);
        }

        transactionPanel.add(title, BorderLayout.NORTH);
        transactionPanel.add(transactionHolder, BorderLayout.CENTER);
    }

    private JPanel createSingleTransaction(Transaction t) {
        JPanel panel = new JPanel(new GridLayout(1, 8));
        Item i  = t.getItem();
        // 0: item name;
        JLabel item = new JLabel(i.getName());

        // 1: editable text field for amount
        JFormattedTextField amount = new JFormattedTextField(i.getAmount());

        // 2
        JFormattedTextField expense = new JFormattedTextField("Cost");

        // 3: delete button
        JButton deleteButton = new JButton("Delete");
        //doDelete(deleteButton, i);

        panel.add(item, 0); // 0
        panel.add(amount, 1); // 1
        panel.add(expense);
        panel.add(deleteButton); // 8
        return panel;
    }

    // EFFECTS: set the three textField's layout
    private void setLabelTextLayout() {
        incomeLabel.setLabelFor(incomeField);
        expenseLabel.setLabelFor(expenseField);
        balanceLabel.setLabelFor(balanceField);

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(incomeLabel);
        labelPane.add(expenseLabel);
        labelPane.add(balanceLabel);

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(incomeField);
        fieldPane.add(expenseField);
        fieldPane.add(balanceField);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        summary.add(labelPane, BorderLayout.CENTER);
        summary.add(fieldPane, BorderLayout.LINE_END);
    }

    // EFFECTS: create the text field for the
    private void createTextField() {
        incomeField = new JFormattedTextField(incomeFormat);
        incomeField.setValue(new Double(income));
        incomeField.setColumns(10);
        incomeField.addPropertyChangeListener("value", this);

        expenseField = new JFormattedTextField(expenseFormat);
        expenseField.setValue(new Double(expense));
        expenseField.setColumns(10);
        expenseField.setEditable(false);
        expenseField.addPropertyChangeListener("value", this);

        balanceField = new JFormattedTextField(balanceFormat);
        balanceField.setValue(new Double(balance));
        balanceField.setColumns(10);
        balanceField.setEditable(false);
        balanceField.setForeground(Color.red);
    }

    private void createLabels() {
        incomeLabel = new JLabel(incomeString);
        expenseLabel = new JLabel(expenseString);
        balanceLabel = new JLabel(balanceString);
    }

    private double computeBalance() {
        return spending.getBalance();
    }

    //Create and set up number formats. These objects also
    //parse numbers input by user.
    private void setUpFormats() {
        incomeFormat = NumberFormat.getNumberInstance();
        expenseFormat = NumberFormat.getNumberInstance();
        balanceFormat = NumberFormat.getNumberInstance();
    }


    /**
     * Called when a field's "value" property changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == incomeField) {
            income = ((Number) incomeField.getValue()).doubleValue();
            spending.setIncome(income);
        }

        double expense = spending.getExpense();
        expenseField.setValue(new Double(expense));

        double balance = spending.getBalance();
        balanceField.setValue(new Double(balance));
    }
}
