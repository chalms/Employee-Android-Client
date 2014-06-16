package views;

import models.nodes.FireNode;

public class ListItemContent {
	
	FireNode ref; 
	
	public ListItemContent (FireNode t) {
		ref = t; 
	}
	
	public String getID() {
		return ref.getID();
	}

	public String getDisplay() {
		return ref.getDisplay();
	}

	public String getTag() {
		return ref.getTag();
	}
	
	public int getChecked(){
		return ref.getChecked();
	}
	
	public void setChecked(int checked){
		ref.setChecked(checked);
	}
	
    public void setCompleted(boolean c) {
    	//this may not make s
		ref.completed = c; 
	}
	
	public boolean getCompleted(boolean t) {
		return ref.checkCompleted(t);
	}
}
