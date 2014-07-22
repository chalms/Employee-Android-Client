package util;

import models.Model;
import org.json.JSONObject;

public abstract class JsonReader {

	private Model model;
	
	public JsonReader(Model m) {
		this.setModel(m);
	}

	public abstract void read(JSONObject response);


	public void errorMessage(String msg) {
		System.out.println("Error message in json reader");
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}
