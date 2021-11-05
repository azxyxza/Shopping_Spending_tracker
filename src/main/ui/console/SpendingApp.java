package ui.console;

import model.ShoppingList;
import model.Spending;
import model.Transaction;
import ui.console.ShoppingListApp;

import java.util.LinkedList;
import java.util.Scanner;



// This MainPage references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp

/**
 * This is the spending Page of the shopping-spending tracker
 */


public class SpendingApp {
    //    private static Spending spending;
//    private static ShoppingList shoppingList;
    private Spending spending;
    private ShoppingList shoppingList;
    private Scanner input;



    // EFFECTS: runs the spending page
    public SpendingApp(Spending spending, ShoppingList shoppingList) {
//        SpendingApp.shoppingList = shoppingList;
//        try {
//            SpendingApp.spending = shoppingList.getSpending();
//        } catch (NullPointerException e) {
//            SpendingApp.spending = spending;
//        }
        this.shoppingList = shoppingList;
        this.spending = spending;

        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runSpendingAndTraction();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for main page
    private void runSpendingAndTraction() {
        boolean isInt;
        int command = 0;
        while (true) {
            displayMenu();
            do {
                if (input.hasNextInt()) {
                    isInt = true;
                    command = input.nextInt();
                } else {
                    System.out.println(">>>Please enter a number from 1-3");
                    isInt = false;
                    input.next();
                }
            } while (!isInt);

            if (command == 3) {
                return;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n<<<<<<<<<< Welcome to your Spending & Transaction manager >>>>>>>>>>>");
        System.out.println("\tCheck for: ");
        System.out.println("\t1 -> Spending");
        System.out.println("\t2 -> Transactions");
        System.out.println("\t3 -> Back to main page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(int command) {
        switch (command) {
            case 1:
                runSpending();
                break;
            case 2:
                runTransaction();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input for spending page
    private void runSpending() {
        boolean isInt;
        int command = 0;
        while (true) {
            displaySpendingMenu();
            do {
                if (input.hasNextInt()) {
                    isInt = true;
                    command = input.nextInt();
                } else {
                    System.out.println(">>>Please enter a number 1 or 0");
                    isInt = false;
                    input.next();
                }
            } while (!isInt);

            if (command == 0) {
                return;
            } else {
                processSpendingCommand(command);
            }
        }
    }

    // EFFECTS: displays menu of options to user in spending page
    private void displaySpendingMenu() {
        double income = 0.0;
        income += spending.getIncome();
        System.out.println("\n>>> Here are your financial tracker: ");
        System.out.println("\tIncome: " + income);
        System.out.println("\tExpense: " + spending.getExpense());
        System.out.println("\tBalance: " + spending.getBalance());
        System.out.println("\n");
        System.out.println("\tEnter 1 to add your income");
        System.out.println("\tEnter 2 to track your expenses");
        System.out.println("\tEnter 0 to go back to main page");
    }

    // MODIFIES: this
    // EFFECTS: synchronize the expenses from shopping list
    private void doGetExpense() {
        Scanner input = new Scanner(System.in);
        if (!shoppingList.getSpending().getTransactions().isEmpty()) {
            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                System.out.println("Do you want to track the expense for " + t.getItem().getName() + " ?");
                System.out.println("\t 1 -> Yes, upload it to my expenses");
                System.out.println("\t 0 -> Cancel uploading");
                String choice = input.next();
                if (choice.equals("1")) {
                    LinkedList<Transaction> transactions = new LinkedList<>();
                    transactions.add(t);
                    shoppingList.getSpending().trackExpense(transactions);
                    System.out.println("Your expenses has been tracked!");
                    System.out.println("You have spent: " + shoppingList.getSpending().getExpense());
                    updateExpense();
                } else if (choice.equals("0")) {
                    return;
                } else {
                    System.out.println(">>>> Please enter 1 or 0: ");
                }
            }
        } else {
            System.out.println("You don't have an expense at this point!");
        }
    }

    private void updateExpense() {
        //spending.setExpense(spending.getExpense() + shoppingList.getSpending().getExpense());
        spending.setExpense(shoppingList.getSpending().getExpense());
    }

    // MODIFIES: this
    // EFFECTS: process user input for spending page
    private void processSpendingCommand(int command) {
        if (command == 1) {
            doSetIncome();
        } else if (command == 2) {
            doGetExpense();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: let the user set input
    private void doSetIncome() {
        Scanner input = new Scanner(System.in);
        System.out.println("(Enter a number) What did you earn: ");
        boolean isDouble;
        double income = 0.0;
        do {
            if (input.hasNextDouble()) {
                isDouble = true;
                income = input.nextDouble();
            } else if (input.hasNextInt()) {
                isDouble = true;
                income = input.nextInt();
            } else {
                System.out.println(">>>Please enter a number for your income");
                isDouble = false;
                input.next();
            }
        } while (!isDouble);
        spending.setIncome(income);
    }

    // MODIFIES: this
    // EFFECTS: processes user input for transaction page
    protected static void runTransaction() {
        Scanner input = new Scanner(System.in);
        boolean isInt;
        int command = 0;
        while (true) {
            displayTransactionMenu();
            do {
                if (input.hasNextInt()) {
                    isInt = true;
                    command = input.nextInt();
                } else {
                    System.out.println(">>>Please enter a number 1-3");
                    isInt = false;
                    input.next();
                }
            } while (!isInt);

            if (command == 3) {
                return;
            } else {
                processTransactionCommand(command);
            }
        }
    }

    // EFFECTS: displays transaction menu of options to user
    private static void displayTransactionMenu() {
        if (ShoppingListApp.shoppingList == null) {
            System.out.println("\n>>>> There is no Transactions made.");
        } else {
            if (ShoppingListApp.shoppingList.getSpending().getTransactions().isEmpty()) {
                System.out.println("\n>>>> There is no Transactions made.");
            } else {
                System.out.println(">>>> Here are the things you bought: ");
                printTransaction();
                System.out.println("\n --- Do you want to: ---");
                System.out.println("\t1 -> Enter the prices of each items in order");
                System.out.println("\t2 -> Enter the price for a particular item");
            }
        }
        System.out.println("\t3 -> Back to main page");
    }


    // MODIFIES: this
    // EFFECTS: processes user transaction command
    private static void processTransactionCommand(int command) {
        switch (command) {
            case 1:
                doSetExpense();
                break;
            case 2:
                doEnterPrice();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: print the current transactions in spending list
    private static void printTransaction() {
        for (Transaction t : ShoppingListApp.shoppingList.getSpending().getTransactions()) {
            System.out.println(t.getItem().getName() + " is bought at " + t.getItem().getDate()
                    + " with " + t.getExpense() + " dollars.");
        }
    }

    // MODIFIES: this
    // EFFECTS: let user set the expense for items in order
    private static void doSetExpense() {
        Scanner input = new Scanner(System.in);
        boolean isDouble;
        double price = 0.0;
        for (Transaction t : ShoppingListApp.shoppingList.getSpending().getTransactions()) {
            System.out.println("How much did you spend on " + t.getItem().getAmount() + " "
                    + t.getItem().getName() + ": ");

            do {
                if (input.hasNextDouble()) {
                    isDouble = true;
                    price = input.nextDouble();
                } else if (input.hasNextInt()) {
                    isDouble = true;
                    price = input.nextInt();
                } else {
                    System.out.println(">>>Please enter a number for your price:");
                    isDouble = false;
                    input.next();
                }
            } while (!isDouble);

            t.setExpense(price);

        }
    }

    // MODIFIES: this
    // EFFECTS: let user set the expense for specific items
    private static void doEnterPrice() {
        Scanner input = new Scanner(System.in);
        System.out.println("What item do you want to record the price for: ");
        System.out.println("Enter the name of item: ");
        String name = input.nextLine();
        for (Transaction t : ShoppingListApp.shoppingList.getSpending().getTransactions()) {
            if (t.getItem().getName().equals(name)) {
                System.out.println("The price: ");
                String price = input.nextLine();
                t.setExpense(Double.parseDouble(price));
            } else {
                System.out.println("The item is not in the bought list ~");
            }
        }
    }
}



