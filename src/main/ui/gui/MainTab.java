package ui.gui;

import model.Home;
import model.ShoppingList;
import model.Spending;
import persistence.JsonHomeReader;
import persistence.JsonShoppingReader;
import persistence.JsonSpendingReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainTab extends Tab {
    private static final String JSON_STORE_HOME = "./data/HomeHistory.json";
    private static final String JSON_STORE_SHOPPING = "./data/ShoppingHistory.json";
    private static final String JSON_STORE_SPENDING = "./data/SpendingHistory.json";
    private Home home;
    private ShoppingList shoppingList;
    private Spending spending;
    private JsonHomeReader jsonHomeReader;
    private JsonShoppingReader jsonShoppingReader;
    private JsonSpendingReader jsonSpendingReader;
    private static final String INIT_GREETING = "Welcome to your personal shopping and financial tracker!";
    private JLabel greeting;


    public MainTab(Main controller) {
        super(controller);
        home = new Home();
        shoppingList = new ShoppingList();
        spending = new Spending();
        jsonHomeReader = new JsonHomeReader(JSON_STORE_HOME);
        jsonShoppingReader = new JsonShoppingReader(JSON_STORE_SHOPPING);
        jsonSpendingReader = new JsonSpendingReader(JSON_STORE_SPENDING);

        setLayout(new GridLayout(3, 1));

        welcomeText();
        saveAndLoad();
        picturePanel();

    }

    private void picturePanel() { //TODO
        ImageIcon icon;
        icon = createImageIcon("logo.png");
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setBackground(Color.white);
        label.setVisible(true);
        this.add(label);
    }

    private ImageIcon createImageIcon(String s) {
        java.net.URL imgURL = getClass().getResource(s);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + s);
            return null;
        }
    }

    private void saveAndLoad() {
        JButton saveButton = new JButton("Save current data");
        JButton loadButton = new JButton("Load previous data");

        JPanel buttonRow = formatButtonRow(saveButton);
        buttonRow.add(loadButton);
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        saveButton.setFocusable(false);
        loadButton.setFocusable(false);

//        saveButton.addActionListener();
//        loadButton.addActionListener();

        this.add(buttonRow);
    }

    private void welcomeText() {
        greeting = new JLabel(
                INIT_GREETING,
                JLabel.CENTER
        );
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }


//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == loadButton) {
//            try {
//                home = jsonHomeReader.read();
//                shoppingList = jsonShoppingReader.read();
//                spending = jsonSpendingReader.read();
//                System.out.println("Loaded previous data successfully");
//            } catch (IOException | AvoidDuplicateException exp) {
//                System.out.println("Unable to read from file.");
//            }
//        }
//        loadButton.setEnabled(false);
//
//    }
}
