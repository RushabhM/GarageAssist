package com.example.internshala;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UsecasesActivity extends Activity {//implements Runnable{
    
	public ProgressDialog progDialog;	
	public static boolean isService = false;
	protected Update upd;
	
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usecases);
        
        DatabaseOp.initialize(this);
        
        Button search_button=(Button)findViewById(R.id.btn_1);
        
        search_button.setOnClickListener(new Button.OnClickListener()
          {
              public void onClick(View v)
               {
            	 //Intent newIntent = new Intent(UsecasesActivity.this, SearchSettingsActivity.class);
            	  Intent newIntent = new Intent(UsecasesActivity.this, ShowMapActivity.class);
            	  newIntent.putExtra("isFromSettings", false);
             	 startActivity(newIntent);            	 
             	
               }
          }
         );
        
        Button register_button=(Button)findViewById(R.id.btn_2);
        
        register_button.setOnClickListener(new Button.OnClickListener()
          {
              public void onClick(View v)
               {
            	 //Intent newIntent = new Intent(UsecasesActivity.this, SearchSettingsActivity.class);
            	  Intent newIntent = new Intent(UsecasesActivity.this, RegisterClaimActivity.class);
            	  startActivity(newIntent);            	 
             	
               }
          }
         );

        Button update_button=(Button)findViewById(R.id.btn_3);
                
        update_button.setOnClickListener(new Button.OnClickListener()
          {
              public void onClick(View v)
               {
            	  isService = true;
            	  //showDialog(1);
            	  upd = new Update();
            	  upd.execute(UsecasesActivity.this);
            	  
               }
          }
         );
        
   }// end of onCreate
    
	protected class Update extends AsyncTask<Context, Void, String> {
		
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
            dialog = ProgressDialog.show(UsecasesActivity.this,"Database Updation","Please wait..",true);
            dialog.show();
        }
		
		@Override
		protected String doInBackground(Context... params) {
			startService(new Intent(UsecasesActivity.this, UpdateListService.class));
			return "Complete!";
			
		}

		@Override
		protected void onPostExecute(String str) {
			Toast.makeText(UsecasesActivity.this, str, Toast.LENGTH_SHORT).show();
			dialog.dismiss();
		}
	}
}

