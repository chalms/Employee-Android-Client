package models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;
import views.ReportTaskLineItem;

public class ReportTask {
	Boolean complete; 
	Date completionTime; 
	Integer id; 
	String note; 
	String updated_at; 
	String description;
	Location completed_location; 
	
	public boolean sweep (JSONObject params) throws JSONException {
	    if (params.has("complete") && !params.isNull("complete")) complete = params.getBoolean("complete");
	    if (params.has("completion_time") && !params.isNull("completion_time")) completionTime = Formatter.getDateFromString(params.getString("completion_time")); 
	    if (params.has("id") && !params.isNull("id")) task_id = params.getInt("id");
	    if (params.has("note") && !params.isNull("note")) testNote = params.getString("note");
	    if (params.has("updated_at") && !params.isNull("updated_at")) updated_at = Formatter.getDateFromString(params.getString("updated_at"));
	    if (params.has("users_report_id") && !params.isNull("users_report_id")) setUsers_report_id(params.getInt("users_report_id"));
	    if (params.has("task") && !params.isNull("task")) {
	    	JSONObject taskObject = params.getJSONObject("task");
	    	description = taskObject.getString("description");
	    	setName(description);
	    }
	    memento = params; 
	    return true; 
	}
	
	public void build(JSONObject params) {
		try {
			if (sweep(params)) {
				System.out.println("Params were updated!");
			} else {
				System.out.println("No change to params!");
			}
		} catch (JSONException e) {
			System.out.println("Error sweeping report task json object params");
			e.printStackTrace();
		}	
	}
	
	
	public JSONObject getJson() {
		JSONObject params = new JSONObject(); 
		try { 
			if (complete != null) params.put("complete", complete);
			if (completionTime != null) params.put("completed_timet", completionTime.toString());
			if (description != null) params.put("description", description);
			if (testNote != null) params.put("note", testNote);
			if (testResult != null) params.put("test_result", testResult);
			if (getUsers_report_id() != null) params.put("users_report_id", getUsers_report_id());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public void setCompleted(boolean c) {
		if (this.completionTime == null) {
			this.completionTime = new Date();  
		}
		this.completed = c;
	}

	@Override
	public boolean checkCompleted() {
		if (!this.testResult.equals("")) {
			this.completed = true; 
		} 
		return this.completed; 
	}

	@Override
	public ReportTaskLineItem createRowContent() {
		ReportTaskLineItem item = new ReportTaskLineItem (this);
		return item;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCompletedTime() {
		return completionTime; 
	}

	public void setCompletionTime(Date completed_at) {
		this.completionTime = completed_at;
	}	
	    
}
