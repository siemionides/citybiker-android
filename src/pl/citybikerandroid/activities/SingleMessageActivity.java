package pl.citybikerandroid.activities;

import pl.citybikerandroid.R;
import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.Message;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Single Message Screen (#07 mockup)
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class SingleMessageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.single_message_acitivity);
		
		Intent i = getIntent();
		Message msg = (Message) i.getSerializableExtra(Message.SERIALIZABLE_NAME);
		Log.d("SingleMessageActivity", "received intent!");
		
		if(msg instanceof InformativeMessage){
			Log.d("SingleMessageActivity", "received intent");
			
			Toast.makeText(getApplicationContext(),
					"Received Informative Message!",
					Toast.LENGTH_LONG).show();
		}
		
		populateViews(msg);
	}
	
	private void populateViews(Message msg){
		
		//populate Title - Bike(ID) > <Type of message>
//		String title = "Bike(" + 
		
		//poulate Date
		
		//populate Author
		
		//populate Message
		
		//populate Photo
	}
}
