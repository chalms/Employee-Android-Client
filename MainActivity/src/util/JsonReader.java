package util;

import main.metrics.MainController;
import models.Model;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class JsonReader {

	private Model model;
	
	public JsonReader(Model m) {
		this.model = m;
	}

	public abstract void read(JSONObject response);
	

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
