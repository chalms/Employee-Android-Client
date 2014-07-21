package main.metrics;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import errors.InvalidParametersException;
import views.LoadingBar;
import web.WebClient;

public class MainController {
	private MainActivity context; 
	private WebClient webClient; 
	private Model model; 
	private int state = 0;
	private LoadingBar loadingBar;
	private HashMap <String, JSONObject> companyEmployees =  new HashMap <String, JSONObject> (); 
	private ActiveController controller = null; 
	private HashMap <String, String> companies = new HashMap<String, String> (); 
	
	public MainController(MainActivity c) {
		setWebClient(c.getWebClient());
		setModel(c.getModel());
		context = c; 
	}
	
	public void setActiveController(ActiveController c) {
		controller = c; 
	}
	
	public ActiveController getActiveController() {
		return controller; 
	}
	
	public void printParams(JSONArray response, int i) {
		try {
			System.out.println(response.getJSONObject(i).getString("email"));
			System.out.println(response.getJSONObject(i).getString("setup"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEmployeesFromResponse(JSONArray response){
		System.out.println(response.toString());
		for (int i = 0; i < response.length(); i++) {
			try {
				printParams(response, i);
				companyEmployees.put(response.getJSONObject(i).getString("email"), response.getJSONObject(i));
			} catch (JSONException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} 
		}
		respondToValidity(); 
	}
	
	public void login(String username, String password) throws JSONException, UnsupportedEncodingException, InvalidParametersException {
		System.out.println("Logging in with: ");
		System.out.println("Email: |" + username.toString() + "|");
		System.out.println("Password: |" + password.toString() + "|");
		JSONObject params = new JSONObject(); 
		params.put("email", username);
		params.put("password", password);
		System.out.println("Posting with params: |" + params.toString() + "|");
		context.getRouter().post("/logins", params);
	}
	
	public void stateMessageError() {
		if (state == 0) {
			context.getLoginController().makeToast("Sorry! You could not be logged in");
		} 
	}
	
	public void respondToValidity() {
		if (this.getActiveController() == null) return; 
		EditText t = this.getActiveController().getUserName();
		String str = t.getText().toString();
		System.out.println("Matching: " + str);
		try {
			if (companyEmployees.containsKey(str)) {
				System.out.println("contains key");
				JSONObject employee = companyEmployees.get(str);
				if (employee.getBoolean("setup")) {
					if (this.getActiveController().controllerName().equals("signup")) {
							System.out.println("signup controller");
							context.getSignupController().dismissDialog();
							this.setActiveController(context.getLoginController());
							context.getLoginController().showDialog();
							t = this.getActiveController().getUserName();
							t.setText(str);
					}
				} else if (this.getActiveController().controllerName().equals("login")) {
						System.out.println("login controller");
						context.getLoginController().dismissDialog();
						this.setActiveController(context.getSignupController());
						context.getSignupController().showDialog();
						t = this.getActiveController().getUserName();
						t.setText(str);
				}
				t.setTextColor(context.getResources().getColor(main.metrics.R.color.green));
			} else {
				t.setTextColor(context.getResources().getColor(main.metrics.R.color.red));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getCompanyEmployees() throws InterruptedException {
		if (companyEmployees.isEmpty()) {
			context.getRouter().get("/special_index");
		} else {
			respondToValidity(); 
		}
		
	}
	
	public void signup(JSONObject params) throws InvalidParametersException, JSONException, UnsupportedEncodingException {
		if (!params.getString("password").equals(params.getString("password_confirmation"))) {
			throw new InvalidParametersException("Password and Password Confirmation do not match");
		}
		context.getRouter().post("/signups", params);
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

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public void setCompaniesList(JSONArray arr) {
		for (int i = 0; i < arr.length(); i++) {
			try {
				companies.put(arr.getJSONObject(i).getString("name"),arr.getJSONObject(i).getString("id"));
			} catch (JSONException e) {
				try {
					System.out.println("Problem adding: " + arr.getString(i) + " to companies");
				} catch (JSONException e1) {
					System.out.println("JSONArray arr(" + String.valueOf(i) + ") is not a string");
					e1.printStackTrace();
				}
				e.printStackTrace();
			} 
		}
	}

	public void displayCompaniesSelector() {
		if (companies.size() > 0) {
			List<String> spinnerArray = new ArrayList <String> (companies.keySet());
		    ArrayAdapter <String> adapter = new ArrayAdapter<String> (context, android.R.layout.simple_spinner_item, spinnerArray);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			context.getSignupController().getSpinner().setAdapter(adapter);
		}
	}
}