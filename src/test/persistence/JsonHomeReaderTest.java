package persistence;

import model.Home;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonHomeReaderTest{
    @Test
    void testReaderNonExistentFile() {
        JsonHomeReader reader = new JsonHomeReader("./data/noSuchFile.json");
        try {
            Home home = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHome() {
        JsonHomeReader reader = new JsonHomeReader("./data/testReaderEmptyHome.json");
        try {
            Home home = reader.read();
            assertEquals(0, home.getAll().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHome() {
        JsonHomeReader reader = new JsonHomeReader("./data/testReaderGeneralHome.json");
        try {
            Home home = reader.read();
            assertEquals(5, home.getAll().size());
            assertEquals("strawberry cake", home.getFood().get(0).getName());
            assertEquals("milk", home.getDrinks().get(0).getName());
            assertEquals("apple", home.getFruitAndVeg().get(0).getName());
            assertEquals("toilet paper", home.getNecessities().get(0).getName());
            assertEquals("pencil", home.getOthers().get(0).getName());
            assertEquals("strawberry cake", home.getFavorite().get(0).getName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
