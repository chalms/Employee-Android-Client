package views;

import java.util.Date;

import models.ReportTask;

public class ReportTaskLineItem {
	
	ReportTask ref; 
	
	public ReportTaskLineItem (ReportTask t) {
		ref = t; 
	}
	
	public String getId() {
		return String.valueOf(ref.getId());
	}

	public String getDisplay() {
		return ref.getDescription();
	}

	public String getTag() {
		return String.valueOf(ref.getId());
	}
	
	public Date getCompleted(){
		return ref.getCompletedTime(); 
	}
}
