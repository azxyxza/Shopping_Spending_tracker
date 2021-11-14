package model;

import model.exception.AvoidDuplicateException;
import model.exception.NotInTheListException;
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
        try {
            testShoppingList.addItem(item);
        } catch (AvoidDuplicateException e) {
            fail("AvoidDuplicateException thrown, not expected");
        }
        assertEquals(1, testShoppingList.totalItem());

        try {
            testShoppingList.addItem(item); // same object
        } catch (AvoidDuplicateException e) {
            // expected
        }
        assertEquals(1, testShoppingList.totalItem());

        Item cake = new Item("strawberry cake", 2, Others, LocalDate.now());

        try {
            testShoppingList.addItem(cake); // same name, different object
        } catch (AvoidDuplicateException e) {
            // expected
        }
        assertEquals(1, testShoppingList.totalItem());
    }


    @Test
    void testAddItemMultipleAndRemoveWithException() {
        try {
            testShoppingList.deleteItem(item);
        } catch (NotInTheListException e) {
            // expected
        }
        assertEquals(0, testShoppingList.totalItem());
    }

    @Test
    void testAddItemMultipleAndRemove() {
        Item juice = new Item("apple juice", 2, Drinks, LocalDate.now());
        Item nuts = new Item("nuts", 1, Food, LocalDate.now());
        Item peach = new Item("peach", 3, FruitAndVegetables, LocalDate.now());
        try {
            testShoppingList.addItem(juice);
            testShoppingList.addItem(nuts);
            testShoppingList.addItem(peach);
        } catch (AvoidDuplicateException e) {
            fail("AvoidDuplicateException thrown, not expected");
        }
        assertEquals(3, testShoppingList.totalItem());

        try {
            testShoppingList.deleteItem(juice);
        } catch (NotInTheListException e) {
            fail("NotInTheListException thrown, not expected");
        }
        assertEquals(2, testShoppingList.totalItem());
        assertTrue(testShoppingList.isContained("nuts"));
        assertTrue(testShoppingList.isContained("peach"));
        assertFalse(testShoppingList.isContained("apple juice"));
    }

    @Test
    void testMarkItemWithException() {
        assertEquals(0, testShoppingList.getBought().size());
        try {
            testShoppingList.markItem(item);
        } catch (NotInTheListException e) {
            // expected
        }
        assertEquals(0, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());
    }

    @Test
    void testMarkItemNoException() {
        Item i = new Item("strawberry bread", 1, Food, LocalDate.now());
        assertEquals(0, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());

        try {
            testShoppingList.addItem(item);
            testShoppingList.addItem(i);
        } catch (AvoidDuplicateException e) {
            fail("AvoidDuplicateException thrown, not expected");
        }
        assertEquals(2, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());

        try {
            testShoppingList.markItem(item);
        } catch (NotInTheListException e) {
            fail("NotInTheListException thrown, not expected");
        }
        assertEquals(1, testShoppingList.totalItem());
        assertEquals(0, testShoppingList.getBought().size());
        assertEquals(1, testShoppingList.getSpending().getTransactions().size());
    }

    @Test
    void testAddTransaction() {
        Item i = new Item("strawberry bread", 1, Food, LocalDate.now());
        testShoppingList.getBought().add(item);
        testShoppingList.getBought().add(i);
        assertEquals(2, testShoppingList.getBought().size());
        testShoppingList.addTransaction();
        assertEquals(2, testShoppingList.getSpending().transactions.size());
        assertEquals(0, testShoppingList.getBought().size());

        testShoppingList.getBought().add(i);
        testShoppingList.addTransaction();
        assertEquals(3, testShoppingList.getSpending().transactions.size());
        assertEquals(0, testShoppingList.getBought().size());
    }

    @Test
    void testConvertToCategory(){
        assertEquals(Food, testShoppingList.convertToCategory("Food"));
        assertEquals(FruitAndVegetables, testShoppingList.convertToCategory("Fruit And Vegetables"));
        assertEquals(Drinks, testShoppingList.convertToCategory("Drinks"));
        assertEquals(Necessities, testShoppingList.convertToCategory("Necessities"));
        assertEquals(Others, testShoppingList.convertToCategory("Others"));
    }
}
