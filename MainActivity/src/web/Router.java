package web;

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
	
	public Router(MainActivity c) {
		super(c);
	}
	
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
	
	public void build() {
		addRoute("/reports"); 
		addRoute("/chats", new DefaultJsonResponseHandler()); 
		addRoute("/clients", new DefaultJsonResponseHandler()); 
		addRoute("/companies",  new CompaniesResponseHandler());
		addRoute("/logins", new LoginsResponseHandler(), new LoginsCallbackWrapper());
		addRoute("/signups", new SignupsResponseHandler(), new SignupsCallbackWrapper());
	}
	
	public void post(String url, JSONObject params) {
		WebRequest request = routes.get(url);
		request.setRequestWrapper();
		new Synchronizer(request.callbackWrapper, request.requestWrapper);
	}
	
	public void get(String url) {
		WebRequest request = routes.get(url);
		request.setRequestWrapper();
		new Synchronizer(request.callbackWrapper, request.requestWrapper);
	}
	
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
	
	class LoginsResponseHandler extends AsyncHttpResponseHandler {
		public void onSuccess(int statusCode, Header[] headers, byte[] response) {
		}
		public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
	    	System.out.println("FAILURE");
	    }
	}
	
	class SignupsResponseHandler extends  AsyncHttpResponseHandler {
		
	}
	
	class CompaniesResponseHandler extends AsyncHttpResponseHandler {
		 public void onStart() { }
		 public void onSuccess(int statusCode, Header[] headers, byte[] response) {
	    	String data = new String(response); 
	    	try {
				JSONArray arr = new JSONArray(data);
				getMainController().setEmployeesFromResponse(arr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 }
	    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
	    	System.out.println("FAILURE");
	    }
	}
	
	class DefaultJsonResponseHandler extends JsonHttpResponseHandler {
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			WebClient.printValues(new String("success"), statusCode, headers, null, response);
			new DefaultReader(getModel()).read(response);
		}
		
		public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
			WebClient.printValues(new String("success"), statusCode, headers, null, errorResponse);
			getMainController().stateMessageError();
		}
	}
	
	class TokenHandler extends JsonHttpResponseHandler {
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			WebClient.printValues(new String("success"), statusCode, headers,
					null, response);
			getMainContext().getTokenController().setAuthentication(response);
		}

		public void onFailure(int statusCode, Header[] headers, Throwable e,
				JSONObject errorResponse) {
			WebClient.printValues(new String("success"), statusCode, headers,
					null, errorResponse);
		}
	}
}
