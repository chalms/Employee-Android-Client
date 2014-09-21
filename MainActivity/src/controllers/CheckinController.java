package controllers;

import main.metrics.ControllerHelper;
import main.metrics.MainActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CheckinController extends ControllerHelper {
	int LAYOUT = main.metrics.R.layout.main; 
	
	public CheckinController(MainActivity c){
		super(c); 
		Button btnSignIn = this.getCheckInButton();
		Button btnCancel = this.getCancelButton();
		btnCancel.setOnClickListener(cancelListener);
		btnSignIn.setOnClickListener(checkinListener);
	}
	
	private OnClickListener checkinListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			showDialog();
			context.getMainController().displayDailyReport(); 
			dismissDialog(); 
		}
	};
	
	private OnClickListener cancelListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dismissDialog(); 
		}
	};
	
	public void render() {

	}
	
	private Button getCheckInButton() {
		return (Button) this.context.findViewById(main.metrics.R.id.button1);
	}
	
	private Button getCancelButton() {
		return (Button) getDialog().findViewById(main.metrics.R.id.button1);
	}

	public void makeToast(String butter) {
		Toast.makeText(context, butter, Toast.LENGTH_LONG).show();
		return;
	}

	public void dismissDialog() {
		getDialog().dismiss();
		return;
	}

	public void showDialog() {
		getDialog().show();
		return;
	}

}
