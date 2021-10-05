package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the shoppingList class. We can set the budget for this shopping. And the toBuy list stores
 * all the things that we added which needed to buy. When the items are bought (added to the cart), the
 * things are removed from the toBuy list and goes into the bought list.
 */

public class ShoppingList {
    private double budget;
    protected List<Item> toBuy;
    protected List<Item> bought;

    public ShoppingList() {
        budget = 0.0;
        toBuy = new ArrayList<>();
        bought = new ArrayList<>();
    }

    // Getter and Setter
    public double getBudget() {
        return budget;
    }

    public List<Item> getToBuy() {
        return toBuy;
    }

    public List<Item> getBought() {
        return bought;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    // REQUIRES: item != null, item not contain in the shopping list already
    // MODIFIES: this
    // EFFECTS: add the given Item (item) into the toBuy list
    public boolean addToBuy(Item item) {
        if (!isContained(item.getName())) {
            toBuy.add(item);
            return true;
        }
        return false;
    }

    // REQUIRES: items not null, item is in the toBuy list
    // MODIFIES: this
    // EFFECTS: for the already-added-to-cart items, stores the transaction to home
    // create a new transaction for the item.
    public void bought(Item item) {
        if (isContained(item.getName())) {
            delete(item);
            bought.add(item);
        }
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: delete the items from the toBuy list
    public void delete(Item item) {
        toBuy.remove(item);
    }

    // EFFECTS: return the number of items needed to buy in the list
    public int amountToBuy() {
        int sum = 0;
        for (Item i : toBuy) {
            sum++;
        }
        return sum;
    }

    // EFFECTS: check if the item is already in the list with given item name, if it is in the list,
    // return true, if not return false
    public Boolean isContained(String name) {
        for (Item i : toBuy) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

