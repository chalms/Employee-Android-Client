package models;

import java.util.Date;
import java.util.HashMap;
import models.nodes.ReportTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;

public class UsersReport {
		public Integer chatId = null; 
		public Date checkin = null; 
		public Date checkout = null; 
		public Boolean complete = false; 
		public Integer id = null;
		public Integer locationId = null; 
		public Integer managerId = null; 
		public Integer reportId = null; 
		public HashMap <String, ReportTask> reportTasks = new HashMap <String, ReportTask> ();
		public JSONObject memento = null;
		public JSONObject params = null;
		public Integer user_id = null; 
		public String name = null;
		

	public boolean sweep(JSONObject params) throws JSONException {
		
		if(params.has("chat_id") && !params.isNull("chat_id")) chatId = params.optInt("chat_id"); 
		
		if(params.has("checkin") && !params.isNull("checkin")) checkin = Formatter.parseDateTime(params.getString("checkin")); 
		
		if(params.has("checkout") && !params.isNull("checkout")) checkout = Formatter.parseDateTime(params.getString("checkout")); 
		
		if(params.has("complete") && !params.isNull("comeplete")) complete = params.getBoolean("checkout");
		
		if(params.has("id") && !params.isNull("id")) id = params.getInt("id");
		
		if(params.has("location_id") && !params.isNull("location_id")) locationId = params.getInt("location_id");
		if(params.has("manager_id") && !params.isNull("manager_id")) managerId = params.getInt("manager_id");	
		
		if(params.has("reports_tasks") && !params.isNull("reports_tasks")) buildReportTasks(); 
		
		if(params.has("user_id") && !params.isNull("user_id")) user_id = params.getInt("user_id");
		if(params.has("id") && !params.isNull("id")) reportId = params.getInt("report_id");	
		
		if(params.has("report")&& !params.isNull("report")) {
			JSONObject reportObject = params.getJSONObject("report");
			if(reportObject.has("name") && !reportObject.isNull("name")) name = reportObject.getString("name");	
		}
		return (params.equals(memento));
	}
	
	public void buildReportTasks() throws JSONException {
		System.out.println("Building report tasks");
		JSONArray arr = params.getJSONArray("reports_tasks");
		for (int i = 0; i < arr.length(); i++) {
			System.out.println("Building a report task");
			ReportTask task = new ReportTask(name, String.valueOf(Formatter.getGlobalId()), arr.getJSONObject(i).getString("id"),"Leaf"  );
			task.build(arr.getJSONObject(i));
			System.out.println("Build task, name =>  " + task.getName());
			reportTasks.put(task.getID(), task);
		}
	}
	
	public boolean build () throws JSONException {
			boolean returnValue = (!sweep(params));
			memento = params; 
			return returnValue; 
	}
	
	public UsersReport(JSONObject p) {
		params = p; 
		
	}
}
