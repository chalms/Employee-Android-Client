package controllers;

import main.firealertapp.MainActivity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


	public class ListViewItemController implements OnItemClickListener {
   
		public Context window; 
		public ListViewItemController(Context con){
			/* 
			 * Send the listener the main activity context so that it can call 
			 * MainActivity functions
			 */
			window = con; 
		}
		
		public void headerClicked(String headerId){
	
			if (headerId.equals("1")) {
//				/*
//				 *  The top search bar must of been clicked, so tell the main
//				 *  activity to create and pass control to the search controller
//				 *  which will call its showSearchDialog() method;
//				 */
//				((MainActivity) window).getSearchController().showSearchDialog();
				
			} else {
				/*
				 *  A header was clicked so the list has to move up to that node. 
				 *  
				 */
				((MainActivity) window).moveContextNodeUp(headerId);
			
			}
			return; 
		}
		
		public void rowClicked(TextView textViewItem){
			
				String rowContentTag = textViewItem.getTag().toString();
				((MainActivity) window).getNodeController().goToNodeItemInList(rowContentTag);	
				((MainActivity) window).getListViewController().renderListView();
			
		}
		
//		 @Override
//		public void onClick(View arg0) {
//		        Log.v(TAG, "onItemClick at position" + mPosition);          
//		   }    
		 
		 
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		
			TextView headerViewItem = ((TextView) view.findViewById(main.firealertapp.R.id.headerViewItem));
			

			 if (headerViewItem != null)
			{
	
				headerClicked(headerViewItem.getTag().toString());
			} else {

				TextView textViewItem = ((TextView) view.findViewById(main.firealertapp.R.id.textViewItem));

				if (!(textViewItem.getTag().equals("InspectionElement"))) {
					rowClicked(textViewItem);
				}
			}
		}
	}
	