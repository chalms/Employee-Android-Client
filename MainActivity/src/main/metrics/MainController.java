package main.metrics;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import models.Model;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import util.WebClient;
import views.LoadingBar;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MainController {
	private MainActivity context; 
	private WebClient webClient; 
	private Model model; 
	private int state = 0;
	private LoadingBar loadingBar;
	private HashMap <String, Boolean> companyEmployees =  new HashMap <String, Boolean> (); 
	private int companyId = 1; 
	private ActiveController controller = null; 


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
	
	public void setEmployeesFromResponse(JSONObject response){
		System.out.println("BIG SUCCESS");
		System.out.println(response.toString());
		for (int i = 0; i < response.names().length(); i++) {
			try {
				companyEmployees.put(response.names().getJSONObject(i).getString("email"), response.names().getJSONObject(i).getBoolean("setup"));
			} catch (JSONException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} 
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
	private HashMap<String, Boolean> getCompanyEmployees() {
		if (companyEmployees.isEmpty()) {
			webClient.setEmployees(new String("/companies/" + String.valueOf(companyId) + "/employees.json"));	
		}
		return companyEmployees;
	}
	
	public void signup(JSONObject params) throws InvalidParametersException, JSONException, UnsupportedEncodingException {
		if (params.getString("password") != params.getString("password_confirmation")) {
			throw new InvalidParametersException("Password and Password Confirmation do not match");
		}
		
		webClient.signIn(params, "/signups.json"); 
	}
	
	public boolean companyHasEmployee(String email) {
		return getCompanyEmployees().containsKey(email);
	}
	
	public boolean emailIsSetup(String email) {
		return getCompanyEmployees().get(email);
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