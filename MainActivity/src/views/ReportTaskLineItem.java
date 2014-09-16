package views;

import models.ReportTask;

public class ReportTaskLineItem {
	
	ReportTask ref; 
	
	public ReportTaskLineItem (ReportTask t) {
		ref = t; 
	}
	
	public String getId() {
		return ref.getId();
	}

	public String getDisplay() {
		return ref.getDescription();
	}

	public String getTag() {
		return ref.getId();
	}
	
	public int getCompleted(){
		return ref.getCompleted(); 
	}
}
