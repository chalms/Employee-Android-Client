package main.metrics;

import android.app.Dialog;

public class ControllerHelper {
	protected MainActivity context; 
	protected Dialog dialog; 
	protected String controllerName; 
	public ControllerHelper(MainActivity c) {
		context = c;
	}
	public Dialog getDialog() {
		if (dialog == null) dialog = new Dialog(context); 
		return dialog; 
	}
	public void setDialog(Dialog d) {
		dialog = d; 
	}
	public void setControllerName(String n) {
		controllerName = n; 
	}
	
	
	

}
