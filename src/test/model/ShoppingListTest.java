package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static model.Categories.Food;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListTest {
    private ShoppingList testShoppingList;
    private Item item;

    @BeforeEach
    void setUp(){
        testShoppingList = new ShoppingList();
        item = new Item("strawberry cake", 2, Food, LocalDateTime.now());

    }

    @Test
    void testSetBudget(){
        testShoppingList.setBudget(100.00);
        assertEquals(100.00, testShoppingList.getBudget());
    }

    @Test
    void testAddToBuy(){
        Item sameName = new Item("strawberry cake", 2, Food, LocalDateTime.now());
        Item differentName = new Item("strawberry bread", 1, Food, LocalDateTime.now());
        assertTrue(testShoppingList.addToBuy(item));
        assertFalse(testShoppingList.addToBuy(sameName));
        assertTrue(testShoppingList.addToBuy(differentName));
        assertEquals(2, testShoppingList.getToBuy().size());
    }

    @Test
    void testSingleBought(){
        Item i = new Item("strawberry bread", 1, Food, LocalDateTime.now());
        testShoppingList.bought(item);
        assertEquals(0, testShoppingList.amountToBuy());
        assertEquals(0, testShoppingList.getBought().size());

        testShoppingList.addToBuy(item);
        testShoppingList.addToBuy(i);
        assertEquals(2, testShoppingList.amountToBuy());

        testShoppingList.bought(item);
        assertEquals(1, testShoppingList.amountToBuy());
        assertEquals(1, testShoppingList.getBought().size());

    }

    @Test
    void testMultipleBought(){
        testShoppingList.addToBuy(item);

    }
}
