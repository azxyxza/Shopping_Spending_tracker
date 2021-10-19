package persistence;

import model.Home;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
}
