package ui.gui;

import javax.swing.*;

public abstract class Tab extends JPanel {
    private final Main controller;

    //REQUIRES: SmartHomeUI controller that holds this tab
    public Tab(Main controller) {
        this.controller = controller;
    }
}
