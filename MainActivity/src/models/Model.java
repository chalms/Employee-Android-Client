package models;

import java.util.ArrayList;

import main.metrics.MainActivity;
import models.nodes.FireNode;
import models.nodes.Report;
import models.nodes.UsersReport;

import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;
import errors.InvalidParametersException;

public class Model extends FireNode {
	private MainActivity context; 
	private UsersChats userChats;
	private UsersReport userReports;
	
	public Model(MainActivity c, JSONObject params){
		
		tryNameSetup(params);
		
		System.out.println("Model.new(..) {parameters must be present}");
		setContext(c); 
		
		Report report = setUserReports(params);
		
		System.out.println("Model.new(..) -> report = setUserReports(..");
		this.childList = new ArrayList <FireNode>(); 
		this.childList.add(report); 
		System.out.println("Model.new(..) -> childlist.add(report) ");
		context.setRoot(this);
		context.setHomeView();
	}
	
	void tryNameSetup(JSONObject params) {
		try {
			setName(params.getString("name"));
		} catch (JSONException e) {
			System.out.println("Name setup failed!");
			e.printStackTrace();
			setName("Chalmee");
		}
	}
	
	public Report setUserReports(JSONObject params) {
		try {
			userReports = new UsersReport(params);
			if (userReports.build(params) ) {
				Report report = new Report("#users_report", 
						String.valueOf(Formatter.getGlobalId()), 
						String.valueOf(userReports.id), 
						String.valueOf(userReports.complete));
				return report; 
			} else {
				context.getMainController().makeToast("User reports are unavailable at the moment");
				return null;
			}
		} catch (JSONException e) {
			System.out.println("User reports could not be built");
			e.printStackTrace();
			return null;
		}
	}
	
	public void setUserChats(JSONObject params){
		try {
			userChats = new UsersChats(params);
			ChalmTree root = new ChalmTree("#root"); 
			root.nextRoute.put("#userchats", userChats.getTree());
		} catch (JSONException e) {
			System.out.println("Error in the creation of user chats [JSON Exception]");
			e.printStackTrace();
		} catch (InvalidParametersException e) {
			System.out.println("Error in the creation of user chats [Invalid Parameters Exception]");
			e.printStackTrace();
		}
	}
	
	public UsersReport getUsersReports() {
		return userReports; 
	}
	
	public UsersChats getUsersChats() {
		return userChats; 
	}

	public MainActivity getContext() {
		return context;
	}

	public void setContext(MainActivity context) {
		this.context = context;
	}
	
	public void setName(String n) {
		this.name = n; 
	}
	
	public String getName() {
		return name; 
	}
	
	
	
	
	
	
	//
//	public void addPart(JSONArray parts) {
//		for (int i = 0; i < parts.length(); i++) {
//			try {
//				this.report.add(PartFactory.build((JSONObject) parts.get(i), createID()));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public void addTask(JSONArray task) {
//		if (task == null) return; 
//		for (int i = 0; i < tasks.length(); i++) {
//			try {
//				report.add(TaskFactory.build((JSONObject) task.get(i), createID()));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			} 
//		}
//	}
}
