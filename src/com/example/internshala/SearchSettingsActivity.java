package com.example.internshala;

//key- 0fMgJJ83mbU_9z2CZO46wsxSAeodpoG6q3cw84w
//certi - AB:7E:E2:55:B3:91:88:5D:99:37:FF:1D:C5:27:20:35

import java.util.ArrayList;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchSettingsActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	int radioTimeSelected;
	int radioTopSelected;
	
	
	@Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.setting_main);
        
    	Context ctx = getApplicationContext();
    	Resources res = ctx.getResources();

    	String[] options = res.getStringArray(R.array.setting_names);
    
    	setListAdapter(new ArrayAdapter<String>(this, R.layout.setting_list_item, options));
	
    	ArrayList<String> manuf = DatabaseOp.getAllManuf();
      	
      	final CharSequence[] dataManuf = new CharSequence[manuf.size()];
      	
      	for(int i=0; !manuf.isEmpty();i++)
      	{
      		dataManuf[i] = manuf.remove(0);
      		Log.e("Manuf_Add", dataManuf[i].toString());
      	}
    	
      	ArrayList<String> cities = DatabaseOp.getAllCities();
      	
      	final CharSequence[] dataCity = new CharSequence[cities.size()+1];
      	
      	dataCity[0] = "All Cities";
      	
      	for(int i=1; !cities.isEmpty(); i++)
      	{
      		dataCity[i] = cities.remove(0);
      		Log.e("Citi_Add", dataCity[i].toString());
      	}
    	
    	ListView lv = getListView();
    	
    	lv.setTextFilterEnabled(true);
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		     switch (position)
		     { 
		       case 0:showPopUpDistance();
		              break;
		       case 1:showPopUpCity(dataCity);
		       		  break;
		       case 2:showPopUpCashless();
		    	      break;
		       case 3:showPopUpManuf(dataManuf);
   		              break;
		     }
		    }
		  });
   
	}// end of onCreate
	
	
    	private void showPopUpDistance()
    	{
    		final CharSequence[] items = {"5 km","10 km","25 km","50 km","No limit"};
    		final int[] arrDist = {5,10,25,50};
   		 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
   		 helpBuilder.setTitle("Make Selection");
   		 
   		 helpBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
   			    public void onClick(DialogInterface dialog, int item) {
   			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
   			        radioTimeSelected = item;
   			        if(item==4)
   			        	ShowMapActivity.distSelected = -1;
   			        else
   			        	ShowMapActivity.distSelected = arrDist[item];
   			    }
   			});
   		 
   		 helpBuilder.setPositiveButton("Confirm",
   					new DialogInterface.OnClickListener() {
   						public void onClick(DialogInterface dialog, int id) {
   							Toast.makeText(SearchSettingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
   						}
   					});	
   		 
   		 

   		 // Remember, create doesn't show the dialog
   		 AlertDialog helpDialog = helpBuilder.create();
   		 helpDialog.show();

    	}
    	
    	private void showPopUpCity(CharSequence[] data)
    	{
    		final CharSequence[] items = data;
      	 
   		 	AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
   		 	helpBuilder.setTitle("Make Selection");
   		 
   		 	helpBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
   			    public void onClick(DialogInterface dialog, int item) {
   			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
   			        radioTimeSelected = item;
   			        String slc = (String) items[item];
   			        if(item==0)
   			        	ShowMapActivity.citySelected = null;
   			        else
   			        	ShowMapActivity.citySelected = slc;
   			    }
   			});
   		 
   		 helpBuilder.setPositiveButton("Confirm",
   					new DialogInterface.OnClickListener() {
   						public void onClick(DialogInterface dialog, int id) {
   							Toast.makeText(SearchSettingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
   						}
   					});	

   		 // Remember, create doesn't show the dialog
   		 AlertDialog helpDialog = helpBuilder.create();
   		 helpDialog.show();
       	}
    	
    	
    	private void showPopUpCashless()
    	{
   		 final CharSequence[] items = {"Only Cashless","All garages"};
		 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		 helpBuilder.setTitle("Make Selection");
		 
		 helpBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			        radioTimeSelected = item;
			        ShowMapActivity.cashSelected = item;
			    }
			});
		 
		 helpBuilder.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Toast.makeText(SearchSettingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
						}
					});	

		 // Remember, create doesn't show the dialog
		 AlertDialog helpDialog = helpBuilder.create();
		 helpDialog.show();
    	}
    	
    	private void showPopUpManuf(CharSequence[] data)
    	{
      	  //CharSequence[] items = {"All models","Audi","Ford","Hyundai","Mahindra & Mahindra","Maruti","Tata"};
    		final CharSequence[] items = data;
      	 
   		 	AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
   		 	helpBuilder.setTitle("Make Selection");
   		 
   		 	helpBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
   			    public void onClick(DialogInterface dialog, int item) {
   			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
   			        radioTimeSelected = item;
   			        
   			        String slc = (String) items[item];
   			        if(item==0)
   			        	ShowMapActivity.manufSelected = null;
   			        else 
   			        	ShowMapActivity.manufSelected = slc;
   			    }
   			});
   		 
   		 helpBuilder.setPositiveButton("Confirm",
   					new DialogInterface.OnClickListener() {
   						public void onClick(DialogInterface dialog, int id) {
   							Toast.makeText(SearchSettingsActivity.this, "Success", Toast.LENGTH_SHORT).show();
   						}
   					});	

   		 // Remember, create doesn't show the dialog
   		 AlertDialog helpDialog = helpBuilder.create();
   		 helpDialog.show();
       	}
   
    	
    	public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.settings_done, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settingsDone: Intent newIntent = new Intent(this, ShowMapActivity.class);
             		            newIntent.putExtra("isFromSettings", true);
                				startActivity(newIntent);
             		            break;                
            }
            return true;
        }
}