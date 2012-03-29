package com.example.internshala;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterClaimActivity extends Activity {
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    int PICK_REQUEST_CODE = 0;
    
    Uri selectedFile, selectedImage;
    Intent email;
    
	/** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimregister);
        
        email = new Intent(Intent.ACTION_SEND);
  	  	email.setType("message/rfc822");
                
        Button claim=(Button)findViewById(R.id.registerclaim);
        
        claim.setOnClickListener(new Button.OnClickListener()
          {
              public void onClick(View v)
               {  
            	  String body, policyNo, mobileNo, vehicleNo;
            	  
            	  EditText editText = (EditText)findViewById(R.id.policytext);
            	  policyNo = editText.getText().toString();
            	  
            	  editText = (EditText)findViewById(R.id.mobiletext);
            	  mobileNo = editText.getText().toString();
            	  
            	  editText = (EditText)findViewById(R.id.vehicletext);
            	  vehicleNo = editText.getText().toString();
            	  
            	  body = "Hello! \n \n";
            	  body += "Please consider this email as my registration for claiming insurance\n \n";
            	  body += "Following are my details: \n";
            	  body += "Policy No - " + policyNo + "\n";
            	  body += "Mobile No - " + mobileNo + "\n";
            	  body += "Vehicle No - " + vehicleNo + "\n \n";
            	  body += "Please find attachments. \n Looking forward to hearing from you! \nThanks.";
            	  
            	  email.putExtra(Intent.EXTRA_EMAIL  , new String[]{"rushabhmehta05@gmail.com", "varun@internshala.com"});
            	  email.putExtra(Intent.EXTRA_SUBJECT, "Registration of Claim");
            	  email.putExtra(Intent.EXTRA_TEXT   , body);
            	  
            	  try {
            	      startActivity(Intent.createChooser(email, "Send mail..."));
            	  } catch (android.content.ActivityNotFoundException ex) {
            	      Toast.makeText(RegisterClaimActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            	  }
               }
          }
         );          
        
        Button attach_file=(Button)findViewById(R.id.attach);
        
        attach_file.setOnClickListener(new Button.OnClickListener()
          {
              public void onClick(View v)
               {  
            	  Intent intent = new Intent();
            	  intent.setAction(Intent.ACTION_PICK);
            	  startActivityForResult(intent, PICK_REQUEST_CODE);
               }
          }
         ); 
        
               
        Button takePic = (Button)findViewById(R.id.photo);
        
        takePic.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
             {  
            	String fileName = "new-photo-name.jpg";
                
                //create parameters for Intent with filename
                
                ContentValues values = new ContentValues();
                
                values.put(MediaStore.Images.Media.TITLE, fileName);
                
                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
                
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                
                Uri imageUri = getContentResolver().insert(
                
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                
                //create new Intent
                
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
             }
        }
       );

   }// end of onCreate
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
       if (requestCode == PICK_REQUEST_CODE)
       {
    	   if (resultCode == RESULT_OK)
    	   {
    		   selectedFile = intent.getData();
    		   email.putExtra(Intent.EXTRA_STREAM, selectedFile);
    		   
    		   Toast.makeText(this, "Attached!", Toast.LENGTH_LONG).show();
    	   }
    	   else
    	   {
    		   Toast.makeText(this, "Error Attaching", Toast.LENGTH_LONG).show();
    	   }
       }
       else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
       {
    	   if (resultCode == RESULT_OK)
    	   {
    		   selectedImage = intent.getData();
    		   email.putExtra(Intent.EXTRA_STREAM, selectedImage);
    		   
    		   Toast.makeText(this, "Photo taken!", Toast.LENGTH_LONG).show();
    	   }
    	   else
    	   {
    		   Toast.makeText(this, "Error!!", Toast.LENGTH_LONG).show();
    	   }
       }
    }
}

