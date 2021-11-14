package ui.gui;


import model.ShoppingList;
import model.Spending;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * This is the spending panel that display the spending summary and transaction list
 */
public class SpendingTab extends Tab implements PropertyChangeListener {
    private Spending spending;
    private ShoppingList shoppingList;
    private JPanel panel;
    private JPanel summary;

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
    public SpendingTab(Main controller) {
        super(controller);
        setUpFormats();
        panel = new JPanel(new GridLayout(2, 1));
        summary = new JPanel();

        this.shoppingList = controller.shoppingList;
        this.spending = controller.spending;

        // top summary panel
        spending.trackExpense(shoppingList.getSpending().getTransactions());
        income = spending.getIncome();
        expense = spending.getExpense();
        balance = spending.getBalance();
        balance = computeBalance();
        createLabels();
        createTextField();
        setLabelTextLayout();

        panel.add(summary);
        logoPanel();
        add(panel);
    }

    /**
     * Bottom panel
     */
    // EFFECTS: create a logo panel that display the app's image logo
    private void logoPanel() {
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/ui/gui/images/moneyIcon.png"));
            Image newImage = myPicture.getScaledInstance(150,
                    150, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(newImage));
            panel.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: set the three textField's layout
    private void setLabelTextLayout() {
        summary.setLayout(new GridLayout(5, 1));

        incomeLabel.setLabelFor(incomeField);
        expenseLabel.setLabelFor(expenseField);
        balanceLabel.setLabelFor(balanceField);

        //Lay out the labels in a panel.
        JPanel incomePanel = new JPanel(new GridLayout(0, 2));
        incomePanel.add(incomeLabel);
        incomePanel.add(incomeField);

        JPanel expensePanel = new JPanel(new GridLayout(0, 2));
        expensePanel.add(expenseLabel);
        expensePanel.add(expenseField);

        JPanel balancePanel = new JPanel(new GridLayout(0, 2));
        balancePanel.add(balanceLabel);
        balancePanel.add(balanceField);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        summary.add(incomePanel);
        summary.add(expensePanel);
        summary.add(balancePanel);
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

    // EFFECTS: create the labels for three textFields
    private void createLabels() {
        incomeLabel = new JLabel(incomeString);
        expenseLabel = new JLabel(expenseString);
        balanceLabel = new JLabel(balanceString);
    }

    // EFFECTS: compute the balance
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


    // TODO: refreshTab() method
    public void refreshTab() {
        controller.loadNewSpending();
    }
}
