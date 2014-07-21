package main.metrics;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import models.Model;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;

import errors.InvalidParametersException;
import views.LoadingBar;
import web.CallbackWrapper;
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
				companyEmployees.put(response.getJSONObject(i).getString("email"), response.getJSONObject(i).getBoolean("setup"));
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
		context.getRouter().post("/signups", params);
	}
	
	public void stateMessageError() {
		if (state == 0) {
			context.getLoginController().makeToast("Sorry! You could not be logged in");
		} 
	}
	
	private void respondToValidity() {
		if (this.getActiveController() == null) return; 
		EditText t = this.getActiveController().getUserName();
		String str = t.getText().toString();
		if (companyEmployees.containsKey(str)) {
			if (this.getActiveController().controllerName().equals("login")) {
				t.setTextColor(context.getResources().getColor(main.metrics.R.color.green));
				return ; 
			} else {
				context.getLoginController().dismissDialog();
				this.setActiveController(context.getSignupController());
				context.getSignupController().showDialog();
				t = this.getActiveController().getUserName();
				t.setTextColor(context.getResources().getColor(main.metrics.R.color.green));
			}
		} else {
			if  (this.getActiveController().controllerName().equals("signup")) {
					t.setTextColor(context.getResources().getColor(main.metrics.R.color.red));
					return ; 
			} else {
				context.getSignupController().dismissDialog();
				this.setActiveController(context.getLoginController());
				context.getLoginController().showDialog();
				t = this.getActiveController().getUserName();
				t.setTextColor(context.getResources().getColor(main.metrics.R.color.red));
			}
		}
	}
	
	public void getCompanyEmployees() throws InterruptedException {
		if (companyEmployees.isEmpty()) {
			context.getRouter().addRoute("/employees", 
				(new AsyncHttpResponseHandler() {
					public void onSuccess(int statusCode, Header[] headers, byte[] response) {
						String str = new String(response);
						JSONArray employees;
						try {
							employees = new JSONArray(str);
						} catch (JSONException e) {
							System.out.println("The string could not be parsed!");
							return; 
						}
						setEmployeesFromResponse(employees);
					}
					public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
				    	System.out.println("FAILURE");
				    }
				}), (new CallbackWrapper () {
					public void render() {
						respondToValidity();
					};
				})
			);
			context.getRouter().get("/employees");
		} else {
			respondToValidity(); 
		}
		
	}
	
	public void signup(JSONObject params) throws InvalidParametersException, JSONException, UnsupportedEncodingException {
		if (!params.getString("password").equals(params.getString("password_confirmation"))) {
			throw new InvalidParametersException("Password and Password Confirmation do not match");
		}
		context.getRouter().get("/signups");
	}
	
	
	public boolean companyHasEmployee(String email) {
		try {
			if (companyEmployees.containsKey(email)) {
				
			}
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