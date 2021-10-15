package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the home class that stores the items that we already have/just bought,
 * while the list "all" stores all the items, the items are also stored based on categories
 * (with each one of the food, fruitAndVegetables, drinks, necessities, others); for the items
 * that set to be favorite, they are also stored in favorite list.
 * Home extends CategoryList used for categorize the items into proper category.
 */

public class Home extends CategoryList {
    private List<Item> all;
    private List<Item> favorite;

    // EFFECTS: create a home with empty all list and favorite list
    //          add the items that marked favorite into favorite list
    public Home() {
        all = new ArrayList<>();
        favorite = new ArrayList<>();
        addToFavorite();
    }


    // Getters
    public List<Item> getFood() {
        return food;
    }

    public List<Item> getFruitAndVeg() {
        return fruitAndVeg;
    }

    public List<Item> getDrinks() {
        return drinks;
    }

    public List<Item> getNecessities() {
        return necessities;
    }

    public List<Item> getOthers() {
        return others;
    }

    public List<Item> getAll() {
        return all;
    }

    public List<Item> getFavorite() {
        return favorite;
    }


    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the appropriate categories within this class
    public void addItem(Item it) {
        Categories categories = it.getCategories();
        super.addToList(it, categories);
        all.add(it);
    }

    // EFFECTS: get total amount of items at home
    public int totalItem() {
        return all.size();
    }

    // REQUIRES: the item is in the list
    // MODIFIES: this
    // EFFECTS: remove the certain item from the whole list and the given categories
    public void deleteItem(Item it) {
        if (isContained(it.getName())) {
            all.remove(it);
            switch (it.getCategories()) {
                case Food:
                    food.remove(it);
                case Drinks:
                    drinks.remove(it);
                case FruitAndVegetables:
                    fruitAndVeg.remove(it);
                case Necessities:
                    necessities.remove(it);
                default:
                    others.remove(it);
            }
        }
    }

    // REQUIRES: item != null
    // EFFECTS: add the item to the favorite
    public void addToFavorite() {
        for (Item i : all) {
            if (i.isFavorite()) {
                favorite.add(i);
            }
        }
    }


    // REQUIRES: the item is in one of the categories, or it is in favorite
    // MODIFIES: this
    // EFFECTS: delete the item from previous categories and move an item to the new categories
    public void moveItem(Item item, Categories categories) {
        if (all.contains(item)) {
            deleteItem(item);
            super.addToList(item, categories);
            all.add(item);
        }
    }


    // EFFECTS: get amount of items in specific categories
    public int getTypeAmount(Categories categories) {
        switch (categories) {
            case Food:
                return food.size();
            case Drinks:
                return drinks.size();
            case FruitAndVegetables:
                return fruitAndVeg.size();
            case Necessities:
                return necessities.size();
            default:
                return others.size();
        }
    }

    // EFFECTS: return true if item is contained at home
    public boolean isContained(String item) {
        for (Item i : all) {
            if (i.getName().equals(item)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return the items given item's name
    public Item getItem(String name) {
        for (Item i : getAll()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
