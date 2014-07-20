package models.nodes;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import util.WebClient;

public class Part extends Task {

	private Date scanned_at; //datestamp for the completion method --> completed: 
	private String part_name; 
	private String barcode;
	private String taskID; 

						// the params below need to be filtered first for nil
						//id+report_id, createID, id, description, scanned_at, report_id, report_index, geolocatio
	
	public Part(String name, String id, String nodeID, String desc, Date completedAt, Date scan, String repId, String repIndex, String geo, Boolean completed, String pn, String bCode) {
		
		super(name, id, nodeID, completedAt, desc, repId, repIndex, geo, completed);  
		scanned_at = scan; 
		part_name = pn; 
		barcode = bCode; 
		taskID = nodeID; 
		
	}
	
	public void upload (WebClient webClient) {
		JSONObject params = new JSONObject(); 
		
		try {
				if (id == null) { return ; } else	params.put("id", id);
				if (part_name != null) params.put("part_name", part_name);
				if (scanned_at != null) params.put("scanned_at", scanned_at.toString());
				if (barcode != null) params.put("barcode", barcode);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		webClient.post(new String( "/tasks/"+taskID+ "/parts/" + nodeID), params);
	}

	String getResult(String result){
		if (result == null){
			return new String("");
		} else {
			return result; 
		}
	}


	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
}		

