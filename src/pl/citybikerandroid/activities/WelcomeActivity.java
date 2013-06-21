package pl.citybikerandroid.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import pl.citybikerandroid.Constants;
import pl.citybikerandroid.R;
import pl.citybikerandroid.bikes.Bike;
import pl.citybikerandroid.domain.Station;
import pl.citybikerandroid.domain.StationCollection;
import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.ServiceMessage;
import pl.citybikerandroid.network.CollectionRequest;
import pl.citybikerandroid.stations.BikeStation;
import pl.citybikerandroid.stations.BikeStationAround;
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

public class WelcomeActivity extends Activity {

	/** ListView for showing up the results */
	private ListView mListView;

	/** Adapter for ListView of BikeStationAround objects */
	StationsAroundAdapter adapter = null;

	private SpiceManager contentManager = new SpiceManager(
			JacksonSpringAndroidSpiceService.class);
	private String lastRequestCacheKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// mListView = (ListView) findViewById(R.id.list);

		handleIntent(getIntent());

		displayListView();

	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			// handles a click on a search suggestion; launches activity to show
			// word
			/*
			 * Intent wordIntent = new Intent(this, WordActivity.class);
			 * wordIntent.setData(intent.getData()); startActivity(wordIntent);
			 */
			Log.d("debug", "handle Intent");
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			String query = intent.getStringExtra(SearchManager.QUERY);
			showResults(query);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// Because this activity has set launchMode="singleTop", the system
		// calls this method
		// to deliver the intent if this activity is currently the foreground
		// activity when
		// invoked again (when the user executes a search from this activity, we
		// don't create
		// a new instance of this activity, so the system delivers the search
		// intent here)
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

		/*
		 * Cursor cursor = managedQuery(DictionaryProvider.CONTENT_URI, null,
		 * null, new String[] {query}, null);
		 * 
		 * if (cursor == null) { // There are no results
		 * mTextView.setText(getString(R.string.no_results, new Object[]
		 * {query})); } else { // Display the number of results int count =
		 * cursor.getCount(); String countString =
		 * getResources().getQuantityString(R.plurals.search_results, count, new
		 * Object[] {count, query}); mTextView.setText(countString);
		 * 
		 * // Specify the columns we want to display in the result String[] from
		 * = new String[] { DictionaryDatabase.KEY_WORD,
		 * DictionaryDatabase.KEY_DEFINITION };
		 * 
		 * // Specify the corresponding layout elements where we want the
		 * columns to go int[] to = new int[] { R.id.word, R.id.definition };
		 * 
		 * // Create a simple cursor adapter for the definitions and apply them
		 * to the ListView SimpleCursorAdapter words = new
		 * SimpleCursorAdapter(this, R.layout.result, cursor, from, to);
		 * 
		 * 
		 * mListView.setAdapter(words);
		 * 
		 * // Define the on-click listener for the list items
		 * mListView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // Build the Intent used to open
		 * WordActivity with a specific word Uri Intent wordIntent = new
		 * Intent(getApplicationContext(), WordActivity.class); Uri data =
		 * Uri.withAppendedPath(DictionaryProvider.CONTENT_URI,
		 * String.valueOf(id)); wordIntent.setData(data);
		 * startActivity(wordIntent); } }); }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);

		// set stations searchable
		MenuItem searchItem = menu.findItem(R.id.menu_search_station);
		SearchView mSearchView = (SearchView) searchItem.getActionView();
		// setupSearchView(searchItem);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		// SearchView searchView = (SearchView)
		// menu.findItem(R.id.).getActionView();
		// Assumes current activity is the searchable activity
		mSearchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		mSearchView.setIconifiedByDefault(false); // Do not iconify the widget;
													// expand it by default

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// switch(item.getItemId()){
		// case R.id.menu_search_bike: {
		// AlertDialog.Builder alert = HelperToolkit.createAlertDialog(this,
		// "searchBikeAlert",
		// "searchBike button preessed - to be implemented!");
		// alert.show();
		// break;
		// }
		// case R.id.menu_search_station:{
		// AlertDialog.Builder alert = HelperToolkit.createAlertDialog(this,
		// "searchStationAlert",
		// "searchStation button preessed - to be implemented!");
		// alert.show();
		// break;
		// }
		// }

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

		ArrayList<BikeStationAround> stationAroundList = new ArrayList<BikeStationAround>();

		/*
		 * BikeStationAround bs = new BikeStationAround(
		 * "ul Warynskiego - ul. Nowowiejska", 6364,
		 * BikeStation.MORE_THAN_FOUR); bs.addInformativeMessage(new
		 * InformativeMessage( "Bardzo piękny dzień, jest super!"));
		 * bs.addLogisticalMessage(new LogisticalMessage(
		 * "Będę za 15 minut na 6364", LogisticalMessage.GOING_TO, new
		 * BikeStation(), bs, 3432)); bs.addServiceMessage(new
		 * ServiceMessage("Dzwonek nie działa!")); bs.setDistanceTo(0.2);
		 * stationAroundList.add(bs);
		 * 
		 * bs = new BikeStationAround("Plac Zbawiciela - Nowowiejska", 6376,
		 * BikeStation.MORE_THAN_FOUR); bs.addInformativeMessage(new
		 * InformativeMessage( " jest super, dobry dzień!"));
		 * bs.addLogisticalMessage(new LogisticalMessage(
		 * "Będę za 23 minut na 6376", LogisticalMessage.GOING_TO, new
		 * BikeStation(), bs, 343)); bs.addServiceMessage(new
		 * ServiceMessage("Hamulec nie działa!")); bs.setDistanceTo(0.5);
		 * stationAroundList.add(bs);
		 * 
		 * bs = new BikeStationAround("Plac Politechniki - ul. Nowowiejska",
		 * 63642, 3); bs.addInformativeMessage(new InformativeMessage(
		 * "Bardzo super dzień, jest super!")); bs.addLogisticalMessage(new
		 * LogisticalMessage( "Zajęło mi to naście minut",
		 * LogisticalMessage.TIME_BETWEEN, new BikeStation(), bs, 453));
		 * bs.addServiceMessage(new ServiceMessage(
		 * "Światło nie działa! nie działa!")); bs.setDistanceTo(0.8);
		 * stationAroundList.add(bs);
		 */

