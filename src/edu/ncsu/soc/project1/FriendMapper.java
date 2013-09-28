package edu.ncsu.soc.project1;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;


public class FriendMapper extends MapActivity {

	private FriendsMapOverlay mapOverlay;
	private ArrayList<String> markedNames = new ArrayList<String>();
	private ArrayList<GeoPoint> gp=new ArrayList<GeoPoint>();
	
	private static double a;
	private static double b;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_map);

		// create the map overlay
		Drawable marker = getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		mapOverlay = new FriendsMapOverlay(this, marker);

		// configure the friends list
		ListView list = (ListView) findViewById(R.id.FriendList);
		list.setAdapter(new FriendViewAdaptor(this));

		// configure the map
		MapView mapView = (MapView) findViewById(R.id.MapView);
		mapView.getController().setCenter(new GeoPoint(35772052, -78673718));
		mapView.getController().setZoom(15);
		mapView.getOverlays().add(mapOverlay);
		
		LocationManager locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Criteria hdCrit = new Criteria();
		hdCrit.setAccuracy(Criteria.ACCURACY_FINE);
		final LocationManager locManagerFinal=locManager;
		LocationListener locationListener = new LocationListener() {
			 public void onLocationChanged(Location location) {
				 
				Location locFinal = locManagerFinal.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				a=locFinal.getLatitude();
				b=locFinal.getLongitude();
				
				//System.out.println("new values"+locFinal.getLatitude());
				 
			 }
			 public void onStatusChanged(String provider, int status, Bundle extras) {}

			    public void onProviderEnabled(String provider) {}

			    public void onProviderDisabled(String provider) {}
			 
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);	 
		
		

	}

	
	
	public void addMarkerAtCurrentLocation(String markerName) {

		// TODO get current GPS coordinates and plot them on the map
		// NOTE: there should only be one marker on the map per person
		// mapOverlay.addMarker(...);

				MapView mapView = (MapView) findViewById(R.id.MapView);
				mapView.invalidate();
				double lat=a;
				double lon=b;
				GeoPoint g=new GeoPoint((int)(lat*1e6),(int)(lon*1e6));
				if(!markedNames.contains(markerName))
				{
					//System.out.println("Name not found : "+markerName+"lat lon"+lat+" "+lon);
					markedNames.add(markerName);
					markedNames.add((int)(lat*1e6)+"");
					markedNames.add((int)(lon*1e6)+"");
					gp.add(g);
					mapView.getController().setCenter(g);
					mapOverlay.addMarker(markerName,g);
					//mapView.getOverlays().add(mapOverlay);
				}
				else
				{
					int index=markedNames.indexOf(markerName);					
					int oldLat=Integer.parseInt(markedNames.get(index+1));
					int oldLon=Integer.parseInt(markedNames.get(index+2));
					GeoPoint gtodelete=gp.get(index/3);
					//System.out.println("old lat "+oldLat+" and lon "+oldLon+ "for name "+markerName);
					
					//if((int)(lat*1e6)!=oldLat || (int)(lon*1e6)!=oldLon)
					//{
						//System.out.println("came inside");
						//System.out.println("entered secnd if");
						mapOverlay.removeMarker(markerName,gtodelete);
						mapView.getController().setCenter(g);
				    	mapOverlay.addMarker(markerName,g);
						//mapView.getOverlays().add(mapOverlay);
					//}
				}
				
				

	}
	
	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}