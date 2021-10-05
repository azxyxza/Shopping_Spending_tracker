package model;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static model.Categories.Food;

public class TransactionTest {
    private Transaction testTransaction;
    private Item i;

    @BeforeEach
    void setUp(){
        i = new Item("strawberry cake", 2, Food, LocalDateTime.now());
        testTransaction = new Transaction(i, 0.0);
    }
}
