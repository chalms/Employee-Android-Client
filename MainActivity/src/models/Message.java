package models;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import util.Formatter;

public class Message {
	Date createdAt;
	String data; 
	public Integer id; 

	public Message (JSONObject object) throws JSONException {
		if (object.has("data")) data = object.getString("data");
		if (object.has("created_at")) createdAt = Formatter.getDateFromString(object.getString("created_at"));
		if (object.has("id")) {
			id = object.getInt("id") ;
		}
	}
}