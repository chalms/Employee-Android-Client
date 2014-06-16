package models.nodes;

import java.util.ArrayList;
import java.util.Date;

import models.Manager;

public class Report extends FireNode {
	
	private String description = null;
	private Date reportDate = null;
	private Date checkin = null;
	private Date checkout = null;
	public Manager manager = null;
	private String nodeID = null;

	public Report() {
	
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

	public void upload() {
		// TODO Auto-generated method stub

		
	}
}
