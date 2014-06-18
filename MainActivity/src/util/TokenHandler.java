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
	private String SESSION_START = "http://10.0.2.2:3000/api/sessions";
	private UserAccount credentials; 
	private Token token = null; 
	private Context context; 
	
	JsonHttpResponseHandler tokenHandler = new JsonHttpResponseHandler() {
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			WebClient.printValues(new String("success"), statusCode, headers, null, response);
			setAuthentication(response); 
		}
		
		public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
			WebClient.printValues(new String("success"), statusCode, headers, null, errorResponse);
			waitAndTryAgain(); 
		}
		
		void setAuthentication(JSONObject response) {
			try {
				token = new Token(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (token != null) {
				WebClient.setAuthenticationHeader(token.token);
			}
		}
		
		void waitAndTryAgain () {
			System.out.println("Not gonna try again cause we testing");
//			new Timer().schedule( 
//			        new TimerTask() {
//			            @Override
//			            public void run() {
//			        		if (credentials.isValid()) { 
//			        			try {
//									new TokenTimer();
//								} catch (UnsupportedEncodingException e) {
//									e.printStackTrace();
//								} catch (JSONException e) {
//									e.printStackTrace();
//								} 
//			        		} 
//			            }
//			        }, 5000 
//			);
		}
	};
	
	class Token {
		private String token = null; 
		public double ttl = 0; 
		
		boolean isValid() { return ((ttl < 10) || (token == null)); }
		
		Token(JSONObject apiSessionToken) throws JSONException {
			token = apiSessionToken.getString("token");
			ttl = apiSessionToken.getDouble("ttl");
		}
		
		public String getToken() {
			return this.token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
	}
	
	private void getNewToken() throws JSONException, UnsupportedEncodingException {
		client.post(context, SESSION_START, credentials.asStringEntity(), "application/json", tokenHandler);
	}
	
	public TokenHandler(Context c, String email, String password) throws UnsupportedEncodingException, JSONException {
		context = c; 
		credentials = new UserAccount(email, password);
		if (credentials.isValid()) { 
			new TokenTimer(); 
		} 
	}
	
	boolean tokenValid() {
		if (token == null) return false; 
		if (token.ttl < 10) return false; 
		return true; 
	}
	
	class TokenTimer extends TimerTask
	{
	  public void run()
	  {
		  if (token != null) --token.ttl;
	  }

	  public TokenTimer() throws UnsupportedEncodingException, JSONException  {
		 if (tokenValid()) {
			 Timer timer = new Timer();
			 timer.schedule(new TokenTimer(), 0, 1000);
		 } else {
			getNewToken(); 
		 }
	  }
	}

	
	class UserAccount {
		private String email = null; 
		private String password = null; 
		
		UserAccount(String email, String password) {
			setEmail(email);
			setPassword(password);
		}
		
		StringEntity asStringEntity() throws JSONException, UnsupportedEncodingException{
			JSONObject params = new JSONObject(); 
			params.put("email", email);
			params.put("password", password);
			return new StringEntity(params.toString());
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public boolean isValid() {
			return ((email != null) && (password != null));
		}
		
	}
}
