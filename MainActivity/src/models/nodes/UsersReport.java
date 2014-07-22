package models.nodes;

import java.util.Date;
import java.util.HashMap;

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
		public HashMap <Integer, ReportTask> reportTasks = new HashMap <Integer, ReportTask> ();
		public JSONObject memento = null;
		public JSONObject params = null;
		

	public void sweep(JSONObject params) throws JSONException {
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
	
	public void buildReportTasks() throws JSONException {
		JSONArray arr = params.getJSONArray("reportTasks");
		for (int i = 0; i < arr.length(); i++) {
			ReportTask task = new ReportTask("#report-task", String.valueOf(Formatter.getGlobalId()), arr.getJSONObject(i).getString("id"),"#report-task"  );
			task.build(arr.getJSONObject(i));
			reportTasks.put(task.users_report_id, task);
		}
	}
	
	public boolean build (JSONObject params) throws JSONException {
			sweep(params);
			if (params.equals(memento)) {
				return false; 
			} 
			
			return true; 
	}
	
	public UsersReport(JSONObject p) {
		params = p; 
	}
}
