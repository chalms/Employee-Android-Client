package views;

import main.metrics.MainActivity;
import main.metrics.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListItem extends ArrayAdapter<ReportTaskLineItem> {

	Context mContext;
	int layoutResourceId;
	ReportTaskLineItem data[] = null;
	private boolean[] failCheckBoxes = new boolean[100];
	private boolean[] passCheckBoxes = new boolean[100];
	private int[] checkedItems = new int[100];

	public ListItem(Context mContext, int layoutResourceId, ReportTaskLineItem[] data) {
		super(mContext, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	final Context context = parent.getContext();
    	final ReportTaskLineItem listItem = data[position];
    	
    	convertView = this.inflateView(convertView, parent, listItem);
    	
        TextView textViewItem;
        textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(listItem.getDisplay());
        OnLongClickListener chalmee = new OnLongClickListener {

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				return false;
			}
        	
        }; 
        textViewItem.setOnLongClickListener( );
        convertView.invalidate();
        convertView.setTag(convertView.getTag());
        return convertView;
    }

	private View inflateView(View convertView, ViewGroup parent, ReportTaskLineItem listItem) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		if (listItem.getDisplay().equals("Leaf")) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(
					main.metrics.R.layout.list_view_row_triplebuttons, parent, false);
			System.out.println("Changed view");
		}
		return convertView;
	}

}
