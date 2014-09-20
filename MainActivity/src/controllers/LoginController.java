package controllers;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import errors.InvalidParametersException;
import main.metrics.ControllerHelper;
import main.metrics.MainActivity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginController extends ControllerHelper {
	
	MainActivity context; 

	private OnClickListener loginListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				authenticated(getPassword(), getUserName());
			} catch (Exception e) {
				e.printStackTrace();
				makeToast("Invalid! Try again");
			}
			
		}
	};
	
	private OnFocusChangeListener focusChanged = new OnFocusChangeListener () {
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			if (!arg1) {
				 try {
					context.getMainController().getCompanyEmployees();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	public LoginController(MainActivity c) {
		super(c); 
		setDialog(new Dialog(this.context));
		getDialog().setContentView(main.metrics.R.layout.login);
		getDialog().setTitle("Login");
		getUserName().setOnFocusChangeListener(focusChanged);
		Button btnSignIn = this.getSignInButton();
		btnSignIn.setOnClickListener(loginListener);
		System.out.println("Showing Login Controller");
		showDialog();
	}
	
	public String controllerName() {
		return "login";
	}
	
	 public void login() {
		makeToast("Welcome to the Mobile Dashboard");
		dismissDialog();
		this.context.getMainController().getHome(); 
	}

	private Button getSignInButton() {
		System.out.println("Getting signin button ");
		return (Button) getDialog().findViewById(main.metrics.R.id.buttonSignIn);
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
	

	public EditText getUserName() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextUserNameToLogin);
	}

	public EditText getPassword() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextPasswordToLogin);
	}

	public void authenticated(final EditText password, final EditText username) {
		try {
			this.context.getMainController().login(username.getText().toString(), password.getText().toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InvalidParametersException e) {
			e.printStackTrace();
		}
	}

}