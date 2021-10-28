package persistence;

import model.ShoppingList;
import org.json.JSONObject;

// This JsonShoppingWriter references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a writer that writes JSON representation of Shopping List to file
public class JsonShoppingWriter extends JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonShoppingWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Home to file
    public void write(ShoppingList shoppingList) {
        JSONObject json = shoppingList.toJson();
        saveToFile(json.toString(TAB));
    }
}
