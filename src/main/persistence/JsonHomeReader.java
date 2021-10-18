package persistence;

import model.Categories;
import model.Home;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.time.LocalDate.parse;

// Represents a reader that reads Home from JSON data stored in file
public class JsonHomeReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonHomeReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Home from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Home read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHome(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses home from JSON object and returns it
    private Home parseHome(JSONObject jsonObject) {
        Home home = new Home();
        addHomes(home, jsonObject);
        return home;
    }

    // MODIFIES: home
    // EFFECTS: parses lists in home from JSON object and adds them to home
    private void addHomes(Home home, JSONObject jsonObject) {
        JSONArray jsonAll = jsonObject.getJSONArray("all");
        for (Object json : jsonAll) {
            JSONObject nextThingy = (JSONObject) json;
            addHistoryItem(home, nextThingy);
        }
    }

    private void addHistoryItem(Home home, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        Categories categories = Categories.valueOf(jsonObject.getString("categories"));
        String date = jsonObject.getString("date");
        boolean isFavorite = jsonObject.getBoolean("favorite");
        Item item = new Item(name, amount, categories, parse(date));
        home.addItem(item);
        if (isFavorite) {
            home.getFavorite().add(item);
        }
    }

}
