package main.metrics;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import errors.InvalidParametersException;
import views.LoadingBar;
import web.WebClient;

public class MainController {
	private MainActivity context; 
	private WebClient webClient; 
	private Model model; 
	private int state = 0;
	private LoadingBar loadingBar;
	private HashMap <String, Boolean> companyEmployees =  new HashMap <String, Boolean> (); 
	private int companyId = 1; 
	private ActiveController controller = null; 
	private CountDownLatch doneSignal = new CountDownLatch(1);

	public MainController(MainActivity c) {
		webClient = c.getWebClient();
		setModel(c.getModel());
		context = c; 
	}
	
	public void setActiveController(ActiveController c) {
		controller = c; 
	}
	
	public ActiveController getActiveController() {
		return controller; 
	}
	
	public void setEmployeesFromResponse(JSONArray response){
		System.out.println("BIG SUCCESS");
		System.out.println(response.toString());
		for (int i = 0; i < response.length(); i++) {
			try {
				System.out.println(response.getJSONObject(i).getString("email"));
				System.out.println(response.getJSONObject(i).getString("setup"));
				companyEmployees.put(response.getJSONObject(i).getString("email"), response.getJSONObject(i).getBoolean("setup"));
			} catch (JSONException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} 
		}
		System.out.println("CALLING COUNTDOWN");
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void login(String username, String password) throws JSONException, UnsupportedEncodingException, InvalidParametersException {
		JSONObject params = new JSONObject(); 
		params.put("email", username);
		params.put("password", password);
		webClient.signIn(params, "/logins.json");
	}
	
	public void stateMessageError() {
		if (state == 0) {
			context.getLoginController().makeToast("Sorry! You could not be logged in");
		} 
	}
	
	private HashMap<String, Boolean> getCompanyEmployees() throws InterruptedException {
		if (companyEmployees.isEmpty()) {
			webClient.setEmployees(new String("/companies/" + String.valueOf(companyId) + "/employees.json"));	
			System.out.println("Calling await!");
		
			doneSignal.countDown();
		}
		return companyEmployees;
	}
	
	public ArrayList <String> getCompanyList() {
		BlockingQueue<Result> blockingQueue = new ArrayBlockingQueue<Result>(1);
		
		return null;
		
	}
	
	public void signup(JSONObject params) throws InvalidParametersException, JSONException, UnsupportedEncodingException {
		System.out.println(params.getString("password")); 
		System.out.println(params.getString("password_confirmation")); 
		
		if (!params.getString("password").equals(params.getString("password_confirmation"))) {
			throw new InvalidParametersException("Password and Password Confirmation do not match");
		}
		
		webClient.signIn(params, "/signups.json"); 
	}
	
	public void cancelasync() {
		doneSignal.countDown();
	}
	
	public boolean companyHasEmployee(String email) {
		try {
			return getCompanyEmployees().containsKey(email);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean emailIsSetup(String email) {
		try {
			return getCompanyEmployees().get(email);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void getReport() {
		webClient.get("/todays_reports.json");
	}

	public static void requestError(String errorString) {
		// TODO Auto-generated method stub
	}
	
	public void logout() {
		
	}
	
	public void areYouSure() {
		
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	

	public void userInformationReceived() {
		// To dismiss the dialog
		loadingBar.dismiss();
	}

	public void loggedIn() {
		System.out.println("login called!");
		state = 1; 
		getActiveController().getDialog().dismiss();
		this.loadingBar = new LoadingBar(context, "Updating...", "Grabbing your daily activities");
//		webClient.get("/users");
	}
}