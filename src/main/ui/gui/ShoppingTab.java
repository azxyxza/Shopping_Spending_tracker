package ui.gui;

import model.Categories;
import model.Item;
import model.exception.AvoidDuplicateException;
import model.exception.NotInTheListException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;

/**
 * This is the shopping tab that display the shopping list
 * Users can add new items, delete items, mark items as bought in this tab
 */
public class ShoppingTab extends Tab
        implements PropertyChangeListener, ListSelectionListener {
    private JPanel topPanel;
    private JFormattedTextField budgetField;
    private NumberFormat budgetFormat;

    private JPanel centerPanel;
    private JScrollPane toBuyPanel;
    private DefaultListModel listModel;
    private JList list;

    private JButton refreshButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton boughtButton;


    String[] categories = {"Food", "Fruit And Vegetables", "Drinks", "Necessities", "Others"};


    // EFFECTS: create a new shopping tab with the top panel, center panel and bottom panel initiate
    public ShoppingTab(Main controller) {
        super(controller);
        setLayout(new BorderLayout());

        topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(new Color(122, 189, 194));
        createBudget();
        createRefreshButton();

        createPaneList();
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        centerPanel.setBackground(new Color(181, 241, 227, 207));

        createAddButton();

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: create a button to redisplay the shopping list
    private void createRefreshButton() {
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
//                controller.loadNewShopping();
                this.repaint();
            }
        });
        topPanel.add(refreshButton);
    }


    // EFFECTS: when click on the add button, show a new dialogue window to allow inputs
    private void createAddButton() {
        addButton = new JButton("Add");
        createImageIconForAdd();
        addButton.addActionListener(e -> {
            JTextField itemName = new JTextField();
            JTextField itemAmount = new JFormattedTextField("1");
            JComboBox categoryList = new JComboBox(categories);
            JPanel panel = new JPanel(new GridLayout(3, 2));

            panel.add(new JLabel("Item's name: "));
            panel.add(itemName);
            panel.add(new JLabel("Item's amount: "));
            panel.add(itemAmount);
            panel.add(new JLabel("Choose a Category: "));
            panel.add(categoryList);

            processCommand(e, panel, itemName, itemAmount, categoryList);
        });
        addButton.setBackground(new Color(122, 189, 194));
        addButton.setFont(new Font("Verdana", Font.BOLD, 16));
    }

    // EFFECTS: create an image icon for add button
    private void createImageIconForAdd() {
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/ui/gui/images/addIcon.png"));
            Image newImage = myPicture.getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            addButton.setIcon(new ImageIcon(newImage));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    // EFFECTS: when click on the add button, process to add items to toBuy list
    private void processCommand(ActionEvent e, JPanel panel, JTextField itemName,
                                JTextField itemAmount, JComboBox categoryList) {
        if (e.getSource() == addButton) {
            int result = createImageIcon(panel);
            if (result == JOptionPane.OK_OPTION) {
                String name = itemName.getText();
                int amount = Integer.parseInt(itemAmount.getText());
                String category = (String) categoryList.getSelectedItem();
                Categories type = controller.shoppingList.convertToCategory(category);
                Item newItem = new Item(name, amount, type, LocalDate.now());
                try {
                    controller.shoppingList.addItem(newItem);
                } catch (AvoidDuplicateException avoidDuplicateException) {
                    checkException(name);
                }
            }

            listModel.addElement(itemAmount.getText() + "  " + itemName.getText());
            deleteButton.setEnabled(true);
            boughtButton.setEnabled(true);
        }
    }


    // EFFECTS: create an image icon displayed in the left of the popup dialog
    private int createImageIcon(JPanel panel)  {
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


    // EFFECTS: check the exception for adding items to shopping list
    private void checkException(String name) {
        if (name == null | name.isEmpty() | name.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "You haven't enter the name for the item!",
                    "Oops...",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "You already added the item to shopping list!",
                    "Oops...",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // EFFECTS: have multiple panel as list displayed as the To buy list
    private void createPaneList() {
        centerPanel = new JPanel(new BorderLayout());
        JPanel selection = createSelectionBar();
        toBuyPanel = createToBuyList();

        toBuyPanel.setVisible(true);
        centerPanel.add(selection, BorderLayout.PAGE_START);
        centerPanel.add(toBuyPanel, BorderLayout.CENTER);
    }

    // EFFECTS: create list of items that needed to buy in the shopping list
    private JScrollPane createToBuyList() {
        listModel = new DefaultListModel();
        for (Item i : controller.shoppingList.getToBuy()) {
            listModel.addElement(i.getAmount() + "  " + i.getName());
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane toBuyPanel = new JScrollPane(list);

        toBuyPanel.setBackground(new Color(248, 255, 194, 210));
        return toBuyPanel;
    }


    // EFFECTS: create the example bar that display the basic element of list
    private JPanel createSelectionBar() {
        JPanel example = new JPanel(new GridLayout(1, 2));
        createBoughtButton();
        createDeleteButton();
        example.add(boughtButton);
        example.add(deleteButton);
        centerPanel.add(example, BorderLayout.PAGE_START);
        return example;
    }

    // EFFECTS: do delete from the shopping list
    private void createDeleteButton() {
        deleteButton = new JButton("Delete");
        if (controller.shoppingList.getToBuy().isEmpty()) {
            deleteButton.setEnabled(false);
        }
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                listModel.remove(index);
                Item i = controller.shoppingList.getToBuy().get(index);
                try {
                    controller.shoppingList.deleteItem(i);
                } catch (NotInTheListException notInTheListException) {
                    notInTheListException.printStackTrace();
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);

                if (controller.shoppingList.getToBuy().isEmpty()) {
                    deleteButton.setEnabled(false);
                }
            }
        });
    }

    // EFFECTS: create a bought button that delete items from shopping list
    private void createBoughtButton() {
        boughtButton = new JButton("Bought");
        if (controller.shoppingList.getToBuy().isEmpty()) {
            boughtButton.setEnabled(false);
        }
        boughtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                listModel.remove(index);
                Item i = controller.shoppingList.getToBuy().get(index);
                if (e.getSource() == boughtButton) {
                    try {
                        controller.shoppingList.markItem(i);
                    } catch (NotInTheListException notInTheListException) {
                        notInTheListException.printStackTrace();
                    }
                }
                if (controller.shoppingList.getToBuy().isEmpty()) {
                    boughtButton.setEnabled(false);
                }
            }
        });
    }


    /**
     * have a budget text at the top of the page
     */
    private void createBudget() {
        //Create the labels.
        String budgetString = "Set the budget: ";
        JLabel budgetLabel = new JLabel(budgetString);
        //Create the text fields and set them up.
        budgetField = new JFormattedTextField(budgetFormat);
        budgetField.setValue(controller.shoppingList.getBudget());
        budgetField.setColumns(10);
        budgetField.addPropertyChangeListener("value", this);

        //Tell accessibility tools about label/textfield pairs.
        budgetLabel.setLabelFor(budgetField);
        budgetFormat = NumberFormat.getNumberInstance();

        //Lay out the labels in a panel.
        JPanel budgetPane = new JPanel();
        budgetPane.setBackground(new Color(122, 189, 194));
        budgetPane.add(budgetLabel);
        budgetPane.add(budgetField);
        topPanel.add(budgetPane);
    }


    @Override
    // EFFECTS: when enter new text into budgetField, change the budget to new value
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        double newBudget;
        if (source == budgetField) {
            newBudget = ((Number) budgetField.getValue()).doubleValue();
            controller.shoppingList.setBudget(newBudget);
        }
    }


    @Override
    // EFFECTS: if there is no item selected, disable the button
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        if (list.getSelectedIndex() == -1) {
            //No selection, disable fire button.
            deleteButton.setEnabled(false);
            boughtButton.setEnabled(false);
        } else {
            //Selection, enable the fire button.
            deleteButton.setEnabled(true);
            boughtButton.setEnabled(true);
        }
    }

}










