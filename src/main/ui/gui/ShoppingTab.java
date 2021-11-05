package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingTab extends Tab implements ActionListener {
//    private JSplitPane splitPane;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel choicePanel;
    private JScrollPane listScrollPane;

    public ShoppingTab(Main controller) {
        super(controller);
        setLayout(new BorderLayout());

        topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(new Color(186, 224, 202, 207));

        initLeftSplit();
        initRightSplit();

        topPanel.add(choicePanel);
        topPanel.add(listScrollPane);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(122, 189, 194));
        createButton();

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    private void createButton() {
        JButton deleteButton = new JButton("Delete");
        JButton addButton = new JButton("Add");
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(deleteButton);
        bottomPanel.add(addButton);
    }

    private void initLeftSplit() {
        choicePanel = new JPanel();
        choicePanel.setBackground(Color.BLUE);
        choicePanel.setLayout(new GridLayout(4, 1));

        addTextField();
    }

    private void addTextField() {
        JTextField budget = new JTextField();
        JButton budgetPanel = new JButton();
        budgetPanel.add(budget);
//        budget.setPreferredSize(new Dimension(choicePanel.getWidth(), choicePanel.getHeight() / 4));
//        budget.setActionCommand("Budget");
//        budget.addActionListener(this);
        choicePanel.add(budgetPanel);

        JFormattedTextField numToBuy = new JFormattedTextField();
        JButton numToBuyPanel = new JButton();
        numToBuyPanel.add(numToBuy);
//        numToBuy.setActionCommand("Needs to buy");
//        numToBuy.addActionListener(this);
        choicePanel.add(numToBuyPanel);

        JFormattedTextField numBought = new JFormattedTextField();
//        numBought.setActionCommand("Have bought");
//        numBought.addActionListener(this);
        JButton numBoughtPanel = new JButton();
        numBoughtPanel.add(numBought);
        choicePanel.add(numBoughtPanel);

        JFormattedTextField time = new JFormattedTextField(
                java.util.Calendar.getInstance().getTime());
//        time.setActionCommand("Current date");
//        time.addActionListener(this);
        JButton timePanel = new JButton();
        timePanel.add(time);
        choicePanel.add(timePanel);
        //choicePanel.add(time);

        //addLabelToText(budget, numToBuy, numBought, time);
    }


    private void addLabelToText(JTextField budget, JFormattedTextField numToBuy,
                                JFormattedTextField numBought, JFormattedTextField time) {
        JLabel budgetLabel = new JLabel("Budget: ");
        budgetLabel.setLabelFor(budget);
        budgetLabel.setVisible(true);

        JLabel numToBuyLabel = new JLabel("Need to buy: ");
        numToBuyLabel.setLabelFor(numToBuy);

        JLabel numBoughtLabel = new JLabel("Have bought: ");
        numBoughtLabel.setLabelFor(numBought);

        JLabel timeLabel = new JLabel("Today is: ");
        timeLabel.setLabelFor(time);

//        GridBagLayout gridbag = new GridBagLayout();
//        GridBagConstraints c = new GridBagConstraints();

//        JLabel[] labels = {budgetLabel, numToBuyLabel, numBoughtLabel, timeLabel};
//        JTextField[] textFields = {budget, numToBuy, numBought, time};
//        addLabelTextRows(labels, textFields, gridbag, choicePanel);
//        c.gridwidth = GridBagConstraints.REMAINDER; //last
//        c.anchor = GridBagConstraints.WEST;
//        c.weightx = 1.0;
        choicePanel.add(budget);
        choicePanel.add(numToBuy);
        choicePanel.add(numBought);
        choicePanel.add(time);


    }

//    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields,
//                                  GridBagLayout gridbag, Container choicePanel) {
//        GridBagConstraints c = new GridBagConstraints();
//        c.anchor = GridBagConstraints.EAST;
//        int numLabels = labels.length;
//
//        for (int i = 0; i < numLabels; i++) {
//            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
//            c.fill = GridBagConstraints.NONE;      //reset to default
//            c.weightx = 0.0;                       //reset to default
//            choicePanel.add(labels[i], c);
//
//            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 1.0;
//            choicePanel.add(textFields[i], c);
//        }
//    }

    private void initRightSplit() {
        listScrollPane = new JScrollPane();
        choicePanel.setLayout(new GridLayout(1, 3));
    }

//    private void createSplitPane() {
//        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//                choicePanel, listScrollPane);
//        splitPane.setOneTouchExpandable(true);
//        splitPane.setDividerLocation(200);
//
//        //Provide minimum sizes for the two components in the split pane.
//        Dimension minimumSize = new Dimension(100, 50);
//        choicePanel.setMinimumSize(minimumSize);
//        listScrollPane.setMinimumSize(minimumSize);
//
//        //Provide a preferred size for the split pane.
//        splitPane.setPreferredSize(new Dimension(500, 500));
//        topPanel.add(splitPane);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
