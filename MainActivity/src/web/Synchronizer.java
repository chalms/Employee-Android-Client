package web;

import android.app.Activity;
import android.os.AsyncTask;


public class Synchronizer extends Activity {
	CallbackWrapper callback; 
	RequestWrapper request; 
	
	public Synchronizer(CallbackWrapper c, RequestWrapper r) { 
		callback = c; 
		request = r; 
		new Async().execute();
	}

	class Async extends AsyncTask <String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          
        }

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			int result = request.executeRequest();
			System.out.println(result);
			return null;
		}
		
        protected void onProgressUpdate(String... progress) {
            System.out.println("ANDRO_ASYNC"); 
            System.out.println(progress[0]);

        }

        @Override
        protected void onPostExecute(String unused) {
	       callback.render(); 
        }

    } 
}
