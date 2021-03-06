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
				.findViewById(main.metrics.R.id.search_layout_id);
		final Button submitBtn = (Button) descriptionDialog
				.findViewById(main.metrics.R.id.button_search);
		setButtonOnClickListener(submitBtn, failDescription, descriptionDialog);
		((MainActivity) context).setPopUpPresent(true); 
		descriptionDialog.show();
	}
	
	public void showSearchDialog(String input) {
		// TODO Auto-generated method stub
		final Dialog descriptionDialog = createDescriptionDialog();
		final EditText failDescription = (EditText) descriptionDialog
				.findViewById(main.metrics.R.id.search_layout_id);
		final Button submitBtn = (Button) descriptionDialog
				.findViewById(main.metrics.R.id.button_search);
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
				String noteValue = failDescription.getText().toString();
				((MainActivity) context).getListViewController().goToEquipment(noteValue);
				d.dismiss();
				((MainActivity) context).setPopUpPresent(false); 
			}
		});
	}
	
	public void callIt(String input){
		((MainActivity) context).getListViewController().goToEquipment(input);
		return;	
	}
	
	private Dialog createDescriptionDialog(){
		Dialog descriptionDialog = new Dialog(context);
		descriptionDialog.setContentView(main.metrics.R.layout.search_layout);
		descriptionDialog.setTitle("Equipment Search");
		return descriptionDialog; 
	}
}