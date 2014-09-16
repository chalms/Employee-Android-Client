package views;

import main.metrics.MainActivity;
import main.metrics.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListItem extends ArrayAdapter<ListItemContent> {

	Context mContext;
	int layoutResourceId;
	ListItemContent data[] = null;
	private boolean[] failCheckBoxes = new boolean[100];
	private boolean[] passCheckBoxes = new boolean[100];
	private int[] checkedItems = new int[100];

	public ListItem(Context mContext, int layoutResourceId, ListItemContent[] data) {
		super(mContext, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	final Context context = parent.getContext();
    	final ListItemContent listItem = data[position];
    	
    	convertView = this.inflateView(convertView, parent, listItem);
    	
        TextView textViewItem;
        textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(listItem.getDisplay());
        textViewItem.setTag(listItem.getTag());
        

        if (listItem.getTag().equals("Leaf")) {
        	if (listItem.getDisplay().equals("Part"))
        	{
        		final CheckBox good_button = (CheckBox) convertView.findViewById(R.id.GoodCheckBox);
                final CheckBox poor_button = (CheckBox) convertView.findViewById(R.id.PoorCheckBox);
                final CheckBox NA_button = (CheckBox) convertView.findViewById(R.id.NACheckBox);
                
                textViewItem.setTag("Leaf");
                int checked = listItem.getChecked();
            	if ( checked != 0) {
            		if (checked == 4){
            			//Good has been selected
            			good_button.setChecked(true);
            			poor_button.setChecked(false);
            			NA_button.setChecked(false);
            		} else if (checked == 3){
            			good_button.setChecked(false);
            			poor_button.setChecked(true);
            			NA_button.setChecked(false);
            		} else if (checked == 2) {
            			good_button.setChecked(false);
            			poor_button.setChecked(false);
            			NA_button.setChecked(true);
            		}
            	}
            	
                good_button.setOnClickListener((new View.OnClickListener() {
    	 			@Override
					public void onClick(View v) {
    	 				good_button.setChecked(true);
    	 				poor_button.setChecked(false);
    	 				NA_button.setChecked(false);
    	 				listItem.setChecked(4);
    	 				((MainActivity) context).getNodeController().getReportTaskById(listItem.getID()).setTestResult("Good");
    	 				((MainActivity) context).getNodeController().addToDictionary(listItem.getID());
    	 			}
                }));
                
                poor_button.setOnClickListener((new View.OnClickListener() {
    	 			@Override
					public void onClick(View v) {
    	 				good_button.setChecked(false);
    	 				poor_button.setChecked(true);
    	 				NA_button.setChecked(false);
    	 				listItem.setChecked(3);
    	 				((MainActivity) context).getTasksController().showReportTasks(listItem.getID(), "Poor");
    				}
                }));
                
                NA_button.setOnClickListener((new View.OnClickListener() {
    	 			@Override
					public void onClick(View v) {
    	 				good_button.setChecked(false);
    	 				poor_button.setChecked(false);
    	 				NA_button.setChecked(true);
    	 				listItem.setChecked(2);
    	 				((MainActivity) context).getTasksController().showReportTasks(listItem.getID(), "N/A");
    	 			}
                }));
                
            } else { 
        	 	final CheckBox fail_button = (CheckBox) convertView.findViewById(R.id.FailCheckBox);
                final CheckBox pass_button = (CheckBox) convertView.findViewById(R.id.PassCheckBox);
                textViewItem.setTag("Leaf");
            	checkedItems[Integer.valueOf(position)] = listItem.getChecked();
            	int checked = checkedItems[Integer.valueOf(position)];
            	
            	if ( checked != 0) {
            		if (checked == 1){
            			failCheckBoxes[Integer.valueOf(position)] = false; 
            			passCheckBoxes[Integer.valueOf(position)] = true;
            		} else if (checked == -1){
            			failCheckBoxes[Integer.valueOf(position)] = true; 
            			passCheckBoxes[Integer.valueOf(position)] = false;
            		}
            		
            	} else {
        			failCheckBoxes[Integer.valueOf(position)] = false; 
        			passCheckBoxes[Integer.valueOf(position)] = false;
        		}
            	fail_button.setChecked(failCheckBoxes[Integer.valueOf(position)]);
            	pass_button.setChecked(passCheckBoxes[Integer.valueOf(position)]);
            			      
                fail_button.setOnClickListener((new View.OnClickListener() {
    	 			@Override
					public void onClick(View v) {
    	 				System.out.println("fail");
    	 				v.setSelected(true); 
    	 				pass_button.setChecked(false);
    	 				fail_button.setChecked(true);
    	 				checkedItems[Integer.valueOf(position)] = -1;
    	 				failCheckBoxes[Integer.valueOf(position)] = true; 
            			passCheckBoxes[Integer.valueOf(position)] = false;
    	 				((MainActivity) context).getTasksController().showReportTasks(listItem.getID());
    	 			}
    			}));
       
                pass_button.setOnClickListener((new View.OnClickListener() {
    	 			@Override
					public void onClick(View v) {
    	 				v.setSelected(true);
    	 				pass_button.setChecked(true);
    	 				fail_button.setChecked(false);
    	 				checkedItems[Integer.valueOf(position)] = 1;
    	 				failCheckBoxes[Integer.valueOf(position)] = false; 
            			passCheckBoxes[Integer.valueOf(position)] = true;
            			System.out.println("Item just clicked, list item ID -> " + listItem.getID().toString());
    	 				((MainActivity) context).getNodeController().getReportTaskById(listItem.getID()).setTestResult("Pass");
    	 				((MainActivity) context).getNodeController().addToDictionary(listItem.getID());
    	 			}
                }));
        	}
        } else {
        	if (listItem.getCompleted(true)){
        		textViewItem.setText(listItem.getDisplay() + "    √");
        	} else {
        		textViewItem.setText(listItem.getDisplay());
        	}
            textViewItem.setTag(listItem.getTag());
        }
        convertView.invalidate();
        convertView.setTag(convertView.getTag());
        return convertView;
    }

	private View inflateView(View convertView, ViewGroup parent, ListItemContent listItem) {

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
