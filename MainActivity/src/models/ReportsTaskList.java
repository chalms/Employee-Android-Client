package models;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportsTaskList {
	private static ReportsTaskList reportsTaskList = null; 
	UsersReport usersReport = null; 
	HashMap <Integer, ReportTask> reportsTasks = null;
	
	private ReportsTaskList(UsersReport usersRep) {
		 usersReport = usersRep; 
		 reportsTasks = new HashMap <Integer, ReportTask> ();
	}
	
	public static ReportsTaskList build(UsersReport usersRep) {
		if (reportsTaskList == null) {
			reportsTaskList = new ReportsTaskList(usersRep); 
			return reportsTaskList; 
		} else {
			return null; 
		}
 	}
	
	public void updateList(JSONArray reportsTasksJson) throws JSONException {
		for (int i = 0; i < reportsTasksJson.length(); i++) {
			JSONObject reportTask  = reportsTasksJson.getJSONObject(i); 
			if (reportTask.has("id") && !reportTask.isNull("id")) {
				Integer id = reportTask.getInt("id"); 
				if (reportsTasks.containsKey(id)) 
					reportsTasks.get(id).update(reportTask); 
				else 
					reportsTasks.put(id, new ReportTask(reportTask)); 
					
			}
		}
	}

}
