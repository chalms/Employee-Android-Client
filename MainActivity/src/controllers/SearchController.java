package controllers;

import main.metrics.MainActivity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchController {
	private MainActivity context; 

	public SearchController(MainActivity context){
		this.context = context;
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
	
	private void  setButtonOnClickListener(Button submitBtn, final EditText productNumberInput, final Dialog d){
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String productId = productNumberInput.getText().toString();
				submitSearch(productId); 
				d.dismiss();
				((MainActivity) context).setPopUpPresent(false); // some clean up
			}
		});
	}
	
	public void submitSearch(String input){
		this.context.getUsersReportController().goToEquipment(input);
		return;	
	}
	
	private Dialog createDescriptionDialog(){
		Dialog descriptionDialog = new Dialog(context);
		descriptionDialog.setContentView(main.metrics.R.layout.search_layout);
		descriptionDialog.setTitle("Equipment Search");
		return descriptionDialog; 
	}
}