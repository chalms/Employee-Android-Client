package models.nodes;

import java.util.Date;

public class Part extends Task {

	private Date scanned_at; //datestamp for the completion method --> completed: 
	private String part_name; 
	private String barcode;
	@SuppressWarnings("unused")
	private String partID; 

	public Part(String name, String id, String nodeID, String desc, Date completedAt, Date scan, String repId, String repIndex, String geo, Boolean completed, String pn, String bCode) {
		super(name, id, nodeID, completedAt, desc, repId, repIndex, geo, completed);  
		setScanned_at(scan); 
		part_name = pn; 
		setBarcode(bCode); 
		partID = nodeID; 
	}
	
//	public void changed() {
//		
//	}
//	
//	public void upload (Router r) {
//		JSONObject params = new JSONObject(); 
//		try {
//				if (id == null) { return ; } else	params.put("id", id);
//				if (part_name != null) params.put("part_name", part_name);
//				if (scanned_at != null) params.put("scanned_at", scanned_at.toString());
//				if (barcode != null) params.put("barcode", barcode);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		
//	}

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

