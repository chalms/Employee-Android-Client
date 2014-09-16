package models;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;

public class UsersReport {
		public static UsersReport usersReport = null; 
		public static final String route = "/daily_report.json"; 
		
		public Integer id = null; 
		public Date checkin = null; 
		public Date checkout = null; 
		public Boolean complete = false; 
		public String updatedAt = null; 
		public ReportsTaskList reportsTaskList = null; 
		
	
	public boolean changed(JSONObject params) {
		if(params.has("updated_at") && !params.isNull("updated_at")) 
			return !(updatedAt.equals(Formatter.getDateFromString(params.optString("updated_at")))); 
		else return false; 
	}

	public void update(JSONObject params) throws JSONException {
		if (changed(params)) {
			if(params.has("checkin") && !params.isNull("checkin")) checkin = Formatter.parseDateTime(params.getString("checkin")); 
			if(params.has("checkout") && !params.isNull("checkout")) checkout = Formatter.parseDateTime(params.getString("checkout")); 
			if(params.has("complete") && !params.isNull("comeplete")) complete = params.getBoolean("checkout");
			if(params.has("id") && !params.isNull("id")) id = params.getInt("id");
			if(params.has("reports_tasks") && !params.isNull("reports_tasks")) buildReportTasks(params); 
		}
	}
	
	public void buildReportTasks(JSONObject params) throws JSONException {
		JSONArray arr = params.getJSONArray("reports_tasks");
		if (reportsTaskList == null) reportsTaskList = ReportsTaskList.build(this); 
		reportsTaskList.updateList(arr);
	}
	
	private UsersReport(JSONObject p) {
		
	}
}
