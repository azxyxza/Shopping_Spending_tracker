package ui;

import model.Categories;
import model.Item;
import model.ShoppingList;

import java.time.LocalDate;
import java.util.Scanner;

import static model.Categories.*;

public class ShoppingListApp {
    static ShoppingList shoppingList;
    private Scanner input;
    private int amount;

    // EFFECTS: run the home page
    public ShoppingListApp() {
        shoppingList = new ShoppingList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runShoppingList();
    }


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

    private void displayMenu() {
        int totalItem = shoppingList.totalItem();
        System.out.println("\n<<<<< Plan for the shopping? Let's list! >>>>>");
        System.out.println("You budget for this shopping is: " + shoppingList.getBudget());

        System.out.println("\nYou have " + totalItem + " items in the to-buy list now!");
        if (totalItem != 0) {
            System.out.println("They are: ");
            printToBuy();
        }

        System.out.println("\nDo you want to:");
        System.out.println("\t1 -> Set the budget for this shopping");
        System.out.println("\t2 -> Add a new thing to buy");
        System.out.println("\t3 -> Delete something from the list");
        System.out.println("\t4 -> Mark something as bought");
        System.out.println("\t5 -> View what you have bought");
        System.out.println("\t6 -> Enter the transactions for your bought items");
        System.out.println("\t7 -> Back to main page");
    }

    private void printToBuy() {
        int count = 1;
        for (Item i : shoppingList.getToBuy()) {
            System.out.println(count + ". " + i.getName() + " -> Requires amount : " + i.getAmount());
            count++;
        }
    }

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


    private void doSetBudget() {
        Scanner input = new Scanner(System.in);
        int budget = 0;
        boolean isInt;
        System.out.println("Before shopping, it's always useful to set a budget. "
                + "With the to-buy things added on, you can change the budget anytime!");
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

    public void doAddItem() {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the item's name that you want to buy?");
        String name = input.nextLine();
        System.out.println("How many do you want to buy?");
        checkInt();
        System.out.println("You categorize " + name + " as in: ");
        System.out.println("\nSelect from:");
        System.out.println("\tf -> Food");
        System.out.println("\tv -> Fruits & Vegetables");
        System.out.println("\td -> Drinks");
        System.out.println("\tn -> Necessities");
        System.out.println("\to -> Others");
        String category = input.next();
        category = category.toLowerCase();

        Categories type = categorize(category);
        Item i = new Item(name, amount, type, LocalDate.now());
        shoppingList.addItem(i);
        System.out.println("You have successfully added " + name + " to the list!");
    }

    // TODO
    public void checkInt() {
        boolean isInt;
        amount = 0;
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
    }


    private void doDeleteItem() {
        Scanner input = new Scanner(System.in);
        System.out.println("What's the item's name that you want to delete?");
        String name = input.nextLine();

        if (shoppingList.isContained(name)) {
            shoppingList.deleteItem(getToBuyItem(name));
            System.out.println("You have successfully deleted " + name + " from the list!");
        } else {
            System.out.println("Oops... You don't have this item in list! No need to delete :)");
        }
    }

    private void doMarkItem() {
        Scanner inputName = new Scanner(System.in);

        boolean isInt;
        String name = "";
        do {
            System.out.println("Enter the item that you have added to cart: ");

            if (inputName.hasNextLine()) {
                isInt = true;
                name = inputName.nextLine();
            } else {
                System.out.println(">>>Please enter the item's name: ");
                isInt = false;
                inputName.next();
            }
        } while (!isInt);

        if (shoppingList.isContained(name)) {
            shoppingList.markItem(getToBuyItem(name));
            System.out.println("You have bought " + name + " at " + getBoughtItem(name).getDate());
        } else {
            doMakeSure();
        }
    }

    // TODO
    private void doMakeSure() {
        Scanner input = new Scanner(System.in);
        System.out.println("The item you mark is not in the planed shopping list, do you want to: ");
        System.out.println("\t 1 -> Create this new item in shopping list");
        System.out.println("\t 0 -> Cancel buying");

        String choice = input.nextLine();
        if (Integer.parseInt(choice) == 1) {
            doAddItem();
            doMarkItem();
        } else {
            System.out.println("Put it back and save for money :)");
        }
    }

    private void doViewBought() {
        int amount = shoppingList.getBought().size();
        if (amount == 0) {
            System.out.println("No items found in bought list!");
        } else {
            System.out.println("You have bought " + amount + " items now!");
            System.out.println("They are: ");
            for (Item i : shoppingList.getBought()) {
                System.out.println(i.getName());
            }
        }
    }


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

    public Item getToBuyItem(String name) {
        for (Item i : shoppingList.getToBuy()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    public Item getBoughtItem(String name) {
        for (Item i : shoppingList.getBought()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
