package util;

import main.metrics.MainActivity;

import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class WebClient {

	private AsyncHttpClient client = new AsyncHttpClient();
	private static String DOMAIN = "localhost:3000/api";
	private RequestParams defaultParams = new RequestParams();
	private JsonReader jsonReader;
	JsonHttpResponseHandler responseHandler;
	

	public WebClient(MainActivity c) {
		jsonReader = new JsonReader(c.getModel());
		responseHandler = new JsonHttpResponseHandler() {
			
	
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				System.out.println("Successful request with status: " + String.valueOf(statusCode));
				try {
					System.out.println("here is the JSON -> " + response.toString(0));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

				System.out.println("and here are the headers:"); 
				for (Header header : headers) {
					System.out.println(header.getElements().toString()); 
				}
				jsonReader.read(response);
			}
			
			public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
				System.out.println("Error occured: " + e.getMessage()) ; 
				System.out.println("Status Code: " + String.valueOf(statusCode));
				try {
					System.out.println("JSON -> " + errorResponse.toString(0));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

				System.out.println("and here are the headers:"); 
				for (Header header : headers) {
					System.out.println(header.getElements().toString()); 
				}
			};
		};
	}

	public void authenticateClient() {
		client.setBasicAuth("username","password", new AuthScope("example.com", 80, AuthScope.ANY_REALM));
	}

	public void get(String url, RequestParams params) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public void post(String url, RequestParams params) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return DOMAIN + relativeUrl;
	}

	void addDefault(String key, String value) {
		defaultParams.put(key , value);
	}

	RequestParams setParam(String key, String value, RequestParams params) {
		if (params == null) {
			params = defaultParams;
		}
		params.put(key , value);
		return params;
	}
}