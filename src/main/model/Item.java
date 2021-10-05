package model;

public class Item {

    private String name;
    private int amount;
    private Categories categories;
    private boolean isFavorite;

    public Item(String name, int amount, Categories categories) {
        this.name = name;
        this.amount = amount;
        this.categories = categories;
        this.isFavorite = false;
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


}
