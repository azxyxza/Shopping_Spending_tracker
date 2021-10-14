package model;

import java.util.LinkedList;

/**
 * This is the Spending class. The spending stores the list of budget, expenses, and total balance for each month
 */

public class Spending extends Home {
    private double income;
    private double expense;
    private double balance; // TODO: why never assigned?
    protected LinkedList<Transaction> transactions;

    // EFFECTS: create a spending object that have the transaction keeping track
    public Spending() {
        transactions = new LinkedList<>();
        trackExpense(transactions);
    }

    // Getters
    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    // EFFECTS: balance is calculated as income - expense (can be negative, positive, zero)
    public double getBalance() {
        return balance = income - expense;
    }


    // MODIFIES: this
    // EFFECTS: record new income
    public void setIncome(Double income) {
        this.income += income;
    }


    // MODIFIES: this
    // EFFECTS: store the expense for the transaction when new items bought
    public void trackExpense(LinkedList<Transaction> transactions) {
        for (Transaction t : transactions) {
            this.expense += t.getExpense();
        }
    }
}