		// create an ArrayAdaptar from the String Array
		adapter = new StationsAroundAdapter(this,
				R.layout.list_item_station_around, stationAroundList);
		performRequest();
		ListView listView = (ListView) findViewById(R.id.listStationsAroundView);
		// Assign adapter to ListView
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				BikeStationAround station = (BikeStationAround) parent
						.getItemAtPosition(position);

				// Start new bike Station Activity!
				Intent i = new Intent(WelcomeActivity.this,
						BikeStationActivity.class);
				i.putExtra(BikeStation.SERIALIZABLE_NAME, (BikeStation) station);
				startActivity(i);

				Toast.makeText(getApplicationContext(),
						"Clicked on Row: " + station.getName(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void performRequest() {

		CollectionRequest<StationCollection> request = new CollectionRequest<StationCollection>(
				StationCollection.class, Constants.STATIONS_URI);
		lastRequestCacheKey = request.createCacheKey();
		contentManager.execute(request, lastRequestCacheKey,
				DurationInMillis.ALWAYS_EXPIRED, new StationRequestListener());
		Toast.makeText(getApplicationContext(),
				"Request performed!",
				Toast.LENGTH_LONG).show();
	
	}

	private class StationRequestListener implements
			RequestListener<StationCollection> {

		@Override
		public void onRequestSuccess(StationCollection listStations) {

			Toast.makeText(getApplicationContext(),
					"Succesfully retrieved messages!",
					Toast.LENGTH_LONG).show();
			
			if (listStations == null) {
				return;
			}

			adapter.clear();

			final List<Station> stations = listStations.getResults();
			for (Station station : stations) {
				BikeStationAround bs = new BikeStationAround(station.getLocation(),
						station.getNumber(), station.getBicycles());
				adapter.add(bs);
			}

			adapter.notifyDataSetChanged();
		}

		@Override
		public void onRequestFailure(SpiceException e) {
			Toast.makeText(getApplicationContext(),
					"Error during request: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	private class StationsAroundAdapter extends ArrayAdapter<BikeStationAround> {

		private ArrayList<BikeStationAround> stationsAroundList;

		public StationsAroundAdapter(Context context, int textViewResourceId,
				ArrayList<BikeStationAround> stationsAroundList) {
			super(context, textViewResourceId, stationsAroundList);
			this.stationsAroundList = new ArrayList<BikeStationAround>();
			this.stationsAroundList.addAll(stationsAroundList);
		}

		private class ViewHolder {
			TextView stationName;
			TextView stationInfo;
			TextView stationMessages;
			TextView distanceTo;
			// CheckBox name;
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
				holder.stationName = (TextView) convertView
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

			// let's fill the views from object!
			BikeStationAround station = stationsAroundList.get(position);

			holder.stationName.setText(station.getName());
			holder.stationInfo.setText(formatStationInfoText(station));
			// prepare messages string
			holder.stationMessages.setText(formatStationMessagesText(station));
			holder.distanceTo.setText(formatDistanceToText(station));
			return convertView;
		}

		private String formatStationInfoText(BikeStationAround station) {
			String stationId = station.getId();
			int nrBikes = station.getNrBikes();

			String nrBikesStr = Integer.toString(nrBikes);
			if (nrBikes == BikeStation.MORE_THAN_FOUR) {
				nrBikesStr = "4+";
			}

			return "Station Nr: " + stationId + "\nBikes Nr: " + nrBikesStr;
		}

		private String formatStationMessagesText(BikeStationAround station) {
			String messages = "Inf: "
					+ station.getLastInformativeMessage().getText() + "\nLog: "
					+ station.getLastLogisticalMessage().getText() + "\nServ: "
					+ station.getLastServiceMessage().getText();
			return messages;
		}

		private String formatDistanceToText(BikeStationAround station) {
			return station.getDistanceTo().toString() + " km";
		}
	}

	// /** For setting up the search view */
	// private void setupSearchView(MenuItem searchItem) {
	// if (isAlwaysExpanded()) {
	// mSearchView.setIconifiedByDefault(false);
	// } else {
	// searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
	// | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	// }
	//
	// SearchManager searchManager = (SearchManager)
	// getSystemService(Context.SEARCH_SERVICE);
	// if (searchManager != null) {
	// List<SearchableInfo> searchables =
	// searchManager.getSearchablesInGlobalSearch();
	//
	// SearchableInfo info =
	// searchManager.getSearchableInfo(getComponentName());
	// for (SearchableInfo inf : searchables) {
	// if (inf.getSuggestAuthority() != null
	// && inf.getSuggestAuthority().startsWith("applications")) {
	// info = inf;
	// }
	// }
	// mSearchView.setSearchableInfo(info);
	// }
	//
	// mSearchView.setOnQueryTextListener(this);
	// }

	/*
	 * @Override public boolean onQueryTextChange(String newText) { // TODO
	 * Auto-generated method stub return false; }
	 */

	/*
	 * @Override public boolean onQueryTextSubmit(String query) { // TODO
	 * Auto-generated method stub return false; }
	 */

	protected boolean isAlwaysExpanded() {
		return false;
	}

}
