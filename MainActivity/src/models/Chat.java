package models;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import errors.InvalidParametersException;

public class Chat {
	HashMap <Integer, Message> messages = new HashMap <Integer, Message> (); 
	Integer id; 
	
	Chat (JSONObject chat) throws JSONException, InvalidParametersException {
		setMessages(chat.getJSONArray("get_messages")); 
		id = chat.getInt("chat_id");
	}
	
	void setMessages(JSONArray messageObjects) throws JSONException, InvalidParametersException {
		for (int i = 0; i < messageObjects.length(); i++) {
			Message m = new Message(messageObjects.getJSONObject(i)); 
			if (m.id != null) {
				if (messages.containsKey(m.id)) {
					//do some update 
				} else {
					// perhaps a notification
				}
				messages.put(m.id, m);
			}
		}
	}
}

