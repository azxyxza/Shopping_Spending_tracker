package ui;

import model.Home;
import model.ShoppingList;
import model.Spending;

import java.util.Scanner;

// This MainPage references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp

/**
 * This is the Main Page of the shopping-spending tracker
 */

public class MainPage {
    private Scanner input;
    private Home home;
    private ShoppingList shoppingList;
    private Spending spending;

    // EFFECTS: runs the main page
    public MainPage() {
        home = new Home();
        shoppingList = new ShoppingList();
        spending = new Spending();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
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
        System.out.println("\nYou can go to:");
        System.out.println("\th -> My home");
        System.out.println("\tl -> Shopping list");
        System.out.println("\ts -> Spending & Transaction");
        System.out.println("\tq -> quit");
    }

}
