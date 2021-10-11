package ui;

import model.Item;
import model.Spending;
import model.Transaction;

import java.util.Scanner;

import static ui.ShoppingListApp.shoppingList;

public class SpendingApp {
    private static Spending spending;
    private Scanner input;

    // EFFECTS: runs the spending page
    public SpendingApp() {
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

    // TODO: expense & balance not display
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

    private static void displayTransactionMenu() {
        if (shoppingList.getSpending().getTransactions().isEmpty()) {
            System.out.println("\n>>>> There is no Transactions made.");
        } else {
            System.out.println(">>>> Here are the things you bought: ");
            printTransaction();
        }

        System.out.println("\n --- Do you want to: ---");
        System.out.println("\t1 -> Enter the prices of each items in order");
        System.out.println("\t2 -> Enter the price for a particular item");
        System.out.println("\t3 -> Back to main page");
    }

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


    private static void printTransaction() {
        for (Transaction t : shoppingList.getSpending().getTransactions()) {
            System.out.println(t.getItem().getName() + " is bought at " + t.getItem().getDate());
        }
    }


    private static void doSetExpense() {
        Scanner input = new Scanner(System.in);
        for (Item i : shoppingList.getBought()) {
            System.out.println("How much did you spend on " + i.getAmount() + " " + i.getName() + ": ");
            String price = input.nextLine();
            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                t.setExpense(Double.parseDouble(price));
            }
        }
    }

    private static void doEnterPrice() {
        Scanner input = new Scanner(System.in);
        System.out.println("What item do you want to record the price for: ");
        System.out.println("Enter the name of item: ");
        String name = input.nextLine();
        if (shoppingList.getBought().contains(getItem(name))) {
            System.out.println("The price: ");
            String price = input.nextLine();

            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                if (t.getItem().getName().equals(name)) {
                    t.setExpense(Double.parseDouble(price));
                }
            }
        } else {
            System.out.println("The item is not in the bought list~");
        }

    }

    public static Item getItem(String name) {
        for (Item i : shoppingList.getBought()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}




