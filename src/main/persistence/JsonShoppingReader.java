package persistence;

import model.Categories;
import model.Item;
import model.ShoppingList;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.time.LocalDate.parse;

// Represents a reader that reads Shopping list from JSON data stored in file
public class JsonShoppingReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonShoppingReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads shopping list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ShoppingList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShoppingList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Shopping list from JSON object and returns it
    private ShoppingList parseShoppingList(JSONObject jsonObject) {
        ShoppingList shoppingList = new ShoppingList();
        addShoppingList(shoppingList, jsonObject);
        return shoppingList;
    }

    // EFFECTS: add the budget, tobuy, bought data from Json to current shopping list
    private void addShoppingList(ShoppingList shoppingList, JSONObject jsonObject) {
        double jsonBudget = jsonObject.getDouble("budget");
        shoppingList.setBudget(jsonBudget);

        JSONArray jsonToBuy = jsonObject.getJSONArray("Needs to buy");
        JSONArray jsonTransaction = jsonObject.getJSONArray("Have bought");
        for (Object json : jsonToBuy) {
            JSONObject nextThingy = (JSONObject) json;
            addHistoryItem(shoppingList, nextThingy);
        }
        for (Object json : jsonTransaction) {
            JSONObject nextThingy = (JSONObject) json;
            addHistoryTransaction(shoppingList, nextThingy);
        }
    }

    private void addHistoryTransaction(ShoppingList shoppingList, JSONObject jsonObject) {
        JSONObject item = jsonObject.getJSONObject("item");
        String name = item.getString("name");
        int amount = item.getInt("amount");
        Categories categories = Categories.valueOf(item.getString("categories"));
        String date = item.getString("date");
        Item i = new Item(name, amount, categories, parse(date));
        double expense = jsonObject.getDouble("expense");
        Transaction t = new Transaction(i, expense);
        shoppingList.getSpending().getTransactions().add(t);
    }

    private void addHistoryItem(ShoppingList shoppingList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        Categories categories = Categories.valueOf(jsonObject.getString("categories"));
        String date = jsonObject.getString("date");
        Item item = new Item(name, amount, categories, parse(date));
        shoppingList.addItem(item);

    }
}
