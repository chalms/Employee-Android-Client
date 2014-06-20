package factories;

import java.util.Date;

import models.nodes.Part;

import org.json.JSONException;
import org.json.JSONObject;

public class PartFactory {

	public static Part build(JSONObject object, String id) {
		try {
			String description = object.getString("description");
			String report_id = object.getString("report_id"); 
			String part_name = object.getString("part_name"); 
			String report_index = object.getString("report_index"); 
			String createID = id;
			String actualID = object.getString("id");
			Date scanned_at = (Date) object.get("scanned_at"); // <--- might fail there 
			Date completed_at = (Date) object.get("scanned_at");
			String geolocation_id = object.getString("geolocation_id");  
			String name = actualID + report_id; 
			Boolean completed = object.getBoolean("completed");
			String barcode = object.getString("barcode");
			
			Part t = new Part(name, createID, actualID,description, scanned_at, completed_at, report_id, report_index, geolocation_id, completed, part_name, barcode);
			return t; 
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}
	}

}
