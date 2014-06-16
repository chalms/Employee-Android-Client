package controllers;

import java.util.ArrayList;
import java.util.Stack;

import main.firealertapp.MainActivity;
import main.firealertapp.R;
import models.nodes.FireNode;
import views.ListItem;
import views.ListItemContent;
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
	private NodeController nodeController;
	private Stack<View> deletedStack;
	private ArrayList<View> headerList;
	Stack<FireNode> jumpDownList;
	boolean dontAddHeader;
	private Context context;
	boolean topHeaderAdded;
	boolean secondHeaderAdded;
	Button sendButton;
	Button viewXmlButton;
	Button searchXmlButton;
	SettingsView settingsView; 
	
	public ListViewController(Context con, NodeController nodeController){
		this.context = con; 
		this.deletedStack = new Stack<View>();
		this.headerList = new ArrayList<View>();
		this.jumpDownList = new Stack<FireNode>();
		this.nodeController = nodeController; 
		this.topHeaderAdded = false;
		this.sendButton = null;
	}
	
	public void renderListView() {			
		/* 																
		 * Create a listView within the current activity. Inflate the 
		 * listView with all the headers within the 'headerList'. The ViewGroup
		 * 'header' references the template for 'header' list items. It is created 
		 * with data held in the NodeControllers 'parent node' object. 
		 * Non-header items are created based off the data held in the 
		 * node controllers 'childlist' nodes, by calling 'getRowContent' 
		 * on each FireNode in the controllers childlist 
		 */
		
		ListView listViewItems = new ListView(this.context);
		LayoutInflater inflater = (LayoutInflater)this.context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		
		//ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_header, (ViewGroup) listViewItems.getParent(), false);	

		createHeader(inflater, listViewItems);
		addHeaderView(listViewItems);		
		fillListView(listViewItems);
	}
	public void goBack() {
		if (1 < nodeController().parentNodes.size()) {
			System.out.println("in the if statement");
		   if (1 < nodeController().parentNodes.size()){
		    	nodeController().parentNodes.pop();
		    	deleteHeaders(1);
		    }
		    System.out.println("This happens after the while loop " + String.valueOf(nodeController.parentNodes.size()));
		    System.out.println(nodeController.parentNodes.size());
		    nodeController().childNodes = nodeController().parentNodes.peek().childList();
			} else {
			dontAddHeader = true; 
		}
		renderListView(); 
	}
	public void deleteHeaders(int amount) {
		/* 
		 * This method removes a number 'amount', from the 
		 * tail of the ArrayList, 'headerList'. This is used to remove header views 
		 * when an ancestor headerNode (from the context) is selected.
		 */
		if (amount > (headerList.size())) {
			amount = headerList.size();
		}
		for (int j = 0; j < amount; j++) {
			deletedStack.push(headerList.remove(headerList.size() - 1));
			dontAddHeader = true;
		}
		if (amount == 0){
			dontAddHeader = true;
		}
	}
	public void goToEquipment(String input){
		boolean clearNodeData = false; 
		goToRoot(clearNodeData);
		
		System.out.println("Here is the input in the list view controller: " + input + "|");
		
		setJumpDownList(input);
		
		//Stack <FireNode> tempStack = new Stack <FireNode> ();
		

		if (!jumpDownList.empty()){
			System.out.println(jumpDownList.peek().getID());
			jumpDownList.pop();
//			System.out.println(t.getID() + ":" + t.getTag());
//			tempStack.push(t);	
		}

		
		if (!jumpDownList.empty()) {
			//dontAddHeader = true;

			while (jumpDownList.size() > 0) {
				
				nodeController().goToNodeItemInList(jumpDownList.pop().getID());
				renderListView();
				
			}
		
			Toast.makeText(context, nodeController().parentNodes.peek().getDisplay(),
					Toast.LENGTH_LONG).show();

		} else {

			Toast.makeText(context, "Search failed!", Toast.LENGTH_LONG).show();
		}
	}
	

	public void goToRoot(boolean clear){
		System.out.println("This is the root: " + String.valueOf(nodeController.parentNodes.size()));
		if (1 < nodeController().parentNodes.size()) {
			System.out.println("in the if statement");
		    while(1 < nodeController().parentNodes.size()){
		    	nodeController().parentNodes.pop();
		    	deleteHeaders(1);
		    }
		    System.out.println("This happens after the while loop " + String.valueOf(nodeController.parentNodes.size()));
		    System.out.println(nodeController.parentNodes.size());
		    nodeController().childNodes = nodeController().parentNodes.peek().childList();
			} else {
			dontAddHeader = true; 
		}
		if (clear) nodeController.parentNodes.peek().clear();
	    renderListView();
	}
	
	public void goUpOne() {
		deleteHeaders(1);
		renderListView();
	}

	
	public void setJumpDownList(String input) {
		jumpDownList = nodeController().parentNodes.peek().checkForId(input);
		return; 
	}
	
	public void complete(String name) {
		Toast.makeText(context, name + " complete!", Toast.LENGTH_LONG).show();
	}
	
	
	/*						     Private Methods							*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*
	 *																		*/
	
	private NodeController nodeController() {
		return ((MainActivity) context).getNodeController();
	}
	public void makeToast(String butter) {
		Toast.makeText(context, butter, Toast.LENGTH_LONG).show();
		return;	
	}
	@SuppressLint("ShowToast")
	private void createHeader(LayoutInflater inflater, ListView listViewItems){
		
		ViewGroup header = null;
		if (!topHeaderAdded) {
			//The top header hasn't been added
			header = (ViewGroup) inflater.inflate(R.layout.top_list_header, (ViewGroup) listViewItems.getParent(), false);
			TextView tv = (TextView) header.findViewById(main.firealertapp.R.id.headerViewItem); 
			setTextView(tv);
			SettingsOnTextViewClick(tv);
		
			System.out.println("top header created");
			topHeaderAdded = true;
			
			sendButton = (Button) header.findViewById(main.firealertapp.R.id.sendXmlButton);
			viewXmlButton = (Button) header.findViewById(main.firealertapp.R.id.viewXmlButton);
			searchXmlButton = (Button) header.findViewById(main.firealertapp.R.id.searchButton);
			
			sendButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Send button clicked");
					if (nodeController.root.checkCompleted(true)){
						try {
						((MainActivity) context).getSendController().showSendDialog();
						} catch (Exception e) {
							System.out.println(e.getMessage());
							Toast.makeText(context, "There was an error in the sending process. Check your computer info.", 5);
						}
					} else {
						makeToast("Sorry, you must complete all inspection items before sync.");
					}
				}
			});
			
			viewXmlButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Send button clicked");
					((Object) context).displayViewDocumentController();
				}
			});
			
			searchXmlButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Send button clicked");
					((MainActivity) context).getSearchController().showSearchDialog();
				}
			});
		}
		else
		{ //The top header has been added
		
				header = (ViewGroup) inflater.inflate(R.layout.list_header, (ViewGroup) listViewItems.getParent(), false);
				setTextView(((TextView) header.findViewById(main.firealertapp.R.id.headerViewItem)));
				System.out.println("lower header created");
		
		}	
		addHeader(header);
	}
	
	private void setTextView(TextView text){
		
		text.setText(nodeController.getParentNodeDisplay());
		text.setTag(nodeController.getContextNode().getID());
		
		if (text.getText().equals(this.nodeController.root.getDisplay())) {
			text.setText(((MainActivity) context).getCurrentUserName());
			//text.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_search, 0, 0, 0);
		}
		
	}
	
	private void addHeader(ViewGroup header){
		if (!dontAddHeader) {
			headerList.add(header);
		} else {
			dontAddHeader = false;
		}
	}
	
	private boolean checkHeaderCompleted(int i){
		return ((MainActivity) context).getNodeController().parentNodes.get(i).checkCompleted(true);
	}
	
	private void addHeaderView(ListView listViewItems) {
		for (int i = 0; i < headerList.size(); i++) {
			
			if (checkHeaderCompleted(i)) {
				headerList.get(i).setBackgroundResource(R.color.green);
				
			} else { 
				headerList.get(i).setBackgroundResource(R.color.maroonred);
			}
			listViewItems.addHeaderView(headerList.get(i));
		}
	}
	
	private void fillListView(ListView listViewItems) {
		ListItemContent[] rowContent = nodeController().getRowContents();
		
		ListItem adapter = setListItemAdapter(rowContent);

		listViewItems.setBackgroundColor(((MainActivity) context).getTitleColor());
		listViewItems.setOnItemClickListener(new ListViewItemController(this.context));
		listViewItems.setAdapter(adapter);
		
		((MainActivity) context).setContentView(listViewItems);
		listViewItems.recomputeViewAttributes(listViewItems);
		listViewItems.bringToFront();
		listViewItems.requestLayout();
	}
	
	private ListItem setListItemAdapter(ListItemContent[] rowContent) {
		if (rowContent[0].getTag().equals("InspectionElement")) {
			return new ListItem(context, main.firealertapp.R.layout.list_view_row_buttons, rowContent);
		}
		else {
			return new ListItem(context, main.firealertapp.R.layout.list_view_row_item, rowContent);
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