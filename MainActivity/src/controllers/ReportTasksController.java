package controllers;

import main.metrics.MainActivity;
import models.nodes.ReportTask;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReportTasksController {
	private Context window;

	public ReportTasksController(Context context) {
		this.window = context;
	}

	public void showReportTasks(String itemId) {
		ReportTask theElement = getIE(itemId);
		final Dialog dialog = getDialog(theElement);
		final EditText editPassOrFail = getPassOrFailEditText(dialog, theElement);
		final Button btnSubmit = (Button) dialog.findViewById(main.metrics.R.id.descriptionSubmitButton);
		setTestItemSubmissionOnClickListener(btnSubmit, dialog, theElement, editPassOrFail, "Fail");
		dialog.show();
	}

	public void showReportTasks(String itemId, String type) {
		ReportTask theElement = getIE(itemId);
		final Dialog dialog = getDialog(theElement);
		final EditText editPassOrFail = getPassOrFailEditText(dialog, theElement);
		final Button btnSubmit = (Button) dialog.findViewById(main.metrics.R.id.descriptionSubmitButton);
		setTestItemSubmissionOnClickListener(btnSubmit, dialog, theElement, editPassOrFail, type);
		dialog.show();
	}

	private void setTestItemSubmissionOnClickListener(Button btnSubmit,final Dialog dialog, final ReportTask theElement, final EditText editPassOrFail, final String type) {
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String testNote = editPassOrFail.getText().toString();
				theElement.setTestResult(type);
				theElement.setTestNote(testNote);
				dialog.dismiss();
				((MainActivity) window).getNodeController().addToDictionary(theElement.getID());
				((MainActivity) window).getListViewController().dontAddHeader = true;
				((MainActivity) window).getListViewController().renderListView();
			}
		});
	}

	private ReportTask getIE(String itemId) {
		return ((MainActivity) window).getNodeController().getReportTaskById(itemId);
	}

	private EditText getPassOrFailEditText(Dialog dialog, ReportTask theElement) {
		EditText editPassOrFail = (EditText) dialog.findViewById(main.metrics.R.id.editText1);
		editPassOrFail.setText(theElement.getTestNote());
		return editPassOrFail;
	}

	private Dialog getDialog(ReportTask theElement) {
		Dialog dialog = new Dialog(window);
		dialog.setContentView(main.metrics.R.layout.fail_description_dialouge);
		dialog.setTitle(theElement.getDisplay());
		return dialog;
	}
}
