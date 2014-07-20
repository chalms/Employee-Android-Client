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
	private HashMap <String, Boolean> companyEmployees = null; 
	private int companyId = 1; 
	
	
	public MainController(MainActivity c) {
		webClient = c.getWebClient();
		setModel(c.getModel());
		context = c; 
	}
	
	public void login(String username, String password) {
		try {
			webClient.login(username, password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void stateMessageError() {
		if (state == 0) {
			context.getLoginController().makeToast("Sorry! You could not be logged in");
		} 
	}
	
	private HashMap<String, Boolean> getCompanyEmployees() {
		if (companyEmployees == null) {
			JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler() {
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					System.out.println("Get company employees ->");
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
				public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
					System.out.println(e.getMessage());
				}
			};
			webClient.get(new String("/companies/" + String.valueOf(companyId) + "/employees"), jsonResponseHandler);
		}
		return companyEmployees; 
	}
	
	public void signup(JSONObject params) throws InvalidParametersException, JSONException {
		if (params.getString("password") != params.getString("password_confirmation")) {
			throw new InvalidParametersException("Password and Password Confirmation do not match");
		}
		webClient.post("/signups", params); 
	}
	
	public boolean companyHasEmployee(String email) {
		return this.getCompanyEmployees().containsKey(email);
	}
	
	public boolean emailIsSetup(String email) {
		return this.getCompanyEmployees().get(email);
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
		context.getLoginController().dismissDialog();
		this.loadingBar = new LoadingBar(context, "Updating...", "Grabbing your daily activities");
		webClient.get("/users");
	}

	public void searchForEmail(String string, JsonHttpResponseHandler responseHandler) {
		webClient.get("/users/show.json?email=" + string, responseHandler);
	}
}