package persistence;

import model.Categories;
import model.Item;
import model.ShoppingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// test for JsonShoppingWriter
public class JsonShoppingWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ShoppingList shoppingList = new ShoppingList();
            JsonHomeWriter writer = new JsonHomeWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShoppingList() {
        try {
            ShoppingList sl = new ShoppingList();
            JsonShoppingWriter writer = new JsonShoppingWriter("./data/testWriterEmptyShoppingList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonShoppingReader reader = new JsonShoppingReader("./data/testWriterEmptyShoppingList.json");
            sl = reader.read();
            assertEquals(0.0, sl.getBudget());
            assertEquals(0, sl.getToBuy().size());
            assertEquals(0, sl.getBought().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShoppingList() {
        try {
            ShoppingList sl = new ShoppingList();
            JsonShoppingWriter writer = new JsonShoppingWriter("./data/testWriterGeneralShoppingList.json");
            sl.setBudget(100.0);
            Item i1 = new Item("A", 3, Categories.Food, LocalDate.now());
            Item i2 = new Item("B", 3, Categories.Food, LocalDate.now());
            sl.addItem(i1);
            sl.addItem(i2);
            sl.markItem(i1);
            // Bought i1, Tobuy i2
            writer.open();
            writer.write(sl);
            writer.close();

            JsonShoppingReader reader = new JsonShoppingReader("./data/testWriterGeneralShoppingList.json");
            sl = reader.read();
            assertEquals(100.0, sl.getBudget());
            assertEquals(1, sl.getToBuy().size());
            assertEquals("B", sl.getToBuy().get(0).getName());
            assertEquals("A", sl.getSpending().getTransactions().get(0).getItem().getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
