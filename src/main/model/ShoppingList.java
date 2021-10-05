package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends Categorize {
    private double budget;
    private List<Item> toBuy;
    private List<Item> bought;

    public ShoppingList() {
        budget = 0.0;
        toBuy = new ArrayList<>();
    }

    public double getBudget() {
        return budget;
    }

    // EFFECTS: set the budget for this shopping
    public void setBudget(double budget) {
        this.budget = budget;
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the shopping list
    public void addToBuy(Item it) {
        toBuy.add(it);
    }


    // REQUIRES: items not null;
    // MODIFIES: this
    // EFFECTS: for the already-added-to-cart items, move the given item to the bought list,
    // create a new transaction for the item.
    public void checkIt(Item item) {

    }

    public void delete(Item item) {

    }

    // EFFECTS: check whether the item is already in the list
    public Boolean isContained(Item item) {
        if (toBuy.contains(item)) {
            return true;
        }
        return false;
    }
}

