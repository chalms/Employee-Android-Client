package util;

import main.metrics.MainController;
import models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReader {

	private Model model;
	
	public JsonReader() {} 
	public JsonReader(Model m) {
		this.model = m;
	}

	public void read(JSONObject response){
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

	public void jsonReport(JSONArray reports) {
		try {
			int i = 0;
			while (i < reports.length()) {
				model.setOrUpdateReports(reports.optJSONObject(i));
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

	public void errorMessage(String msg) {

	}
}
