package persistence;

import model.Categories;
import model.Item;
import model.Spending;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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


    @Test
    void testWriterGeneralSpending() {
        try {
            Spending spending = new Spending();
            spending.setIncome(100.0);
            spending.setBalance(20);
            Item i1 = new Item("A", 3, Categories.Food, LocalDate.now());
            Item i2 = new Item("B", 3, Categories.Food, LocalDate.now());
            Transaction t1 = new Transaction(i1, 30.00);
            Transaction t2 = new Transaction(i2, 50.00);
            spending.getTransactions().add(t1);
            spending.getTransactions().add(t2);
            spending.trackExpense(spending.getTransactions());
            JsonSpendingWriter writer = new JsonSpendingWriter("./data/testWriterGeneralSpending.json");
            writer.open();
            writer.write(spending);
            writer.close();

            JsonSpendingReader reader = new JsonSpendingReader("./data/testWriterGeneralSpending.json");
            spending = reader.read();
            assertEquals(100.0, spending.getIncome());
            assertEquals(20.0, spending.getBalance());
            assertEquals(80.0, spending.getExpense());
            assertEquals(2, spending.getTransactions().size());
            assertEquals("A", spending.getTransactions().get(0).getItem().getName());
            assertEquals("B", spending.getTransactions().get(1).getItem().getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
