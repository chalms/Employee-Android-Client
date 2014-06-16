package util;

import java.io.UnsupportedEncodingException;

import main.metrics.MainActivity;

import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class WebClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static String DOMAIN = "http://10.0.2.2:3000/api";
	private JsonReader jsonReader;

	
	JSONObject jsonParams = new JSONObject();
	Context context;
	JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
		
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			
			System.out.println("Successful request with status: " + String.valueOf(statusCode));
			System.out.println("here is the JSON -> " + response.toString()); 
			System.out.println("and here are the headers:"); 
			if (headers != null) {
				for (Header header : headers) {
					String r = header.toString();
					if (r != null) System.out.println(r); 
				}
			}
			jsonParams  = new JSONObject();
			jsonReader.read(response);
		}
		
		public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
			jsonParams  = new JSONObject();
			System.out.println("Error occured: " + e.getMessage()) ; 
			System.out.println("Status Code: " + String.valueOf(statusCode));
			if (errorResponse != null) {
				System.out.println("JSON -> " + errorResponse.toString());
			} 
			System.out.println("and here are the headers:"); 
			if (headers != null) {
				for (Header header : headers) {			
					String r = header.toString();
					if (r != null) System.out.println(r); 
				}
			}	
		};
	};


	public WebClient(MainActivity c) {
		context = c.getApplicationContext();
		jsonReader = new JsonReader(c.getModel());
	}

	public void authenticateClient() {
		client.setBasicAuth("username","password", new AuthScope("example.com", 80, AuthScope.ANY_REALM));
	}

	public void get(String url, JSONObject params) {
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
}