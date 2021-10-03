package model;

import java.util.ArrayList;
import java.util.List;

public class Spending {
    private double budget;
    private double expense;
    private double balance;
    private List<Transaction> transactions;


    public Spending() {
        transactions = new ArrayList<>();
    }
}
