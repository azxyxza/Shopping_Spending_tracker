package ui;

import model.Categories;
import model.Item;
import model.ShoppingList;

import java.time.LocalDate;
import java.util.Scanner;

import static model.Categories.*;

public class ShoppingListApp {
    private ShoppingList shoppingList;
    private Scanner input;

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

            if (command == 6) {
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
        System.out.println("\t6 -> Back to main page");
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

    private void doAddItem() {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the item's name that you want to buy?");
        String name = input.nextLine();

        System.out.println("How many do you want to buy?");
        int amount = Integer.parseInt(input.nextLine());

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

    private void doDeleteItem() {
        Scanner input = new Scanner(System.in);
        System.out.println("What's the item's name that you want to delete?");
        String name = input.nextLine();

        if (shoppingList.isContained(name)) {
            shoppingList.deleteItem(getItem(name));
            System.out.println("You have successfully deleted " + name + " from the list!");
        } else {
            System.out.println("Oops... You don't have this item in list! No need to delete :)");
        }
    }

    private void doMarkItem() { // TODO
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the item that you have added to cart: ");
        String name = input.nextLine();
        shoppingList.markItem(getItem(name));
        System.out.println("You have bought " + name + " at " + getItem(name).getDate());
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

    public Item getItem(String name) {
        for (Item i : shoppingList.getToBuy()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
