package ui.gui;

import model.Home;
import model.ShoppingList;
import model.Spending;
import model.exception.AvoidDuplicateException;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainTab extends Tab {
    private static final String JSON_STORE_HOME = "./data/HomeHistory.json";
    private static final String JSON_STORE_SHOPPING = "./data/ShoppingHistory.json";
    private static final String JSON_STORE_SPENDING = "./data/SpendingHistory.json";
    private Home home;
    private ShoppingList shoppingList;
    private Spending spending;
    private JsonHomeWriter jsonHomeWriter;
    private JsonShoppingWriter jsonShoppingWriter;
    private JsonSpendingWriter jsonSpendingWriter;
    private JsonHomeReader jsonHomeReader;
    private JsonShoppingReader jsonShoppingReader;
    private JsonSpendingReader jsonSpendingReader;
    private static final String INIT_GREETING = "Welcome to your personal shopping and financial tracker!";
    private JLabel greeting;


    public MainTab(Main controller, Home home, ShoppingList shoppingList, Spending spending) {
        super(controller);
        this.home = home;
        this.shoppingList = shoppingList;
        this.spending = spending;
        jsonHomeWriter = new JsonHomeWriter(JSON_STORE_HOME);
        jsonShoppingWriter = new JsonShoppingWriter(JSON_STORE_SHOPPING);
        jsonSpendingWriter = new JsonSpendingWriter(JSON_STORE_SPENDING);
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
        icon = createImageIcon("images/middle.gif");
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setBackground(Color.white);
        label.setPreferredSize(new Dimension(64, 64));
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

    // do the Json save & load
    private void saveAndLoad() {
        JButton saveButton = new JButton("Save current data");
        JButton loadButton = new JButton("Load previous data");

        JPanel buttonRow = formatButtonRow(saveButton);
        buttonRow.add(loadButton);
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        saveButton.setFocusable(false);
        loadButton.setFocusable(false);

        saveActionListener(saveButton);
        loadActionListener(loadButton);

        this.add(buttonRow);
    }

    /**
     * do the load action
     */
    private void loadActionListener(JButton loadButton) {
        loadButton.addActionListener(e -> {
            if (e.getSource() == loadButton) {
                try {
                    home = jsonHomeReader.read();
                    shoppingList = jsonShoppingReader.read();
                    spending = jsonSpendingReader.read();
                    System.out.println("Loaded previous data successfully");
                } catch (IOException | AvoidDuplicateException exp) {
                    System.out.println("Unable to read from file.");
                }
            }
        });
    }

    /**
     * do the save action
     */
    private void saveActionListener(JButton saveButton) {
        saveButton.addActionListener(e -> {
            if (e.getSource() == saveButton) {
                saveHome();
                saveShopping();
                saveSpending();
            }
        });
    }


    // EFFECTS: save the spending into file
    private void saveSpending() {
        try {
            jsonSpendingWriter.open();
            jsonSpendingWriter.write(spending);
            jsonSpendingWriter.close();
            System.out.println("Saved current data to " + JSON_STORE_SPENDING);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_SPENDING);
        }
    }

    // EFFECTS: save the shopping into file
    private void saveShopping() {
        try {
            jsonShoppingWriter.open();
            jsonShoppingWriter.write(shoppingList);
            jsonShoppingWriter.close();
            System.out.println("Saved current data to " + JSON_STORE_SHOPPING);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_SHOPPING);
        }
    }

    // EFFECTS: save the home into file
    private void saveHome() {
        try {
            jsonHomeWriter.open();
            jsonHomeWriter.write(home);
            jsonHomeWriter.close();
            System.out.println("Saved current data to " + JSON_STORE_HOME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_HOME);
        }
    }


    // show the welcome message at the top first panel
    private void welcomeText() {
        greeting = new JLabel(
                INIT_GREETING,
                JLabel.CENTER
        );
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }



}
