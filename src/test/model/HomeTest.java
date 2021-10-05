package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Categories.*;
import static org.junit.jupiter.api.Assertions.*;

class HomeTest {
    private Item food;
    private Item fruitAndVeg;
    private Item drinks;
    private Item necessities;
    private Item others;
    private Home testHome;

    @BeforeEach
    void runBefore(){
        testHome = new Home();
        food = new Item("strawberry cake", 2, Food);
        fruitAndVeg = new Item("apple", 5, FruitAndVegetables);
        drinks = new Item("milk", 1, Drinks);
        necessities = new Item("toilet paper", 1, Necessities);
        others = new Item("pencil", 2, Others);
    }

    @Test
    void testAddItem(){
        testHome.addItem(food);
        assertEquals(1, testHome.getFood().size());
        assertEquals(1, testHome.getAll().size());
        assertEquals("strawberry cake", testHome.getFood().get(0).getName());
        assertEquals("strawberry cake", testHome.getAll().get(0).getName());

        assertEquals(0, testHome.getDrinks().size());
        assertEquals(0, testHome.getFruitAndVeg().size());
        assertEquals(0, testHome.getNecessities().size());
        assertEquals(0, testHome.getOthers().size());
    }

    @Test
    void testTotalItem(){
        assertEquals(0, testHome.totalItem());
        testHome.addItem(food);
        testHome.addItem(fruitAndVeg);
        testHome.addItem(drinks);
        testHome.addItem(necessities);
        testHome.addItem(others);
        assertEquals(5, testHome.totalItem());
    }

    @Test
    void testGetAmountType(){
        assertEquals(0, testHome.getAmountType(Food));
        Item food2 = new Item("donut", 1, Food);
        Item food3 = new Item("croissant", 1, Food);
        Item food4 = new Item("toast", 1, Food);
        testHome.addItem(food);
        testHome.addItem(food2);
        testHome.addItem(food3);
        testHome.addItem(food4);
        testHome.addItem(fruitAndVeg);
        testHome.addItem(necessities);
        assertEquals(4, testHome.getAmountType(Food));
        assertEquals(1, testHome.getAmountType(FruitAndVegetables));
        assertEquals(1, testHome.getAmountType(Necessities)); // TODO: why return 2???
        assertEquals(0, testHome.getAmountType(Others));
    }

    @Test
    void testAddToFavorite(){
        Item food2 = new Item("donut", 1, Food);
        Item food3 = new Item("croissant", 1, Food);
        testHome.addItem(food);
        testHome.addItem(food2);
        testHome.addItem(food3);
        testHome.addItem(fruitAndVeg);
        assertEquals(0, testHome.getFavorite().size());
        food3.setToFavorite();
        testHome.addToFavorite();
        assertEquals(1, testHome.getFavorite().size());
        assertTrue(testHome.getFavorite().contains(food3));
    }

    @Test
    void testMoveItem(){
        testHome.addItem(drinks);
        testHome.addItem(others);
        assertEquals(2, testHome.getAll().size());
        assertEquals(1, testHome.getAmountType(Others));
        assertEquals(1, testHome.getAmountType(Drinks));

        testHome.moveItem(drinks,Others);
        assertEquals(2, testHome.getAll().size());
        assertEquals(2, testHome.getAmountType(Others));
        assertEquals(0, testHome.getAmountType(Drinks));

        testHome.moveItem(food,Others);
        assertEquals(2, testHome.getAll().size());
        assertEquals(2, testHome.getAmountType(Others));
        assertEquals(0, testHome.getAmountType(Drinks));
    }
}
