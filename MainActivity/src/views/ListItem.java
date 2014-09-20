package views;

import main.metrics.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListItem extends ArrayAdapter<ReportTaskLineItem> {

	Context mContext;
	int layoutResourceId;
	ReportTaskLineItem data[] = null;
    OnLongClickListener textListener = new OnLongClickListener (){
		@Override
		public boolean onLongClick(View arg0) { return false;}
    }; 

	public ListItem(Context mContext, int layoutResourceId, ReportTaskLineItem[] data) {
		super(mContext, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	final ReportTaskLineItem reportTaskLineItem = data[position];
    	convertView = this.inflateView(convertView, parent, reportTaskLineItem);
        TextView textViewItem;
        textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(reportTaskLineItem.getDisplay());
        textViewItem.setOnLongClickListener(textListener);
        convertView.invalidate();
        convertView.setTag(convertView.getTag());
        return convertView;
    }

	private View inflateView(View convertView, ViewGroup parent, ReportTaskLineItem listItem) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		return convertView;
	}

}
