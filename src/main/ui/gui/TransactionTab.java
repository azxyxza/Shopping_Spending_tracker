package ui.gui;

import model.Item;
import model.Transaction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This is the transaction tab that display the bought items
 */
public class TransactionTab extends Tab implements ListSelectionListener, ActionListener {
    private JPanel topBar;
    private JMenuItem food;
    private JMenuItem fruitAndVegetables;
    private JMenuItem drinks;
    private JMenuItem necessities;
    private JMenuItem others;
    private JButton refreshButton;

    private DefaultListModel listModel;
    private JList list;
    private JScrollPane scrollPanel;

    private JButton addCostButton;

    // EFFECTS: construct a transaction tab
    public TransactionTab(Main controller) {
        super(controller);
        setLayout(new BorderLayout());
        createTopBar();
        createCentralPanel(controller.shoppingList.getSpending().getTransactions());
        refresh();
        createBottomPanel();

        add(topBar, BorderLayout.PAGE_START);
        add(scrollPanel, BorderLayout.CENTER);
        add(addCostButton, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: create a bottom panel that can edit the costs of each item
    private void createBottomPanel() {
        addCostButton = new JButton("Edit");
        if (controller.shoppingList.getSpending().getTransactions().isEmpty()) {
            addCostButton.setEnabled(false);
        }
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/ui/gui/images/moneyAddIcon.png"));
            Image newImage = myPicture.getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            addCostButton.setIcon(new ImageIcon(newImage));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        addCostButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(1, 2));
            JTextField itemCost = new JFormattedTextField();

            panel.add(new JLabel("What is the price for this? "));
            panel.add(itemCost);

            processCommand(e, panel, itemCost);
            controller.loadNewTransaction();
            controller.loadNewSpending();
        });

        addCostButton.setBackground(new Color(122, 189, 194));

    }

    //MODIFIES: this
    //EFFECTS: edit the item's cost in the transaction list
    private void processCommand(ActionEvent e, JPanel panel, JTextField itemCost) {
        if (e.getSource() == addCostButton) {
            int result = createImageIcon(panel);
            if (result == JOptionPane.OK_OPTION) {
                double amount = Double.parseDouble(itemCost.getText());
                int index = list.getSelectedIndex();
                listModel.remove(index);
                Item newItem = controller.shoppingList.getSpending().getTransactions().get(index).getItem();
                Transaction newTransaction = new Transaction(newItem, amount);

                controller.shoppingList.getSpending().getTransactions().remove(index);
                controller.shoppingList.getSpending().getTransactions().add(newTransaction);
            }
        }
    }


    // EFFECTS: create a central panel that display the transaction list
    private void createCentralPanel(List<Transaction> transactions) {
        listModel = new DefaultListModel();
        scrollPanel = new JScrollPane();
        JLabel notice = new JLabel("You haven't made any transactions yet ~", JLabel.CENTER);
        notice.setFont(new Font("Verdana", Font.BOLD, 16));
        if (transactions.isEmpty()) {
            notice.setForeground(new Color(108, 80, 241, 255));
            scrollPanel.setViewportView(notice);

        } else {
            notice.setVisible(false);
            for (Transaction t : transactions) {
                Item i = t.getItem();
                listModel.addElement(i.getAmount() + " " + i.getName() + " bought at " + i.getDate()
                        + " -- Cost : " + t.getExpense());
            }
//            for (Transaction t : controller.shoppingList.getSpending().getTransactions()) {
//                Item i = t.getItem();
//                listModel.addElement(i.getAmount() + " " + i.getName() + " bought at " + i.getDate()
//                        + " -- Cost : " + t.getExpense());
//            }

            list = new JList(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);
            list.addListSelectionListener(this);
            list.setVisibleRowCount(5);
            scrollPanel.setViewportView(list);
        }
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }


    // EFFECTS: create a top bar displaying the date and popupMenu
    private void createTopBar() {
        topBar = new JPanel();
        topBar.setLayout(new GridLayout(1, 3));
        DateFormat df = new SimpleDateFormat();
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        JButton dateButton = new JButton(todayAsString);

        JButton sortButton = new JButton("View by Categories");
        JPopupMenu menu = new JPopupMenu();

        createSubMenu();

        menu.add(food);
        menu.add(fruitAndVegetables);
        menu.add(drinks);
        menu.add(necessities);
        menu.add(others);

        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                menu.show(sortButton, sortButton.getWidth() / 2, sortButton.getHeight() / 2);
            }
        });

        topBar.add(dateButton);
        topBar.add(sortButton);
    }


    /**
     * create five submenu
     */
    private void createSubMenu() {
        food = new JMenuItem("Food");
        food.setMnemonic(KeyEvent.VK_F);
        food.addActionListener(this);

        fruitAndVegetables = new JMenuItem("Fruit And Vegetables");
        fruitAndVegetables.setMnemonic(KeyEvent.VK_V);
        fruitAndVegetables.addActionListener(this);

        drinks = new JMenuItem("Drinks");
        drinks.setMnemonic(KeyEvent.VK_D);
        drinks.addActionListener(this);

        necessities = new JMenuItem("Necessities");
        necessities.setMnemonic(KeyEvent.VK_N);
        necessities.addActionListener(this);

        others = new JMenuItem("Others");
        others.setMnemonic(KeyEvent.VK_O);
        others.addActionListener(this);
    }


    // EFFECTS: create the reset button that redisplay the central panel of transaction list
    private void refresh() {
        refreshButton = new JButton("Refresh");
        try {
            Image img = ImageIO.read(getClass().getResource("images/refreshIcon.png"));
            Image newImage = img.getScaledInstance(20,
                    20, Image.SCALE_DEFAULT);
            refreshButton.setIcon(new ImageIcon(newImage));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        refreshButton.addActionListener(e -> {
            if (e.getSource() == refreshButton) {
                controller.loadNewTransaction();
            }
        });
        topBar.add(refreshButton);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

//    // EFFECTS: choosing certain menu will display certain list in central panel
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        JMenuItem source = (JMenuItem) (e.getSource());
//        if (source == food) {
//            listDisplay(controller.shoppingList.getFood());
//        } else if (source == fruitAndVegetables) {
//            listDisplay(controller.shoppingList.getFruitAndVeg());
//        } else if (source == drinks) {
//            listDisplay(controller.shoppingList.getDrinks());
//        } else if (source == necessities) {
//            listDisplay(controller.shoppingList.getNecessities());
//        } else if (source == others) {
//            listDisplay(controller.shoppingList.getOthers());
//        }
//    }
//
//    // EFFECTS: display the transaction list with the item's name and cost
//    private void listDisplay(List<Item> itemList) {
//        List<Transaction> transactions = new ArrayList<>();
//        for (Item i : itemList) {
//            double cost = 0;
//            for (Transaction t : controller.shoppingList.getSpending().getTransactions()) {
//                if (t.getItem().equals(i)) {
////                    cost = t.getExpense();
//                    transactions.add(t);
//                }
//            }
////            DefaultListModel listModel = new DefaultListModel();
////            listModel.addElement(i.getAmount() + "   " + i.getName() + "   bought at   " + i.getDate()
////                    + " --- Cost : \n" + cost);
//        }
//        createCentralPanel(transactions);
//    }


    // EFFECT: create the popup dialogue panel that asks to add costs for item
    private int createImageIcon(JPanel panel) {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("src/main/ui/gui/images/moneyDisplay.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newImage = myPicture.getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon img = new ImageIcon(newImage);
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Adding this item's cost...", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, img);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}



