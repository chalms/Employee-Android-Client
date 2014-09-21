package views;

import java.util.ArrayList;
import java.util.HashMap;

import main.metrics.MainActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PhotoGalleryAdapter extends BaseAdapter {
	HashMap <String, Integer> references; 
	 ArrayList<String> GalleryFileList;
	 MainActivity context;
	 
 
	 PhotoGalleryAdapter(MainActivity cont){
	  context = cont;
	  GalleryFileList = new ArrayList<String>();    
	  references = new HashMap<String, Integer>(); 
	}
	 
	@Override
	public int getCount() {
		return GalleryFileList.size();
	}
	
	@Override
	public Object getItem(int position) {
		return GalleryFileList.get(position);
	}
	
	public boolean contains(String item) {
		return references.containsKey(item);
	}
	
	@Override
	public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Bitmap bm = BitmapFactory.decodeFile(GalleryFileList.get(position));
		
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new Gallery.LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);
		
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);
		
		layout.addView(imageView);
		return layout;
	
	}
	
	public void add(String newitem){
		GalleryFileList.add(newitem);
		references.put(newitem, GalleryFileList.size()); 
	}

}
