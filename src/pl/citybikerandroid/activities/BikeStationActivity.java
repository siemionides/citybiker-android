package pl.citybikerandroid.activities;

import pl.citybikerandroid.R;
import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

public class BikeStationActivity extends Activity {
	// private FragmentTabHost mTabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bike_station_activity);

		TabHost tabs = (TabHost) findViewById(R.id.TabHost01);

		tabs.setup();

		TabHost.TabSpec spec1 = tabs.newTabSpec("tag1");
			spec1.setContent(R.id.tab1Layout);
			spec1.setIndicator("informative");
		
		tabs.addTab(spec1);
				
		
		TabHost.TabSpec spec2 = tabs.newTabSpec("tag2");

		spec2.setContent(R.id.tab2Layout);
		spec2.setIndicator("logistical");

		tabs.addTab(spec2);

		TabHost.TabSpec spec3 = tabs.newTabSpec("tag3");
		spec3.setContent(R.id.tab3Layout);
		spec3.setIndicator("service");

		tabs.addTab(spec3);
	}
}
