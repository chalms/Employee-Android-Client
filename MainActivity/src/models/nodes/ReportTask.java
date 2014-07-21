package models.nodes;

import java.util.Date;

import org.json.JSONObject;

import util.Formatter;

public class ReportTask {
	Boolean complete; 
	Date completion_time; 
	Integer task_id; 
	String note; 
	Date updated_at; 
	Integer users_report_id; 
	
	public ReportTask(JSONObject params) {		
        if (params.has("complete")) complete = params.getBoolean("complete");
        if (params.has("completion_time")) completion_time = Formatter.getDateFromString(params.getString("completion_time")); 
        if (params.has("id")) task_id = params.getInt("id");
        if (params.has("note")) note = params.getString("note");
        if (params.has("updated_at")) updated_at = Formatter.getDateFromString(params.getString("updated_at"));
        if (params.has("users_report_id")) users_report_id = params.getInt("users_report_id");
        if (params.has("description")) Tasks.put(task_id, params.getString("description")); 
	}
}
