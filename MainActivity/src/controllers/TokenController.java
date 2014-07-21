package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import main.metrics.MainActivity;
import models.Token;
import models.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import errors.InvalidParametersException;

public class TokenController {
	Token token = null;
	MainActivity context; 
	UserAccount credentials; 
	JSONObject params; 
	
	public TokenController(MainActivity c, JSONObject p, String url) throws UnsupportedEncodingException, JSONException{
		params = p; 
		context = c; 
		try {
			credentials = new UserAccount(params, url);
		} catch (InvalidParametersException e) {
			e.printStackTrace();
		}
		if (credentials.isValid()) {
			getNewToken(); 
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
			context.getWebClient().setToken(token);
		}
	}
	
	private void getNewToken() throws JSONException,UnsupportedEncodingException {
		print(); 
		context.getRouter().post("/signups", params);
		return; 
	}

	boolean tokenValid() {
		if (token == null)
			return false;
		if (token.ttl < 10)
			return false;
		return true;
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
	
	void print() {
		try {
			credentials.asStringEntity().writeTo(System.out);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Getting a new token:");
		System.out.print("Url is: "); 
		System.out.println(credentials.getUrl());
		System.out.print("Credentials as string: ");
	}
}
