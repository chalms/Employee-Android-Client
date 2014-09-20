package main.metrics;

import java.io.UnsupportedEncodingException;
import models.Company;
import models.LocationTime;
import models.UsersReport;
import org.json.JSONException;
import org.json.JSONObject;
import views.SettingsView;
import web.Router;
import web.WebClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import controllers.CheckinController;
import controllers.LoginController;
import controllers.ReportTaskLineItemController;
import controllers.SearchController;
import controllers.SignupController;
import controllers.TokenController;
import controllers.UsersReportController;

public class MainActivity extends Activity {

	//For Toast ---> 
	AlertDialog alertDialogStores;
	AlertDialog alertDialogDescription;

	//Logical Vars && Data structures ---> 
	public boolean login = true;
	boolean lockPopUp = false;
	Integer globalID;
	Integer numberHeaders;
	boolean dontAddHeader;
	Integer numberOfHeadersLeft;
	boolean lock = false;
	public String userName = null; 
	int count = 0; 
	boolean popUp = false; 
	boolean loggedOut = false; 
	boolean loggedIn = false; 

	// For SCANNER -----> 
	private String ACTION_CONTENT_NOTIFY = "android.intent.action.CONTENT_NOTIFY";
	private DataReceiver dataScanner = new DataReceiver();
	private TextView tv_getdata_from_edittext;

	// Controllers -----> 
	public MainController mainController = null; 
	public UsersReportController usersReportController = null;
	public SearchController searchController = null;
	public ReportTaskLineItemController reportTaskLineItemController = null; 
	public LoginController loginController = null; 
	public SignupController signupController = null; 
	public TokenController tokenController = null; 
	public CheckinController checkinController = null; 
	
	SettingsView settingsView = null; // <-- for logout
	private EditText editText; //<--- login input

	//Models ----> 
	private Company company = null;

	//Utilities ----> 
	private WebClient webClient = null;
	private Router router;
	public PendingIntent pendingIntent; 
	private LocationManager mLocationManager; 
	private String checkinTime; 



