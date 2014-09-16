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

	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView headerViewItem = ((TextView) view.findViewById(main.metrics.R.id.headerViewItem));
	}
}
