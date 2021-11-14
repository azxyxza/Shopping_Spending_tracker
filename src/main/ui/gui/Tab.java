package ui.gui;

import model.Home;
import model.ShoppingList;
import model.Spending;

import javax.swing.*;

/**
 * This is the abstract class for the top tab bar displayed on frame,
 * can be selected between main, shopping, spending, and transaction
 */
public abstract class Tab extends JPanel {
    protected Main controller;

    //REQUIRES: SmartHomeUI controller that holds this tab
    public Tab(Main controller) {
        this.controller = controller;
    }
}
