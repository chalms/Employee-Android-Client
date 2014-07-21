package web;

import main.metrics.MainActivity;
import main.metrics.MainController;
import models.Model;

public class WebObject {
	private WebClient webClient = null; 
	private MainActivity context = null;
	private MainController mainController = null;
	private Model model = null;
	public String domain;
	
	public WebObject(MainActivity c) {
		setMainContext(c);  
		setMainController(getMainContext().getMainController());
		setModel(getMainController().getModel());
		setWebClient(getMainContext().getWebClient());
		domain = getMainContext().getServerName();
	}
	
	public void setMainController(MainController m) {
		this.mainController = m;
	}
	
	public MainController getMainController() {
		return this.mainController;
	}
	
	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setMainContext(MainActivity c){
		context = c;
	}
	
	public MainActivity getMainContext() {
		return context;
	}
	
}
