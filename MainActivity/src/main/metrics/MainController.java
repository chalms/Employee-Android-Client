package main.metrics;

import com.loopj.android.http.RequestParams;

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
		RequestParams params = new RequestParams();
		params.put("email", username);
		params.put("password", password);
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