package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static model.Categories.Food;
import static model.Categories.Others;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Item testItem;
    private LocalDateTime t;

    @BeforeEach
    void runBefore() {
        t = LocalDateTime.of(2021,9,16,2,2);
        testItem = new Item("strawberry cake", 2, Food, t);
    }

    @Test
    void testGetters() {
        assertEquals("strawberry cake", testItem.getName());
        assertEquals(2, testItem.getAmount());
        assertEquals(Food, testItem.getCategories());
        assertFalse(testItem.isFavorite());
        testItem.setToFavorite();
        assertTrue(testItem.isFavorite());
    }

    @Test
    void testSetters() {
        Item i = new Item("", 0, Others, t);
        i.setName("donut");
        i.setAmount(1);
        i.setCategories(Food);
        assertEquals("donut", i.getName());
        assertEquals(1, i.getAmount());
        assertEquals(Food, i.getCategories());
    }

    @Test
    void testSetTime() { // TODO: how to test setTime
        assertEquals(t, testItem.getTime());
        testItem.setTime();
        assertEquals(LocalDateTime.now(), testItem.getTime());
    }
}
