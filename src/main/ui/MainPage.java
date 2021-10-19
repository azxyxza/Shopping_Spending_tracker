package ui;

import model.Home;
import model.ShoppingList;
import model.Spending;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// This MainPage references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp

/**
 * This is the Main Page of the shopping-spending tracker
 */

public class MainPage {
    private static final String JSON_STORE_HOME = "./data/HomeHistory.json";
    private static final String JSON_STORE_SHOPPING = "./data/ShoppingHistory.json";
    private static final String JSON_STORE_SPENDING = "./data/SpendingHistory.json";
    private Scanner input;
    private Home home;
    private ShoppingList shoppingList;
    private Spending spending;
    private JsonHomeWriter jsonHomeWriter;
    private JsonShoppingWriter jsonShoppingWriter;
    private JsonSpendingWriter jsonSpendingWriter;
    private JsonHomeReader jsonHomeReader;
    private JsonShoppingReader jsonShoppingReader;
    private JsonSpendingReader jsonSpendingReader;


    // EFFECTS: runs the main page
    public MainPage() {
        home = new Home();
        shoppingList = new ShoppingList();
        spending = new Spending();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonHomeWriter = new JsonHomeWriter(JSON_STORE_HOME);
        jsonShoppingWriter = new JsonShoppingWriter(JSON_STORE_SHOPPING);
        jsonSpendingWriter = new JsonSpendingWriter(JSON_STORE_SPENDING);
        jsonHomeReader = new JsonHomeReader(JSON_STORE_HOME);
        jsonShoppingReader = new JsonShoppingReader(JSON_STORE_SHOPPING);
        jsonSpendingReader = new JsonSpendingReader(JSON_STORE_SPENDING);
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean running = true;
        while (running) {
            displayMenu();
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                saveOrLeave();
                running = false;
                System.out.println("Goodbye! Have a nice day!");
            } else {
                processCommand(command);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "p":
                loadPrevious();
                break;
            case "h":
                new HomeApp(home);
                break;
            case "l":
                new ShoppingListApp(shoppingList, home);
                break;
            case "s":
                new SpendingApp(spending, shoppingList);
                break;
            default:
                System.out.println("Not valid input...");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n\n=======Welcome to your personal shopping and financial tracker=======");
        System.out.println("-----------------------Shop smart, spend wise-----------------------");
        System.out.println("\nYou can :");
        System.out.println("\tp -> Load previous information");
        System.out.println("\th -> My home");
        System.out.println("\tl -> Shopping list");
        System.out.println("\ts -> Spending & Transaction");
        System.out.println("\tq -> quit");
    }

    private void saveOrLeave() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nBefore leaving, do you want to SAVE your data?");
        System.out.println("\ty -> Save and Leave!");
        System.out.println("\tn -> Nah, leave anyway.");
        String choice = input.nextLine();

        if (choice.equals("y")) {
            saveHome();
            saveShopping();
            saveSpending();
        }
    }

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


    private void loadPrevious() {
        try {
            home = jsonHomeReader.read();
            shoppingList = jsonShoppingReader.read();
            spending = jsonSpendingReader.read();
            System.out.println("Loaded previous data successfully");
        } catch (IOException e) {
            System.out.println("Unable to read from file.");
        }
    }

}


