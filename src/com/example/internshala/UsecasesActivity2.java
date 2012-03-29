package com.example.internshala;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UsecasesActivity2 extends Activity {//implements Runnable{
    
	public ProgressDialog progDialog;	
	ProgressThread progThread;
	public static boolean isService = false;
	
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
            	  Intent newIntent = new Intent(UsecasesActivity2.this, ShowMapActivity.class);
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
            	  Intent newIntent = new Intent(UsecasesActivity2.this, RegisterClaimActivity.class);
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
            	  showDialog(1);   
            	  //startService(new Intent(UsecasesActivity.this, UpdateListService.class));

            	  /*
                  final ProgressDialog progDailog = ProgressDialog.show(UsecasesActivity.this,
                          "Berkshire Database", "Updating please wait....",
                          true);
                          new Thread() {
                          public void run() {
                                              try{
                               // just doing some long operation
                              
                          } catch (Exception e) {  }
                       handler.sendEmptyMessage(0);
                       //Toast.makeText(UsecasesActivity.this, "Updation Complete", Toast.LENGTH_SHORT).show();
                     progDailog.dismiss();                                   
                     }
                 }.start();
            	 */ 
               }
          }
         );
        
        
        
   }// end of onCreate
    
    
    protected Dialog onCreateDialog(int id)
    {
        switch(id)
        {
        case 1:                      // Spinner
        	 progDialog = new ProgressDialog(this);
             progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
             progDialog.setMessage("Updating...");
             progThread = new ProgressThread(handler);
             progThread.start();
             return progDialog;
        default:
        		return null;
        }
    }
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            /*
        	// Get the current value of the variable total from the message data
            // and update the progress bar.
            int total = msg.getData().getInt("total");
            progDialog.setProgress(total);
            if (total <= 0){
                dismissDialog(1);
                progThread.setState(ProgressThread.DONE);
            }
            */
        }
    };
    
    
    private class ProgressThread extends Thread {	
        
        // Class constants defining state of the thread
        final static int DONE = 0;
        final static int RUNNING = 1;
        
        Handler mHandler;
        int mState;
        int total;
    
        // Constructor with an argument that specifies Handler on main thread
        // to which messages will be sent by this thread.
        
        ProgressThread(Handler h) {
            mHandler = h;
        }
        
        @Override
        public void run() {
            
        	mState = RUNNING;
        	
            while (isService == true) {

                try {
                	  startService(new Intent(UsecasesActivity2.this, UpdateListService.class)); 
                    
                } catch (Exception e) {
                    Log.e("ERROR", "Thread was Interrupted");
                }
                
                // Send message (with current value of  total as data) to Handler on UI thread
                // so that it can update the progress bar.
                
                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                msg.setData(b);
                mHandler.sendMessage(msg);
               } 
            
        }
      
    }
}

