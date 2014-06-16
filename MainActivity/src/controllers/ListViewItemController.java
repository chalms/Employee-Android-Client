package controllers;

import main.metrics.MainActivity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ListViewItemController implements OnItemClickListener {

	public Context window; 
	public ListViewItemController(Context con){
		window = con; 
	}

	public void headerClicked(String headerId){
		if (headerId.equals("1")) {

		} else {
			((MainActivity) window).moveContextNodeUp(headerId);
		}
		return; 
	}

	public void rowClicked(TextView textViewItem){
		String rowContentTag = textViewItem.getTag().toString();
		((MainActivity) window).getNodeController().goToNodeItemInList(rowContentTag);	
		((MainActivity) window).getListViewController().renderListView();
	} 
	
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
