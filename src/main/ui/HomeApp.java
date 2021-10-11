package ui;

import model.Categories;
import model.Home;
import model.Item;

import java.time.LocalDate;
import java.util.Scanner;

import static model.Categories.*;

public class HomeApp {
    private Home home;
    private Scanner input;

    // EFFECTS: run the home page
    public HomeApp() {
        home = new Home();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runHome();
    }

    private void runHome() {

        while (true) {
            displayMenu();
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                return;
            } else {
                processCommand(command);
            }
        }

    }

    private void processCommand(String command) {
        switch (command) {
            case "v":
                runViewItems();
                break;
            case "a":
                doAddItem();
                break;
            case "d":
                doDeleteItem();
                break;
            case "f":
                doFavorite();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }


    private void displayMenu() {
        int totalItem = home.totalItem();
        System.out.println("\n---Welcome Home!---");
        System.out.println("You have " + totalItem + " items at home now!");
        System.out.println("\nDo you want to:");
        System.out.println("\tv -> view what you have at home");
        System.out.println("\ta -> add a new thing to home");
        System.out.println("\td -> delete something from home");
        System.out.println("\tf -> mark something as your favorite item");
        System.out.println("\tq -> back to main page");
    }


    /**
     * view Items
     */

    private void runViewItems() {
        boolean isInt;
        int command = 0;
        while (true) {
            displayViewer();

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
                processView(command);
            }
        }

    }

    private void displayViewer() {
        System.out.println("\nWhich category do you want to view:");
        System.out.println("\t1 -> Food");
        System.out.println("\t2 -> Fruits & Vegetables");
        System.out.println("\t3 -> Drinks");
        System.out.println("\t4 -> Necessities");
        System.out.println("\t5 -> Others");
        System.out.println("\t6 -> back to Home");
    }

    private void processView(int command) {
        switch (command) {
            case 1:
                categoryView(Food);
                break;
            case 2:
                categoryView(FruitAndVegetables);
                break;
            case 3:
                categoryView(Drinks);
                break;
            case 4:
                categoryView(Necessities);
                break;
            case 5:
                categoryView(Others);
                break;
            case 6:
                return;
            default:
                System.out.println("Not valid input...");
        }
    }


    private void categoryView(Categories categories) {
        int a = home.getTypeAmount(categories);
        if (a == 0) {
            System.out.println("There is no item in this category right now!");
        } else {
            System.out.println("There are " + a + " in this category");
            System.out.println("They are ... ");
            for (Item i : home.getFood()) {
                System.out.println(i.getName());
            }
        }
    }


    private void doAddItem() {
        Scanner item = new Scanner(System.in);
        System.out.println("What is the item's name?");
        String name = item.nextLine();

        System.out.println("How many do you want to store?");
        int amount = item.nextInt();

        System.out.println("In which categories do you want to store the " + name + "?");
        System.out.println("\nSelect from:");
        System.out.println("\tf -> Food");
        System.out.println("\tv -> Fruits & Vegetables");
        System.out.println("\td -> Drinks");
        System.out.println("\tn -> Necessities");
        System.out.println("\to -> Others");
        String category = item.next();
        category = category.toLowerCase();

        Categories type = categorize(category);
        Item i = new Item(name, amount, type, LocalDate.now());
        home.addItem(i);
        System.out.println("You have successfully added " + name + " to your home at " + LocalDate.now());
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

    private void doFavorite() {
        Scanner input = new Scanner(System.in);
        System.out.println("What items at home do you want to add to favorite?");
        String name = input.nextLine();
        if (home.isContained(name)) {
            Item i = getItem(name);
            i.setToFavorite();
            home.addToFavorite();
            System.out.println("Now " + name + " becomes your favorite!");
        } else {
            System.out.println("Oops... It seems " + name + " is not at home!");
        }
    }


    public void doDeleteItem() {
        Scanner item = new Scanner(System.in);
        System.out.println("What's the item's name that you want to delete?");
        String name = item.nextLine();

        if (home.isContained(name)) {
            home.deleteItem(getItem(name));
            System.out.println(name + " is gone now!");
        } else {
            System.out.println("Oops... You don't have this item at home! No need to delete :)");
        }


    }

    public Item getItem(String name) {
        for (Item i : home.getAll()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}

