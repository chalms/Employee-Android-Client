package controllers;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import main.metrics.MainActivity;
import main.metrics.R;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginController {

	public MainActivity context;
	private final Dialog dialog;


	private OnClickListener loginListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				authenticated(getPassword(), getUserName());
			} catch (Exception e) {
				System.out.println(e.getCause().getMessage());
				makeToast("Invalid! Try again");
			}
			
		}
	};

	private OnFocusChangeListener focusChanged = new OnFocusChangeListener () {
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			if (!arg1) {
				String currentEmail = ((EditText) arg0).getText().toString(); 
				if (context.getMainController().companyHasEmployee(currentEmail)) {
					getUserName().setTextColor(context.getResources().getColor(R.color.green));
					if (!context.getMainController().emailIsSetup(currentEmail)) {
						dismissDialog(); 
						context.getSignupController().getUserName().setText(currentEmail);
						context.getSignupController().showDialog();
					}
				} else {
					getUserName().setTextColor(context.getResources().getColor(R.color.red));
				}
			}
		}
	};

	public LoginController(MainActivity c) {
		this.context = c;
		this.dialog = new Dialog(this.context);
		this.dialog.setContentView(main.metrics.R.layout.login);
		this.dialog.setTitle("Login");
		getUserName().setOnFocusChangeListener(focusChanged);
		Button btnSignIn = this.getSignInButton();
		btnSignIn.setOnClickListener(loginListener);
		System.out.println("Showing Login Controller");
		showDialog();
	}
	
	 public void login() {
		makeToast("Welcome to the Mobile Dashboard");
		dismissDialog();
		((MainActivity) context).getListViewController().renderListView();
	}

	private Button getSignInButton() {
		return (Button) this.dialog.findViewById(main.metrics.R.id.buttonSignIn);
	}

	public void makeToast(String butter) {
		Toast.makeText(context, butter, Toast.LENGTH_LONG).show();
		return;
	}

	public void dismissDialog() {
		this.dialog.dismiss();
		return;
	}

	public void showDialog() {
		this.dialog.show();
		return;
	}

	public EditText getUserName() {
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextUserNameToLogin);
	}

	public EditText getPassword() {
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextPasswordToLogin);
	}

	public void authenticated(final EditText password, final EditText username) {
		try {
			this.context.getMainController().login(username.getText().toString(), password.getText().toString());
		} catch (Exception e) {
			makeToast(e.getMessage());
		}
	}

}