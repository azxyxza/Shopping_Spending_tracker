package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract class of Category List, including the method that
 * Home and ShoppingList inherited and abstract method.
 */

public abstract class CategoryList {
    protected List<Item> food;
    protected List<Item> fruitAndVeg;
    protected List<Item> drinks;
    protected List<Item> necessities;
    protected List<Item> others;
    protected List<Item> all;

    // EFFECTS: create a category list with instantiated 5 categories and "all" that stores all the items at home
    public CategoryList() {
        food = new ArrayList<>();
        fruitAndVeg = new ArrayList<>();
        drinks = new ArrayList<>();
        necessities = new ArrayList<>();
        others = new ArrayList<>();
        all = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add the item to given list based on categories
    public void addToList(Item it, Categories categories) {
        switch (categories) {
            case Food:
                food.add(it);
                break;
            case Drinks:
                drinks.add(it);
                break;
            case FruitAndVegetables:
                fruitAndVeg.add(it);
                break;
            case Necessities:
                necessities.add(it);
                break;
            default:
                others.add(it);
        }
    }


    // EFFECTS: get total amount of items at home
    public abstract int totalItem();

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item into the list
    public abstract void addItem(Item item);

    // REQUIRES: the item is in the list
    // MODIFIES: this
    // EFFECTS: remove the certain item from the list
    public abstract void deleteItem(Item item);
}
