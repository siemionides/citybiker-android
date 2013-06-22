package pl.citybikerandroid.activities;

import pl.citybikerandroid.R;
import pl.citybikerandroid.domain.Bike;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

/**
 * New Mesage for Bike
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class NewMessageBikeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_message_bike_activity);
		
		//handle intent
		//if intent is present
		Intent i = getIntent();
		Bike b = (Bike) i.getSerializableExtra(Bike.SERIALIZABLE_NAME);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
