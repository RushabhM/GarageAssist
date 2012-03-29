package com.example.internshala;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InternshalaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        
        EditText editText = (EditText)findViewById(R.id.username);
  	  	final String uname = editText.getText().toString();
  	  	
  	  	editText = (EditText)findViewById(R.id.password);
  	  	final String pswd = editText.getText().toString();
        
        Button login_button=(Button)findViewById(R.id.login_btn);
        
        login_button.setOnClickListener(new Button.OnClickListener()
          {
              public void onClick(View v)
               {  
            	 if(uname.equals("intern")&&pswd.equals("intern")) 
            	 {
            		 Intent newIntent = new Intent(InternshalaActivity.this, UsecasesActivity.class);
            		 startActivity(newIntent);
            	 }
            	 
            	 else
            	 {
            		 Toast.makeText(InternshalaActivity.this, "Invalid Login credentials", Toast.LENGTH_SHORT).show();
            	 }
            	 
               }
          }
         );          

   }// end of onCreate
}