package controllers;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import main.metrics.MainActivity;
import main.metrics.R;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupController {

	public MainActivity context;
	private final Dialog dialog;
	private int companyId = 1; 


	private OnClickListener signupListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				authenticated(send());
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
					if (context.getMainController().emailIsSetup(currentEmail)) {
						dialog.dismiss(); 
						context.getLoginController().getUserName().setText(currentEmail);
						context.getLoginController().showDialog();
					}
				} else {
					getUserName().setTextColor(context.getResources().getColor(R.color.red));
				}
			}
		}
	};
    
	public SignupController(MainActivity c) {
		this.context = c;
		this.dialog = new Dialog(this.context);
		this.dialog.setContentView(main.metrics.R.layout.login);
		this.dialog.setTitle("Login");
		getUserName().setOnFocusChangeListener(focusChanged);
		Button btnSignIn = this.getSignInButton();
		btnSignIn.setOnClickListener(signupListener);
		showDialog();
	}
	
	 public void login() {
		makeToast("Welcome to the Mobile Dashboard");
		dismissDialog();
		((MainActivity) context).getListViewController().renderListView();
	}

	private Button getSignInButton() {
		return (Button) this.dialog.findViewById(main.metrics.R.id.buttonSignUp);
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
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextUserNameToSignup);
	}
	
	public EditText getPassword() {
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextPasswordToSignup);
	}
	
	public EditText getPasswordConfirmation() {
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextPasswordConfirmationToSignup);
	}

	public EditText getEmployeeNumber() {
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextEmployeeNumberToSignup);
	}
	
	public EditText getName() {
		return (EditText) this.dialog.findViewById(main.metrics.R.id.editTextEmployeeNameToSignup);
	}
	private JSONObject serialize() {
		JSONObject params = new JSONObject();
		try {
			params.put("password", getPassword().getText().toString());
			params.put("password_confirmation", getPasswordConfirmation().getText().toString());
			params.put("email", getUserName().getText().toString());
			params.put("name", getName().getText().toString());
			params.put("employee_number", getEmployeeNumber().getText().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Invalid Login!", Toast.LENGTH_SHORT);
			params = null; 
			e.printStackTrace();
		}
		return params; 
	}
	
	public boolean send() {
		JSONObject params = this.serialize(); 
		context.getMainController().signup(params);
	}
	
	public void authenticated(final EditText password, final EditText username) {
		try {
			this.context.getMainController().login(username.getText().toString(), password.getText().toString());
		} catch (Exception e) {
			makeToast(e.getMessage());
		}
	}
}
