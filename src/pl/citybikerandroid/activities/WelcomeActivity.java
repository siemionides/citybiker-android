package pl.citybikerandroid.activities;

import java.util.ArrayList;
import java.util.Date;

import pl.citybikerandroid.Constants;
import pl.citybikerandroid.R;
import pl.citybikerandroid.domain.Bike;
import pl.citybikerandroid.domain.InformativeMessage;
import pl.citybikerandroid.domain.LogisticalMessage;
import pl.citybikerandroid.domain.Message;
import pl.citybikerandroid.domain.MessageCollection;
import pl.citybikerandroid.domain.ServiceMessage;
import pl.citybikerandroid.domain.Station;
import pl.citybikerandroid.domain.StationCollection;
import pl.citybikerandroid.network.CollectionRequest;
import pl.citybikerandroid.network.CollectionRequestListener;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

public class WelcomeActivity extends Activity {

	/** ListView for showing up the results */
	private ListView mListView;

	/** Adapter for ListView of BikeStationAround objects */
	StationsAroundAdapter adapter = null;

	private SpiceManager contentManager = new SpiceManager(
			JacksonSpringAndroidSpiceService.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		handleIntent(getIntent());
		displayListView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		contentManager.start(this);
	}

	@Override
	protected void onStop() {
		contentManager.shouldStop();
		super.onStop();
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Log.d("debug", "handle Intent");
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			showResults(query);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	/**
	 * Searches the dictionary and displays results for the given query.
	 * 
	 * @param query
	 *            The search query
	 */
	private void showResults(String query) {

		Log.d("debug", "show results staerted! + query: " + query);

		final ArrayList<String> values = new ArrayList<String>();
		values.add("super");
		values.add("duper");
		final ArrayAdapter<String> resAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);

		mListView.setAdapter(resAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search_station);
		SearchView mSearchView = (SearchView) searchItem.getActionView();
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		mSearchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		mSearchView.setIconifiedByDefault(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_search_station:
			onSearchRequested();
			return true;
		case R.id.menu_search_bike:
			// start new BikeActivity with an Intent
			Bike b = new Bike(65432);

			b.addInformativeMessage(new InformativeMessage(
					"from Intent: Bardzo piękny dzień, jest super!", new Date(
							2012, 05, 23, 17, 34)));
			b.addInformativeMessage(new InformativeMessage(
					"from Intent:  jest super, dobry dzień!", new Date(2012,
							03, 23, 12, 34)));
			b.addInformativeMessage(new InformativeMessage(
					"from Intent:  Bardzo super dzień, jest super!", new Date(
							2011, 04, 18, 123, 34)));

			b.addServiceMessage(new ServiceMessage(
					"from Intent:  Dzwonek nie działa!"));
			b.addServiceMessage(new ServiceMessage(
					"from Intent:  Hamulec nie działa!"));
			b.addServiceMessage(new ServiceMessage(
					"from Intent:  Światło nie działa! nie działa!"));

			Intent i = new Intent(WelcomeActivity.this, BikeActivity.class);
			i.putExtra(Bike.SERIALIZABLE_NAME, (Bike) b);
			startActivity(i);

			return true;
		default:
			return false;
		}

		// TODO Auto-generated method stub
		// return super.onOptionsItemSelected(item);
	}

