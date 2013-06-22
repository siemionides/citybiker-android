package pl.citybikerandroid.activities;

import pl.citybikerandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * New Mesage for Bike
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class NewMessageStationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_message_station_activity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
