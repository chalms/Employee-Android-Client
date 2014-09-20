package main.metrics;

import android.app.Activity;
import android.os.Bundle;

public class LocationIntentHandler<IParcel> extends Activity {
	
	  public void onCreate(Bundle savedInstanceState) {  
	       
	         super.onCreate(savedInstanceState);  

	         IParcel pc = getIntent().getExtras().getParcelable("obj");
	         if(pc != null){
	                // this will launch the bluetooth enabling activity
	         } 

	  }
	
}
