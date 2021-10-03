package model;

public class Transaction {

    private Item item;
    private double expense;

    public Transaction(Item item, double expense) {
        this.item = item;
        this.expense = expense;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
