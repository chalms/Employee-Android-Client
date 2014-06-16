package views;

import android.view.View;
import android.view.View.OnClickListener;

public class OnItemClickListener implements OnClickListener{       
    private int mPosition;
    public OnItemClickListener(){
     
    }
    
	@Override
	public void onClick(View v) {
	//	System.out.println(arg0.getTag());
		System.out.println("");
	}

	public int getmPosition() {
		return mPosition;
	}

	public void setmPosition(int mPosition) {
		this.mPosition = mPosition;
	}       
}

