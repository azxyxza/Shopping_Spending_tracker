package ui.gui;

import model.CategoryList;
import model.Home;
import model.ShoppingList;
import model.Spending;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Main page that initiate all information
 */
public class Main extends JFrame {
    public static final int MAIN_TAB_INDEX = 0;
    public static final int SHOPPING_TAB_INDEX = 1;
    public static final int TRANSACTION_TAB_INDEX = 2;
    public static final int SPENDING_TAB_INDEX = 3;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 630;
    private JTabbedPane topBar;

    protected Home home;
    protected ShoppingList shoppingList;
    protected Spending spending;
    protected CategoryList categoryList;

    protected JPanel mainTab;
    protected JPanel shoppingTab;
    protected JPanel transactionTab;
    protected JPanel spendingTab;



    // EFFECTS: init
    public static void main(String[] args) {
        new Main();
    }

    //MODIFIES: this
    //EFFECTS: creates MainPageUI, loads SmartHome appliances, displays topBars
    private Main() {
        super("Shopping-Spending tracker");
        home = new Home();
        shoppingList = new ShoppingList();
        spending = new Spending();
        categoryList = new ShoppingList();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        topBar = new JTabbedPane();
        topBar.setTabPlacement(JTabbedPane.TOP);

        loadTabs();
        add(topBar);

        //Display the window.
        pack();
        setResizable(true);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        getContentPane().setBackground(new Color(218, 185, 255, 255));
    }

    // EFFECTS: load the top three tabs for choices (switching between panel)
    public void loadTabs() {
        mainTab = new MainTab(this);
        shoppingTab = new ShoppingTab(this);
        transactionTab = new TransactionTab(this);
        spendingTab = new SpendingTab(this);

        topBar.add(mainTab, MAIN_TAB_INDEX);
        topBar.setTitleAt(MAIN_TAB_INDEX, "Main");

        topBar.add(shoppingTab, SHOPPING_TAB_INDEX);
        topBar.setTitleAt(SHOPPING_TAB_INDEX, "Shopping");

        topBar.add(transactionTab, TRANSACTION_TAB_INDEX);
        topBar.setTitleAt(TRANSACTION_TAB_INDEX, "Transaction");

        topBar.add(spendingTab, SPENDING_TAB_INDEX);
        topBar.setTitleAt(SPENDING_TAB_INDEX, "Spending");
    }


    // EFFECTS: load the shopping tab
    public void loadNewShopping() {
        if (shoppingTab != null) {
            topBar.remove(shoppingTab);
        }
        shoppingTab = new ShoppingTab(this);
        topBar.add(shoppingTab, SHOPPING_TAB_INDEX);
        topBar.setTitleAt(SHOPPING_TAB_INDEX, "Shopping");
    }

    // EFFECTS: load the new transaction tab
    public void loadNewTransaction() {
        if (transactionTab != null) {
            topBar.remove(transactionTab);
        }
        transactionTab = new TransactionTab(this);
        topBar.add(transactionTab, TRANSACTION_TAB_INDEX);
        topBar.setTitleAt(TRANSACTION_TAB_INDEX, "Transaction");

    }

    // EFFECTS: load the new spending tab
    public void loadNewSpending() {
        if (spendingTab != null) {
            topBar.remove(spendingTab);
        }
        spendingTab = new SpendingTab(this);
        topBar.add(spendingTab, SPENDING_TAB_INDEX);
        topBar.setTitleAt(SPENDING_TAB_INDEX, "Spending");
    }
}
