package models;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
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
	LocationTime completed_location; 
	ArrayList <String> photoNames; 
	
	public boolean update(JSONObject params) throws JSONException {
		try {
			if (update(params)) {			
			    if (params.has("complete") && !params.isNull("complete")) complete = params.getBoolean("complete");
			    if (params.has("completion_time") && !params.isNull("completion_time")) completionTime = Formatter.getDateFromString(params.getString("completion_time")); 
			    if (params.has("id") && !params.isNull("id")) id = params.getInt("id");
			    if (params.has("note") && !params.isNull("note")) note= params.getString("note");
			    if (params.has("updated_at") && !params.isNull("updated_at")) updated_at = params.getString("updated_at");
			    if (params.has("description") && !params.isNull("description")) description = params.getString("description");
			    if (params.has("location") && !params.isNull("location")) completed_location = LocationTime.build(params); 
			    if (params.has("photos") && !params.isNull("photos")) setPhotos(params.getJSONArray("photos")); 
			} else {
				System.out.println("No change to params!");
			}
		} catch(JSONException e) {
			e.printStackTrace(); 
		}
	    return true; 
	}
	
	public void upload(JSONObject params) {
		
	}
	
	public JSONObject getJson() {
		JSONObject params = new JSONObject(); 
		try { 
			if (complete != null) params.put("complete", complete);
			if (completionTime != null) params.put("completed_time", completionTime.toString());
			if (description != null) params.put("description", description);
			if (note != null) params.put("note", note);
			if (id != null) params.put("id", id);
			if ((updated_at) != null) params.put("updated_at", updated_at);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return params;
	}

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
	
	public String getNote() {
		return note; 
	}
	
	public void setNote(String n) {
		note = n; 
	}
	
	public int getId() {
		return id; 
	}
	
	public void setId(int i) {
		id = i; 
	}
	
	public void setPhotos(JSONArray photos) {
		photoNames = new ArrayList <String> (); 
		for (int i = 0; i < photos.length(); i++) {
			try {
				photoNames.add(photos.getString(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public ArrayList<String> getPhotoNames() {
		return photoNames; 
	}

	    
}
