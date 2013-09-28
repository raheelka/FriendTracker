package edu.ncsu.soc.project1;

import java.util.ArrayList;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class FriendViewAdaptor extends BaseAdapter {

	private FriendMapper friendMapper;

	private ArrayList<String> names = new ArrayList<String>();

	public FriendViewAdaptor(FriendMapper friendMapper) {

		this.friendMapper = friendMapper;

		// TODO put code here for getting contact's names from the
		// contentProvider and storing them in the list
		String [] mProjection = new String[]
			    {
			        
				ContactsContract.Contacts.DISPLAY_NAME,
			       
			    };
		Cursor cur =
		        friendMapper.getContentResolver().query(
		        		ContactsContract.Contacts.CONTENT_URI,
		                mProjection ,
		                null,
		                null,
		                null);
	
		if (cur.getCount() > 0) {
		    while (cur.moveToNext()){
		  
		    	String name = cur.getString
		    			(cur.getColumnIndex
		    					(ContactsContract.Contacts.DISPLAY_NAME));
		    	names.add(name);
		    }
		    }
		
	

	}

	public int getCount() {
		return names.size();
	}

	public Object getItem(int position) {
		return names.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(friendMapper);
		textView.setText(names.get(position));
		textView.setClickable(true);
		textView.setOnClickListener(new FriendListener());
		return textView;
	}

	public class FriendListener implements OnClickListener {

		public void onClick(View view) {
			TextView textView = (TextView) view;
			friendMapper.addMarkerAtCurrentLocation(textView.getText()
					.toString());
		}
	}
}