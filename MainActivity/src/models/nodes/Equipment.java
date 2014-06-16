package models.nodes;

import java.util.Date;

import android.location.Location;
import views.ListItemContent;

public class Equipment extends FireNode {
	private String testResult; // used below for json
	private String testNote; //note: (testResult.to_string().upcase() +': ' +  testNote)
	private String description; //name: 
	private Date scanned_at; //datestamp for the completion method --> completed: 
	private String reportId;  //the way to find the correct report --> report_id: 
	private String reportIndex; // the order for display in a list --> report_index: 
	private Location geoLocation; //  geo_locations_id:
	private String part_name; 
	private static String tag = "Leaf";
	//note---> nodeID will hold Equipments (id:) for the server  
	
						// the params below need to be filtered first for nil
						//id+report_id, createID, id, description, scanned_at, report_id, report_index, geolocation
	public Equipment(String name, String id, String nodeID, String desc, Date scan, String repId, String repIndex, String geo, String pn) {
		super(name, id, nodeID, tag);
		setPart_name(pn);
		setDescription(desc); 
		setScanned_at(scan); 
		setReportId(repId); 
		setReportIndex(repIndex);
		setGeoLocation(geo); 
		
		this.testResult = getResult(testResult); 
		this.testNote = getResult(testNote);
	}

	public Equipment(String name, String createID, String description2,
			Date scanned_at2, String report_id, String report_index,
			String geolocation_id, String part_name2) {
		// TODO Auto-generated constructor stub
	}

	String getResult(String result){
		if (result == null){
			return new String("");
		} else {
			return result; 
		}
	}

	@Override
	public int getChecked(){
		return getNumericalResult();
	}

	int ensurePresent(){
		if (this.testResult == null){
			this.testResult = "";
		}
		return checked; 
	}

	public void clear(){ 
		this.testNote = "";
		this.testResult = "";
		this.completed = false;
		this.checked = 0; 
		return; 
	}

	public int getNumericalResult() {	
		if (this.testResult.equals("Good"))
			return 4;
		else if (this.testResult.equals("Poor"))
			return 3;
		else if (this.testResult.equals("N/A"))
			return 2;
		else if (this.testResult.equals("Pass"))
			return 1;
		else if (this.testResult.equals("Fail"))
			return -1;			
		else
			return ensurePresent(); 
	}


	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
		setCompleted(true);

	}

	public String getTestNote() {
		return testNote;
	}

	public void setTestNote(String testNote) {
		this.testNote = testNote;
		setCompleted(true);
	}

	public void setCompleted(boolean c) {
		this.completed = c;
	}

	@Override
	public boolean checkCompleted(boolean t) {
		if (!this.testResult.equals("")) {
			this.completed = true; 
		} 
		return this.completed; 
	}

	@Override
	public ListItemContent createRowContent () {
		ListItemContent item = new ListItemContent (this);
		item.setChecked(getNumericalResult());
		return item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getScanned_at() {
		return scanned_at;
	}

	public void setScanned_at(Date scanned_at) {
		this.scanned_at = scanned_at;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportIndex() {
		return reportIndex;
	}

	public void setReportIndex(String reportIndex) {
		this.reportIndex = reportIndex;
	}

	public Location getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		// some transfer here to set it
		//this.geoLocation = geoLocation;
	}

	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
}		

