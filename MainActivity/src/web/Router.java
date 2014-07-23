package web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import main.metrics.MainActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DefaultReader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;


public class Router extends WebObject {
	private HashMap <String, WebRequest> routes = new HashMap <String, WebRequest> ();
	private JSONObject loginParams; 
	Synchronizer sync; 
	
	public Router(MainActivity c) {
		super(c);
		sync = new Synchronizer();
		build();
	}
	
	//~~~~~~~~~CREDENTIALS FOR CALL TO TOKEN CONTROLLER~~~~~~~~~
	public void addCredentials(String url, JSONObject params){
		if (url.equals("logins")){
			loginParams = params; 
		} else {
			if (loginParams == null) loginParams = new JSONObject(); 
			try {
				loginParams.put("email", params.getString("email"));
				loginParams.put("password", params.getString("password"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//~~~~~~~~~~~~~~~POST OR GET ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void post(String url, JSONObject params) {
		System.out.println("Router POST called for: " + url);
		addCredentials(url, params);
		System.out.println("Credentials added (ll: Router->post()");
		System.out.println("Executing SYNC for POST -> " + url);
		sync.execute(createPostRequestWrapper(url, new TokenHandler(), params));
	}
	
	public void get(String url) {
		WebRequest request = routes.get(url);
		sync.execute((request.requestWrapper)); 
	}
	
	
	//~~~~~~~~~~~~~~~~~~~WITH DEFAULTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void addRoute(String url) {
		WebRequest request = new WebRequest(url, new DefaultJsonResponseHandler());
		routes.put(request.getUrl(), request);
	}
	public void addRoute(String url, AsyncHttpResponseHandler h) {
		WebRequest request = new WebRequest(url, h);
		routes.put(request.getUrl(), request);
	}
	
	public void addRoute(String url, AsyncHttpResponseHandler h, CallbackWrapper callback) {
		WebRequest request = new WebRequest(url, h, callback);
		routes.put(request.getUrl(), request);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~LIST OF PREMADE ROUTES~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void build() {
		addRoute("/reports"); 
		addRoute("/chats", new DefaultJsonResponseHandler()); 
		addRoute("/clients", new DefaultJsonResponseHandler()); 
		addRoute("/companies",  new CompaniesResponseHandler(), new CompaniesCallbackWrapper());
		addRoute("/logins", new TokenHandler(), null);
		addRoute("/signups", new TokenHandler(), null);
		addRoute("/special_index", new EmployeesEmailResponseHandler(), new EmployeesEmailCallbackWrapper());
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~POST REQUEST WRAPPER~~~~~~~~~~~~~~~~~~~~~
	public RequestWrapper createPostRequestWrapper(final String url, final AsyncHttpResponseHandler handler, final JSONObject parameters) {
		CallbackWrapper callbackWrapper = new CallbackWrapper() {
			@Override
			public void render() {
				System.out.println("Ran default callback wrapper");
				return; 
			}
		};
		
		return (new RequestWrapper(callbackWrapper) {
			public CallbackWrapper executeRequest() {
				int x = WebClient.post(url, parameters, handler);
				if (x == 1) {
					return callback;
				} else {
					return null;
				}
			}
		});
	}

   //~~~~~~~~~~~~~~~~~~~~~LOGIN/SIGNUP CALLBACK HANDLERS~~~~~~~~~~~~~~~~~~
	class SignupsCallbackWrapper extends CallbackWrapper {
		SignupsCallbackWrapper() {
			super();
		}
		public void render() {
			getMainContext().getSignupController().dismissDialog();
			getMainController().loggedIn();
		}
	}
	
	class LoginsCallbackWrapper extends CallbackWrapper {
		LoginsCallbackWrapper() {
			super(); 
		}
		public void render() {
			getMainContext().getLoginController().dismissDialog();
			getMainController().loggedIn();
		}
	}
	
	//~~~~~~~~~~~~~SETS LOGIN/SIGNUP BASED ON IF EMAIL IS SETUP~~~~~~~~~~
	class EmployeesEmailResponseHandler extends AsyncHttpResponseHandler {
		
		public void onSuccess(int statusCode, Header[] headers, byte[] response) {
				String str = new String(response);
				JSONArray employees;
				try {
					employees = new JSONArray(str);
				} catch (JSONException e) {
					System.out.println("The string could not be parsed!");
					return; 
				}
				System.out.println("Setting employees from response");
				getMainController().setEmployeesFromResponse(employees);
				 
			}
		
			public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
		    	System.out.println("FAILURE");
		    }
			
	};
	
	class EmployeesEmailCallbackWrapper extends CallbackWrapper {
		public void render() {
			System.out.println("Telling main controller to respond: ");
		};
	}
	
	//~~~~~~~~~~~~~~~SETS COMPANY LIST FOR SELECTOR~~~~~~~~~~~~~~~~~~
	class CompaniesResponseHandler extends AsyncHttpResponseHandler {
		 public void onStart() { }
		 public void onSuccess(int statusCode, Header[] headers, byte[] response) {
	    	String data = new String(response); 
	    	try {
				JSONArray arr = new JSONArray(data);
				getMainController().setCompaniesList(arr); //~~> set companies list
				getMainController().displayCompaniesSelector(); 
				return ; //~~~~~> now call callback wrapper
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 }
	    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
	    	System.out.println("FAILURE");
	    }
	}
	
	class CompaniesCallbackWrapper extends CallbackWrapper {
		public void render() {
		
		};
	}
	
	//~~~~~ DEFAULT JSON READER THAT CHECKS FOR COMMON ROUTES~~~~~~~~~~~
	class DefaultJsonResponseHandler extends JsonHttpResponseHandler {
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			WebClient.printValues(new String("success"), statusCode, headers, null, response);
			new DefaultReader(getModel(), getMainContext()).read(response);
		}
		
		public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
			WebClient.printValues(new String("success"), statusCode, headers, null, errorResponse);
			getMainController().stateMessageError();
		}
	}
	
	//~~~~~~ TOKEN CONTROLLER TO HANDLE SUCCESSFUL LOGIN~~~~~~~~~~
	class TokenHandler extends AsyncHttpResponseHandler {
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] r) {
			System.out.println("Token handler success method called");
			String str = new String(r);
			System.out.println("Received data: " + str);
			JSONObject response = new JSONObject();
			
			try { 
				response = new JSONObject(str);
			} catch (JSONException e1) {
				e1.printStackTrace();
				return; 
			}
			
			WebClient.printValues(new String("success"), statusCode, headers,
					null, response);
			
			try {
				getMainContext().getTokenController(loginParams, "/logins").setAuthentication(response);
				SignupsCallbackWrapper signups = new SignupsCallbackWrapper();
				signups.render();
				getMainController().getHome(response);
				
			} catch (UnsupportedEncodingException e) {
				System.out.println("Token Controller could not be created");
				e.printStackTrace();
			} catch (JSONException e) {
				System.out.println("Token Controller could not be created");
				e.printStackTrace();
			}
		}

		public void onFailure(int statusCode, Header[] headers, Throwable e,
				JSONObject errorResponse) {
			WebClient.printValues(new String("success"), statusCode, headers,
					null, errorResponse);
			getMainContext().makeToast("Sorry, there was error with your sign in. Please try again.");
		}
	}
}
