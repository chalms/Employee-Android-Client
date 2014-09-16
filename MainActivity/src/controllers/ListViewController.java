package controllers;

import java.util.ArrayList;
import java.util.Stack;

import main.metrics.MainActivity;
import main.metrics.R;
import models.nodes.FireNode;
import views.ListItem;
import views.ReportTaskLineItem;
import views.SettingsView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewController { 

	public ListViewController(){

	}

	public void renderListView() {			
		ListView listViewItems = new ListView(this.context);
		LayoutInflater inflater = (LayoutInflater)this.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		createHeader(inflater, listViewItems);
		addHeaderView(listViewItems);		
		fillListView(listViewItems);
	}


	public void complete(String name) {
		Toast.makeText(context, name + " complete!", Toast.LENGTH_LONG).show();
	}

	public void makeToast(String butter) {
		Toast.makeText(context, butter, Toast.LENGTH_LONG).show();
		return;	
	}

	/*						     Private Methods							*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*
	 */

	@SuppressLint("ShowToast")
	private void createHeader(LayoutInflater inflater, ListView listViewItems){
		ViewGroup header = null;
		if (!topHeaderAdded) {
			System.out.println("ListViewController() -> createHead.. -> setting header textview & settings ");
			header = (ViewGroup) inflater.inflate(R.layout.top_list_header, (ViewGroup) listViewItems.getParent(), false);
			TextView tv = (TextView) header.findViewById(main.metrics.R.id.headerViewItem); 
			setTextView(tv);
			SettingsOnTextViewClick(tv);

			topHeaderAdded = true;
			signOut = (Button) header.findViewById(main.metrics.R.id.sendXmlButton); //sendXmlButton
			messageBox = (Button) header.findViewById(main.metrics.R.id.viewXmlButton); //viewXmlButton
			searchButton = (Button) header.findViewById(main.metrics.R.id.searchButton); 

			System.out.println("ListViewController() -> createHead.. -> setting button listeners.. ");
			signOut.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Send button clicked");
					if (nodeController.root.checkCompleted(true)){
						try {
				//TODO: 			((MainActivity) context).getMainController().checkout(); 
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else {
					//	((MainActivity) context).getMainController(). logout
					}
				}
			});

			messageBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				//TODO:	((MainActivity) context).getChatsController().open(); 
				}
			});

			searchButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity) context).getSearchController().showSearchDialog();
				}
			});

		} else { 
			header = (ViewGroup) inflater.inflate(R.layout.list_header, (ViewGroup) listViewItems.getParent(), false);
			setTextView(((TextView) header.findViewById(main.metrics.R.id.headerViewItem)));
			System.out.println("lower header created");
		}	
		addHeader(header);
	}

	private void setTextView(TextView text){
		text.setText();
		text.setTag(nodeController.getContextNode().getTag());
	}

	private void addHeaderView(ListView listViewItems) {
		for (int i = 0; i < headerList.size(); i++) {
			if (checkHeaderCompleted(i)) {
				headerList.get(i).setBackgroundResource(R.color.green);
			} else { 
				headerList.get(i).setBackgroundResource(R.color.maroonred);
			}
			System.out.println("ListViewController() -> checkHead.. -> About to add header view  ");
			
			listViewItems.addHeaderView(headerList.get(i));
		}
	}

	private void fillListView(ListView listViewItems) {
		ReportTaskLineItem[] rowContent = nodeController().getRowContents();
		ListItem adapter = setListItemAdapter(rowContent);
		listViewItems.setBackgroundColor(((MainActivity) context).getTitleColor());
		listViewItems.setOnItemClickListener(new ListViewItemController(this.context));
		listViewItems.setAdapter(adapter);
		((MainActivity) context).setContentView(listViewItems);
		listViewItems.recomputeViewAttributes(listViewItems);
		listViewItems.bringToFront();
		listViewItems.requestLayout();
	}

	private ListItem setListItemAdapter(ReportTaskLineItem[] rowContent) {
		System.out.println("Setting list item adaptor:");
		System.out.println(rowContent.length);
		if (rowContent.length == 0) return null; 
		System.out.println(rowContent.toString());
		System.out.println(rowContent[0].toString());
		
		if (rowContent[0].getTag().equals("Leaf")) {
			return new ListItem(context, main.metrics.R.layout.list_view_row_buttons, rowContent);
		}
		else {
	
			return new ListItem(context, main.metrics.R.layout.list_view_row_item, rowContent);
		}
	}

	private void SettingsOnTextViewClick (final TextView tv) {
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) context).launchSettingsView();
			}
		});
	}
}