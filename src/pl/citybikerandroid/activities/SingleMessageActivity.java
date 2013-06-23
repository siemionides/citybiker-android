package pl.citybikerandroid.activities;

import pl.citybikerandroid.R;
import pl.citybikerandroid.domain.Bike;
import pl.citybikerandroid.domain.Message;
import pl.citybikerandroid.domain.InformativeMessage;
import pl.citybikerandroid.domain.LogisticalMessage;
import pl.citybikerandroid.domain.ServiceMessage;
import pl.citybikerandroid.domain.Station;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.TextView;
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
		Station bs = (Station) i.getSerializableExtra(Station.SERIALIZABLE_NAME);
		Bike b = (Bike)i.getSerializableExtra(Bike.SERIALIZABLE_NAME);
		
		Log.d("SingleMessageActivity", "received intent!");
		
		if(msg instanceof InformativeMessage){
			Log.d("SingleMessageActivity", "received intent");
			
			Toast.makeText(getApplicationContext(),
					"Received Informative Message!",
					Toast.LENGTH_LONG).show();
		}
		
		populateViews(msg, bs, b);
	}
	
	private void populateViews(Message msg, Station bs, Bike b ){
		
		//populate Title - Bike(ID) > <Type of message>
		//				or Station(ID) <Typeof Message>
		String title = "";
		if (bs == null){
			title += "Bike(";
			title += b.getId();
			
		}
		else{
			title += "Station(";
			title += bs.getId();
		}
		title += ") > ";
		//add message type to informative
		if (msg instanceof InformativeMessage) title += "Informative";
		else if (msg instanceof LogisticalMessage) title += "Logistical";
		else if (msg instanceof ServiceMessage) title += "Service";
		
		//inject to view
		TextView tv = (TextView) findViewById(R.id.tv_single_message_title);
		tv.setText(title);
		
		//poulate Date
		String date = msg.getCreatedAt().toString();
		tv = (TextView) findViewById(R.id.tv_single_message_date);
		tv.setText(date);
		
		//populate Author
		String author = msg.getAuthor();
		tv = (TextView) findViewById(R.id.single_message_tv_author);
		tv.setText(author);
		
		//populate Message
		String messageText = msg.getText();
		tv = (TextView) findViewById(R.id.tv_single_message_message);
		tv.setText(messageText);
		
		//populate Photo
		//TODO
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.welcome, menu);

		return super.onCreateOptionsMenu(menu);
	}
}
