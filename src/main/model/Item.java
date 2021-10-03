package model;

public class Item {

    private String name;
    private int amount;
    private Categories categories;
    private boolean isNecessary;

    public Item(String name, int amount, Categories categories) {
        this.name = name;
        this.amount = amount;
        this.categories = categories;
        this.isNecessary = false;
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

    public boolean isNecessary() {
        return isNecessary;
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

    public void setNecessary(boolean necessary) {
        isNecessary = necessary;
    }


}
