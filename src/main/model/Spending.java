package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the Spending class. The spending stores the list of budget, expenses, and total balance for each month
 */

public class Spending extends Home implements Writable {
    private double income;
    private double expense;
    private double balance;
    protected LinkedList<Transaction> transactions;

    // EFFECTS: create a spending object that have the transaction keeping track
    public Spending() {
        transactions = new LinkedList<>();
        // trackExpense(transactions);
        income = 0.0;
        expense = 0.0;
        balance = income - expense;
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

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // MODIFIES: this
    // EFFECTS: store the expense for the transaction when new items bought
    public void trackExpense(LinkedList<Transaction> transactions) {
        for (Transaction t : transactions) {
            this.expense += t.getExpense();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("income", income);
        json.put("expense", expense);
        json.put("balance", balance);
        json.put("transactions", addToJson(transactions));
        return json;
    }

    // EFFECTS: returns items in this home as a JSON array
    private JSONArray addToJson(List<Transaction> transactions) {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJsonTransaction());
        }

        return jsonArray;
    }
}

