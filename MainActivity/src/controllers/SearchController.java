package controllers;

import main.metrics.MainActivity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchController {
	private Context context; 

	public SearchController(Context context){
		this.context = context;
	}
	
	public void showSearchDialog() {
		// TODO Auto-generated method stub
		final Dialog descriptionDialog = createDescriptionDialog();
		final EditText failDescription = (EditText) descriptionDialog
				.findViewById(main.firealertapp.R.id.search_layout_id);
		final Button submitBtn = (Button) descriptionDialog
				.findViewById(main.firealertapp.R.id.button_search);
		setButtonOnClickListener(submitBtn, failDescription, descriptionDialog);
		((MainActivity) context).setPopUpPresent(true); 
		descriptionDialog.show();
	}
	
	public void showSearchDialog(String input) {
		// TODO Auto-generated method stub
		final Dialog descriptionDialog = createDescriptionDialog();
		final EditText failDescription = (EditText) descriptionDialog
				.findViewById(main.firealertapp.R.id.search_layout_id);
		final Button submitBtn = (Button) descriptionDialog
				.findViewById(main.firealertapp.R.id.button_search);
		failDescription.setText(input);
		setButtonOnClickListener(submitBtn, failDescription, descriptionDialog);
		((MainActivity) context).setPopUpPresent(true); 
		descriptionDialog.show();
		
	}
	
	private void  setButtonOnClickListener(
			Button submitBtn, 
			final EditText failDescription,
			final Dialog d){
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String noteValue = failDescription.getText().toString();
				((MainActivity) context).getListViewController().goToEquipment(noteValue);
				d.dismiss();
				((MainActivity) context).setPopUpPresent(false); 
				// .setTestNote(noteValue);
			}
		});
	}
	
	public void callIt(String input){
		((MainActivity) context).getListViewController().goToEquipment(input);
//		descriptionDialog.dismiss();
		return;	
	}
	
	private Dialog createDescriptionDialog(){
		Dialog descriptionDialog = new Dialog(context);
		descriptionDialog.setContentView(main.firealertapp.R.layout.search_layout);
		descriptionDialog.setTitle("Equipment Search");
		return descriptionDialog; 
	}
}
