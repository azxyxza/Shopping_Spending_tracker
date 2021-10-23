package persistence;

import org.json.JSONObject;


// This Writable references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    // EFFECTS: return this as JSON object
    JSONObject toJson();
}
