package model;

import java.util.LinkedList;

/**
 * This is the Spending class. The spending stores the list of budget, expenses, and total balance for each month
 */
public class Spending { // TODO: update monthly?
    private double income;
    private double expense;
    private double balance;
    protected LinkedList<Transaction> transactions;

    public Spending() {
//        reset();
        transactions = new LinkedList<>();
        trackExpense(transactions);
    }

    public double getIncome() {
        return income;
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
    public double trackExpense(LinkedList<Transaction> transactions) {
        for (Transaction t : transactions) {
            this.expense += t.getExpense();
        }
        return expense;
    }


    //    // MODIFIES: this
//    // EFFECTS: reset the budget, expense, balance to 0 to start a new month
//    public void reset() {
//        if (LocalDate.now().getDayOfMonth() == 1) {
//            budget = 0.0;
//            expense = 0.0;
//            balance = 0.0;
//        }
//    }
    public void setBalance() {
        this.balance = income - expense;
    }

}

