package persistence;

import model.Item;
import model.Spending;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonSpendingReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonSpendingReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads shopping list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Spending read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSpending(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    private Spending parseSpending(JSONObject jsonObject) {
        Spending spending = new Spending();
        addSpending(spending, jsonObject);
        return spending;
    }

    private void addSpending(Spending spending, JSONObject jsonObject) {
        double income = jsonObject.getDouble("income");
        double expense = jsonObject.getDouble("expense");
        double balance = jsonObject.getDouble("balance");
        spending.setIncome(income);
        spending.setExpense(expense);
        spending.setBalance(balance);
        JSONArray transaction = jsonObject.getJSONArray("transactions");
        for (Object json : transaction) {
            JSONObject next = (JSONObject) json;
            addHistoryTransaction(spending, next);
        }
    }

    private void addHistoryTransaction(Spending spending, JSONObject jsonObject) {
        String itemName = jsonObject.getString("item");
        Item item = spending.getItem(itemName);
        double expense = jsonObject.getDouble("expense");
        Transaction t = new Transaction(item, expense);
        spending.getTransactions().add(t);
    }


}
