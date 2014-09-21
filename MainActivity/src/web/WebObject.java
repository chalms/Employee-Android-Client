package web;
import main.metrics.MainActivity;
import main.metrics.MainController;
public class WebObject {

	private WebClient webClient = null; 
	private MainActivity context = null;
	private MainController mainController = null;
	public String domain;
	
	public WebObject(MainActivity c) {
		setMainContext(c);  
		setMainController(getMainContext().getMainController());
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

	
	public void setMainContext(MainActivity c){
		context = c;
	}
	
	public MainActivity getMainContext() {
		return context;
	}

}
