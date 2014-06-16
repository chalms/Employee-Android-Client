package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Factories.EquipmentFactory;
import Factories.TaskFactory;
import main.metrics.MainActivity;
import models.nodes.Report;

public class Model {
	private Report report = null; 
	private ArrayList <String> errors; 
	private Integer globalId = 0;
	private HashMap<String, Chat> chats;
	private HashMap<String, Report> reports; 
	private HashMap<String, Manager> managers; 
	private boolean saved = true; 
	private MainActivity context; 
	
	public boolean upload() {
// <----- below is the untest method call for chats
//		for (String key : chats.keySet()) {
//			chats.get(key).upload(); 
//		}
		for (String key : reports.keySet()) {
			reports.get(key).upload(); 
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
		context = c; 
	}

	public void addEquipment(JSONArray equipment) {
		for (int i = 0; i < equipment.length(); i++) {
			try {
				this.report.add(EquipmentFactory.build((JSONObject) equipment.get(i), createID()));
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

	public void addManager(JSONObject managerJSON) throws JSONException {
		if (managerJSON == null) return; 
		if (this.report.manager == null) {
			this.report.manager = new Manager();
		}
		while (managerJSON.keys().hasNext()) {
			String key = (String) managerJSON.keys().next(); 
			if (key.equals("first_name") && (managerJSON.optString(key) != null)) {
				this.report.manager.setFirstName(managerJSON.getString(key));
			}
			if (key.equals("last_name") && (managerJSON.optString(key) != null)) {
				this.report.manager.setLastName(managerJSON.getString(key));
			}
			if (key.equals("email") && (managerJSON.optString(key) != null)) {
				this.report.manager.setEmail(managerJSON.getString(key));
			}
			if (key.equals("id") && (managerJSON.optString(key) != null)) {
				this.report.manager.setId(managerJSON.getString(key));
			}
			
			if (key.equals("company_name") && (managerJSON.optString(key) != null)) {
				this.report.manager.setCompany_name(managerJSON.getString(key));
			}
		}
	}

	public void setReportDate(Report tempReport, JSONObject jObject) {
		Date reportDate;
		String dateString = jObject.optString("report_date");
		if (dateString != null) {
			reportDate = stringToDate(dateString);
			if (Date.class.isInstance(reportDate)) {
				tempReport.setDate(reportDate);
			}
		}
	}

	public void setOrUpdateReport(JSONObject jObject) throws JSONException {
		if (this.reports == null) {
			this.reports = new HashMap <String, Report> (); 
		}
		Report tempReport = new Report(context.getWebClient());
		String checkinString = jObject.optString("checkin");
		if (checkinString != null) {
			tempReport.setCheckin(stringToDate(checkinString));
		}
		String checkoutString = jObject.optString("checkout");
		if (checkoutString != null) {
			tempReport.setCheckout(stringToDate(checkoutString));
		}
		addManager(jObject.optJSONObject("manager"));
		
		setReportDate(tempReport, jObject);
		Calendar reportDate = Calendar.getInstance();
		reportDate.setTime(tempReport.getReportDate());
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date());
		String description = jObject.optString("description");

		if (description != null) {
			tempReport.setDescription(description);
		}
		
		addEquipment(jObject.optJSONArray("equipment"));
		addTask(jObject.optJSONArray("task")); 

		if (cal.get(Calendar.DATE) == reportDate.get(Calendar.DATE)) {
			this.report = tempReport; 
		} 
		this.reports.put(tempReport.hashString(), tempReport); 
		
	}

	public void setOrUpdateChat(JSONObject jObject) throws JSONException {
		if (this.chats == null) { 
			this.chats = new HashMap <String, Chat>(); 
		}
		
		if (jObject == null) return ; 
		Chat chat = new Chat(); 
		JSONObject manager = jObject.optJSONObject("manager");
		
		if (manager == null) { 
			addError("A manager must be included in chat!");
			return; 
		}
		
		addManager(manager);
		chat.setManager(this.managers.get(manager.optString("email")));
		JSONArray messages = jObject.optJSONArray("messages");
		
		if (messages == null) { 
			addError("No messages found!"); return ; 
		}
		
		int i = 0; 
		while (i < messages.length()) {
			addMessage(messages.getJSONObject(i), chat);
		}
		this.chats.put(chat.getManager().getEmail(), chat);
	}

	private void addMessage(JSONObject jsonObject, Chat chat) {
		// TODO Auto-generated method stub
		Message message = new Message (); 
		message.setChat(chat);
		String messageBody = jsonObject.optString("messageBody");
		if (messageBody != null) {
			message.setMessageBody(messageBody);
			if (message != null) {
				chat.addMessage( message);
			}
		}
	}

	public void setOrUpdateManager(JSONObject jObject) {
		if (this.managers == null) {
			this.managers = new HashMap <String, Manager> (); 
		}

		if (jObject == null) return ; 
		Manager manager = new Manager(); 

		String str = jObject.optString("firstName");
		if (str != null) manager.setFirstName(str);

		str = jObject.optString("lastName");
		if (str != null) manager.setLastName(str);

		str = jObject.optString("email");
		if (str != null) manager.setEmail(str);

		str = jObject.optString("id");
		if (str != null) manager.setId(str);

		managers.put(manager.getEmail(), manager);
	}

	public Date stringToDate(String token){
		try {
			DateFormat formatter = null;
			if (token.contains("-")) {
				formatter = new SimpleDateFormat("dd-MMM-yy", Locale.CANADA);
			} else {
				formatter = new SimpleDateFormat("HH:mm", Locale.CANADA);
			}
			Date d = (Date) formatter.parse(token);
			return d; 
		} catch (ParseException ex) {
			return null; 
		}
	}; 

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
}
