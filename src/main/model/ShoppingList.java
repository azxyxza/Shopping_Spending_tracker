package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {

    private double budget;
    private List<Item> food;
    private List<Item> drinks;
    private List<Item> necessities;
    private List<Item> others;
    private List<List<Item>> toBuy;
    private List<Item> bought;

    public ShoppingList() {
        budget = 0.0;
        food = new ArrayList<>();
        drinks = new ArrayList<>();
        necessities = new ArrayList<>();
        others = new ArrayList<>();
        toBuy = new ArrayList<>();
        toBuy.add(food);
        toBuy.add(drinks);
        toBuy.add(necessities);
        toBuy.add(others);
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the shopping list in appropriate categories if the item is not
    // in the fridge
    public void addToBuy(Item it) {
        Categories categories = it.getCategories();

        if (isContained(it)) {
            switch (categories) {
                case food:
                    this.food.add(it);
                    break;
                case drinks:
                    this.drinks.add(it);
                    break;
                case necessities:
                    this.necessities.add(it);
                    break;
                case others:
                    this.others.add(it);
                    break;
            }
        }
    }


    // REQUIRES: items not null;
    // MODIFIES: this
    // EFFECTS: for the already-added-to-cart items, move the given item to the bought list,
    // create a new transaction for the item.
    public void checkIt(Item item) {
        // TODO
    }

    public void delete(Item item) {

    }

    public Boolean isContained(Item item) {
        for (List<Item> items : toBuy) {
            if (items.contains(item)) {
                return true;
            }
        }
        return false;
    }
}

