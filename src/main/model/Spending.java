package model;

import java.util.LinkedList;

/**
 * This is the Spending class. The spending stores the list of budget, expenses, and total balance for each month
 */

public class Spending extends Home {
    private double income;
    private double expense;
    private double balance;
    protected LinkedList<Transaction> transactions;

    public Spending() {
        transactions = new LinkedList<>();
        trackExpense(transactions);
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public double getBalance() {
        return balance = income - expense;
    }

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }


    // MODIFIES: this
    // EFFECTS: set the budget for this new month
    public void setIncome(Double income) {
        this.income += income;
    }


    // MODIFIES: this
    // EFFECTS: store the expense for the transaction when new items bought
    public void trackExpense(LinkedList<Transaction> transactions) throws NullPointerException {
        for (Transaction t : transactions) {
            this.expense += t.getExpense();
        }
    }

    public void setBalance() {
        this.balance = income - expense;
    }

}

