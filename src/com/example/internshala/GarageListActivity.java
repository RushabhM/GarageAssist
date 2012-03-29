package com.example.internshala;

//key- 0fMgJJ83mbU_9z2CZO46wsxSAeodpoG6q3cw84w
//certi - AB:7E:E2:55:B3:91:88:5D:99:37:FF:1D:C5:27:20:35

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GarageListActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	int radioTimeSelected;
	int radioTopSelected;
	ArrayList<Garage> garages;
	ArrayList<Garage> ordered;
	DecimalFormat df;
	static int itemSelected;
	
	@Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        garages = ShowMapActivity.gCurrentList;
        ordered = new ArrayList<Garage>();
        int index = -1, x, size = garages.size();
        double d, temp;
        df = new DecimalFormat();
        
        /*
        while(!garages.isEmpty())
        {
        	temp = Double.MAX_VALUE;
        	for(x=0; x< garages.size(); x++)
        	{
        		d = garages.get(x).getDistance();
        		if(d < temp)
        		{
        			temp = d;
        			index = x;
        		}
        	}
        	ordered.add(garages.remove(index));
        }
        */
        
        Garage[] g = new Garage[size];
        Garage t;
        
        for(int i=0; !garages.isEmpty(); i++)
        {
        	g[i] = garages.remove(0);
        }
        
        for(int j=0; j<size; j++)
        {
        	for(int k=1; k<(size-j); k++)
        	{
        		if(g[k-1].getDistance()>g[k].getDistance())
        		{
        			t = g[k-1];
        			g[k-1] = g[k];
        			g[k] = t;
        		}
        	}
        }
        
        for(int j=0; j<size; j++)
        {
        	ordered.add(g[j]);
        }
        
    	String[] garagenames = new String[size];
    	double[] distances = new double[size];

    	for(int y=0; y<size; y++)
    	{
    		garagenames[y] = ordered.get(y).getName();
    		distances[y] = ordered.get(y).getDistance();
    	}
    	
    	setListAdapter(new IconicAdapter(garagenames, distances));
	
    	ListView lv = getListView();
    	//ListView lv = (ListView)findViewById(android.R.id.list);
    	lv.setTextFilterEnabled(true);
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    		
		    	showDetailedInfo(position);
		    }
		  });
   
	}// end of onCreate
	
	public void showDetailedInfo(int pos)
	{
		itemSelected = pos;
		
		Intent newIntent = new Intent(GarageListActivity.this, GarageDetailsActivity.class);
		newIntent.putExtra("Name",ordered.get(pos).getName());
		newIntent.putExtra("Street", ordered.get(pos).getAddr().getStreet());
		newIntent.putExtra("City",ordered.get(pos).getAddr().getCity());
		newIntent.putExtra("Pincode",ordered.get(pos).getAddr().getPincode());
		newIntent.putExtra("State",ordered.get(pos).getAddr().getState());
		newIntent.putExtra("Cashless", ordered.get(pos).getCashless());
		newIntent.putExtra("Manuf",ordered.get(pos).getManufacturer());
		newIntent.putExtra("Person", ordered.get(pos).getConDetails().getPerson());
		newIntent.putExtra("Landline", ordered.get(pos).getConDetails().getLandline());
		newIntent.putExtra("Mobile",ordered.get(pos).getConDetails().getMobile());
		newIntent.putExtra("Email", ordered.get(pos).getConDetails().getEmail());
		newIntent.putExtra("Lat",ordered.get(pos).getLat());
		newIntent.putExtra("Lng",ordered.get(pos).getLng());
		startActivity(newIntent);
		
	}
	
	
	class IconicAdapter extends ArrayAdapter<String> 
	{
	 private String[] names;
	 private double[] distances;

	 	IconicAdapter(String[] garagenames, double[] dis) 
	 	{
	 		super(GarageListActivity.this, R.layout.garage_item, R.id.garagename, garagenames);
	 		names = garagenames;
	 		distances = dis;
	    }
	 
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View row=super.getView(position, convertView, parent);
			
			TextView t1=(TextView)row.findViewById(R.id.garagename);
			t1.setText(names[position]);
			
			TextView t2=(TextView)row.findViewById(R.id.garagedistance);
			
			df.setMinimumFractionDigits(2);
			df.setMaximumFractionDigits(2);
			
			t2.setText( df.format(distances[position]) + " kms");
			
			return(row);
		}
	}  
	
	/*
	class GarageComparator implements Comparator<Garage>
	{
		public int compare(Garage g1, Garage g2)
		{
			return (int)(g1.getDistance() - g2.getDistance());
		}
	}
	*/
}