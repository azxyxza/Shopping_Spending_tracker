package persistence;

import model.Home;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static model.Categories.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonHomeWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Home home = new Home();
            JsonHomeWriter writer = new JsonHomeWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHome() {
        try {
            Home home = new Home();
            JsonHomeWriter writer = new JsonHomeWriter("./data/testWriterEmptyHome.json");
            writer.open();
            writer.write(home);
            writer.close();

            JsonHomeReader reader = new JsonHomeReader("./data/testWriterEmptyHome.json");
            home = reader.read();
            assertEquals(0, home.getAll().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHome() {
        try {
            Home home = new Home();
            Item food = new Item("strawberry cake", 2, Food, LocalDate.now());
            Item fruitAndVeg = new Item("apple", 5, FruitAndVegetables, LocalDate.now());
            Item drinks = new Item("milk", 1, Drinks, LocalDate.now());
            Item necessities = new Item("toilet paper", 2, Necessities, LocalDate.now());
            Item others = new Item("pencil", 2, Others, LocalDate.now());
            food.setToFavorite();
            home.addItem(food);
            home.addItem(drinks);
            home.addItem(necessities);
            home.addItem(fruitAndVeg);
            home.addItem(others);

            JsonHomeWriter writer = new JsonHomeWriter("./data/testWriterGeneralHome.json");
            writer.open();
            writer.write(home);
            writer.close();

            JsonHomeReader reader = new JsonHomeReader("./data/testWriterGeneralHome.json");
            home = reader.read();
            assertEquals(5, home.getAll().size());
            assertEquals("strawberry cake", home.getFood().get(0).getName());
            assertEquals("milk", home.getDrinks().get(0).getName());
            assertEquals("apple", home.getFruitAndVeg().get(0).getName());
            assertEquals("toilet paper", home.getNecessities().get(0).getName());
            assertEquals("pencil", home.getOthers().get(0).getName());
            assertEquals("strawberry cake", home.getFavorite().get(0).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
