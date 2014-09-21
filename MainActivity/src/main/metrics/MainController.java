package main.metrics;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Company;
import models.LocationTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import errors.InvalidParametersException;
import views.LoadingBar;
import web.WebClient;

public class MainController {
	private MainActivity context; 
	private WebClient webClient; 
	private Company company; 
	private LoadingBar loadingBar;
	private HashMap <String, JSONObject> companyEmployees =  new HashMap <String, JSONObject> (); 
	private ControllerHelper controller = null; 
	private HashMap <String, String> companies = new HashMap<String, String> (); 

	public MainController(MainActivity c) {
		setWebClient(c.getWebClient());
		setCompany(c.getCompany());
		context = c; 
	}
	
	public void setControllerHelper(ControllerHelper c) {
		controller = c; 
	}
	
	public ControllerHelper getControllerHelper() {
		return controller; 
	}
	
	public HashMap <String, String> getCompanies() {
		return companies; 
	}

	public void stateMessageError() {
		if (!context.loggedIn) {
			context.getLoginController().makeToast("Sorry! You could not be logged in");
		} 
	}
	
	public void loadComplete() {
		this.loadingBar.dismiss();
	}

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public void getHome() {
//		if (UsersReport.usersReport == null) 
//			this.context.getCheckinController().render(UsersReport.build()); 
			
//		if (UsersReport.usersReport.checkin == null)
//			this.context.getCheckinController().render(); 
//		else
//			this.context.getUsersReportController().render(); 
	}
	
	public void getHome(JSONObject j){
		System.out.println(j);
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
	
	public void printLogin(String username, String password) {
		System.out.println("Logging in with: ");
		System.out.println("Email: |" + username.toString() + "|");
		System.out.println("Password: |" + password.toString() + "|");
	}
	
	public void login(String username, String password) throws JSONException, UnsupportedEncodingException, InvalidParametersException {
		printLogin(username, password);
		JSONObject params = new JSONObject(); 
		params.put("email", username);
		params.put("password", password);
		System.out.println("Posting with params: |" + params.toString() + "|");
		context.getRouter().post("/logins", params);
	}

	
	public void respondToValidity() {
		if (this.getControllerHelper() == null) return; 
		EditText t = this.getControllerHelper().getUserName();
		String str = t.getText().toString();
		System.out.println("Matching: " + str);
		try {
			if (companyEmployees.containsKey(str)) {
				System.out.println("contains key");
				JSONObject employee = companyEmployees.get(str);
				if (employee.getBoolean("setup")) {
					if (this.getControllerHelper().getControllerName().equals("signup")) {
							System.out.println("signup controller");
							context.getSignupController().dismissDialog();
							this.setControllerHelper(context.getLoginController());
							context.getLoginController().showDialog();
							t = this.getControllerHelper().getUserName();
							t.setText(str);
					}
				} else if (this.getControllerHelper().getControllerName().equals("login")) {
						System.out.println("login controller");
						context.getLoginController().dismissDialog();
						this.setControllerHelper(context.getSignupController());
						context.getSignupController().showDialog();
						t = this.getControllerHelper().getUserName();
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
	
	public void sendCheckin(LocationTime l) {
		
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
		System.out.println("Request Error Occurred!: " + errorString);
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

	public void makeToast(String string) {
		Toast.makeText(context, string, Toast.LENGTH_LONG).show(); 
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void displayDailyReport() {
		
	}
	
}