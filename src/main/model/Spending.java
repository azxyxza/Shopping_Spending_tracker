package model;

import java.util.LinkedList;
import java.util.List;

/** This is the Spending class. The spending stores the list of budget, expenses, and total balance for each month
 * */
public class Spending { // TODO: update monthly?
    private double budget;
    private double expense;
    private double balance;
    private List<Transaction> transactions;

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
}
