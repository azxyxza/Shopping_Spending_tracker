package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static model.Categories.*;
import static org.junit.jupiter.api.Assertions.*;

class HomeTest {
    private Item food;
    private Item fruitAndVeg;
    private Item drinks;
    private Item necessities;
    private Item others;
    private Home testHome;
    private LocalDate t;

    @BeforeEach
    void runBefore(){
        testHome = new Home();
        t = LocalDate.now();
        food = new Item("strawberry cake", 2, Food, t);
        fruitAndVeg = new Item("apple", 5, FruitAndVegetables,t);
        drinks = new Item("milk", 1, Drinks,t);
        necessities = new Item("toilet paper", 2, Necessities,t);
        others = new Item("pencil", 2, Others,t);
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
    void testDeleteItem(){
        testHome.addItem(food);
        testHome.addItem(fruitAndVeg);
        assertEquals(2, testHome.getAll().size());
        assertEquals(food, testHome.getFood().get(0));
        assertEquals(fruitAndVeg, testHome.getFruitAndVeg().get(0));

        testHome.deleteItem(food);
        assertEquals(1, testHome.getAll().size());
        assertFalse(testHome.getFood().contains(food));
        assertFalse(testHome.isContained("strawberry cake"));
        assertEquals(fruitAndVeg, testHome.getFruitAndVeg().get(0));
    }

    @Test
    void testGetAmountType(){
        assertEquals(0, testHome.getTypeAmount(Food));

        Item donut = new Item("donut", 1, Food, t);
        Item croissant = new Item("croissant", 1, Food,t);
        Item toast = new Item("toast", 1, Food, t);
        testHome.addItem(food);
        testHome.addItem(donut);
        testHome.addItem(croissant);
        testHome.addItem(toast);
        testHome.addItem(necessities);

        assertEquals(4, testHome.getTypeAmount(Food));
        assertEquals(1, testHome.getTypeAmount(Necessities));
        assertEquals(0, testHome.getTypeAmount(FruitAndVegetables));
        assertEquals(0, testHome.getTypeAmount(Others));
    }

    @Test
    void testAddToFavorite(){
        Item food2 = new Item("donut", 1, Food, t);
        Item food3 = new Item("croissant", 1, Food, t);
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
        assertEquals(1, testHome.getTypeAmount(Others));
        assertEquals(1, testHome.getTypeAmount(Drinks));

        testHome.moveItem(drinks,Others);
        assertEquals(2, testHome.getAll().size());
        assertEquals(2, testHome.getTypeAmount(Others));
        assertEquals(0, testHome.getTypeAmount(Drinks));

        testHome.moveItem(food,Others);
        assertEquals(2, testHome.getAll().size());
        assertEquals(2, testHome.getTypeAmount(Others));
        assertEquals(0, testHome.getTypeAmount(Drinks));
    }

}
