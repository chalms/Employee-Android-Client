package models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import main.metrics.MainActivity;
import models.nodes.Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;
import errors.InvalidParametersException;
import factories.PartFactory;
import factories.TaskFactory;

public class Model {
	private Report report = null; 
	private ArrayList <String> errors; 
	private Integer globalId = 0;
	private HashMap<String, Report> reports; 
	private boolean saved = true; 
	private MainActivity context; 
	private UsersChats chats; 
	
	public boolean upload() {
		for (String key : reports.keySet()) {
			reports.get(key).update(); 
		}
		return true; 
	}

	public String createID() {
		String nug = globalId.toString();
		globalId = globalId + 1;
		return nug;
	}

	public Model(MainActivity c){
		this.errors = new ArrayList <String> (); 
		setContext(c); 
	}

	public void addPart(JSONArray parts) {
		for (int i = 0; i < parts.length(); i++) {
			try {
				this.report.add(PartFactory.build((JSONObject) parts.get(i), createID()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void addTask(JSONArray task) {
		if (task == null) return; 
		for (int i = 0; i < task.length(); i++) {
			try {
				report.add(TaskFactory.build((JSONObject) task.get(i), createID()));
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
	}

	public void setReportDate(Report tempReport, JSONObject jObject) {
		Date reportDate;
		String dateString = jObject.optString("report_date");
		if (dateString != null) {
			reportDate = Formatter.getDateFromString(dateString);
			if (Date.class.isInstance(reportDate)) {
				tempReport.setDate(reportDate);
			}
		}
	}

	public void setOrUpdateReports(JSONObject jObject) throws JSONException {
		if (this.reports == null) {
			this.reports = new HashMap <String, Report> (); 
		}
		
		String checkinString = jObject.optString("checkin");
		
		if (checkinString != null) {
			tempReport.setCheckin(stringToDate(checkinString));
		}
		
		String checkoutString = jObject.optString("checkout");
		if (checkoutString != null) {
			tempReport.setCheckout(stringToDate(checkoutString));
		}
		
		setReportDate(tempReport, jObject);
		Calendar reportDate = Calendar.getInstance();
		reportDate.setTime(tempReport.getReportDate());
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date());
		String description = jObject.optString("description");

		if (description != null) {
			tempReport.setDescription(description);
		}
		
		addPart(jObject.optJSONArray("parts"));
		addTask(jObject.optJSONArray("task")); 

		if (cal.get(Calendar.DATE) == reportDate.get(Calendar.DATE)) {
			this.report = tempReport; 
		} 
		this.reports.put(tempReport.hashString(), tempReport); 
		
	}

	public Report getReport() {
		return this.report; 
	}

	public void addError(String error) {
		this.errors.add(error);
	}

	public boolean saved() {
		return saved;
	}

	public boolean isSaved() {
		return saved;
	}

	public void saveChanges() {
		saved = false; 
		saved = upload();
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public MainActivity getContext() {
		return context;
	}

	public void setContext(MainActivity context) {
		this.context = context;
	}
}
