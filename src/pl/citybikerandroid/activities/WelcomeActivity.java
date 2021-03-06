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
import pl.citybikerandroid.helper.HelperToolkit;
import pl.citybikerandroid.network.CollectionRequest;
import pl.citybikerandroid.network.CollectionRequestListener;
import pl.citybikerandroid.network.LocationRequest;
import pl.citybikerandroid.network.LocationRequest.LocationRequestListener;
import pl.citybikerandroid.providers.BikeStationDatabase;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

public class WelcomeActivity extends Activity implements LocationRequestListener {
	
	public static final int ACTION_CODE_QR_SCANNING = 4534535;
	

	/** ListView for showing up the results */
	private ListView mListView;

	/** Adapter for ListView of BikeStationAround objects */
	StationsAroundAdapter adapter = null;

	private SpiceManager contentManager = new SpiceManager(
			JacksonSpringAndroidSpiceService.class);
	
	CollectionRequest<StationCollection> collectionRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		handleIntent(getIntent());
		displayListView();
		
		//debug - were' not using it, all Stations are hardcoded 
//		performAllStationsRequest();
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

	/** handles Intent from search suggestions service*/
	private void handleIntent(Intent intent) {
		/* this handles when user clicks on elements*/
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			//when user click on suggested result - start BikeStationActivity
			//content://pl.citybikerandroid.providers.BikeStationProvider/bikestation/29
			
				Uri uri = intent.getData();
		       Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		       
		        if (cursor == null) {
		            finish();
		        } else {
		            cursor.moveToFirst();
		            String stationNumber = cursor.getString(cursor.getColumnIndex(BikeStationDatabase.KEY_STATION_ID));
		            performOneStationRequest(stationNumber);
		        }
		            
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
			
		case R.id.menu_search_bike_qr:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	        
	        //chech whether Intent will be resolved - is there app installed?
	        PackageManager pm = getPackageManager();
	        ComponentName cn = intent.resolveActivity(pm);
	        if( cn == null){ //no activity
	    
				Uri marketUri = Uri
						.parse("market://search?q=pname:com.google.zxing.client.android");
				Intent marketIntent = new Intent(Intent.ACTION_VIEW)
						.setData(marketUri);
				if (marketIntent.resolveActivity(pm) != null) {
					startActivity(marketIntent);
				} else {
					Log.e("error", "Market client not available.");
					HelperToolkit.createAlertDialog(WelcomeActivity.this, "Problem", 
							"QR app not found. Cannot connect to Google Play too. (are you online?) ").show();
				}
	        }else{
		        //start Activity
	        	startActivityForResult(intent, WelcomeActivity.ACTION_CODE_QR_SCANNING);	
	        }
	        
	        

	        
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
		collectionRequest = new CollectionRequest<StationCollection>(
				StationCollection.class, Constants.STATIONS_URI);
		collectionRequest.addLimit(Integer.toString(limit));
		LocationRequest lr = new LocationRequest(this,this);
	}
	
	@Override
	public void onLocationResult(double[] loc, LocationRequest request) {
		request.stop();
		collectionRequest.addLocation(Double.toString(loc[0])+","+Double.toString(loc[1]));
		collectionRequest.perform(contentManager, new StationCollectionRequest(getApplicationContext()));
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
	
	/** Retrieves all stations data
	 * For the presentation it's not used and all data info is simply hardcoded in array in strings.xml! */
	@Deprecated
	private void performAllStationsRequest() {
		CollectionRequest<StationCollection> request = new CollectionRequest<StationCollection>(
				StationCollection.class, Constants.STATIONS_URI);
//		request.addLimit(Integer.toString(limit));
//		request.addLocation("21.016181,52.216837");
		request.perform(contentManager, new AllStationCollectionRequest(getApplicationContext()));
	}
	
	private class AllStationCollectionRequest extends CollectionRequestListener<StationCollection> {
		
		public AllStationCollectionRequest(Context context) {
			super(context);
		}

		public void performOnSuccess(StationCollection collection) {

			HelperToolkit.makeToast(WelcomeActivity.this, "all data is back, size:" + collection.size());
			
			for (Station station : collection){
				Log.d("debug1", station.getLocation() + ";" + station.getNumber());
			}
			//			adapter.clear();
//			for (Station station : collection) {
//				Station bs = new Station(station.getLocation(),
//						station.getNumber(), station.getBicycles());
//				bs.setId(station.getId());
//				performMessagesRequest(bs, "logistic", 1);
//				adapter.add(station);
//			}
//			adapter.notifyDataSetChanged();
		}
	}

	/** Request performed onSuggestions listview Intem Click */
	private void performOneStationRequest(String stationNumber){
		CollectionRequest<StationCollection> request = new CollectionRequest<StationCollection>(
				StationCollection.class, Constants.STATIONS_URI);
		request.addFilter("number::" + stationNumber);
//		request.addLimit(Integer.toString(limit));
//		request.addLocation("21.016181,52.216837");
		request.perform(contentManager, new OneStationCollectionRequest(getApplicationContext()));
	}
	
	
	/** Request performed onSuggestions listview Intem Click*/
	private class OneStationCollectionRequest extends CollectionRequestListener<StationCollection> {

		public OneStationCollectionRequest(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		/** On success we are starting new Station Activity*/
		@Override
		public void performOnSuccess(StationCollection collection) {
			if (collection.size() != 1){
				for(Station s : collection){
					Log.d("debug5", "returned station (nr)" + s.getNumber() + " , (name:)" + s.getLocation());
				}
				
				throw new IllegalStateException("Collection returned should contain only 1 station, and it contains:" + collection.size());
			}else{
				Log.i("debug4", "receiving one station from requests, starting new activity");
				Station s = collection.get(0);
				Intent stationIntent = new Intent(WelcomeActivity.this, StationActivity.class);
				stationIntent.putExtra(Station.SERIALIZABLE_NAME, s);
				startActivity(stationIntent);
			}
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
	
	/** Used for retrieving Intents from QR code application scanner*/
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == WelcomeActivity.ACTION_CODE_QR_SCANNING) {
	        if (resultCode == RESULT_OK) {
	            String contents = intent.getStringExtra("SCAN_RESULT");
	            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	            
	            HelperToolkit.createAlertDialog(WelcomeActivity.this, "qr return", 
	            		"Qr returned:" + contents + ", " + format).show();
	            // Handle successful scan
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
	    }
	}

}
