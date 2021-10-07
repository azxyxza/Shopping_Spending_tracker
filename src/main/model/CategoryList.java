package model;

import java.util.ArrayList;
import java.util.List;

public abstract class CategoryList {
    protected List<Item> food;
    protected List<Item> fruitAndVeg;
    protected List<Item> drinks;
    protected List<Item> necessities;
    protected List<Item> others;
    protected List<Item> all;

    public CategoryList() {
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
                food.add(it);
                break;
            case Drinks:
                drinks.add(it);
                break;
            case FruitAndVegetables:
                fruitAndVeg.add(it);
            case Necessities:
                necessities.add(it);
                break;
            case Others:
                others.add(it);
                break;
        }
    }

    public abstract int totalItem();

    public abstract void addItem(Item item);

    public abstract void deleteItem(Item item);
}
