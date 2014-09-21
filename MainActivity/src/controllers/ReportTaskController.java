package controllers;

import java.util.ArrayList;
import java.util.Date;

import views.PhotoGallery;
import main.metrics.MainActivity;
import models.ReportTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ReportTaskController {


	ReportTask reportTask;
	MainActivity context; 
	ScrollView container; 
	View header; 
	LinearLayout stiffLayout; 
	TextView headerTextView; 
	TextView descriptionTextView; 
	EditText noteEditText;
	Button reportTaskCompleteButton; 
	PhotoGallery gallery; 
	Button takePhotoButton; 
	Button galleryPhotoButton; 
	ProgressDialog progress; 
	
	
	public ReportTaskController(MainActivity c, ReportTask repTask) {
		context = c; 
		reportTask = repTask; 
		getReferencesToViews(); 
		fillViewContent(); 
		setListeners(); 
	}
	
	public void destroyMe() {
		context.getUsersReportController().setVisible(true);
		container.destroyDrawingCache();
		container = null; 
		stiffLayout = null; 
		headerTextView = null; 
		descriptionTextView = null; 
		noteEditText = null;
		reportTaskCompleteButton = null; 
		gallery = null; 
		progress = null; 
	}
	
	
	OnClickListener headerClicked = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			destroyMe(); 
		}
	}; 
	
	OnFocusChangeListener noteEdited = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			if (!arg1) {
				String note = ((EditText) arg0).getText().toString();
				reportTask.setNote(note);
			}
		}
	}; 
	
	OnClickListener takePhoto = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			context.dispatchTakePictureIntent();
		}
	}; 
	
	OnClickListener selectPhoto = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			context.selectPhotoIntent();
		};
	};
	
	OnClickListener reportTaskComplete = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
				reportTask.setCompletionTime(new Date());
				reportTask.upload(reportTask.getJson()); 
				progress = new ProgressDialog(context);
				progress.setTitle("Loading");
				progress.setMessage("Wait while loading...");
				progress.show();
		};
	};
	
	public String getReportId() {
		return String.valueOf(reportTask.getId()); 
	}
	
	public void hideLoadingBar() {
		progress.dismiss();
		progress = null; 
	}
	
	public void setPicture(Uri contentUrl) {
		if (gallery == null) return; 
		gallery.add(contentUrl.toString());
		stiffLayout.recomputeViewAttributes(gallery.myPhotoGallery);
	}	
	
	private void getReferencesToViews() {
		LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    container = (ScrollView) li.inflate(main.metrics.R.layout.report_task_scroll_view, null);
		stiffLayout = (LinearLayout) container.findViewById(main.metrics.R.layout.report_task_view); 
		header = (View) stiffLayout.findViewById(main.metrics.R.layout.top_list_header); 
		headerTextView = (TextView) header.findViewById(main.metrics.R.id.headerViewItem);
		descriptionTextView = (TextView) stiffLayout.findViewById(main.metrics.R.id.report_task_description);
		noteEditText = (EditText) stiffLayout.findViewById(main.metrics.R.id.report_task_notes); 
		reportTaskCompleteButton = (Button) stiffLayout.findViewById(main.metrics.R.id.report_task_complete); 
		gallery = new PhotoGallery(context, (Gallery) stiffLayout.findViewById(main.metrics.R.id.gallery));
		takePhotoButton = (Button) stiffLayout.findViewById(main.metrics.R.id.take_photo_button); 
		galleryPhotoButton = (Button) stiffLayout.findViewById(main.metrics.R.id.gallery_photo_button);
	}
	
	private void fillViewContent() {
		setHeaderText("<---"); 
		setDescriptionText(reportTask.getDescription()); 
		setNoteText(reportTask.getNote());
		setCompleteButton(reportTask.getCompletedTime()); 
		setGallery(reportTask.getPhotoNames()); 
	}
	
	private void setListeners() {
		header.setOnClickListener(headerClicked);
		noteEditText.setOnFocusChangeListener(noteEdited);
		
	}
	
	private void setHeaderText(final String text) {
		headerTextView.setText(text);
	}
	
	private void setDescriptionText(final String text) {
		descriptionTextView.setText(text);
	}
	
	private void setNoteText(final String text) {
		noteEditText.setText(text);
	}
	
	private void setCompleteButton(Date completed) {
		if (completed != null) {
			reportTaskCompleteButton.setBackgroundColor(Color.GREEN);
			reportTaskCompleteButton.setText("Completed at: " + completed.toLocaleString()); 
		} 
	}
	 
	private void setGallery(ArrayList<String> photoNames) {
		for (String photo : photoNames) {
			if (!gallery.containsPhoto(photo)) {
				gallery.add(photo); 
			}
		}
	}
	
	public ScrollView getScrollView() {
		return this.container; 
	}

}
