package ui.gui;

import model.Home;
import model.ShoppingList;
import model.Spending;
import model.exception.AvoidDuplicateException;
import persistence.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**This is the main tab that display the welcome message,save and load, and also the logo*/
public class MainTab extends Tab {
    private static final String JSON_STORE_HOME = "./data/HomeHistory.json";
    private static final String JSON_STORE_SHOPPING = "./data/ShoppingHistory.json";
    private static final String JSON_STORE_SPENDING = "./data/SpendingHistory.json";
    private JsonHomeWriter jsonHomeWriter;
    private JsonShoppingWriter jsonShoppingWriter;
    private JsonSpendingWriter jsonSpendingWriter;
    private JsonHomeReader jsonHomeReader;
    private JsonShoppingReader jsonShoppingReader;
    private JsonSpendingReader jsonSpendingReader;


    // EFFECTS: the tab divide the panel into three parts, top is welcome text,
    //          middle is the app's logo, bottom is the two buttons
    public MainTab(Main controller) {
        super(controller);
        jsonHomeWriter = new JsonHomeWriter(JSON_STORE_HOME);
        jsonShoppingWriter = new JsonShoppingWriter(JSON_STORE_SHOPPING);
        jsonSpendingWriter = new JsonSpendingWriter(JSON_STORE_SPENDING);
        jsonHomeReader = new JsonHomeReader(JSON_STORE_HOME);
        jsonShoppingReader = new JsonShoppingReader(JSON_STORE_SHOPPING);
        jsonSpendingReader = new JsonSpendingReader(JSON_STORE_SPENDING);

        setLayout(new GridLayout(3, 1));

        welcomeText();
        logoPanel();
        saveAndLoad();
    }

    /**Top panel*/
    // EFFECTS: show the welcome message at the top first panel
    private void welcomeText() {
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JLabel greeting = new JLabel(
                "Welcome\n"
                        + "to your personal shopping and financial tracker!",
                JLabel.CENTER);
        JLabel subTitle = new JLabel(
                "Shop smart, spend wise!",
                JLabel.CENTER);
        greeting.setFont(new Font("Verdana", Font.BOLD, 16));
        greeting.setForeground(new Color(108, 80, 241, 255));
        subTitle.setFont(new Font("Verdana", Font.ITALIC, 14));

        topPanel.add(greeting);
        topPanel.add(subTitle);
        this.add(topPanel);
    }



    /**Middle panel*/
    // EFFECTS: create a logo panel that display the app's image logo
    private void logoPanel() {
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/ui/gui/images/logo.png"));
            Image newImage = myPicture.getScaledInstance(130,
                    130, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(newImage));
            add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**Bottom panel*/
    // EFFECTS: create two buttons that do the Json save & load
    private void saveAndLoad() {
        JButton saveButton = new JButton("Save current data");
        JLabel saveLabel = new JLabel("Save the data successfully", SwingConstants.CENTER);
        saveLabel.setVisible(false);
        JButton loadButton = new JButton("Load previous data");
        JLabel loadLabel = new JLabel("Loaded previous data successfully", SwingConstants.CENTER);
        loadLabel.setVisible(false);

        JPanel buttonRow = new JPanel(new GridLayout(2, 2));
        buttonRow.add(saveButton);
        buttonRow.add(loadButton);
        buttonRow.add(saveLabel);
        buttonRow.add(loadLabel);

        buttonRow.setSize(WIDTH, HEIGHT / 3);

        saveButton.setFocusable(false);
        loadButton.setFocusable(false);

        saveActionListener(saveButton, saveLabel);
        loadActionListener(loadButton, loadLabel);

        this.add(buttonRow);
    }

    /**
     * do the load action
     */
    private void loadActionListener(JButton loadButton, JLabel loadLabel) { // TODO: can't load info successfully
        loadButton.addActionListener(e -> {
            if (e.getSource() == loadButton) {
                try {
                    controller.home = jsonHomeReader.read();
                    controller.shoppingList = jsonShoppingReader.read();
                    controller.spending = jsonSpendingReader.read();


                    // call "updateTabs" method in controller, which calls spendingTab.refresh()..., etc.
//                    this.controller.loadTabs();
                    this.controller.loadNewShopping();
                    this.controller.loadNewSpending();
                    this.controller.loadNewTransaction();
                    loadLabel.setVisible(true);
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
    private void saveActionListener(JButton saveButton, JLabel saveLabel) {
        saveButton.addActionListener(e -> {
            if (e.getSource() == saveButton) {
                saveHome();
                saveShopping();
                saveSpending();
                saveLabel.setVisible(true);
            }
        });
    }


    // EFFECTS: save the spending into file
    private void saveSpending() {
        try {
            jsonSpendingWriter.open();
            jsonSpendingWriter.write(controller.spending);
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
            jsonShoppingWriter.write(controller.shoppingList);
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
            jsonHomeWriter.write(controller.home);
            jsonHomeWriter.close();
            System.out.println("Saved current data to " + JSON_STORE_HOME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_HOME);
        }
    }






}
