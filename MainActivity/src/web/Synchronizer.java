package web;

import com.loopj.android.http.AsyncHttpResponseHandler;

import main.metrics.MainActivity;
import models.Model;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class Synchronizer extends Activity {
	MainActivity context; 
	Model model; 
	WebClient webClient; 
	ProgressDialog progressDialog; 
	
	
	Synchronizer(MainActivity con, Model mod, WebClient webCli, String url, AsyncHttpResponseHandler responseHandler) {
		context = con; 
		model = mod; 
		webClient = webCli; 
		if (responseHandler == null) {
			responseHandler = new AsyncHttpResponseHandler() {
	    		
	    	};   
		} 
		new Async().execute();
	}

	class Async extends AsyncTask <WebRequest, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            m_ProgressDialog = ProgressDialog.show(context,"", "Loading ...", true);
        }

		@Override
		protected String doInBackground(WebRequest... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
        protected void onProgressUpdate(String... progress) {
            System.out.println("ANDRO_ASYNC"); 
            System.out.println(progress[0]);

        }

        @Override
        protected void onPostExecute(String unused) {
           
	        context.
	        m_ProgressDialog.dismiss();
        }

    } 
}
