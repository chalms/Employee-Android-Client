package main.metrics;

import org.json.JSONException;
import org.json.JSONObject;

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
		JSONObject params = new JSONObject(); 
		JSONObject params_2 = new JSONObject(); 
		try { 
			
		
		params_2.put("email", username);
		params_2.put("password", password);
		params.put("user", params_2);
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		webClient.post("/users/sign_in", params);
		
		if (model.getReport().isValid()) {
			context.root = model.getReport();
			if (context.root != null) {
				context.getLoginController().login();
			}
		}
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