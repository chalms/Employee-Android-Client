package models.nodes;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.Manager;

public class Report extends FireNode {
	private String description = null;
	private Date reportDate = null;
	private Date checkin = null;
	private Date checkout = null;
	public Manager manager = null;
	private String nodeID = null;

	public Report() {
		super(); 
	}
	public void update() {
		try {
			router.post("/reports", upload());
		} catch (JSONException e) {
			System.out.println("Upating report failed");
			e.printStackTrace();
		} 
	}

	public JSONObject upload() throws JSONException {
		JSONObject params = new JSONObject(); 
		params.put("tasks", new JSONArray()); 

		for (FireNode node : this.childList()) {
			if (node.getChanged()) {
				JSONObject n = node.upload();
				if (n != null) params.getJSONArray("tasks").put(n);
			}
		}

		if (getChanged()) {
			try { 
				if (nodeID == null) return null; else params.put("id", nodeID);
				if (description != null) params.put("description", description);
				if (reportDate != null) params.put("report_date", reportDate);
				if (checkin != null) params.put("checkin", checkin.toString());
				if (checkout != null) params.put("checkout", checkout.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return params; 
	}


	private boolean valStr(String str) {
		return (!str.isEmpty() && !str.equals("nil"));
	}

	public boolean isValid() {
		return valStr(nodeID) && valStr(description) && (reportDate != null) && (manager != null); 
	}

	void makeChildList() {
		if (this.childList == null) {
			this.childList = new ArrayList<FireNode>() ;
		}
	}

	public boolean isReady() {
		if (!name.isEmpty() && !id.isEmpty() && !nodeID.isEmpty() && !tag.isEmpty()) {
			makeChildList();
			return true; 
		} else {
			return false; 
		}
	}

	public Report(String name, String id, String nodeID, String tag) {
		super(name, id, nodeID, tag);
		this.childList = new ArrayList<FireNode>() ;
	}

	public void setDescription(String desc) {
		this.description = desc; 
	}

	public String getDescription() {
		return this.description; 
	}

	public void setDate(Date date) {
		this.reportDate = date; 
	}

	@Override
	public String getDisplay() {
		return (name + " - " + this.getNodeID());
	}

	public void add(FireNode element) {
		if ((element != null) && (FireNode.class.isInstance(element))) {
			this.childList.add(element);
		}
	}

	public ArrayList<FireNode> getChildren() {
		return childList;
	}

	public String hashString() {
		return new String(this.reportDate.toString() + this.manager.getEmail()); 
	}

	public void setCheckin(Date c) {
		checkin = c; 
	}

	public void setCheckout(Date c) {
		checkout = c; 
	}

	public String getCheckin() {
		return checkin.toString(); 
	}

	public String getCheckout() {
		return checkout.toString(); 
	}

	public Date getReportDate() {
		return reportDate; 
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
}