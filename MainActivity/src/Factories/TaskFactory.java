package Factories;

import java.util.Date;

import models.nodes.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskFactory {
	public static Task build(JSONObject object, String id) {
	try {
			String description = object.getString("description");
			String report_id = object.getString("report_id"); 
			String report_index = object.getString("report_index"); 
			String createID = id;
			String actualID = object.getString("id");
			Date completed_at = (Date) object.get("completed_at"); // <--- might fail there 
			String geolocation_id = object.getString("geolocation_id");  
			String name = actualID + report_id; 
			String completed = object.getString("completed");
			Task t = new Task(name, createID, actualID, description, completed_at, report_id, report_index, geolocation_id, completed);
			return t; 
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}


	}
}
