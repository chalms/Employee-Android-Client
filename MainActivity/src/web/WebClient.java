package web;

import java.io.UnsupportedEncodingException;
import main.metrics.MainActivity;
import models.Token;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import util.JsonReader;
import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static TokenHandler tokenHandler = null; 
	private JsonReader jsonReader;
	JSONObject jsonParams = new JSONObject();
	Context context;
	int count = 0;
	public static boolean loggedIn = false; 
	private Token token = null; 
	private MainActivity main; 
	
	public WebClient(MainActivity c) {
		setMain(c); 
		context = c.getApplicationContext();
		setJsonReader(new JsonReader(c.getModel()));
	}
	
	public AsyncHttpClient getClient() {
		return client; 
	}
	
	
	public void get(String url, AsyncHttpResponseHandler handler) {
		client.get(url, handler);
	}

	
	public void post(String url, JSONObject params, AsyncHttpResponseHandler responseHandler) {
		System.out.println(params.toString());
		StringEntity entity = null;
		try {
			entity = new StringEntity(params.toString());
			System.out.println("Entity is:" + entity.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ; 
		}
        client.post(context, url, entity, "application/json", responseHandler);
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

	public JsonReader getJsonReader() {
		return jsonReader;
	}

	public void setJsonReader(JsonReader jsonReader) {
		this.jsonReader = jsonReader;
	}

	public MainActivity getMain() {
		return main;
	}

	public void setMain(MainActivity main) {
		this.main = main;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}