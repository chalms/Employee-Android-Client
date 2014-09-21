package controllers;

import main.metrics.MainActivity;
import models.ReportTask;
import models.UsersReport;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ReportTaskLineItemController implements OnItemClickListener {

	public MainActivity context;

	public  ReportTaskLineItemController(MainActivity con) {
		context = con;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	//	TextView rowInList = ((TextView) view.findViewById(main.metrics.R.id.headerViewItem));
		if (view.getTag().equals(UsersReport.usersReport.getTagHash())) {
			// react for top bar
		} else {
			ReportTask r = UsersReport.usersReport.reportsTaskList.reportsTasks.get(Integer.parseInt((String) view.getTag())); 
			context.setReportTaskController(r); 
			context.getUsersReportController().setVisible(false);
			context.setContentView(context.getReportTaskController().getScrollView());
		}
		
	}
}
