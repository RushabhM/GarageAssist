package com.example.internshala;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
//import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;

public class UpdateListActivity extends Activity {

	ArrayList<Garage> garages;
	Garage temp;
	String geo_street, geo_city, geo_state;
	ArrayList<Float> latlng;
	String geo_str;
	//DatabaseOp dbop;
	List<Address> adr;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
        
        DatabaseOp.drop(this);
        DatabaseOp.initialize(this);
    }
	 
    public void parse_insert()
    {
    	GrgXMLParser grgParse = new GrgXMLParser();
		garages = grgParse.runExample("http://www.internfair.internshala.com/internFiles/AppDesign/GarageList.xml");
		
		//Geocoder coder = new Geocoder(this);
		
		//GeoXMLParser geoParse = new GeoXMLParser();
		
		for(int i=0; i<garages.size(); i++)
		{
			temp = garages.get(i);
			geo_street = temp.getAddr().getStreet();//.replace(" ", "+");
			geo_city = temp.getAddr().getCity();//.replace(" ", "+");
			geo_state = temp.getAddr().getState();//.replace(" ", "+");
			
		/*	
			mTimer.schedule(new TimerTask()
		    {
		        @Override
		        public void run()
		        {
		        	geo_url = "http://maps.google.com/maps/api/geocode/xml?address=" + geo_street + ",+" + geo_city + ",+" + geo_state + ",+India&sensor=true";
					latlng = geoParse.runExample(geo_url);
		        }
		    }, 1000);
			
		*/	
			
		//	geo_url = "http://maps.google.com/maps/api/geocode/xml?address=" + geo_street + ",+" + geo_city + ",+" + geo_state + ",+India&sensor=true";
		//	latlng = geoParse.runExample(geo_url);
			
			geo_str = geo_street + ", " + geo_city + ", " + geo_state;
			
			JSONObject jsonLocObj = getLocationInfo(geo_str);
			GeoPoint parsedGeo = getLatLong(jsonLocObj);
		/*	
			try {
				adr = coder.getFromLocationName(geo_str,1);
				while(adr.size()==0)
				adr = coder.getFromLocationName(geo_str,1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (adr == null) {
		        
		    	temp.setLat(0);
				temp.setLng(0);
				Log.e("latlng", "locha...");
		    }
		    else
		    {
		    Address location = adr.get(0);
		    temp.setLat(location.getLatitude());
			temp.setLng(location.getLongitude());
			Log.e("lat", location.getLatitude()+"");
			Log.e("lng",location.getLongitude()+"");
		    }
			
		*/	
			temp.setLat(parsedGeo.getLatitudeE6());
			temp.setLng(parsedGeo.getLongitudeE6());
			Log.e("lat", (double)(parsedGeo.getLatitudeE6())/(1E6)+"");
			Log.e("lng",(double)(parsedGeo.getLongitudeE6())/(1E6)+"");
			
			DatabaseOp.insert(temp);
			/*
			Thread t = new Thread();
			try 
			{
				t.wait(50);	
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
        
    }
    
    
    public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

        address = address.replaceAll(" ","%20");    

        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("json Locha",e.getStackTrace().toString());
        }

        return jsonObject;
    }
    
    public static GeoPoint getLatLong(JSONObject jsonObject) {

    	Double lat = new Double(0);
        Double lon = new Double(0);

        try {

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");

            lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        } catch (Exception e) {
            e.printStackTrace();

        }

        return new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
    }
}