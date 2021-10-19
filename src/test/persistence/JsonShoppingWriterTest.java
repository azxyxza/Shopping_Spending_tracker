package persistence;

import model.ShoppingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
}
