package com.example.internshala;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GarageDetailsActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	String lat,lng,landline,mobile;
	
	@Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String temp;
        Bundle bundle = getIntent().getExtras(); 
        //temp = bundle.getString("myString");
        
    	String[] dataType = {"Name","Address","Cashless","Manufacturer","Contact Person","Landline","Mobile","Email"};
    	String[] dataValue = new String[8];
    	
    	dataValue[0] = bundle.getString("Name");
    	dataValue[1] = bundle.getString("Street")+", "+bundle.getString("City")+" - "+bundle.getString("Pincode")+", "+bundle.getString("State");
    	dataValue[2] = bundle.getString("Cashless");
    	dataValue[3] = bundle.getString("Manuf");
    	dataValue[4] = bundle.getString("Person");
    	
    	dataValue[5] = bundle.getString("Landline");
    	landline = dataValue[5];
    	
    	dataValue[6] = bundle.getString("Mobile");
    	mobile = dataValue[6];
    	
    	dataValue[7] = bundle.getString("Email");
    	lat = new Double(bundle.getDouble("Lat")).toString();
    	lng = new Double(bundle.getDouble("Lng")).toString();

    	setListAdapter(new IconicAdapter(dataType, dataValue));
	
    	ListView lv = getListView();
    	//ListView lv = (ListView)findViewById(android.R.id.list);
    	lv.setTextFilterEnabled(true);
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    		
		    	
		    }
		  });
   
	}// end of onCreate
	
	
	
	class IconicAdapter extends ArrayAdapter<String> 
	{
	 private String[] dataType;
	 private String[] dataValue;

	 	IconicAdapter(String[] types, String[] val) 
	 	{
	 		super(GarageDetailsActivity.this, R.layout.garage_details, R.id.garageDetailDatatype, types);
	 		dataType = types;
	 		dataValue = val;
	    }
	 
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View row=super.getView(position, convertView, parent);
			
			TextView t1=(TextView)row.findViewById(R.id.garageDetailDatatype);
			t1.setText(dataType[position]);
			
			TextView t2=(TextView)row.findViewById(R.id.garageDetailValue);
			t2.setText(dataValue[position]);
			
			return(row);
		}
	}  
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.garagedetailmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.direction: Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW, 
      			  					Uri.parse("http://maps.google.com/maps?saddr=19.173972,72.958136&daddr="+lat+","+lng));
			  						startActivity(intent1);
			  						break;
            case R.id.calllandline: Intent intent2 = new Intent(Intent.ACTION_CALL);
            						intent2.setData(Uri.parse("tel:" + landline));
            						startActivity(intent2);
            						break;
            case R.id.callmobile : 	Intent intent3 = new Intent(Intent.ACTION_CALL);
									intent3.setData(Uri.parse("tel:" + mobile));
									startActivity(intent3); 
									break;
        }
        return true;
    }
}