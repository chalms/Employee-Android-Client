package main.firealertapp;

import com.loopj.android.http.RequestParams;

import models.Model;
import util.WebClient;

public class MainController {
	private MainActivity context; 
	private WebClient webClient; 
	private Model model; 
	
	
	public MainController(MainActivity c) {
		webClient = c.getWebClient();
		model = c.getModel();
		context = c; 
	}
	
	public void login(String username, String password) {
		RequestParams params = new RequestParams();
		params.put("email", username);
		params.put("password", password);
		webClient.post("/users/sign_in", params);
	}

	public static void requestError(String errorString) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
