package models.nodes;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;
import views.ListItemContent;

public class ReportTask extends FireNode {
	Boolean complete; 
	Date completionTime; 
	Integer task_id; 
	String note; 
	Date updated_at; 
	private Integer users_report_id; 
	String description; 
	JSONObject memento; 
	
	private String testResult = ""; 
	private String testNote = "";
	
	public ReportTask(String name, String id, String nodeID, String tag) throws JSONException {		
		super(name, id, nodeID, tag);
	}
	
	public boolean sweep (JSONObject params) throws JSONException {
		if (params.equals(memento)) return false; 
	    if (params.has("complete") && !params.isNull("complete")) complete = params.getBoolean("complete");
	    if (params.has("completion_time") && !params.isNull("completion_time")) completionTime = Formatter.getDateFromString(params.getString("completion_time")); 
	    if (params.has("id") && !params.isNull("id")) task_id = params.getInt("id");
	    if (params.has("note") && !params.isNull("note")) note = params.getString("note");
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
			System.out.println("Error sweeping reporttask jsonobject params");
			e.printStackTrace();
		}	
	}
	
	
	public JSONObject upload () {
		JSONObject params = new JSONObject(); 
		try { 
			if (id == null) { return null; } else  params.put("id", id);
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

	String getResult(String result){
		if (result == null){
			return new String("");
		} else {
			return result; 
		}
	}

	@Override
	public int getChecked(){
		return getNumericalResult();
	}

	int ensurePresent(){
		if (this.testResult == null){
			this.testResult = "";
		}
		return checked; 
	}

	public void clear(){ 
		this.testNote = "";
		this.testResult = "";
		this.completed = false;
		this.checked = 0; 
		return; 
	}

	public int getNumericalResult() {	
		System.out.println(this.testResult);
		if (this.testResult.equals("Good"))
			return 4;
		else if (this.testResult.equals("Poor"))
			return 3;
		else if (this.testResult.equals("N/A"))
			return 2;
		else if (this.testResult.equals("Pass"))
			return 1;
		else if (this.testResult.equals("Fail"))
			return -1;			
		else
			return ensurePresent(); 
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
		setCompleted(true);
	}

	public String getTestNote() {
		return testNote;
	}

	public void setTestNote(String testNote) {
		this.testNote = testNote;
		setCompleted(true);
	}

	public void setCompleted(boolean c) {
		this.completed = c;
	}

	@Override
	public boolean checkCompleted(boolean t) {
		if (!this.testResult.equals("")) {
			this.completed = true; 
		} 
		return this.completed; 
	}

	@Override
	public ListItemContent createRowContent () {
		ListItemContent item = new ListItemContent (this);
		item.setChecked(getNumericalResult());
		return item;
	}
	
	public String getTag() {
		return "Leaf"; 
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

	public Integer getUsers_report_id() {
		return users_report_id;
	}

	public void setUsers_report_id(Integer users_report_id) {
		this.users_report_id = users_report_id;
	}
	
	
	    
}
