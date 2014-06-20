package views;

import main.metrics.MainActivity;
import android.app.ProgressDialog;

public class LoadingBar {
	private ProgressDialog progress;
	
	public LoadingBar(MainActivity c, String title, String message) {
		progress = new ProgressDialog(c); 
		drawLoadingBar(title, message);
	}
	
	public void drawLoadingBar(String title, String message) {
		progress.setTitle(title);
		progress.setMessage(message);
		progress.show();
	}
	
	public void dismiss() { 
		progress.dismiss();
	}
	
}
