package persistence;

import model.Home;
import org.json.JSONObject;

// This JsonHomeWriter references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a writer that writes JSON representation of Home to file
public class JsonHomeWriter extends JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonHomeWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Home to file
    public void write(Home home) {
        JSONObject json = home.toJson();
        saveToFile(json.toString(TAB));
    }

}
