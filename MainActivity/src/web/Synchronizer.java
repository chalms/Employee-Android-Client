package web;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;


public class Synchronizer extends Activity {
	static ArrayList <Async> AsyncTasks = new ArrayList <Async> (); 
	static int count; 
	
	void execute(RequestWrapper r){
		System.out.println("Launching request wrapper with activity: " + r);
		System.out.println(r.toString());
		AsyncTasks.add((Async) new Async().execute(r));
	}
	
//~~~~~~~~~~~~~~~~ Async executor~~~~~~~~~~~
	class Async extends AsyncTask <RequestWrapper, Void, CallbackWrapper>{
	
		protected CallbackWrapper doInBackground(RequestWrapper... arg0) {
			System.out.println("Executing the request");
			return arg0[0].executeRequest();
		}
		
		protected void onPostExecute(CallbackWrapper c) {
			System.out.println("Post execute called");
			c.render(); 
			AsyncTasks.remove(this);
			return; 
		}
	}
}
