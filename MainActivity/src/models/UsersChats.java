package models;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import errors.InvalidParametersException;

class UsersChats {
	HashMap <Integer, Chat> chats = new HashMap <Integer, Chat> (); 
	UsersChats(JSONObject object) throws JSONException, InvalidParametersException {
		JSONArray chatsArray = object.getJSONArray("users_chats_to_json");
		setChats(chatsArray); 
	}
	void setChats (JSONArray chatsArray) throws JSONException, InvalidParametersException {
		for (int i = 0; i < chatsArray.length(); i++) {
			Chat c = new Chat(chatsArray.getJSONObject(i)); 
			if (c.id != null) {
				if (chats.containsKey(c.id)) {
					//can set some update params
				} else {
					// perhaps a notification
				}
				chats.put(c.id, c);
			}
		}
	}
}