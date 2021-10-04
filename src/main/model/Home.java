package model;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private List<Item> food;
    private List<Item> fruitAndVeg;
    private List<Item> drinks;
    private List<Item> necessities;
    private List<Item> others;
    private List<List<Item>> all;
    private List<Item> favorite;

    public Home() {
        food = new ArrayList<>();
        fruitAndVeg = new ArrayList<>();
        drinks = new ArrayList<>();
        necessities = new ArrayList<>();
        others = new ArrayList<>();
        all = new ArrayList<>();
        favorite = new ArrayList<>();
        all.add(food);
        all.add(fruitAndVeg);
        all.add(drinks);
        all.add(necessities);
        all.add(others);
    }


    // EFFECTS: view the items in certain categories along with the amount of each item
    public void viewItems(Categories categories) {
        // stub
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the appropriate categories within this class
    public void addItem(Item it) {
        Categories categories = it.getCategories();
        addToList(it, categories);
    }

    // REQUIRES: the item is in the list
    // MODIFIES: this
    // EFFECTS: remove the certain item from the given categories
    public void delete(Item it) {
        for (List<Item> items : all) {
            if (items.contains(it)) {
                items.remove(it);
                System.out.println(it + " is gone now!");
            }
        }
    }


    // REQUIRES: item != null
    // EFFECTS: add the item to the favorite
    public void beFavorite() {
        for (List<Item> items : all) {
            for (Item i : items) {
                if (i.isFavorite()) {
                    favorite.add(i);
                }
            }
        }
    }


    // EFFECTS: view the list of favorite items in list
    public void viewFavorite() {
        System.out.println("Here is my favorite list!");
        for (Item i : favorite) {
            System.out.println(i + " still have " + i.getAmount());
        }
    }

    // REQUIRES: the item is in one of the categories, or it is in favorite
    // EFFECTS: move an item to a certain categories
    public void moveItem(Item item, Categories categories) {
        for (List<Item> items : all) {
            if (items.contains(item)) {
                if (item.getCategories().equals(categories)) {
                    System.out.println(item + " is already in the " + categories);
                } else {
                    items.remove(item);
                }
            }
        }
        this.addToList(item, categories);
        System.out.println(item + "is now in the " + categories);
    }

    public void addToList(Item it, Categories categories) {
        switch (categories) {
            case Food:
                this.food.add(it);
                break;
            case Drinks:
                this.drinks.add(it);
                break;
            case FruitAndVegetables:
                this.fruitAndVeg.add(it);
            case Necessities:
                this.necessities.add(it);
                break;
            default:
                this.others.add(it);
                break;
        }
    }


    public int totalItem() {
        int sum = 0;
        for (List<Item> items : all) {
            for (Item i : items) {
                sum++;
            }
        }
        return sum;
    }
}
