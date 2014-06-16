package views;

import main.firealertapp.MainActivity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsView {
	
	public Context window;
	private final Dialog dialog;

	private OnClickListener logout = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog.dismiss();
				((MainActivity) window).Logout();
				
			
		}
	};
	public SettingsView(Context c) {
		
		this.window = c;
		this.dialog = new Dialog(this.window);
		this.dialog.setContentView(main.firealertapp.R.layout.settings);
		this.dialog.setTitle("User Settings");

		Button logoutButton = this.SetLogoutButton();

		logoutButton.setOnClickListener(logout);
		
		this.dialog.show();
		
	}

	private Button SetLogoutButton() {
		return (Button) this.dialog.findViewById(main.firealertapp.R.id.settingsLogout);
	}

	
}