	/** Injects the listview with sample fake data */
	private void displayListView() {

		adapter = new StationsAroundAdapter(this,
				R.layout.list_item_station_around);
		performStationsRequest(3);

		ListView listView = (ListView) findViewById(R.id.listStationsAroundView);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Station station = (Station) parent.getItemAtPosition(position);

				Intent i = new Intent(WelcomeActivity.this,
						StationActivity.class);
				i.putExtra(Station.SERIALIZABLE_NAME, (Station) station);
				startActivity(i);

				Toast.makeText(getApplicationContext(),
						"Clicked on Row: " + station.getLocation(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void performStationsRequest(int limit) {
		CollectionRequest<StationCollection> request = new CollectionRequest<StationCollection>(
				StationCollection.class, Constants.STATIONS_URI);
		request.addLimit(Integer.toString(limit));
		request.addLocation("21.016181,52.216837");
		request.perform(contentManager, new StationCollectionRequest(getApplicationContext()));
	}
	
	private class StationCollectionRequest extends CollectionRequestListener<StationCollection> {
		
		public StationCollectionRequest(Context context) {
			super(context);
		}

		public void performOnSuccess(StationCollection collection) {
			adapter.clear();
			for (Station station : collection) {
				Station bs = new Station(station.getLocation(),
						station.getNumber(), station.getBicycles());
				bs.setId(station.getId());
				performMessagesRequest(bs, "logistic", 1);
				adapter.add(station);
			}
			adapter.notifyDataSetChanged();
		}
	}

	private void performMessagesRequest(Station station, String type, int limit) {

		if (type == null || type.trim().isEmpty())
			type = "";
		Message.Types.valueOf(type);

		CollectionRequest<MessageCollection> request = new CollectionRequest<MessageCollection>(
				MessageCollection.class, Constants.MESSAGES_URI);
		request.addFilter("type::" + type);
		request.addLimit(Integer.toString(limit));
		request.perform(contentManager, new MessageCollectionRequest(getApplicationContext(), station));
	}
	
	private class MessageCollectionRequest extends CollectionRequestListener<MessageCollection> {
		
		Station station = null;
		
		public MessageCollectionRequest(Context context, Station station) {
			super(context);
			this.station = station; 
		}

		@Override
		public void performOnSuccess(MessageCollection collection) {
			station.addInformativeMessage(new InformativeMessage("cześć", "ja"));
			for (Message message : collection) {
				String type = message.getType();			
			}
			adapter.notifyDataSetChanged();
		}
	}

	private class StationsAroundAdapter extends ArrayAdapter<Station> {

		public StationsAroundAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		private class ViewHolder {
			TextView stationLocation;
			TextView stationInfo;
			TextView stationMessages;
			TextView distanceTo;
		}

		/** */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.list_item_station_around,
						null);

				holder = new ViewHolder();
				holder.stationLocation = (TextView) convertView
						.findViewById(R.id.tv_station_name);
				holder.stationInfo = (TextView) convertView
						.findViewById(R.id.tv_station_info);
				holder.stationMessages = (TextView) convertView
						.findViewById(R.id.tv_station_messages);
				holder.distanceTo = (TextView) convertView
						.findViewById(R.id.tv_station_distance);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Station station = this.getItem(position);

			holder.stationLocation.setText(station.getLocation());
			holder.stationInfo.setText(formatStationInfoText(station));
			holder.stationMessages.setText(formatStationMessagesText(station));
			holder.distanceTo.setText(formatDistanceToText(station));
			return convertView;
		}

		private String formatStationInfoText(Station station) {
			String stationDistrict = station.getDistrict();
			int bikes = station.getBicycles();

			String bikesString = Integer.toString(bikes);
			if (bikes == Station.MORE_THAN_FOUR) {
				bikesString = "4+";
			}

			return "Station district: " + stationDistrict + "\nBikes: "
					+ bikesString;
		}

		private String formatStationMessagesText(Station station) {

			InformativeMessage informative = station
					.getLastInformativeMessage();
			LogisticalMessage logistical = station.getLastLogisticalMessage();
			ServiceMessage service = station.getLastServiceMessage();

			String informativeText = (informative == null) ? "" : informative
					.getText();
			String logisticalText = (logistical == null) ? "" : logistical
					.getText();
			String serviceText = (informative == null) ? "" : service.getText();

			String messages = "Inf: " + informativeText + "\nLog: "
					+ logisticalText + "\nServ: " + serviceText;
			return messages;
		}

		private String formatDistanceToText(Station station) {
			return station.getDistanceTo().toString() + " km";
		}
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}

}
