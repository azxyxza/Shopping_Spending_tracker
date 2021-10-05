package model;

import java.util.ArrayList;
import java.util.List;

public class Categorize {
    protected List<Item> food;
    protected List<Item> fruitAndVeg;
    protected List<Item> drinks;
    protected List<Item> necessities;
    protected List<Item> others;
    protected List<Item> all;

    public Categorize() {
        food = new ArrayList<>();
        fruitAndVeg = new ArrayList<>();
        drinks = new ArrayList<>();
        necessities = new ArrayList<>();
        others = new ArrayList<>();
        all = new ArrayList<>();
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
            case Others:
                this.others.add(it);
                break;
        }
    }
}