	//Initial settings --> 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildAPI(); 
		setContentView(main.metrics.R.layout.activity_main);
		initializeScanner(); 
		selectController(); 
		setLocationService(); 
	}
	
	public void setLocationService(){
		 Intent intent = new Intent();  
	        intent.setClass(this,LocationIntentHandler.class);  
	        pendingIntent =  PendingIntent.getActivity(this, 0, intent, 0);
	}
	
	public void buildAPI() {
		WebClient.build(this);
		this.getWebClient();
		this.getRouter();
	}
	
	public void selectController() {
		loggedOut = false; 
		this.userName = null; 
		if (this.userName == null) {
			loggedIn = false; 
			// next try: setHomeView(); 
			
		} else {
			loggedIn = true; 
		}	
	}
	
	public void initializeScanner() {
		initialComponent();
		registerScanner();
	}
	
	public Router getRouter() {
		if (router == null) {
			router = new Router(this);
		}
		return router;
	}
	
	public String getServerName() {
		return this.getString(main.metrics.R.string.server); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(main.metrics.R.menu.main, menu);
		return true;
	}

	public boolean getLockPopUp() {
		return lockPopUp;
	}

	public boolean popUpPresent() {
		return popUp; 
	}

	public void setPopUpPresent(boolean p) {
		popUp = p; 
	}
	
	public TokenController getTokenController() {
		return tokenController; 
	}
	
	public void setCurrentUserName(String u) {
		this.userName = new String(u);
	}

	public String getCurrentUserName(){
		return this.userName;
	}
	
	//login
	public LoginController getLoginController() {
		if (this.loginController == null) {
			this.loginController = new LoginController(this); 
		}
		return this.loginController; 
	}
	
	//logout
	public void launchSettingsView() {
		this.settingsView = new SettingsView(this); 
	}

	//Asynchonous logout for saving data --->

	private class LogoutTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog d;
		@Override
		protected void onPreExecute() {
			d = ProgressDialog.show(MainActivity.this, "Saving Document", "Saving...", true); 
			//UPLOAD MODEL ----> 	getModel().saveChanges(); 
			
			while(true) {
				try{ 
					d.dismiss();
				} catch (Exception e) {
					System.out.println("Dialog was showing exception");
				}
				globalID = 0;
				numberHeaders = 0;
				dontAddHeader = false;
				numberOfHeadersLeft = 0;
				try {
					setContentView(main.metrics.R.layout.checkin);
				} catch (Exception e){
					e.printStackTrace();
					System.out.println("Exception... [Logout Task, OnPreExcecute, 2nd try]");
				}
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			//REMEMEBER  if (getModel().saved()) // thn 
				if (true) {
					userName = null;
					System.out.println("Logged out");
				}
//				} else {
//					// SOME EMERGENCY SAVE
//					
//				}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			d.dismiss(); 
			(MainActivity.this).finish();
		}
	}

	public void Logout() {
		loggedOut = true; 
		new LogoutTask().execute();
	}

	//Start and stop settings ---> 

	public void CloseUp() {
		this.finish();
	}

	@Override
	public void onStop()
	{
		super.onStop();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loggedOut = false; 
	}

	//Global Controllers -----> 

	public UsersReportController getUsersReportController() {
		if (this.usersReportController == null) this.usersReportController = new UsersReportController(this); 
		return this.usersReportController; 
	}

	public SearchController getSearchController() {
		if (this.searchController == null) {
			this.searchController = new SearchController(this);
		}
		return this.searchController;
	}
	
	public ReportTaskLineItemController getReportTaskLineItemController() {
		if (this.reportTaskLineItemController == null) {
			this.reportTaskLineItemController = new ReportTaskLineItemController(this); 
		}
		return this.reportTaskLineItemController; 
	}
	
	public MainController getMainController() {
		if (this.mainController == null) {
			this.mainController = new MainController(this); 
		}
		return this.mainController; 
	}
	
	public TokenController getTokenController(JSONObject tokk, String tok) throws UnsupportedEncodingException, JSONException {
		if (this.tokenController == null) {
			this.tokenController = new TokenController(this, tokk, tok); 
		}
		return this.tokenController; 
	}
	
	// WEB UTILITIES ----> 

	public WebClient getWebClient() {
		if (this.webClient == null) {
			this.webClient = new WebClient();
		}
		return this.webClient;
	}

	// SCANNER METHODS ------------> 

	private void registerScanner() {
		dataScanner = new DataReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_CONTENT_NOTIFY);
		registerReceiver(dataScanner, intentFilter);
	}

	private void unregisterReceiver() {
		if (dataScanner != null) unregisterReceiver(dataScanner);
	}

	private void initialComponent() {
		tv_getdata_from_edittext  = (TextView) findViewById(R.id.tv_getdata_from_edittext);
		editText = (EditText) findViewById(R.id.editText);
		editText.addTextChangedListener(textWatcher);
	}

	private TextWatcher textWatcher =  new TextWatcher(){
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count){}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		@Override
		public void afterTextChanged(Editable s){
			String x = editText.getText().toString();
			tv_getdata_from_edittext.setText("Get data from EditText : " + x);
			MainActivity.this.getUsersReportController().goToEquipment(x);
		}
	}; 

	private class DataReceiver extends BroadcastReceiver {
		String content = "";
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_CONTENT_NOTIFY)) {
				Bundle bundle = new Bundle();
				bundle  = intent.getExtras();
				content = bundle.getString("CONTENT");
				System.out.println(content);
				UsersReport.goToEquipment(fixContent(content));
			}	
		}
	}
	
	public SignupController getSignupController() {
		if (signupController == null) {
			signupController = new SignupController(this);
		}
		return signupController; 
	}

	public String fixContent(String c) {
		if ((c.isEmpty()) || (c.length()-1 == 0)){
			return c;
		}
		return c.substring(0, c.length()-1);
	}
	
	private final LocationListener mLocationListener = new LocationListener() {
	    @Override
	    public void onLocationChanged(final Location location) {
	        //your code here
	    	LocationTime locationTime = new LocationTime(location.getLongitude(), location.getLatitude(), checkinTime);
	    	mainController.sendCheckin(locationTime);
	    	
	    }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
	};
//	
//	PendingIntent.OnFinished
//	
//	PendingIntent.getActivity(this, generator.nextInt(), intent,PendingIntent.FLAG_UPDATE_CURRENT);
//	
//	// Define a listener that responds to location updates
//	PendingIntent locationListener = new LocationListener() {
//	    public void onLocationChanged(Location l) {
//			location = l;
//	    	if (firedUp) {
//	    		mainController.sendCheckin(l);
//	    		firedUp = false; 
//	    	}
//	    		// Called when a new location is found by the network location provider.
//	    }
//
//	    public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//	    public void onProviderEnabled(String provider) {}
//
//	    public void onProviderDisabled(String provider) {}
//	};
//
//	// Register the listener with the Location Manager to receive location updates
//	
//
//	
//	//Error Notifications ----> 
	public void packageCheckin() {
	    mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//	    checkinTime = sdf.format(new Date());
	    return ;    
	}
	
	public void makeToast(String butter) {
		Toast.makeText(this, butter, Toast.LENGTH_LONG).show();
		return;	
	}

	public Company getCompany() {
		if (company == null){
			company = new Company(); 
		}
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getLocationString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CheckinController getCheckinController() {
		return this.getCheckinController(); 
	}

}
