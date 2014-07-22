package models.nodes;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import util.Formatter;

public class Part extends FireNode {

	private Date scanned_at; //datestamp for the completion method --> completed: 
	private String part_name; 
	private String barcode;
	@SuppressWarnings("unused")
	private String partID; 

	public Part(String name, String id, String nodeID, String desc) throws JSONException {
		super(name, id, nodeID, desc);
	}
	
	public void build(JSONObject params) throws JSONException {
		if (params.has("scanned")) setScanned_at(Formatter.getDateFromString(params.getString("scanned"))); 
		if (params.has("part_name")) setPart_name(params.getString("part_name"));
		if (params.has("barcode")) setBarcode(params.getString("barcode"));
		if (params.has("part_id")) partID = params.getString("part_id");
	}

	public JSONObject uploadParams () {
		JSONObject params = new JSONObject(); 
		try {
			if (id != null) params.put("id", id);
			if (part_name != null) params.put("part_name", part_name);
			if (scanned_at != null) params.put("scanned_at", scanned_at.toString());
			if (barcode != null) params.put("barcode", barcode);
		} catch (JSONException e) {
			System.out.println("Error with upload parameters for part");
			e.printStackTrace();
		}
		return params; 
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

	public Date getScanned_at() {
		return scanned_at;
	}

	public void setScanned_at(Date scanned_at) {
		this.scanned_at = scanned_at;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}		

