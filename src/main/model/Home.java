package model;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private List<Item> food;
    private List<Item> drinks;
    private List<Item> necessities;
    private List<Item> others;

    public Home() {
        food = new ArrayList<>();
        drinks = new ArrayList<>();
        necessities = new ArrayList<>();
        others = new ArrayList<>();
    }

    // EFFECTS: get the

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the appropriate categories within this class
    public void addItem(Item it) {
        Categories categories = it.getCategories();
        switch (categories) {
            case FOOD:
                this.food.add(it);
                break;
            case DRINKS:
                this.drinks.add(it);
                break;
            case NECESSITIES:
                this.necessities.add(it);
                break;
            default:
                this.others.add(it);
                break;
        }
    }

    public void delete(Item it){

    }

}
