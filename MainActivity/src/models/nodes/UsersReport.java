package models.nodes;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import util.Formatter;

public class UsersReport {
		Integer chatId = null; 
		Date checkin = null; 
		Date checkout = null; 
		Boolean complete = false; 
		Integer id = null;
		Integer locationId = null; 
		Integer managerId = null; 
		Integer reportId = null; 
		HashMap <Integer, Task> reportTasks = new HashMap <Integer, Task> ();
		JSONObject memento = null;
		JSONObject params = null;
		

	public void sweep(JSONObject params) {
		if(params.has("chat_id")) chatId = params.getInt("chat_id"); 
		if(params.has("checkin")) checkin = Formatter.parseDateTime(params.getString("checkin")); 
		if(params.has("checkin")) checkout = Formatter.parseDateTime(params.getString("checkout")); 
		if(params.has("complete")) complete = params.getBoolean("checkout");
		if(params.has("id")) id = params.getInt("id");
		if(params.has("location_id")) locationId = params.getInt("location_id");
		if(params.has("manager_id")) managerId = params.getInt("manager_id");
		if(params.has("report_id")) reportId = params.getInt("report_id");		
		if(params.has("reportTasks")) buildReportTasks(); 
	}
	
	public void buildReportTasks() {
		JSONArray arr = params.getJSONArray("reportTasks");
		for (int i = 0; i < arr.length(); i++) {
			ReportTask task = new ReportTask(arr.getJSONObject(i));
			if (task.id) {
				//new or replace
			} else {
				// update 
			}
			reportTasks.put(task.id, task);
		}
	}
	
	public void build (JSONObject params) {
		sweep(params);
		if (params.equals(memento)) {
			return; 
		} else {
			//re-render 
		}
	}
	
	public UsersReport(JSONObject p) {
		params = p; 
		build(params);
	}
}
