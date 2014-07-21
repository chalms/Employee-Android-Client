package util;

import models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DefaultReader extends JsonReader {
	public DefaultReader(Model m) {
		super(m);
	}

	@Override
	public void read(JSONObject response) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Reading response: " + response.toString());
			JSONArray names = response.names();
			int i = 0; 
			while (i < names.length()) {
				String key = names.optString(i);
				if (key == null) key = "";
				if (key.equals("reports")) {
					jsonReport(response.getJSONArray("reports")); 
				}
				if (key.equals("chats"))  {
					jsonChats(response.getJSONArray("chats"));
				}
				if (key.equals("managers")) {
					jsonManagers(response.getJSONArray("managers"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Error thrown with message: " + e.getMessage());
			errorMessage(e.getMessage());
		}
		return ;
	}

}
