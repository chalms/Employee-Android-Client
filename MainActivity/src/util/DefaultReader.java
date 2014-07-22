package util;

import main.metrics.MainActivity;
import models.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class DefaultReader extends JsonReader {
	private Model model; 
	private MainActivity context; 
	public DefaultReader(Model m, MainActivity c) {
		super(m);
		context = c; 
	}

	@Override
	public void read(JSONObject response) {
		System.out.println("Reading response: " + response.toString());
		if (model != null) {
			Model copy = model; 
			try {
				
				model.setUserReports(response.getJSONObject("users_reports_to_json"));
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("Model could not set users reports so reverting");
				model = copy; 
			}
		} else {
			model = new Model(context, response);
		}
			
		return ;
	}

}
