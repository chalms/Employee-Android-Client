package main.metrics;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import models.Model;
import util.WebClient;

public class MainController {
	private MainActivity context; 
	private WebClient webClient; 
	private Model model; 
	
	public MainController(MainActivity c) {
		webClient = c.getWebClient();
		setModel(c.getModel());
		context = c; 
	}
	
	public void login(String username, String password) {
		try {
			webClient.login(username, password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (WebClient.loggedIn) {
			getReport(); 
		} else {
			context.getLoginController().makeToast("Username or password incorrect. Try again!");
		}
	}
	
	public void getReport() {
		
	}

	public static void requestError(String errorString) {
		// TODO Auto-generated method stub
	}
	
	public void logout() {
		
	}
	
	public void areYouSure() {
		
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}