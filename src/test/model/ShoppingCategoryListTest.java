package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static model.Categories.Food;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCategoryListTest {
    private ShoppingList testShoppingList;
    private Item item;

    @BeforeEach
    void setUp(){
        testShoppingList = new ShoppingList();
        item = new Item("strawberry cake", 2, Food, LocalDate.now());
    }

    @Test
    void testSetBudget(){
        testShoppingList.setBudget(100.00);
        assertEquals(100.00, testShoppingList.getBudget());
    }

    @Test
    void testAddToBuy(){
        Item sameName = new Item("strawberry cake", 2, Food, LocalDate.now());
        Item differentName = new Item("strawberry bread", 1, Food, LocalDate.now());
        testShoppingList.addItem(item);
        testShoppingList.addItem(sameName);
        testShoppingList.addItem(differentName);
        assertEquals(2, testShoppingList.getToBuy().size());
    }

    @Test
    void testBought(){
        Item i = new Item("strawberry bread", 1, Food, LocalDate.now());
        testShoppingList.bought(item);
        assertEquals(0, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());

        testShoppingList.addItem(item);
        testShoppingList.addItem(i);
        assertEquals(2, testShoppingList.totalItem());

        testShoppingList.bought(item);
        assertEquals(1, testShoppingList.totalItem());
        assertEquals(1, testShoppingList.getBought().size());

    }
}
