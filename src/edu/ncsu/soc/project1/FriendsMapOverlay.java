package edu.ncsu.soc.project1;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class FriendsMapOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
	private ArrayList<String> checkname=new ArrayList<String>();
	private ArrayList<String> disp=new ArrayList<String>();

	private Context context;

	public FriendsMapOverlay(Context context, Drawable marker) {
		super(boundCenterBottom(marker));
		this.context = context;
	}

	public void addMarker(String markerName, GeoPoint geoPoint) {
		items.add(new OverlayItem(geoPoint, markerName, markerName));
		checkname.add(markerName);
		disp.add(""+geoPoint.getLatitudeE6()+","+geoPoint.getLongitudeE6());
		super.populate();
	}
	
	public void removeMarker(String markerName, GeoPoint geoPoint)
	{
		
		int index=checkname.indexOf(markerName);
		items.remove(index);
		checkname.remove(index);
		disp.remove(index);
		super.populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return (items.get(i));
	}

	@Override
	protected boolean onTap(int i) {
		
		String st=""+items.get(i).getPoint().getLatitudeE6()/1E6+","
		+items.get(i).getPoint().getLongitudeE6()/1E6;
		//String todisplay=items.get(i).getSnippet()+"\n"+disp.get(i);
		String todisplay=items.get(i).getSnippet()+"\n"+st;
		Toast.makeText(context, todisplay, Toast.LENGTH_SHORT)
				.show();
		return true;
	}

	@Override
	public int size() {
		return items.size();
	}
}