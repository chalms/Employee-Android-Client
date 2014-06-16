package models.nodes;

import views.ListItemContent;

public class Equipment extends FireNode {
	private String testResult; 
	private String testNote;

	public Equipment(String name, String id, String nodeID, String tag, String testResult, String testNote) {

		super(name, id, nodeID, tag);
		this.testResult = getResult(testResult); 
		this.testNote = getResult(testNote);
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
}
