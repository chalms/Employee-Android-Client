package controllers;

import java.io.UnsupportedEncodingException;

import main.metrics.ActiveController;
import main.metrics.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import errors.InvalidParametersException;

public class SignupController extends ActiveController {

	private OnClickListener signupListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				send();
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
    
	
	public String controllerName() {
		return "signup";
	}
	
	public SignupController(MainActivity c) {
		this.context = c;
		setDialog(new Dialog(this.context));
		getDialog().setContentView(main.metrics.R.layout.signup);
		getDialog().setTitle("Signup");
		this.context.getRouter().get("/companies");
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
		return (Button) getDialog().findViewById(main.metrics.R.id.buttonSignUp);
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
	
	public Spinner getSpinner() {
		return (Spinner) getDialog().findViewById(main.metrics.R.id.companySpinner);
	}
	public EditText getUserName() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextUserNameToSignup);
	}
	
	public EditText getPassword() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextPasswordToSignup);
	}
	
	public EditText getPasswordConfirmation() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextPasswordConfirmationToSignup);
	}

	public EditText getEmployeeNumber() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextEmployeeNumberToSignup);
	}
	
	public EditText getName() {
		return (EditText) getDialog().findViewById(main.metrics.R.id.editTextEmployeeNameToSignup);
	}
	private JSONObject serialize() {
		System.out.println("Attempting to serialize!");
		JSONObject params = new JSONObject();
		try {
			params.put("password", getPassword().getText().toString());
			params.put("password_confirmation", getPasswordConfirmation().getText().toString());
			params.put("email", getUserName().getText().toString());
			params.put("name", getName().getText().toString());
			params.put("employee_number", getEmployeeNumber().getText().toString());
			params.put("company_id", context.getMainController().getCompanies().get(getSpinner().getItemAtPosition(0)));
		} catch (JSONException e) {
			e.printStackTrace();
			makeToast("Invalid Login!");
			params = null; 
		}
		return params; 
	}
	

	public void send() {
		JSONObject params = this.serialize(); 
		System.out.println("Signup controller sending params: " + params.toString());
		try {
			context.getMainController().signup(params);
		} catch (JSONException e) {
			e.printStackTrace();
			makeToast("Invalid signup!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			makeToast("Invalid signup!");
		} catch (InvalidParametersException e) {
			e.printStackTrace();
			makeToast(e.getMessage());
		}
	}

}
