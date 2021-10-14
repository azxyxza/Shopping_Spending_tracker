package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static model.Categories.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListTest {
    private ShoppingList testShoppingList;
    private Item item;

    @BeforeEach
    void setUp() {
        testShoppingList = new ShoppingList();
        item = new Item("strawberry cake", 2, Food, LocalDate.now());
    }


    @Test
    void testSetBudget() {
        testShoppingList.setBudget(100.00);
        assertEquals(100.00, testShoppingList.getBudget());
    }

    @Test
    void testTotalItem() {
        assertEquals(testShoppingList.getToBuy().size(), testShoppingList.totalItem());
    }

    @Test
    void addItemOne() {
        assertEquals(0, testShoppingList.totalItem());
        testShoppingList.addItem(item);
        assertEquals(1, testShoppingList.totalItem());

        testShoppingList.addItem(item); // same object
        assertEquals(1, testShoppingList.totalItem());

        Item cake = new Item("strawberry cake", 2, Others, LocalDate.now());
        testShoppingList.addItem(cake); // same name, different object
        assertEquals(1, testShoppingList.totalItem());
    }


    @Test
    void testAddItemMultipleAndRemove() {
        Item juice = new Item("apple juice", 2, Drinks, LocalDate.now());
        Item nuts = new Item("nuts", 1, Food, LocalDate.now());
        Item peach = new Item("peach", 3, FruitAndVegetables, LocalDate.now());
        testShoppingList.addItem(juice);
        testShoppingList.addItem(nuts);
        testShoppingList.addItem(peach);
        assertEquals(3, testShoppingList.totalItem());
        testShoppingList.deleteItem(juice);
        assertEquals(2, testShoppingList.totalItem());
        assertTrue(testShoppingList.isContained("nuts"));
        assertTrue(testShoppingList.isContained("peach"));
        assertFalse(testShoppingList.isContained("apple juice"));
    }

    @Test
    void testMarkItem() {
        Item i = new Item("strawberry bread", 1, Food, LocalDate.now());
        testShoppingList.markItem(item);
        assertEquals(0, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());

        testShoppingList.addItem(item);
        testShoppingList.addItem(i);
        assertEquals(2, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());


        testShoppingList.markItem(item);
        assertEquals(1, testShoppingList.totalItem());
        assertEquals(1, testShoppingList.getBought().size());
    }

    @Test
    void testAddTransaction() {
        Item i = new Item("strawberry bread", 1, Food, LocalDate.now());
        testShoppingList.getBought().add(item);
        testShoppingList.getBought().add(i);
        testShoppingList.addTransaction();
        assertEquals(2, testShoppingList.getSpending().transactions.size());

        testShoppingList.getBought().add(i);
        testShoppingList.addTransaction();
        assertEquals(3, testShoppingList.getSpending().transactions.size());
    }
}
