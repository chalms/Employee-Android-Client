package controllers;

import main.metrics.ActiveController;
import main.metrics.MainActivity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CheckinController extends ActiveController {
	MainActivity context; 
	
	public CheckinController(MainActivity con){
		this.context = con; 
		setDialog(new Dialog(this.context));
		getDialog().setContentView(main.metrics.R.layout.checkin);
		getDialog().setTitle("Checkin");
		Button btnSignIn = this.getCheckInButton();
		Button btnCancel = this.getCancelButton();
		btnCancel.setOnClickListener(cancelListener);
		btnSignIn.setOnClickListener(checkinListener);
		showDialog();
	}
	
	private OnClickListener checkinListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
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


	private Button getCheckInButton() {
		System.out.println("Getting signin button ");
		return (Button) getDialog().findViewById(main.metrics.R.id.button1);
	}
	
	private Button getCancelButton() {
		System.out.println("Getting signin button ");
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
