package ui.console;

import model.Categories;
import model.Home;
import model.Item;
import model.Transaction;
import model.exception.NotInTheListException;

import java.time.LocalDate;
import java.util.Scanner;

import static model.Categories.*;
import static ui.console.ShoppingListApp.shoppingList;

// This HomePage references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp

/**
 * This is the home page of the shopping-spending tracker
 */


public class HomeApp extends CheckInput {
    protected Home home;
    private Scanner input;

    // EFFECTS: run the home page
    public HomeApp(Home home) {
        init();
        this.home = home;
        runHome();
    }

    // EFFECTS: initialize the input console
    public void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user input for home main page
    public void runHome() {
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


    // MODIFIES: this
    // EFFECTS: processes user command for home main page
    private void processCommand(String command) {
        switch (command) {
            case "v":
                runViewItems();
                break;
            case "a":
                doAddItem();
                break;
            case "b":
                doAddBoughtItem();
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

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        int totalItem = home.totalItem();
        System.out.println("\n----------Welcome Home!----------");
        System.out.println("You have " + totalItem + " items at home now!");
        System.out.println("\nDo you want to:");
        System.out.println("\tv -> VIEW what you have at home");
        System.out.println("\ta -> ADD a NEW thing to home");
        System.out.println("\tb -> ADD things you BOUGHT to home");
        System.out.println("\td -> DELETE something from home");
        System.out.println("\tf -> mark something as your FAVORITE item");
        System.out.println("\tq -> BACK to main page");
    }


    // MODIFIES: this
    // EFFECTS: processes user input for v (view)
    private void runViewItems() {
        init();
        while (true) {
            if (home.getAll().isEmpty()) {
                System.out.println(">>> You have nothing at home now. ");
                System.out.println("Go back to add things to home or plan for a shopping!");
                return;
            }
            displayViewer();

            int input = checkInput();
            if (input == 6) {
                return;
            } else {
                processView(input);
            }
        }
    }

    // EFFECTS: helper function for runViewItems to check whether the input is integer
    public int checkInput() {
        boolean isInt;
        int command = 0;
        do {
            if (input.hasNextInt()) {
                isInt = true;
                command = input.nextInt();
            } else {
                System.out.println(">>>Please enter an integer");
                isInt = false;
                input.next();
            }
        } while (!isInt);
        return command;
    }

    // EFFECTS: displays menu of options of view
    private void displayViewer() {
        printAll();
        System.out.println("\nTo view your items in specific category, select: ");
        System.out.println("\t0 -> Favorite");
        System.out.println("\t1 -> Food");
        System.out.println("\t2 -> Fruits & Vegetables");
        System.out.println("\t3 -> Drinks");
        System.out.println("\t4 -> Necessities");
        System.out.println("\t5 -> Others");
        System.out.println("\t6 -> back to Home");
    }

    // EFFECTS: displays all items in home
    private void printAll() {
        int count = 1;
        System.out.println("\n>>>> At home, you have: ");
        for (Item i : home.getAll()) {
            System.out.println(count + ". " + i.getAmount() + " " + i.getName() + " stored at " + i.getDate() + ". ");
        }
    }


    // MODIFIES: this
    // EFFECTS: processes user command for v (view)
    private void processView(int command) {
        switch (command) {
            case 0:
                printFavorite();
                break;
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
            default:
                System.out.println("Not valid input...");
        }
    }


    // EFFECTS: print out what the certain categories have
    private void categoryView(Categories categories) {
        int a = home.getTypeAmount(categories);
        if (a == 0) {
            System.out.println(">>> There is no item in this category right now!");

        } else {
            System.out.println(">>> There are " + a + " in this category! ");
            System.out.println("They are: ");
            printCategory(categories);
        }
    }

    // EFFECTS: print the items in given categories
    public void printCategory(Categories category) {
        switch (category) {
            case Food:
                printFood();
                break;
            case FruitAndVegetables:
                printFruitAndVegetables();
                break;
            case Drinks:
                printDrinks();
                break;
            case Necessities:
                printNecessities();
                break;
            default:
                printOthers();
                break;
        }
    }


    // MODIFIES: this
    // EFFECTS: add new item to home
    private void doAddItem() {
        init();
        System.out.println("What is the item's name?");
        String name = input.nextLine();

        System.out.println("How many do you want to store?");
        int amount = checkInput();

        System.out.println("In which categories do you want to store the " + name + "?");
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
        home.addItem(i);
        System.out.println(">>> You have successfully added " + name + " to your home at " + LocalDate.now());
    }


    // EFFECTS: categorize the certain item
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


    // MODIFIES: this
    // EFFECTS: add item to be favorite
    private void doFavorite() {
        init();
        System.out.println(">>> What items at home do you want to add to favorite?");
        String name = input.nextLine();
        if (home.isContained(name)) {
            Item i = home.getItem(name);
            i.setToFavorite();
            home.addToFavorite();
            System.out.println("Now " + name + " becomes your favorite!");
        } else {
            System.out.println("Oops... It seems " + name + " is not at home!");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete item from home
    public void doDeleteItem() {
        Scanner item = new Scanner(System.in);
        System.out.println(">>> What's the item's name that you want to delete?");
        String name = item.nextLine();

        try {
            home.deleteItem(home.getItem(name));
            System.out.println(name + " is gone now!");
        } catch (NotInTheListException e) {
            System.out.println("Oops... You don't have this item at home! No need to delete :)");
        }
    }

    // MODIFIES: this
    // EFFECTS: add bought item to home
    private void doAddBoughtItem() {
        int count = 1;
        if (!shoppingList.getSpending().getTransactions().isEmpty()) {
            System.out.println("You have bought " + shoppingList.getSpending().getTransactions().size() + " items: ");
            for (Transaction t : shoppingList.getSpending().getTransactions()) {
                System.out.println(count + ". " + t.getItem().getName());
                count++;
            }
            System.out.println("Do you want to add them to your home?");
            System.out.println("\t1 -> Yes, store them to my home!");
            System.out.println("\t0 -> Cancel");
            checkInt();
        } else {
            System.out.println("You haven't bought anything yet!");
        }
    }


    // EFFECTS: check whether input is integer, if not integer,
    //          continue ask the user to re-input the value
    public void checkInt() {
        boolean isInt;
        int choice;
        do {
            if (input.hasNextInt()) {
                isInt = true;
                choice = input.nextInt();
                if (choice == 1) {
                    doAddBought();
                } else if (choice == 0) {
                    runHome();
                }
            } else {
                System.out.println(">>> Please enter 1 or 0: ");
                isInt = false;
                input.next();
            }
        } while (!isInt);
    }


    // MODIFIES: this
    // EFFECTS: add the bought items from shopping list to home
    private void doAddBought() {
        for (Transaction t : shoppingList.getSpending().getTransactions()) {
            home.addItem(t.getItem());
        }
        System.out.println("You have successfully stored your bought items to home!");
    }


    /**
     * Some print helper method
     */

    private void printOthers() {
        for (Item i : home.getOthers()) {
            System.out.println(i.getName());
        }
    }

    private void printNecessities() {
        for (Item i : home.getNecessities()) {
            System.out.println(i.getName());
        }
    }

    private void printDrinks() {
        for (Item i : home.getDrinks()) {
            System.out.println(i.getName());
        }
    }

    private void printFruitAndVegetables() {
        for (Item i : home.getFruitAndVeg()) {
            System.out.println(i.getName());
        }
    }

    private void printFood() {
        for (Item i : home.getFood()) {
            System.out.println(i.getName());
        }
    }

    // EFFECTS: print out the favorite items
    private void printFavorite() {
        int a = home.getFavorite().size();
        if (a == 0) {
            System.out.println("You haven't added anything to your favorite now!");
        } else {
            System.out.println("You have " + a + " favorite item(s)");
            System.out.println("They are ... ");
            for (Item i : home.getFavorite()) {
                System.out.println(i.getName());
            }
        }
    }

}

