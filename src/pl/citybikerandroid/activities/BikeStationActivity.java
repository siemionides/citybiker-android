package pl.citybikerandroid.activities;

import java.util.ArrayList;
import java.util.Date;

import pl.citybikerandroid.R;
import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;
import pl.citybikerandroid.stations.BikeStation;
import pl.citybikerandroid.stations.BikeStationAround;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BikeStationActivity extends Activity {

//	/** ListView for informal messages tab */
//	private ListView mListViewInformalMessages;
//
//	/** ListView for logistical messages tab */
//	private ListView mListViewLogisticalMessages;
//
//	/** ListView for service messages tab */
//	private ListView mListViewServiceMessages;

	/** Adapter for ListView of Informal Messages (for informal tab) objects */
	BikeStationMessagesAdapter<InformativeMessage> adapterInformalMsg = null;

	/** Adapter for ListView of Logistical Messages (for logistical tab) objects */
	BikeStationMessagesAdapter<LogisticalMessage> adapterLogisticalMsg = null;

	/** Adapter for ListView of Service Messages (for service tab) objects */
	BikeStationMessagesAdapter<ServiceMessage> adapterServiceMsg = null;

	// private FragmentTabHost mTabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bike_station_activity);

		/* Fill tabs */
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
	
		
		
		
		//if intent is present
		Intent i = getIntent();
		BikeStation bs = (BikeStation) i.getSerializableExtra(BikeStation.SERIALIZABLE_NAME);
		
		// populate views
		populateListViews(bs);
		
	}
	
	
	/** Injects the listview with sample fake data */
	private void populateListViews(BikeStation bs) {

//		// Array list of countries
//		ArrayList<BikeStationAround> stationAroundList = new ArrayList<BikeStationAround>();

		//for debug for now 
		if (bs == null){
			bs = new BikeStation(
					"ul Warynskiego - ul. Nowowiejska", "6364",
					BikeStation.MORE_THAN_FOUR);
			
				bs.addInformativeMessage(new InformativeMessage(
						"Bardzo piękny dzień, jest super!",new Date(2012,05,23,17,34)));
				bs.addInformativeMessage(new InformativeMessage(
						" jest super, dobry dzień!",new Date(2012,03,23,12,34)));
				bs.addInformativeMessage(new InformativeMessage(
						"Bardzo super dzień, jest super!", new Date(2011,04,18,123,34)));
				
				
				
				bs.addLogisticalMessage(new LogisticalMessage(
						"Będę za 15 minut na 6364", LogisticalMessage.GOING_TO,
						new BikeStation(), bs, 3432));
				
				bs.addLogisticalMessage(new LogisticalMessage(
						"Będę za 23 minut na 6376", LogisticalMessage.GOING_TO,
						new BikeStation(), bs, 343));
				bs.addLogisticalMessage(new LogisticalMessage(
						"Zajęło mi to naście minut", LogisticalMessage.TIME_BETWEEN,
						new BikeStation(), bs, 453));
				
			
				bs.addServiceMessage(new ServiceMessage("Dzwonek nie działa!"));
				bs.addServiceMessage(new ServiceMessage("Hamulec nie działa!"));
				bs.addServiceMessage(new ServiceMessage("Światło nie działa! nie działa!"));
			
		}

		//get prefixed for station name and station id textfield
		String stationNamePrefix = getResources().getString(R.string.activity_bike_station_station_name) + " ";
		String stationIdPrefix = getResources().getString(R.string.activity_bike_station_station_id) + " ";
		
		//set station name, id for informative tab
		TextView tv = (TextView) findViewById(R.id.tab_inf_station_name_text);
		tv.setText(stationNamePrefix + bs.getName());
		tv = (TextView) findViewById(R.id.tab_inf_station_id_text);
		tv.setText(stationIdPrefix + bs.getId());
		
		//set station name, id for logistical tab
		tv = (TextView) findViewById(R.id.tab_log_station_name_text);
		tv.setText(stationNamePrefix + bs.getName());
		tv = (TextView) findViewById(R.id.tab_log_station_id_text);
		tv.setText(stationIdPrefix + bs.getId());
		
		//set station name, id for service tab
		tv = (TextView) findViewById(R.id.tab_ser_station_name_text);
		tv.setText(stationNamePrefix + bs.getName());
		tv = (TextView) findViewById(R.id.tab_ser_station_id_text);
		tv.setText(stationIdPrefix + bs.getId());
		
		
		// create an ArrayAdaptar from the String Array
//		adapter = new StationsAroundAdapter(this,
//				R.layout.list_item_station_around, stationAroundList);
		
		adapterInformalMsg = new BikeStationMessagesAdapter<InformativeMessage>(this, 
				R.layout.bike_station_list_item, bs.getInformativeMessages());
		
		adapterLogisticalMsg = new BikeStationMessagesAdapter<LogisticalMessage>(this, 
				R.layout.bike_station_list_item, bs.getLogisticalMessages());
		
		adapterServiceMsg = new BikeStationMessagesAdapter<ServiceMessage>(this, 
				R.layout.bike_station_list_item, bs.getServicelMessages());
		
		// assign adapter and listener to it to the first tab (informative)
		ListView listView = (ListView) findViewById(R.id.tab_inf_listview);
		listView.setAdapter(adapterInformalMsg);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				
				InformativeMessage msg = (InformativeMessage) parent
						.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),
						"Clicked on Row (informative): " + msg.getText(),
						Toast.LENGTH_LONG).show();
			}
		});
		
		

		// assign adapter and listener to the second tab (logistican)
		listView = (ListView) findViewById(R.id.tab_log_listview);
		listView.setAdapter(adapterLogisticalMsg);
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// When clicked, show a toast with the TextView text
//				BikeStationAround station = (BikeStationAround) parent
//						.getItemAtPosition(position);
//				Toast.makeText(getApplicationContext(),
//						"Clicked on Row (logistical): " + station.getName(),
//						Toast.LENGTH_LONG).show();
//			}
//		});

		// assign adapter and listener to the third tab (service)
		listView = (ListView) findViewById(R.id.tab_ser_listview);
		listView.setAdapter(adapterServiceMsg);
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// When clicked, show a toast with the TextView text
//				BikeStationAround station = (BikeStationAround) parent
//						.getItemAtPosition(position);
//				Toast.makeText(getApplicationContext(),
//						"Clicked on Row: (service) " + station.getName(),
//						Toast.LENGTH_LONG).show();
//			}
//		});
		
		
		
		

	}

	private class BikeStationMessagesAdapter <T extends Message> extends ArrayAdapter<T> {

		private ArrayList<T> messagesList;

		public BikeStationMessagesAdapter(Context context,
				int textViewResourceId, ArrayList<T> messagesList) {
			super(context, textViewResourceId, messagesList);
			this.messagesList = new ArrayList<T>();
			this.messagesList.addAll(messagesList);
		}

		private class ViewHolder {
			TextView messageDate;
			TextView messageText;
			// CheckBox name;
		}

		/** */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.bike_station_list_item, null);

				holder = new ViewHolder();
				holder.messageDate = (TextView) convertView
						.findViewById(R.id.tv_message_date);
				holder.messageText = (TextView) convertView
						.findViewById(R.id.tv_message_text);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// let's fill the views from object!
			Message msg = messagesList.get(position);

			holder.messageDate.setText(msg.getDate().toString());
			holder.messageText.setText(msg.getText());
			// prepare messages string
			return convertView;
		}
	}
}
