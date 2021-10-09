package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;


import static model.Categories.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendingTest {
    private Spending spending;
    private LinkedList<Transaction> ts;
    // setDate();
    // set a fake date to test the method

    @BeforeEach
    void setUp(){
        spending = new Spending();
        Item food = new Item("strawberry cake", 2, Food, LocalDate.now());
        Item fruitAndVeg = new Item("apple", 5, FruitAndVegetables,LocalDate.now());
        Item drinks = new Item("milk", 1, Drinks,LocalDate.now());
        Item necessities = new Item("toilet paper", 2, Necessities,LocalDate.now());
        Item others = new Item("pencil", 2, Others,LocalDate.now());
        Transaction t1 = new Transaction(food, 2.00);
        Transaction t2 = new Transaction(fruitAndVeg, 3.00);
        Transaction t3 = new Transaction(drinks, 4.00);
        Transaction t4 = new Transaction(necessities, 5.00);
        Transaction t5 = new Transaction(others, 6.00);
        ts  = new LinkedList<>();
        ts.add(t1);
        ts.add(t2);
        ts.add(t3);
        ts.add(t4);
        ts.add(t5);
    }

    @Test
    void testSetIncome(){
        spending.setIncome(1000.00);
        assertEquals(1000.00, spending.getIncome());
    }

    @Test
    void testTrackExpense(){
        spending.trackExpense(ts);
        assertEquals(20.00, spending.getExpense());
    }

    @Test
    void testSetBalance(){
        spending.setIncome(1000.00);
        spending.trackExpense(ts);
        spending.setBalance();
        assertEquals(980.00, spending.getBalance());

    }
}
