package models;

import java.util.ArrayList;

import main.metrics.MainActivity;
import models.nodes.FireNode;
import models.nodes.Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;
import errors.InvalidParametersException;

public class Model extends FireNode {
	private MainActivity context; 
	private UsersChats userChats;
	private UsersReport userReports = null;
	private Company company = null;
	
	public Model(MainActivity c, JSONObject params){
		super("", String.valueOf(Formatter.getGlobalId()), null, "root"); 
		System.out.println("PARAMETERS GIVEN TO MODEL => " + params.toString());
		
		setParams(c, params);
		callViews(); 
		
	}
	
	public void callViews() {
		context.setRoot(this);
		if (userReports != null) {
			context.setHomeView();
		} else {
			context.getListViewController().renderListView();
		}
	}
	
	public String getTag() {
		return this.getID();
	}
	
	public void setParams(MainActivity c, JSONObject params) {
		setContext(c); 
		setName(params);
		setCompany(params);
		setUserReports(params);
		if (userReports != null) {
			Report report = createReport(); 
			report.build(userReports, c);
			this.childList = new ArrayList <FireNode>(); 
			this.childList.add(report); 
		} 

	}
	
	public Report createReport() {
		System.out.println("Users report built successfully");
		Report report = new Report("Checkin", String.valueOf(Formatter.getGlobalId()), String.valueOf(userReports.id), "Report");
		System.out.println("Report created successfully");
		return report; 
	}
	
	public void createUsersReport(JSONObject params) throws JSONException {
		System.out.println("Creating users report");
		Object usersReports = params.get("users_reports_to_json");
		JSONObject theUsersReportParams; 
		if (usersReports instanceof JSONArray) {
			theUsersReportParams = ((JSONArray) usersReports).getJSONObject(0);
		} else {
			theUsersReportParams = ((JSONObject) usersReports); 
		}
		userReports = new UsersReport(theUsersReportParams);
		System.out.println("Users report created!");
	}
	
	public void setUserReports(JSONObject params) {
		try {
			createUsersReport(params);
			if (userReports.build()) {
				return; 
			} else {
				//context.getMainController().makeToast("User reports are unavailable at the moment");
				return;
			}
		} catch (JSONException e) {
			System.out.println("User reports could not be built");
			e.printStackTrace();
			return;
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
	
	public void setName(JSONObject params){
		try {
			setName(params.getString("name")); 
		} catch (JSONException e) {
			System.out.println("Name setup failed!");
			e.printStackTrace();
			setName("Chalmee");
		}
	}
	
	public void setName(String n) {
		this.name = n; 
	}
	
	public String getName() {
		return name; 
	}

	public Company getCompany() {
		if (company == null) {
			this.company = context.getCompany();
		}
		return company;
	}
	
	public void setCompany(JSONObject params){
		try {
			JSONObject companyObject = params.getJSONObject("company");
			Company comp = getCompany(); 
			if (companyObject.has("id") && !companyObject.isNull("id")) comp.setId(companyObject.getInt("id"));
			if (companyObject.has("name") && !companyObject.isNull("name")) comp.setName(companyObject.getString("name"));
			setCompany(company);
		} catch (JSONException e) {
			System.out.println("Could not get company");
			e.printStackTrace();
		}
	}

	public void setCompany(Company company) {
		this.company = company;
		context.setCompany(company);
		context.getMainController().setCompany(company);
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
