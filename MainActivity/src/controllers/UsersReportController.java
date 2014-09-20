package controllers;


import main.metrics.MainActivity;
import main.metrics.R;
import models.UsersReport;
import views.ListItem;
import views.ReportTaskLineItem;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UsersReportController extends ListActivity {
	
	int ROW_WITH_BUTTONS = main.metrics.R.layout.list_view_row_buttons;
	int ROW_TRIPLE_BUTTON = main.metrics.R.layout.list_view_row_triplebuttons; 
	int ROW_WITHOUT_BUTTONS = main.metrics.R.layout.list_view_row_item; 
	int LIST_HEADER = main.metrics.R.layout.list_header; 
	UsersReport report; 
	MainActivity context; 
	
	public UsersReportController(MainActivity c) {
		context = c; 
	}
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
	    // Do something when a list item is clicked 
		// ---> this is referenced in report task line item controller
	}
	
	public void goToEquipment(String input) {
		// display activity for a particular equipment piece 
	}
		
	public void renderReport() {			
		ListView reportView = new ListView(this.context);
		LayoutInflater inflater = (LayoutInflater)  this.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		createHeader(inflater, reportView);
		populate(reportView);
	}

	public void complete(String name) {
		Toast.makeText(context, name + " complete!", Toast.LENGTH_LONG).show();
	}

	public void makeToast(String butter) {
		Toast.makeText(context, butter, Toast.LENGTH_LONG).show();
		return;	
	}

	@SuppressLint("ShowToast")
	private void createHeader(LayoutInflater inflater, ListView listViewItems){
		ViewGroup header = null;
		header = (ViewGroup) inflater.inflate(R.layout.list_header, (ViewGroup) listViewItems.getParent(), false);
		TextView headerTextView = ((TextView) header.findViewById(main.metrics.R.id.headerViewItem));
		headerTextView.setBackgroundColor(getResources().getColor(main.metrics.R.color.red));
		headerTextView.setText(report.name);
		headerTextView.setTag(report.getTagHash()); 
	}

	private void populate(ListView reportTaskLineItemView) {
		ReportTaskLineItem[] rowContent = UsersReport.getRowContent(); 
		ListItem adapter = setListItemAdapter(rowContent);
		reportTaskLineItemView.setBackgroundColor(((MainActivity) this.context).getTitleColor());
		reportTaskLineItemView.setOnItemClickListener(this.context.getReportTaskLineItemController());
		reportTaskLineItemView.setAdapter(adapter);
		((MainActivity) context).setContentView(reportTaskLineItemView);
		reportTaskLineItemView.recomputeViewAttributes(reportTaskLineItemView);
		reportTaskLineItemView.bringToFront();
		reportTaskLineItemView.requestLayout();
	}

	private ListItem setListItemAdapter(ReportTaskLineItem[] rowContent) {
			return new ListItem(this.context, ROW_WITHOUT_BUTTONS, rowContent);
	}


}