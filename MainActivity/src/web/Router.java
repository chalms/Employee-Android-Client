package web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DefaultReader;
import web.TokenHandler.TokenTimer;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import errors.InvalidParametersException;
import main.metrics.MainActivity;
import models.Token;
import models.UserAccount;


public class Router extends WebObject {
	private HashMap <String, WebRequest> routes = new HashMap <String, WebRequest> ();
	
	public Router(MainActivity c) {
		super(c);
	}
	public void addRoute(String url) {
		WebRequest request = new WebRequest(url, new DefaultJsonResponseHandler(), new DefaultReader());
		routes.put(request.getUrl(), request);
	}
	public void addRoute(String url, AsyncHttpResponseHandler h) {
		WebRequest request = new WebRequest(url, h, new DefaultReader());
		routes.put(request.getUrl(), request);
	}
	
	public void addRoute(String url, AsyncHttpResponseHandler h, JsonReader reader) {
		WebRequest request = new WebRequest(url, h, reader);
		routes.put(request.getUrl(), request);
	}
	
	public void build() {
		addRoute("/reports"); 
		addRoute("/chats", new DefaultJsonResponseHandler()); 
		addRoute("/clients", new DefaultJsonResponseHandler()); 
		addRoute("/companies",  new CompaniesResponseHandler());
	}
	
	public void issueRequest(String url, JSONObject params) {
		
	}
	
	public WebRequest getRoute(String url) {
		return routes.get(url);
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
			getWebClient().getJsonReader().read(response);
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
