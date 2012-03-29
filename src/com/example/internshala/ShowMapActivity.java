package com.example.internshala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class ShowMapActivity extends MapActivity{
    /** Called when the activity is first created. */
    
    public static Location currentLocation, location;
    double currentLatitude;
    double currentLongitude;
    public static String citySelected = null;
    public static int distSelected= -1;
    public static int cashSelected= -1;
    public static String manufSelected = null;
    public static ArrayList<Garage> gMainList;
    public static ArrayList<Garage> gCurrentList;
    MapView mapView;
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	@Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
       
        LocationManager mlocManager=null;
        LocationListener mlocListener;
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1, 1000, mlocListener);
        
        
        location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      
        Bundle extras = getIntent().getExtras(); 
        boolean ifFromSettings = extras.getBoolean("isFromSettings", false);
        
        if(!ifFromSettings)
        gMainList = DatabaseOp.readAll(1, null);
        displayGarages(gMainList);
        
   }// end of onCreate
	/*
	@Override
	public void onResume()
	{
		displayGarages(gMainList);
	}
	*/
	
	public void displayGarages(ArrayList<Garage> gl)
	{
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        MyItemOverlay itemizedoverlay = new MyItemOverlay(drawable,this);
        
        Drawable icon = this.getResources().getDrawable(R.drawable.mapmarker);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        
        String gName, gStreet, gCity, gCash, gManuf;
        double gDist;
        ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
        ArrayList<OverlayItem> ovi = new ArrayList<OverlayItem>();
        GeoPoint geoTemp;
        OverlayItem oviTemp;
        
        gCurrentList = new ArrayList<Garage>();
        
        for(int i=0; i<gl.size(); i++)
        {
        	gCash = gl.get(i).getCashless();
        	gCity = gl.get(i).getAddr().getCity();
        	gManuf = gl.get(i).getManufacturer(); 
        	
        	if(cashSelected==0&&gCash.equals("No")) continue;
        	if((citySelected!=null)&&(!gCity.equals(citySelected))) continue;
        	if((manufSelected!=null)&&(!gManuf.equals(manufSelected))) continue;
        	
        	gDist = distFrom(gl.get(i).getLat(), gl.get(i).getLng(), location.getLatitude(), location.getLongitude());
        	
        	
        	if((distSelected!=-1)&&(distSelected<gDist)) continue;
        	
        	gl.get(i).setDistance(gDist);
        	
        	gName = gl.get(i).getName();
        	gStreet = gl.get(i).getAddr().getStreet();
        	gCity = gl.get(i).getAddr().getCity();
        	geoTemp = new GeoPoint((int)(gl.get(i).getLat()*1E6),(int)(gl.get(i).getLng()*1E6));
        	points.add(geoTemp);
        	oviTemp = new OverlayItem(geoTemp, gName, gStreet+ "," + gCity );
        	oviTemp.setMarker(icon);
         	itemizedoverlay.addOverlay(oviTemp);
         	gCurrentList.add(gl.get(i));
        }
       
        int test = gCurrentList.size();
        test++;

        //GeoPoint point = new GeoPoint(19173972,72958136);
        GeoPoint point = new GeoPoint( (int) (location.getLatitude() * 1E6),(int) (location.getLongitude() * 1E6));
        OverlayItem overlayitem = new OverlayItem(point, "Your Location" , getAddress());
        //OverlayItem overlayitem = new OverlayItem(point, "Your Location" , (int) (location.getLatitude() * 1E6)+ "\n" +(int) (location.getLongitude() * 1E6));
        
        itemizedoverlay.addOverlay(overlayitem);
        //itemizedoverlay.addOverlay(overlayitem1);
        //itemizedoverlay.addOverlay(overlayitem2);
        
        Drawable myloc = this.getResources().getDrawable(R.drawable.myloc3);
        myloc.setBounds(0, 0, myloc.getIntrinsicWidth(), myloc.getIntrinsicHeight());
        overlayitem.setMarker(myloc);
       
        mapOverlays.add(itemizedoverlay);
	}
	
	
	 	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		    //double earthRadius = 3958.75; 			in miles
	 		double earthRadius = 6731;					// in km
	 		double dLat = Math.toRadians(lat2-lat1);
		    double dLng = Math.toRadians(lng2-lng1);
		    double x = Math.sin(dLat/2);
		    double y = Math.sin(dLng/2);
		    double a = x * x +
		               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * y * y;
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = earthRadius * c;

		    return dist;
		    }
	
	 	String getAddress()
	 	{
		 	String res = null;
	        try{
	            Geocoder gcd = new Geocoder(this, Locale.getDefault());
	            List<Address> addresses = gcd.getFromLocation(location.getLatitude(),location.getLongitude(),100);
	            if (addresses.size() > 0) {
	                StringBuilder result = new StringBuilder();
	                for(int i = 0; i < addresses.size(); i++){
	                    Address address =  addresses.get(i);
	                    int maxIndex = address.getMaxAddressLineIndex();
	                    for (int x = 0; x <= maxIndex; x++ ){
	                        result.append(address.getAddressLine(x));
	                        result.append(",");
	                    }               
	                    result.append(address.getLocality());
	                    result.append(",");
	                    result.append(address.getPostalCode());
	                    result.append("\n");
	                }
	                res = result.toString();
	            }
	        }
	        catch(IOException ex){
	            //addressText.setText(ex.getMessage().toString());
	        }
	        return res;
	    }
	    
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting: Intent newIntent = new Intent(this, SearchSettingsActivity.class);
         		            startActivity(newIntent);
         		            break;
            case R.id.viewaslist: Intent newIntent2 = new Intent(this, GarageListActivity.class);
	            			startActivity(newIntent2);
	            			break;   
            case R.id.gps : startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
            					break;
            
        }
        return true;
    }
}


class MyLocationListener implements LocationListener {

    public static double latitude;
    public static double longitude;

	public void onLocationChanged(Location loc)
	{
		loc.getLatitude();
		loc.getLongitude();
		latitude=loc.getLatitude();
		longitude=loc.getLongitude();
	}


	public void onProviderDisabled(String provider)
	{
		//print "Currently GPS is Disabled";
	}

	public void onProviderEnabled(String provider)
	{
		//print "GPS got Enabled";
	}

	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}
}
