package ui;

import model.Item;
import model.ShoppingList;
import model.Spending;
import model.Transaction;

import java.util.List;
import java.util.Scanner;

public class SpendingApp {
    private Spending spending;
    private Scanner input;
    private ShoppingList shoppingList;

    // EFFECTS: runs the spending page
    public SpendingApp() {
        shoppingList = new ShoppingList();
        spending = new Spending();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runSpendingAndTraction();
    }

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

    private void displayMenu() {
        System.out.println("\n<<<<< Welcome to your Spending & Transaction manager >>>>>");
        System.out.println("\t1 -> Spending");
        System.out.println("\t2 -> Transactions");
        System.out.println("\t3 -> Back to main page");
    }

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

    private void displaySpendingMenu() {
        Double income = 0.0;
        income += spending.getIncome();
        System.out.println(">>> Here are your financial tracker: ");
        System.out.println("\tIncome: " + income);
        System.out.println("\tExpense: " + spending.getExpense());
        System.out.println("\tBalance: " + spending.getBalance());

        System.out.println("\n");
        System.out.println("\tEnter 1 to add your income");
        System.out.println("\tEnter 0 to go back to main page");
    }

    private void processSpendingCommand(int command) {
        if (command == 1) {
            doSetIncome();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    private void doSetIncome() {
        Scanner input = new Scanner(System.in);
        System.out.println("(Enter a number) What did you earn: ");
        double income = Double.parseDouble(input.next());
        spending.setIncome(income);
    }


    private void runTransaction() {
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

    private void displayTransactionMenu() {

        if (shoppingList.getBought().isEmpty()) {
            System.out.println("\n>>>> There is no new bought item that needed to be tracking");
        } else {
            System.out.println(">>>> Here are the things you bought that still needed to enter a price: ");
            printBought();
        }

        System.out.println("\n --- Do you want to: ---");
        System.out.println("\t1 -> Enter the prices of each items in order");
        System.out.println("\t2 -> Enter the price for a particular item");
        System.out.println("\t3 -> Back to main page");
    }

    private void processTransactionCommand(int command) {
        switch (command) {
            case 1:
                doEnterPrice();
                break;
            case 2:
                doSetExpense();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }


    private void printBought() {
        for (Item i : shoppingList.getBought()) {
            System.out.println(i.getName());
        }
    }


    private void doSetExpense() {
        Scanner input = new Scanner(System.in);
        for (Item i : shoppingList.getBought()) {
            System.out.println("How much did you spend on " + i.getAmount() + " " + i.getName() + ": ");
            double price = input.nextDouble();
            for (Transaction t : spending.getTransactions()) {
                t.setExpense(price);
            }
        }
    }

    private void doEnterPrice() {
        Scanner input = new Scanner(System.in);
        System.out.println("What item do you want to record the price for: ");
        System.out.println("Enter the name of item: ");
        String name = input.nextLine();
        if (shoppingList.getBought().contains(getItem(name))) {
            System.out.println("The price: ");
            double price = input.nextDouble();

            for (Transaction t : spending.getTransactions()) {
                if (t.getItem().getName().equals(name)) {
                    t.setExpense(price);
                }
            }
        } else {
            System.out.println("The item is not in the bought list~");
        }

    }

    public Item getItem(String name) {
        for (Item i : shoppingList.getBought()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}




