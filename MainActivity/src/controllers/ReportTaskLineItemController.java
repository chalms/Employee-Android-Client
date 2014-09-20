package controllers;

import models.UsersReport;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ReportTaskLineItemController implements OnItemClickListener {

	public Context window;

	public  ReportTaskLineItemController(Context con) {
		window = con;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView rowInList = ((TextView) view.findViewById(main.metrics.R.id.headerViewItem));
		if (rowInList.getTag().equals(UsersReport.usersReport.getTagHash())) {
			// react for top bar
		} else {
			// react for common folk
		}
		
	}
}
