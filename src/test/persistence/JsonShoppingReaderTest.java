package persistence;

import model.Home;
import model.ShoppingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonShoppingReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonShoppingReader reader = new JsonShoppingReader("./data/noSuchFile.json");
        try {
            ShoppingList shoppingList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShopping() {
        JsonShoppingReader reader = new JsonShoppingReader("./data/testReaderEmptyShopping.json");
        try {
            ShoppingList shoppingList = reader.read();
            assertEquals(0.0, shoppingList.getBudget());
            assertEquals(0, shoppingList.getToBuy().size());
            assertEquals(0, shoppingList.getBought().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShopping() {
        JsonShoppingReader reader = new JsonShoppingReader("./data/testReaderGeneralShopping.json");
        try {
            ShoppingList shoppingList = reader.read();
            assertEquals(100.0, shoppingList.getBudget());
            assertEquals(1, shoppingList.getToBuy().size());
            assertEquals("B", shoppingList.getToBuy().get(0).getName());
            assertEquals("A", shoppingList.getSpending().getTransactions().get(0).getItem().getName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
