package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the shoppingList class. We can set the budget for this shopping. And the toBuy list stores
 * all the things that we added which needed to buy. When the items are bought (added to the cart), the
 * things are removed from the toBuy list and goes into the bought list.
 */

public class ShoppingList extends CategoryList {
    private double budget;
    protected List<Item> toBuy;
    protected List<Item> bought;
    private Spending spending;

    public ShoppingList() {
        budget = 0.0;
        toBuy = new ArrayList<>();
        bought = new ArrayList<>();
        spending = new Spending();
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

    public Spending getSpending() {
        return spending;
    }

    // EFFECTS: set the budget for this shopping
    public void setBudget(double budget) {
        this.budget = budget;
    }


    // EFFECTS: return the number of items needed to buy in the list
    public int totalItem() {
        return toBuy.size();
    }


    // REQUIRES: item != null, item not contain in the shopping list already
    // MODIFIES: this
    // EFFECTS: add the given Item (item) into the toBuy list
    public void addItem(Item item) {
        if (!isContained(item.getName())) {
            toBuy.add(item);
        }
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: delete the items from the toBuy list
    public void deleteItem(Item item) {
        toBuy.remove(item);
    }


    // REQUIRES: items not null, item is in the toBuy list
    // MODIFIES: this
    // EFFECTS: for the already-added-to-cart items, stores the transaction to home
    // create a new transaction for the item.
    public void markItem(Item item) {
        if (isContained(item.getName())) {
            deleteItem(item);
            bought.add(item);
            addTransaction();
        }
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


    // EFFECTS: create a new transaction for each bought items
    public void addTransaction() {
        for (Item i : bought) {
            Transaction t = new Transaction(i, 0.0);
            spending.getTransactions().add(t);
        }
    }
}

