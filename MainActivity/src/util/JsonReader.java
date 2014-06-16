package util;

import main.metrics.MainController;
import models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

	private Model model; 

	public JsonReader(Model m) {
		this.model = m; 
	}
	
	public void read(String response){
		try {
			JSONObject j = new JSONObject(response);
			JSONArray names = j.names();
			int i = 0; 
			while (i < names.length()) {
				String key = names.optString(i);
				if (key == null) key = "";

				if (key.equals("report")) {
					jsonReport(j.getJSONArray("report")); 
				}
				if (key.equals("chats"))  {
					jsonChats(j.getJSONArray("chats"));
				}
				if (key.equals("managers")) {
					jsonManagers(j.getJSONArray("managers"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			errorMessage(e.getMessage());
		}
		return ;
	}

	public void jsonReport(JSONArray reports) {
		try {
			int i = 0; 
			while (i < reports.length()) {
				model.setOrUpdateReport(reports.optJSONObject(i));
			}
		} catch (Exception e) {
			String errorString = e.getMessage();
			MainController.requestError(errorString);
		}
	}

	public void jsonChats(JSONArray chats) {
		try {
			int i = 0; 
			while (i < chats.length()) {
				model.setOrUpdateChat(chats.optJSONObject(i));
			}
		} catch (Exception e) {
			String errorString = e.getMessage();
			MainController.requestError(errorString);
		}
	}

	public void jsonManagers(JSONArray managers) {
		try {
			int i = 0; 
			while (i < managers.length()) {
				model.setOrUpdateManager(managers.optJSONObject(i));
			}
		} catch (Exception e) {
			String errorString = e.getMessage();
			MainController.requestError(errorString);
		}
	}

	public void errorMessage(String msg){

	}
}






