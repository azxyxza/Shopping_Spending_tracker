package ui.gui;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * This is the main, the place to initiate the code
 */

public class TopBars extends JPanel {
    protected JPanel mainPanel;
    protected JPanel homePanel;
    protected JPanel shoppingListPanel;
    protected JPanel spendingPanel;


    public TopBars() {
        super(new GridLayout(1, 0));

        //A border that puts 10 extra pixels at the sides and
        //bottom of each pane.
        Border paneEdge = BorderFactory.createEmptyBorder(0, 10, 10, 10);

        // First pane: Main
        mainPanel = new JPanel();
        mainPanel.setBorder(paneEdge);
        mainPanel.setLayout(new BoxLayout(mainPanel,
                BoxLayout.Y_AXIS));
        //addComponent(mainPanel);

        // Second pane: Home
        homePanel = new JPanel();
        homePanel.setBorder(paneEdge);
        homePanel.setLayout(new BoxLayout(homePanel,
                BoxLayout.Y_AXIS));

        // Third pane: ShoppingList
        shoppingListPanel = new JPanel();
        shoppingListPanel.setBorder(paneEdge);
        shoppingListPanel.setLayout(new BoxLayout(shoppingListPanel,
                BoxLayout.Y_AXIS));

        // Fourth pane: Spending
        spendingPanel = new JPanel();
        spendingPanel.setBorder(paneEdge);
        spendingPanel.setLayout(new BoxLayout(spendingPanel,
                BoxLayout.Y_AXIS));

        addTabbedPane();

    }

//    private void addComponent(Container container) {
//        JPanel comp = new MainTab();
//        container.add(comp);
//
//    }

    public void addTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Main", null, mainPanel, null);
        tabbedPane.addTab("Home", null, homePanel, null);
        tabbedPane.addTab("Shopping", null, shoppingListPanel, null);
        tabbedPane.addTab("Spending", null, spendingPanel, null);
        tabbedPane.setSelectedIndex(0);
        add(tabbedPane);
    }
}
