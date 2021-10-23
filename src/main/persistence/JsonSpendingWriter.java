package persistence;

import model.Spending;
import org.json.JSONObject;

// This JsonSpendingWriter references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git


public class JsonSpendingWriter extends JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonSpendingWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Home to file
    public void write(Spending spending) {
        JSONObject json = spending.toJson();
        saveToFile(json.toString(TAB));
    }

}
