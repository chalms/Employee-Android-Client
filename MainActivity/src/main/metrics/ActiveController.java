package main.metrics;

import android.app.Dialog;
import android.widget.EditText;

public abstract class ActiveController {
	
	public boolean showing() {
		return getDialog().isShowing(); 
	}

	public Dialog getDialog() {
		return dialog;
	}
	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	public MainActivity context;
	private Dialog dialog;
	public EditText getUserName() {
		return null;
	}
	public String controllerName() {
		return null;
	}
}
