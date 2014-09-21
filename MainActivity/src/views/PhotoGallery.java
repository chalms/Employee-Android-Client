package views;

import java.io.File;

import main.metrics.MainActivity;
import android.os.Environment;
import android.widget.Gallery;
import android.widget.Toast;

public class PhotoGallery {

    PhotoGalleryAdapter myGalleryBaseAdapter;
    public Gallery myPhotoGallery;
    
	public PhotoGallery(MainActivity context,Gallery gallery) {
		// TODO Auto-generated constructor stub
		  myPhotoGallery = gallery; 
	        
	      myGalleryBaseAdapter = new PhotoGalleryAdapter(context);
	        
	     String ExternalStorageDirectoryPath = Environment
	    		 .getExternalStorageDirectory()
	    		 .getAbsolutePath();
	        
	     String targetPath = ExternalStorageDirectoryPath + "/test/";
	        
	     Toast.makeText(context.getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
	        
	     File targetDirector = new File(targetPath);
	        
	     File[] files = targetDirector.listFiles();
	        for (File file : files){
	         myGalleryBaseAdapter.add(file.getPath());
	     }
	}
	
	public boolean containsPhoto(String path) {
		return myGalleryBaseAdapter.contains(path); 
	}
	
	public void add(String path) {
		myGalleryBaseAdapter.add(path);
	}

}
