package web;

import java.io.UnsupportedEncodingException;

import main.metrics.MainActivity;
import models.Token;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	static Context context;
	int count = 0;
	public static boolean loggedIn = false; 
	private Token token = null; 
	private static MainActivity main; 
	private static String url;
	
	static public void build(MainActivity c) {
		setMain(c); 
		setUrl(main.getServerName());
		context = c.getApplicationContext();
	}
	
	static public AsyncHttpClient getClient() {
		return client; 
	}
	
	static public String getAbsoluteUrl(String u) {
		return new String(url + u);
	}
	
	static public int get(String url, AsyncHttpResponseHandler handler) {
		try {
			client.get(getAbsoluteUrl(url) + ".json", handler);
			return 1; 
		} catch (Exception e) {
			return 0;
		}
	}
	
	static public StringEntity getStringEntity(JSONObject params) {
		StringEntity entity = null;
		try {
			entity = new StringEntity(params.toString());
			System.out.println("Entity is:" + entity.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity; 
	}
	
	static public int post(String url, JSONObject params, AsyncHttpResponseHandler responseHandler) {
		try {
			client.post(context, getAbsoluteUrl(url) + ".json", getStringEntity(params) , "application/json", responseHandler);
			return 1;
		} catch (Exception e){
			return 0; 
		}
	}
	
	public static void printValues(String type, int statusCode, Header[] headers, Throwable e, JSONObject response) {
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

	static public void setAuthenticationHeader(Token token2) {
		System.out.println("AUTH HEADER SET!");
		client.addHeader("AUTHORIZATION", token2.getToken());
	}

	public static MainActivity getMain() {
		return main;
	}

	public static void setMain(MainActivity m) {
		main = m;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
		setAuthenticationHeader(token);
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		WebClient.url = url;
	}

}