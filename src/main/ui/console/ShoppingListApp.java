package ui.console;

import model.*;
import model.exception.AvoidDuplicateException;
import model.exception.NotInTheListException;

import java.time.LocalDate;
import java.util.Scanner;

import static model.Categories.*;

// This MainPage references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp

/**
 * This is the shopping list Page of the shopping-spending tracker
 */

public class ShoppingListApp {
    protected static ShoppingList shoppingList;
    private Home home;
    private Scanner input;
    private int amount;


    // EFFECTS: run the home page
    public ShoppingListApp(ShoppingList shoppingList, Home home) {
        ShoppingListApp.shoppingList = shoppingList;
        this.home = home;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runShoppingList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runShoppingList() {
        boolean isInt;
        int command = 0;
        while (true) {
            displayMenu();
            do {
                if (input.hasNextInt()) {
                    isInt = true;
                    command = input.nextInt();
                } else {
                    System.out.println(">>>Please enter a number from 1-6");
                    isInt = false;
                    input.next();
                }
            } while (!isInt);

            if (command == 7) {
                return;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        int totalItem = shoppingList.totalItem();
        System.out.println("\n<<<<<<<<<< Plan for the shopping? Let's list! >>>>>>>>>>>");
        System.out.println("Your budget for this shopping is: " + shoppingList.getBudget());
        System.out.println("Bought: " + shoppingList.getSpending().getTransactions().size() + " items(s)");
        System.out.println("Needs to buy: " + totalItem + " item(s)");
        if (totalItem != 0) {
            System.out.println("They are: ");
            printToBuy();
        }

        System.out.println("\nDo you want to:");
        System.out.println("\t1 -> Set the budget for this shopping");
        System.out.println("\t2 -> Add a new thing TO BUY");
        System.out.println("\t3 -> Delete something from TO BUY");
        System.out.println("\t4 -> Mark something as BOUGHT");
        System.out.println("\t5 -> View what you have BOUGHT");
        System.out.println("\t6 -> Enter the transactions for your bought items");
        System.out.println("\t7 -> Back to previous");
    }


    // EFFECTS: print out the to-buy items
    private void printToBuy() {
        int count = 1;
        for (Item i : shoppingList.getToBuy()) {
            System.out.println(count + ". " + i.getName() + " -> Requires amount : " + i.getAmount());
            count++;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(int command) {
        switch (command) {
            case 1:
                doSetBudget();
                break;
            case 2:
                doAddItem();
                break;
            case 3:
                doDeleteItem();
                break;
            case 4:
                doMarkItem();
                break;
            case 5:
                doViewBought();
                break;
            case 6:
                SpendingApp.runTransaction();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: let user set the budget of this shopping
    private void doSetBudget() {
        Scanner input = new Scanner(System.in);
        int budget = 0;
        boolean isInt;
        System.out.println("Before shopping, it's always useful to set a budget.");
        System.out.println("With the to-buy things added on, you can change the budget at anytime!");
        do {
            System.out.println("(Enter An Integer) Your budget is:  ");
            if (input.hasNextInt()) {
                isInt = true;
                budget = input.nextInt();
            } else {
                System.out.println(">>>Please enter an integer for budget!!!");
                isInt = false;
                input.next();
            }
        } while (!isInt);

        shoppingList.setBudget(budget);
        System.out.println("You have successfully set your budget to " + budget);
    }

    // MODIFIES: this
    // EFFECTS: let user add items to shopping list
    public void doAddItem() {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the item's name that you want to buy?");
        String name = input.nextLine();
        if (!home.isContained(name)) {
            System.out.println("How many do you want to buy?");
            int amount = checkInt();
            System.out.println("You categorize " + name + " as in: ");
            printMenu();
            String category = input.next();
            category = category.toLowerCase();
            Categories type = categorize(category);
            Item i = new Item(name, amount, type, LocalDate.now());
            try {
                shoppingList.addItem(i);
            } catch (AvoidDuplicateException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("You have successfully added " + name + " to the list!");
        } else {
            confirmBuyingMultiple(name);
        }
    }

    private void printMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tf -> Food");
        System.out.println("\tv -> Fruits & Vegetables");
        System.out.println("\td -> Drinks");
        System.out.println("\tn -> Necessities");
        System.out.println("\to -> Others");
    }

    // EFFECTS: ask the user whether they want to buy the same item's again
    private void confirmBuyingMultiple(String name) {
        Item i = home.getItem(name);
        System.out.println("There is " + i.getAmount() + " " + name + " at home now.");
        System.out.println("Do you still want to buy this or cancel adding?");
        System.out.println("\t 1 -> Yes, I like it.");
        System.out.println("\t 0 -> Cancel buying");
        boolean isInt;
        int choice = 0;
        do {
            if (input.hasNextInt()) {
                isInt = true;
                choice = input.nextInt();
            } else {
                System.out.println(">>>Please enter 1 or 0 for confirmation: ");
                isInt = false;
                input.next();
            }
        } while (!isInt);
        if (choice == 1) {
            doAddSameItem(i);
        } else {
            System.out.println("Put it back and save for money :)");
        }
    }

    // MODIFIES: this
    // EFFECTS: add item to buy even though home already has that item
    private void doAddSameItem(Item item) {
        System.out.println("There are " + item.getAmount() + " " + item.getName() + " now.");
        System.out.println("How many more do you want to buy?");
        int amount = checkInt();

        Item i = new Item(item.getName(), amount, item.getCategories(), LocalDate.now());
        try {
            shoppingList.addItem(i);
        } catch (AvoidDuplicateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("You have successfully added " + i.getName() + " to the list!");
    }


    // MODIFIES: this
    // EFFECTS: helper function for doAddSameItem, check whether user's input is integer
    public int checkInt() {
        boolean isInt;
        do {
            if (input.hasNextInt()) {
                isInt = true;
                amount = input.nextInt();
            } else {
                System.out.println(">>>Please enter an Integer: ");
                isInt = false;
                input.next();
            }
        } while (!isInt);
        return amount;
    }

    // MODIFIES: this
    // EFFECTS: let user delete items from shopping list
    private void doDeleteItem() {
        Scanner input = new Scanner(System.in);
        System.out.println("What's the item's name that you want to delete?");
        String name = input.nextLine();

        try {
            shoppingList.deleteItem(getToBuyItem(name));
            System.out.println("You have successfully deleted " + name + " from the list!");
        } catch (NotInTheListException e) {
            System.out.println("Oops... You don't have this item in list! No need to delete :)");
        }
    }

    // MODIFIES: this
    // EFFECTS: let user mark something as bought
    private void doMarkItem() {
        Scanner inputName = new Scanner(System.in);
        boolean isInt;
        String name = "";
        do {
            System.out.println("Enter the item that you have added to cart (name): ");
            if (inputName.hasNextLine()) {
                isInt = true;
                name = inputName.nextLine();
            } else {
                System.out.println(">>>Please enter the item's name: ");
                isInt = false;
                inputName.next();
            }
        } while (!isInt);

        try {
            shoppingList.markItem(getToBuyItem(name));
            System.out.println("You have bought " + name + " at " + getTransactionItem(name).getDate());
        } catch (NotInTheListException e) {
            doMakeSure();
        }
    }

    // EFFECTS: get the transaction item given the item's name
    private Item getTransactionItem(String name) {
        for (Transaction t : shoppingList.getSpending().getTransactions()) {
            if (t.getItem().getName().equals(name)) {
                return t.getItem();
            }
        }
        return getBoughtItem(name);
    }


    // MODIFIES: this
    // EFFECTS: ask user whether the new item that not in the list needed to buy or not
    private void doMakeSure() {
        Scanner input = new Scanner(System.in);
        System.out.println("The item you mark is not in the planed shopping list, do you want to: ");
        System.out.println("\t 1 -> Create this new item in shopping list and confirm your new bought item");
        System.out.println("\t 0 -> Cancel buying");
        boolean isInt;
        int choice = 0;
        do {
            if (input.hasNextInt()) {
                isInt = true;
                choice = input.nextInt();
            } else {
                System.out.println(">>>Please enter 1 or 0 for confirmation: ");
                isInt = false;
                input.next();
            }
        } while (!isInt);
        if (choice == 1) {
            doAddItem();
            doMarkItem();
        } else {
            System.out.println("Put it back and save for money :)");
        }
    }

    // MODIFIES: this
    // EFFECTS: let user view what they have bought
    private void doViewBought() {
        int amount = shoppingList.getSpending().getTransactions().size();
        if (amount == 0) {
            System.out.println("You haven't bought anything yet!");
        } else {
            System.out.println("You have bought " + amount + " items now!");
            System.out.println("Here are they: ");
            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                System.out.println(t.getItem().getName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: process the categories
    public Categories categorize(String category) {
        switch (category) {
            case "f":
                return Food;
            case "v":
                return FruitAndVegetables;
            case "d":
                return Drinks;
            case "n":
                return Necessities;
            default:
                return Others;
        }
    }

    // helper that return the item given name
    public Item getToBuyItem(String name) {
        for (Item i : shoppingList.getToBuy()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    // helper that return the item given name
    public Item getBoughtItem(String name) {
        for (Item i : shoppingList.getBought()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

}
