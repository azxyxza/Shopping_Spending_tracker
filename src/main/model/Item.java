package model;

import java.time.LocalDateTime;

/** This is the Item class. Each item contains a name, the amount, a category, and the bought time.
 * we can set the item to be favorite.
 * */

public class Item {

    private String name;
    private int amount;
    private Categories categories;
    private boolean isFavorite;
    private LocalDateTime time;

    public Item(String name, int amount, Categories categories, LocalDateTime time) {
        this.name = name;
        this.amount = amount;
        this.categories = categories;
        this.isFavorite = false;
        this.time = time;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Categories getCategories() {
        return categories;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public LocalDateTime getTime() {
        return time;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public void setToFavorite() {
        this.isFavorite = true;
    }

    // EFFECTS: set the item's time to current time
    public void setTime() {
        this.time = LocalDateTime.now();
    }

}
