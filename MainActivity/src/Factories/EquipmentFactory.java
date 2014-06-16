package Factories;

import models.nodes.Equipment;

import org.json.JSONException;
import org.json.JSONObject;

public class EquipmentFactory {

	public static Equipment build(JSONObject object, String id) {
		try {
			String description = object.getString("description");
			String partID = object.getString("part_name");
			String report_id = object.getString("report_id"); 
			
			Equipment t = new Equipment(report_id, report_id, report_id, report_id, report_id, report_id); 
			
			return t; 
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		}
	}

}
