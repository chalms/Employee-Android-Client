package controllers;

import main.metrics.MainActivity;
import models.nodes.Task;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TasksController {
	private Context window;

	public TasksController(Context context) {
		this.window = context;
	}

	public void showTasks(String itemId) {
		Task theElement = getIE(itemId);
		final Dialog dialog = getDialog(theElement);
		final EditText editPassOrFail = getPassOrFailEditText(dialog, theElement);
		final Button btnSubmit = (Button) dialog.findViewById(main.metrics.R.id.descriptionSubmitButton);
		setTestItemSubmissionOnClickListener(btnSubmit, dialog, theElement, editPassOrFail, "Fail");
		dialog.show();
	}

	public void showTasks(String itemId, String type) {
		Task theElement = getIE(itemId);
		final Dialog dialog = getDialog(theElement);
		final EditText editPassOrFail = getPassOrFailEditText(dialog, theElement);
		final Button btnSubmit = (Button) dialog.findViewById(main.metrics.R.id.descriptionSubmitButton);
		setTestItemSubmissionOnClickListener(btnSubmit, dialog, theElement, editPassOrFail, type);
		dialog.show();
	}

	private void setTestItemSubmissionOnClickListener(Button btnSubmit,final Dialog dialog, final Task theElement, final EditText editPassOrFail, final String type) {
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

	private Task getIE(String itemId) {
		return ((MainActivity) window).getNodeController().getTaskById(itemId);
	}

	private EditText getPassOrFailEditText(Dialog dialog, Task theElement) {
		EditText editPassOrFail = (EditText) dialog.findViewById(main.metrics.R.id.editText1);
		editPassOrFail.setText(theElement.getTestNote());
		return editPassOrFail;
	}

	private Dialog getDialog(Task theElement) {
		Dialog dialog = new Dialog(window);
		dialog.setContentView(main.metrics.R.layout.fail_description_dialouge);
		dialog.setTitle(theElement.getDisplay());
		return dialog;
	}
}
