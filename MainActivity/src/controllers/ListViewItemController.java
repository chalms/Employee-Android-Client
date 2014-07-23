package controllers;

import main.metrics.MainActivity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ListViewItemController implements OnItemClickListener {

	public Context window;

	public ListViewItemController(Context con) {
		window = con;
	}

	public void headerClicked(String headerId) {
		if (headerId.equals("0")) {
			System.out.println("LVIC... headerClicked.. Header equals 1..");
		} else {
			System.out.println("LVIC... headerClicked.. move context node up..");
			((MainActivity) window).moveContextNodeUp(headerId);
		}
		return;
	}

	public void rowClicked(TextView textViewItem) {
		System.out.println("LVIC... rowClicked...");
		String rowContentTag = textViewItem.getTag().toString();
		((MainActivity) window).getNodeController().goToNodeItemInList(
				rowContentTag);
		((MainActivity) window).getListViewController().renderListView();
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("LVIC... onItemClick...");
		System.out.println("LVIC... onItemClick.. View -> " + view.toString());
		TextView headerViewItem = ((TextView) view.findViewById(main.metrics.R.id.headerViewItem));
		
		if (headerViewItem != null) {
			System.out.println("LVIC... OnItemClick... headerViewItem != null ");
	
			if (headerViewItem.getTag() == null) {
				headerViewItem.setTag("1");
			} else {
				System.out.println("header tag" + headerViewItem.getTag().toString());
			}
			System.out.println("header id: " + String.valueOf(headerViewItem.getId()));
			headerClicked(headerViewItem.getTag().toString());
		} else {
			System.out.println("LVIC... OnItemClick... headerViewItem == null");
			TextView textViewItem = ((TextView) view.findViewById(main.metrics.R.id.textViewItem));
			System.out.println("LVIC... OnItemClick... checking textviewitem.getTag()");
			System.out.println("LVIC... OnItemClick... textviewitem.getTag() -> " + textViewItem.getTag().toString());
			if (!textViewItem.getTag().toString().equals("Leaf")) {
				System.out.println("LVIC... OnItemClick... rowClicked(textviewItem)");
				rowClicked(textViewItem);
			}
		}
	}
}
