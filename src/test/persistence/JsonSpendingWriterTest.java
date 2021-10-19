package persistence;

import model.Spending;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonSpendingWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Spending spending = new Spending();
            JsonSpendingWriter writer = new JsonSpendingWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySpending() {
        try {
            Spending spending = new Spending();
            JsonSpendingWriter writer = new JsonSpendingWriter("./data/testWriterEmptySpending.json");
            writer.open();
            writer.write(spending);
            writer.close();

            JsonSpendingReader reader = new JsonSpendingReader("./data/testWriterEmptySpending.json");
            spending = reader.read();
            assertEquals(0.0, spending.getIncome());
            assertEquals(0.0, spending.getExpense());
            assertEquals(0.0, spending.getBalance());
            assertEquals(0, spending.getTransactions().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
