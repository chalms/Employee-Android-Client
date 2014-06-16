package util;

import main.metrics.MainActivity;

import org.apache.http.auth.AuthScope;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WebClient {

	private AsyncHttpClient client = new AsyncHttpClient();
	private static String DOMAIN = "localhost:3000/api";
	private RequestParams defaultParams = new RequestParams();
	private JsonReader jsonReader;
	AsyncHttpResponseHandler responseHandler;

	public WebClient(MainActivity c) {
		jsonReader = new JsonReader(c.getModel());
		responseHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				jsonReader.read(response);
			}
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
