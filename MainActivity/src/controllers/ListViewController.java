package controllers;

import java.util.ArrayList;
import java.util.Stack;

import main.metrics.MainActivity;
import main.metrics.R;
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
	Button signOut;
	Button messageBox;
	Button searchButton;
	SettingsView settingsView; 

	public ListViewController(Context con, NodeController nodeController){
		this.context = con; 
		this.deletedStack = new Stack<View>();
		this.headerList = new ArrayList<View>();
		this.jumpDownList = new Stack<FireNode>();
		this.nodeController = nodeController; 
		this.topHeaderAdded = false;
		this.signOut = null;
	}

	public void renderListView() {			
		System.out.println("ListViewController() -> renderListView ");
		ListView listViewItems = new ListView(this.context);
		LayoutInflater inflater = (LayoutInflater)this.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		createHeader(inflater, listViewItems);
		addHeaderView(listViewItems);		
		fillListView(listViewItems);
	}

	public void goBack() {
		System.out.println("ListViewController() -> goBack ");
		if (1 < nodeController().parentNodes.size()) {
			if (1 < nodeController().parentNodes.size()){
				nodeController().parentNodes.pop();
				deleteHeaders(1);
			}
			nodeController().childNodes = nodeController().parentNodes.peek().childList();
		} else {
			dontAddHeader = true; 
		}
		renderListView(); 
	}

	public void deleteHeaders(int amount) {
		System.out.println("ListViewController() -> deleteHeaders (amount)");
		if (amount > (headerList.size())) amount = headerList.size();
		for (int j = 0; j < amount; j++) {
			deletedStack.push(headerList.remove(headerList.size() - 1));
			dontAddHeader = true;
		}
		if (amount == 0) dontAddHeader = true;
	}

	public void goToEquipment(String input){
		System.out.println("ListViewController() -> goToEquipment(input) ");
		boolean clearNodeData = false; 
		goToRoot(clearNodeData);
		setJumpDownList(input);
		if (!jumpDownList.empty()){
			System.out.println(jumpDownList.peek().getID());
			jumpDownList.pop();
		}
		if (!jumpDownList.empty()) {
			while (jumpDownList.size() > 0) {
				nodeController().goToNodeItemInList(jumpDownList.pop().getID());
				renderListView();
			}
			Toast.makeText(context, nodeController().parentNodes.peek().getDisplay(), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "Search failed!", Toast.LENGTH_LONG).show();
		}
	}

	public void goToRoot(boolean clear){
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

	public void makeToast(String butter) {
		Toast.makeText(context, butter, Toast.LENGTH_LONG).show();
		return;	
	}

	/*						     Private Methods							*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*
	 *																		*/

	private NodeController nodeController() {
		return ((MainActivity) context).getNodeController();
	}

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
			System.out.println("ListViewController() -> createHead.. -> header = (ViewGroup).. ");
			header = (ViewGroup) inflater.inflate(R.layout.list_header, (ViewGroup) listViewItems.getParent(), false);
			System.out.println("ListViewController() -> createHead.. -> header = (setTextView).. ");
			setTextView(((TextView) header.findViewById(main.metrics.R.id.headerViewItem)));
			System.out.println("lower header created");
		}	
		addHeader(header);
	}

	private void setTextView(TextView text){
		System.out.println("ListViewController() -> setTextVie.. -> setting text view");
		text.setText(nodeController.getParentNodeDisplay());
		text.setTag(nodeController.getContextNode().getTag());
		System.out.println("ListViewController() -> setTextVie.. -> setting text view for root");
		if (text.getText().equals(this.nodeController.root.getDisplay())) {
			text.setText(this.nodeController.root.getDisplay());
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
		System.out.println("ListViewController() -> checkHead.. -> Checking if header completed ");
		return nodeController.parentNodes.get(i).checkCompleted(true);
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
		ListItemContent[] rowContent = nodeController().getRowContents();
		System.out.println(rowContent.toString());
		System.out.println("ListViewController() -> fillLi.. -> About to set list item adaptor: ");
		ListItem adapter = setListItemAdapter(rowContent);
		System.out.println("ListViewController() -> fillLi.. -> About to set background color ");
		listViewItems.setBackgroundColor(((MainActivity) context).getTitleColor());
		System.out.println("ListViewController() -> fillLi.. -> About to set OnItemClickLis...");
		listViewItems.setOnItemClickListener(new ListViewItemController(this.context));
		System.out.println("ListViewController() -> fillLi.. -> About to set Adaptor...");
		listViewItems.setAdapter(adapter);
		System.out.println("ListViewController() -> fillLi.. -> About to set ContentView...");
		((MainActivity) context).setContentView(listViewItems);
		System.out.println("ListViewController() -> fillLi.. -> recomputing and showing");
		listViewItems.recomputeViewAttributes(listViewItems);
		listViewItems.bringToFront();
		listViewItems.requestLayout();
	}

	private ListItem setListItemAdapter(ListItemContent[] rowContent) {
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