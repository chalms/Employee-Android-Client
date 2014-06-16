package Factories;

import java.util.Date;

import models.nodes.Equipment;

import org.json.JSONException;
import org.json.JSONObject;

public class EquipmentFactory {

	public static Equipment build(JSONObject object, String id) {
		try {
			// TODO: Implement these into the constructor
			String description = object.getString("description");
			String report_id = object.getString("report_id"); 
			String part_name = object.getString("part_name"); 
			String report_index = object.getString("report_index"); 
			String createID = id;
			String actualID = object.getString("id");
			Date scanned_at = (Date) object.get("scanned_at"); // <--- might fail there 
			String geolocation_id = object.getString("geolocation_id");  
			String name = actualID + report_id; 
			
			//id+report_id, createID, id, description, scanned_at, report_id, report_index, geolocation
			
			Equipment t = new Equipment(name, createID, actualID, description, scanned_at, report_id, report_index, geolocation_id, part_name);
			return t; 
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}
	}

}
