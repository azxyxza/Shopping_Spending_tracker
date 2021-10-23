package persistence;

import model.ShoppingList;
import org.json.JSONObject;

// This JsonShoppingWriter references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

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
