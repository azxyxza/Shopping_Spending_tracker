package ui.gui;

import model.Home;
import model.Item;
import model.ShoppingList;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionTab extends Tab implements ListSelectionListener, ActionListener {
    private Home home;
    private ShoppingList shoppingList;
    private JPanel topBar;
    private JButton refreshButton;
    private JScrollPane scrollPanel;

    private JMenuItem food;
    private JMenuItem fruitAndVegetables;
    private JMenuItem drinks;
    private JMenuItem necessities;
    private JMenuItem others;

    private DefaultListModel listModel;
    private JList list;
    private JPanel bottomPanel;
    private JButton addCostButton;


    public TransactionTab(Main controller, ShoppingList shoppingList, Home home) {
        super(controller);
        this.shoppingList = shoppingList;
        this.home = home;

        setLayout(new BorderLayout());
        createTopBar();
        createCentralPanel();
        createReset();
        createBottomPanel();
    }


    // EFFECTS: create a bottom panel that can edit the costs of each item
    private void createBottomPanel() {
        bottomPanel = new JPanel();
        addCostButton = new JButton("Add");
        addCostButton.addActionListener(e -> {

            JPanel panel = new JPanel(new GridLayout(1, 2));
            JTextField itemCost = new JFormattedTextField();

            panel.add(new JLabel("What is the price for this? "));
            panel.add(itemCost);

            int index = list.getSelectedIndex();
            Transaction t = shoppingList.getSpending().getTransactions().get(index);

            int result = createImageIcon(bottomPanel);
            if (result == JOptionPane.OK_OPTION) {
                double cost = Double.parseDouble(itemCost.getText());
                t.setExpense(cost);
            }
        });

        addCostButton.setBackground(new Color(122, 189, 194));
        add(bottomPanel, BorderLayout.PAGE_END);
    }


    // EFFECTS: create a central panel that display the transaction list
    private void createCentralPanel() {
        listModel = new DefaultListModel();

        JLabel notice = new JLabel("You haven't made any transactions yet ~", JLabel.CENTER);
        if (shoppingList.getSpending().getTransactions().isEmpty()) {
            notice.setForeground(new Color(108, 80, 241, 255));
            scrollPanel = new JScrollPane(notice);
        } else {
            notice.setVisible(false);
            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                Item i = t.getItem();
                listModel.addElement(i.getAmount() + " " + i.getName() + " bought at " + i.getDate()
                        + " -- Cost : " + t.getExpense());
            }
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        scrollPanel = new JScrollPane(list);
        add(scrollPanel, BorderLayout.CENTER);
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
        add(topBar, BorderLayout.NORTH);
    }

    // EFFECTS: create five submenu
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


    private void createReset() {
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
                createCentralPanel();
            }
        });
        topBar.add(refreshButton);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        if (source == food) {
            foodList(home.getFood());
        } else if (source == fruitAndVegetables) {
            foodList(home.getFruitAndVeg());
        } else if (source == drinks) {
            foodList(home.getDrinks());
        } else if (source == necessities) {
            foodList(home.getNecessities());
        } else if (source == others) {
            foodList(home.getOthers());
        }
        list = new JList(listModel);
        scrollPanel = new JScrollPane(list);
    }

    private void foodList(List<Item> itemList) {
        for (Item i : itemList) {
            double cost = 0;
            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                if (t.getItem().equals(i)) {
                    cost = t.getExpense();
                }
            }
            listModel.addElement(i.getAmount() + " " + i.getName() + " bought at " + i.getDate()
                    + " -- Cost : \n" + cost);
        }
    }


    // TODO
    private int createImageIcon(JPanel panel) {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("src/main/ui/gui/images/groceryIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newImage = myPicture.getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon img = new ImageIcon(newImage);
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Adding a new items to buy...", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, img);
        return result;
    }
}
