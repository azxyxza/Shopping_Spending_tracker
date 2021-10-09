package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static model.Categories.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {
    private Transaction testTransaction;
    private Item food;


    @BeforeEach
    void setUp(){
        food = new Item("strawberry cake", 2, Food, LocalDate.now());
        testTransaction = new Transaction(food, 0.00);
    }

    @Test
    void testGetters(){
        assertEquals(food, testTransaction.getItem());
    }
    @Test
    void testSetExpense(){
        assertEquals(0.00, testTransaction.getExpense());
        testTransaction.setExpense(1.00);
        assertEquals(1.00, testTransaction.getExpense());
    }
}
