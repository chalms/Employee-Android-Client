package models.nodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import models.UsersReport;

public class Report extends FireNode {
	UsersReport model;
	
	public Report(String name, String id, String nodeID, String tag) {
		super(name, id, nodeID, tag);
	}
	
	public void build(UsersReport m) {
		this.childList = new ArrayList<FireNode>();
		model = m;
		setName(m.name);
		makeChildList(); 
	}

	void makeChildList() {
			this.childList = new ArrayList<FireNode>() ;
			Iterator <String> iter = model.reportTasks.keySet().iterator(); 
			while(iter.hasNext()) {
				String key = iter.next();
				ReportTask reportTask = model.reportTasks.get(key);
				this.childList.add(reportTask);
				System.out.println("Added a report task to node list");
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

	public void setCheckin(Date c) {
		model.checkin = c; 
	}

	public void setCheckout(Date c) {
		model.checkout = c; 
	}

	public String getCheckin() {
		return model.checkin.toString(); 
	}

	public String getCheckout() {
		return model.checkout.toString(); 
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
}