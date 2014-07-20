package util;

import java.io.UnsupportedEncodingException;

import main.metrics.MainActivity;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import util.Token;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class WebClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static String DOMAIN = "http://10.0.2.2:3000/";
	private static TokenHandler tokenHandler = null; 
	private JsonReader jsonReader;
	JSONObject jsonParams = new JSONObject();
	Context context;
	int count = 0;
	public static boolean loggedIn = false; 
	private Token token = null; 
	private MainActivity main; 
	
	
	JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
		
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			printValues(new String("success"), statusCode, headers, null, response);
			jsonReader.read(response);
		}
		
		public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
			printValues(new String("success"), statusCode, headers, null, errorResponse);
			main.getMainController().stateMessageError();
		};
	};

	static JsonHttpResponseHandler rHandler = new JsonHttpResponseHandler() {
		
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			printValues(new String("success"), statusCode, headers, null, response);
	
		}
		
		public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
			printValues(new String("success"), statusCode, headers, null, errorResponse);
		};
	};

	public WebClient(MainActivity c) {
		main = c; 
		context = c.getApplicationContext();
		jsonReader = new JsonReader(c.getModel());
	}
	
	public void setToken(Token t) {
		this.token = t; 
		this.setAuthenticationHeader(token);
		sendLoginNotification();
	//	client.get("http://localhost:3000/api/users/index.json",params,rHandler  );
	}
	
	public void sendLoginNotification() {
		this.main.getMainController().loggedIn();
	}
	
	public void login(String email, String password) throws UnsupportedEncodingException, JSONException {
		setTokenHandler(new TokenHandler(context, email, password, this )); 
	}
	
	public void get(String url, JsonHttpResponseHandler handler) {
		client.get(getAbsoluteUrl(url), handler);
	}

	public void get(String url) {
        client.get(getAbsoluteUrl(url), responseHandler);
	}
	
	public void post(String url, JSONObject params) {
		System.out.println(params.toString());
		StringEntity entity = null;
		try {
			entity = new StringEntity(params.toString());
			System.out.println("Entity is:" + entity.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ; 
		}
        client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return (DOMAIN + relativeUrl);
	}

	public void addParams(String key, String value) {
		try {
			jsonParams.put(key , value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void printValues(String type, int statusCode, Header[] headers, Throwable e, JSONObject response) {
		System.out.println(type + " request with status code: " + String.valueOf(statusCode));
		if (response != null) {
			System.out.println("JSON -> " + response.toString());
		} 
		System.out.println("and here are the headers:"); 
		if (headers != null) {
			for (Header header : headers) {			
				String r = header.toString();
				if (r != null) System.out.println(r); 
			}
		}	
	}

	public void setAuthenticationHeader(Token token2) {
		client.addHeader("AUTHORIZATION", token2.toString());
	}

	public static TokenHandler getTokenHandler() {
		return tokenHandler;
	}

	public static void setTokenHandler(TokenHandler tokenHandler) {
		WebClient.tokenHandler = tokenHandler;
	}
}