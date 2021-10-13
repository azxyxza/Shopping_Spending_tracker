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
                new HomeApp();
                break;
            case "l":
                new ShoppingListApp();
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
        System.out.println("\n\n=======Welcome to your personal shopping and financial tracker=======");
        System.out.println("-----------------------Shop smart, spend wise-----------------------");
        System.out.println("\nYou can go to:");
        System.out.println("\th -> My home");
        System.out.println("\tl -> Shopping list");
        System.out.println("\ts -> Spending & Transaction");
        System.out.println("\tq -> quit");
    }

}
