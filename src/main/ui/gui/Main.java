package ui.gui;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static final int MAIN_TAB_INDEX = 0;
    public static final int HOME_TAB_INDEX = 1;
    public static final int ShoppingList_TAB_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 630;
    private JTabbedPane topBar;

    public static void main(String[] args) {
        new Main();
    }

    //MODIFIES: this
    //EFFECTS: creates MainPageUI, loads SmartHome appliances, displays topBars
    private Main() {
        super("Shopping-Spending tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadAppliances();

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

    private void loadTabs() {
        JPanel mainTab = new MainTab(this);
        JPanel homeTab = new HomeTab(this);
        JPanel shoppingTab = new ShoppingTab(this);

        topBar.add(mainTab, MAIN_TAB_INDEX);
        topBar.setTitleAt(MAIN_TAB_INDEX, "Main");
        topBar.add(homeTab, HOME_TAB_INDEX);
        topBar.setTitleAt(HOME_TAB_INDEX, "Home");
        topBar.add(shoppingTab, ShoppingList_TAB_INDEX);
        topBar.setTitleAt(ShoppingList_TAB_INDEX, "Shopping");

    }

    private void loadAppliances() {

    }
}
