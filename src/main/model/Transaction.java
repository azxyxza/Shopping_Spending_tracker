package model;

/**
 * This is the Transaction class. Transaction stores the bought item and
 * expenses (item's price) are required to be loaded in.
 */

public class Transaction {
    private final Item item;
    private double expense;

    // EFFECTS: create a transaction that have item and its price
    public Transaction(Item item, double expense) {
        this.item = item;
        this.expense = expense;
    }

    // Getter and setter
    public Item getItem() {
        return item;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

}
