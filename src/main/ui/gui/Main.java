package ui.gui;

import model.Home;
import model.ShoppingList;
import model.Spending;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static final int MAIN_TAB_INDEX = 0;
    public static final int SHOPPING_TAB_INDEX = 1;
    public static final int SPENDING_TAB_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 630;
    private JTabbedPane topBar;

    private Home home;
    private ShoppingList shoppingList;
    private Spending spending;

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

    // load the top three tabs for choices (switching between panel)
    private void loadTabs() {
        JPanel mainTab = new MainTab(this, home, shoppingList, spending);
        JPanel shoppingTab = new ShoppingTab(this, shoppingList);
        JPanel spendingTab = new SpendingTab(this, shoppingList, spending);

        topBar.add(mainTab, MAIN_TAB_INDEX);
        topBar.setTitleAt(MAIN_TAB_INDEX, "Main");

        topBar.add(shoppingTab, SHOPPING_TAB_INDEX);
        topBar.setTitleAt(SHOPPING_TAB_INDEX, "Shopping");

        topBar.add(spendingTab, SPENDING_TAB_INDEX);
        topBar.setTitleAt(SPENDING_TAB_INDEX, "Spending");
    }

}
