package ui;

import java.util.Scanner;

public class MainPage {
    private Scanner input;

    // EFFECTS: runs the main page
    public MainPage() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runTracker();
    }

    private void runTracker() {
        while (true) {
            displayMenu();
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                System.out.println("Goodbye! Have a nice day!");;
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
                new HomeApp();
                break;
            case "l":
                new ShoppingListApp();
                break;
            case "t":
                new TransactionApp();
                break;
            case "s":
                new SpendingApp();
                break;
            case "q":
                return;
            default:
                System.out.println("Not valid input...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n\n---Welcome to your personal shopping and financial tracker---");
        System.out.println("-------------------Shop smart, spend wise--------------------");
        System.out.println("\nYou can go to:");
        System.out.println("\th -> My home");
        System.out.println("\tl -> Shopping list");
        System.out.println("\tt -> Transaction");
        System.out.println("\ts -> Spending");
        System.out.println("\tq -> quit");
    }

}
