package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonSpendingReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonSpendingReader reader = new JsonSpendingReader("./data/noSuchFile.json");
        try {
            Spending spending = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySpending() {
        JsonSpendingReader reader = new JsonSpendingReader("./data/testReaderEmptySpending.json");
        try {
            Spending spending = reader.read();
            assertEquals(0.0, spending.getIncome());
            assertEquals(0.0, spending.getExpense());
            assertEquals(0.0, spending.getBalance());
            assertEquals(0, spending.getTransactions().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSpending() {
        JsonSpendingReader reader = new JsonSpendingReader("./data/testReaderGeneralSpending.json");
        try {
            Spending spending = reader.read();
            assertEquals(100.0, spending.getIncome());
            assertEquals(20.0, spending.getBalance());
            assertEquals(80.0, spending.getExpense());
            assertEquals(2, spending.getTransactions().size());
            assertEquals("A", spending.getTransactions().get(0).getItem().getName());
            assertEquals("B", spending.getTransactions().get(1).getItem().getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
