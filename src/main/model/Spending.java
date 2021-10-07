package model;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 * This is the Spending class. The spending stores the list of budget, expenses, and total balance for each month
 */
public class Spending { // TODO: update monthly?
    private double budget;
    private double expense;
    private double balance;
    private LinkedList<Transaction> transactions;

    public Spending() {
    }

    public double getBudget() {
        return budget;
    }

    public double getExpense() {
        return expense;
    }

    public double getBalance() {
        return balance;
    }


    // MODIFIES: this
    // EFFECTS: reset the budget, expense, balance to 0 to start a new month
    public void reset() {
        if (LocalDate.now().getDayOfMonth() == 1) {
            budget = 0.0;
            expense = 0.0;
            balance = 0.0;
        }
    }


    // MODIFIES: this
    // EFFECTS: set the budget for this new month
    public void setBudget(Double budget) {
        this.budget = budget;
    }

    // MODIFIES: this
    // EFFECTS: store the expense for the transaction when new items bought
    public void trackExpense(LinkedList<Transaction> transactions) {

    }

}

