package Factories;

import models.nodes.Report;
import models.nodes.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskFactory {
	
	
	public static Task build(JSONObject object, String id) {
		// TODO Auto-generated method stub
		try {
			String description = object.getString("description");
			String partID = object.getString("part_name");
			String report_id = object.getString("report_id"); 
			
			Task t = new Task(report_id, report_id, report_id, report_id, report_id, report_id); 
			
			return t; 
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		}
	}

}
