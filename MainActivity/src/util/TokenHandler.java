package util;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TokenHandler {

	private AsyncHttpClient client = new AsyncHttpClient();
	private String PREFIX = "http://10.0.2.2:3000/";
	private UserAccount credentials;
	private Token token = null;
	private Context context;
	private WebClient webClient; 
	
	TokenHandler(WebClient w, UserAccount c) {
		setWebClient(w); 
		credentials = c; 
	}

	JsonHttpResponseHandler tokenHandler = new JsonHttpResponseHandler() {
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			credentials.password = null; 
			WebClient.printValues(new String("success"), statusCode, headers,
					null, response);
			setAuthentication(response);
		}

		public void onFailure(int statusCode, Header[] headers, Throwable e,
				JSONObject errorResponse) {
			WebClient.printValues(new String("success"), statusCode, headers,
					null, errorResponse);
		}

	};
	
	public void setAuthentication(JSONObject response) {
		try {
			token = new Token(response);
			System.out.println("token was created!");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (token != null) {
			System.out.println("setting authentication header!");
			webClient.setToken(token);
		}
	}
	private void getNewToken() throws JSONException,
			UnsupportedEncodingException {
		client.post(context, PREFIX + credentials.getUrl(), credentials.asStringEntity(),
				"application/json", tokenHandler);
	}

	public TokenHandler(Context c, JSONObject params, String url, WebClient w)
			throws UnsupportedEncodingException, JSONException {
		webClient = w;
		context = c;
		credentials = new UserAccount(params, url);
		if (credentials.isValid()) {
			
		}
	}

	boolean tokenValid() {
		if (token == null)
			return false;
		if (token.ttl < 10)
			return false;
		return true;
	}

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	class TokenTimer extends TimerTask {
		public void run() {
			if (token != null)
				--token.ttl;
		}

		public TokenTimer() throws UnsupportedEncodingException, JSONException {
			if (tokenValid()) {
				Timer timer = new Timer();
				timer.schedule(new TokenTimer(), 0, 1000);
			} else {
				getNewToken();
			}
		}
	}
}
