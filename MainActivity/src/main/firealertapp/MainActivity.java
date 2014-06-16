package main.firealertapp;

import java.io.IOException;
import java.util.Stack;

import javax.xml.transform.TransformerException;

import models.Model;
import models.nodes.FireNode;
import old.SendController;
import old.UserAccounts;
import old.ViewDocumentController;

import org.w3c.dom.Document;

import util.WebClient;
import views.SettingsView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import controllers.DeleteController;
import controllers.TasksController;
import controllers.ListViewController;
import controllers.LoginController;
import controllers.NodeController;
import controllers.ParserController;
import controllers.SearchController;
import controllers.SignUpController;

//import android.content.IntentFilter;

public class MainActivity extends Activity {

	AlertDialog alertDialogStores;//
	AlertDialog alertDialogDescription;
	public boolean login = true;
	public NodeController theXmlData;
	boolean lockPopUp = false;
	Integer globalID;
	Integer numberHeaders;
	boolean dontAddHeader;
	Integer numberOfHeadersLeft;
	boolean lock = false;
	public String userName = null; 
	int count = 0; 
	Stack<FireNode> jumpDownList;
	NodeController nodeController; 
	ListViewController listViewController;
	SearchController searchController;
	TasksController inspectionItemsController;
	LoginController loginController; 
	SettingsView settingsView;
	boolean popUp = false; 
	boolean loggedOut = false; 
	FireNode root;
	private String ACTION_CONTENT_NOTIFY = "android.intent.action.CONTENT_NOTIFY";
	private DataReceiver dataScanner = new DataReceiver();
	private TextView tv_getdata_from_edittext;
	private EditText editText;
	private Model model = null;
	private WebClient webClient = null;
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWebClient().
		this.userName = null;
		nodeController = new NodeController(getRootNode());
		globalID = 0;
		numberHeaders = 0;
		dontAddHeader = false;
		numberOfHeadersLeft = 0;
		setContentView(main.firealertapp.R.layout.activity_main);
		initialComponent();
		registerScanner();
		loggedOut = false; 
		
		
		if (this.userName == null) {
			System.out.println("Getting Login Controller from on create");
			getLoginController();
		} else {
			System.out.print("USERNAME -> ");
			System.out.println(this.userName);
			this.getListViewController().renderListView();
		}
		
		
	}

    
    public void CloseUp() {
    	this.finish();
    }
    

    private class LogoutTask extends AsyncTask<Void, Void, Void> {
    	private ProgressDialog d;
        @Override
        protected void onPreExecute() {
            d = ProgressDialog.show(MainActivity.this, "Saving Document", "Saving...", true); 
        	saveChanges(); 
        }

        @Override
        protected Void doInBackground(Void... params) {
        	while(true) {
        		if (getParserController().saved()) break ;
            	try{ 
            		d.dismiss();
            	} catch (Exception e) {
            		System.out.println("Dialog was showing exception");
            	}
        	
        		nodeController = null; 
        	
        		globalID = 0;
        		numberHeaders = 0;
        		dontAddHeader = false;
        		numberOfHeadersLeft = 0;
        		try {
        			nodeController = MainActivity.this.getNodeController();
        			setContentView(main.firealertapp.R.layout.activity_main);
        			userName = null;
        			//unregisterReceiver(); 
    	    	} catch (Exception e) {
    	    		System.out.println(e.getMessage());
    	    	}
        	}  
        
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	d.dismiss(); 
    		(MainActivity.this).finish();
        }
    }
    
    private class SavingTask extends AsyncTask<Void, Void, Void> {
    	private ProgressDialog d;
        @Override
        protected void onPreExecute() {
            d = ProgressDialog.show(MainActivity.this, "Saving Document", "Saving...", true); 
        	saveChanges();
        }

        @Override
        protected Void doInBackground(Void... params) {
        	while(true) {
        		if (getParserController().saved()) break ;
        	}  
        
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            d.dismiss();
            
        }
    }
	public void Logout() {
	   loggedOut = true; 
		new LogoutTask().execute();
		
	}

	private void registerScanner() {
		dataScanner = new DataReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_CONTENT_NOTIFY);
		registerReceiver(dataScanner, intentFilter);
	}

	
	public ListViewController getListViewController() {
		if (this.listViewController == null) {
			/* 
			 * The List View Controller controls the state of the ListView.
			 * If it doesn't exist, create it and pass it the main Context. 
			 */
			this.listViewController = new ListViewController(this, getNodeController()); 
		}
		return this.listViewController; 
	}

	public SearchController getSearchController() {
		if (this.searchController == null) {
			this.searchController = new SearchController(this);
		}
		return this.searchController;
	}

	
	public NodeController getNodeController() {
		if (this.nodeController == null) {
			FireNode root = getRootNode(); 
			if (root != null) {
			this.nodeController = new NodeController(getRootNode()); 
			} else {
				makeToast("There is no daily schedual loaded!");
			}
		}
		return this.nodeController; 
		
	}
	

	
	public TasksController getInspectionItemsController() {
		if (this.inspectionItemsController == null) {
			this.inspectionItemsController = new TasksController(this); 
		}
		return this.inspectionItemsController; 
	}
	

    public String getFileName(){
    	return new String("mnt/sdcard/FireAlertScanner/InspectionData.xml");
    }
    
	public FireNode getRootNode(){
		if (this.root == null) {
			/* 
			 * If the root node does not exist create the node tree structure using the 
			 * parser controller. 
			 */
			this.root = getModel().getReport(); 
		}
		return this.root; 
	}

	
	public void moveContextNodeUp(String headerId){
		/* 
		 * Tell the node controller to move the context node to 
		 * the parent with an Id matching the header string. It will
		 * return an integer of the depth between the header ancestor 
		 * and base header. 
		 */
		int numberOfRemovals = getNodeController().goToParentNode(headerId, this);
	
		/* 
		 * Tell the listViewController to remove that number of parent headers
		 * from the listView, then tell the controller to re-render the View.  
		 *
		 */
		getListViewController().deleteHeaders(numberOfRemovals);
		getListViewController().renderListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(main.firealertapp.R.menu.main, menu);
		return true;
	}
		
	public void setCurrentUserName(String u) {
		System.out.println("Setting username in the method: " + u);
		this.userName = new String(u);
	}
	
	public String getCurrentUserName(){
		return this.userName;
	}

	public boolean getLockPopUp() {
		return lockPopUp;
	}
	
	@Override
	protected void onResume() {
	    
		//initialComponent();
		
		super.onResume();
		loggedOut = false; 

	}
	
	@Override
    public void onStop()
    {
		if(!loggedOut) new SavingTask().execute(); 
        super.onStop();
        //Do whatever you want to do when the application stops.
    }
	

    @Override
    public void onDestroy()
    {
    	unregisterReceiver();
        getParserController().saveEmAll(getNodeController().myDict);
        super.onDestroy();
        //Do whatever you want to do when the application is destroyed.
    }
    
	private void unregisterReceiver() {
		if (dataScanner != null) unregisterReceiver(dataScanner);
	}
	
	private void initialComponent() {
//		tv_getdata_from_scanner = (TextView) findViewById(R.id.tv_getdata_from_scanner);
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
			System.out.println("THIS IS PRINTING:" +  x);
			MainActivity.this.getListViewController().goToEquipment(x);
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
				//tv_getdata_from_scanner.setText("Get data from Scanner : " + content);
				
				getListViewController().goToEquipment(fixContent(content));
			}	
		}
	}
	
	public String fixContent(String c) {
		if ((c.isEmpty()) || (c.length()-1 == 0)){
			return c;
		}
		return c.substring(0, c.length()-1);
	}
	
	public void onBackPressed() {
	    // your code.
		System.out.println("Back Pressed");
		if(this.userName != null) {
			
			if (getListViewController() != null) {
				getListViewController().goBack(); 
			}
		}
	}
	
	public boolean popUpPresent() {
		return popUp; 
	}
	
	public void setPopUpPresent(boolean p) {
		popUp = p; 
	}
	

    
    public void getLoginController() {
			this.loginController = new LoginController(MainActivity.this, getUserAccounts());
     }

	public void getSignUpController() {
		// TODO Auto-generated method stub
//		System.out.println(getSecurePreferences().getString(UserAccounts.getUserKey()));
		this.signUpController = new SignUpController(MainActivity.this, getUserAccounts());
		
	}
	public void makeToast(String butter) {
		Toast.makeText(this, butter, Toast.LENGTH_LONG).show();
		return;	
	}
	public UserAccounts getUserAccounts() {
		if (this.userAccountsModel == null) this.userAccountsModel = new UserAccounts(this);
		return userAccountsModel;
	}
	
	public void launchSettingsView() {
		 this.settingsView = new SettingsView(this); 
	}
	
	public WebClient getWebClient() {
		if (this.webClient == null) {
			this.webClient = new WebClient(this);
		}
		return this.webClient;
	}

	public Model getModel() {
		// TODO Auto-generated method stub
		if (this.model == null) {
			this.model = new Model(this);
		}
		return this.model;
	}


}
