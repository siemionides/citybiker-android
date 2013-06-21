package pl.citybikerandroid.activities;

import java.util.ArrayList;
import java.util.Date;

import pl.citybikerandroid.R;
import pl.citybikerandroid.domain.Bike;
import pl.citybikerandroid.domain.InformativeMessage;
import pl.citybikerandroid.domain.ServiceMessage;
import pl.citybikerandroid.domain.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Bike Scren #05 
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class BikeActivity extends Activity {

//	/** ListView for informal messages tab */
//	private ListView mListViewInformalMessages;
//
//	/** ListView for logistical messages tab */
//	private ListView mListViewLogisticalMessages;
//
//	/** ListView for service messages tab */
//	private ListView mListViewServiceMessages;

	/** Adapter for ListView of Informal Messages (for informal tab) objects */
	BikeMessagesAdapter<InformativeMessage> adapterInformalMsg = null;


	/** Adapter for ListView of Service Messages (for service tab) objects */
	BikeMessagesAdapter<ServiceMessage> adapterServiceMsg = null;

	// private FragmentTabHost mTabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bike_activity);

		/* Fill tabs */
		TabHost tabs = (TabHost) findViewById(R.id.TabHost01);
		tabs.setup();

		TabHost.TabSpec spec1 = tabs.newTabSpec("tag1");
		spec1.setContent(R.id.tab1Layout);
		spec1.setIndicator("informative");
		tabs.addTab(spec1);

		TabHost.TabSpec spec3 = tabs.newTabSpec("tag2");
		spec3.setContent(R.id.tab2Layout);
		spec3.setIndicator("service");
		tabs.addTab(spec3);
	
		
		//if intent is present
		Intent i = getIntent();
		Bike b = (Bike) i.getSerializableExtra(Bike.SERIALIZABLE_NAME);
		
		// populate views
		populateListViews(b);
		
	}
	
	
	/** Injects the listview with sample fake data */
	private void populateListViews(final Bike b) {

//		// Array list of countries
//		ArrayList<BikeStationAround> stationAroundList = new ArrayList<BikeStationAround>();

		Log.d("BikeActivity", "populateListViews " + b);
		
		
		//for debug for now 
		
//		if (b == null){
//				b = new Bike(65432);
//			
//				b.addInformativeMessage(new InformativeMessage(
//						"Bardzo piękny dzień, jest super!",new Date(2012,05,23,17,34)));
//				b.addInformativeMessage(new InformativeMessage(
//						" jest super, dobry dzień!",new Date(2012,03,23,12,34)));
//				b.addInformativeMessage(new InformativeMessage(
//						"Bardzo super dzień, jest super!", new Date(2011,04,18,123,34)));
//				
//				b.addServiceMessage(new ServiceMessage("Dzwonek nie działa!"));
//				b.addServiceMessage(new ServiceMessage("Hamulec nie działa!"));
//				b.addServiceMessage(new ServiceMessage("Światło nie działa! nie działa!"));
//		}

		//get prefixed for station name and station id textfield
		String bikeNumberPrefix = getResources().getString(R.string.activity_bike_number) + " ";
		
		//set bike id for informative tab
		TextView tv = (TextView) findViewById(R.id.tab_inf_bike_id);
		
		tv.setText(bikeNumberPrefix + b.getNumber());
		
		
		//set station name, id for service tab
		tv = (TextView) findViewById(R.id.tab_ser_bike_id);
		tv.setText(bikeNumberPrefix + b.getNumber());
		
		// create an ArrayAdaptar from the String Array
//		adapter = new StationsAroundAdapter(this,
//				R.layout.list_item_station_around, stationAroundList);
		
		adapterInformalMsg = new BikeMessagesAdapter<InformativeMessage>(this, 
				R.layout.bike_station_list_item, b.getInformativeMessages());
		
		
		adapterServiceMsg = new BikeMessagesAdapter<ServiceMessage>(this, 
				R.layout.bike_station_list_item, b.getServicelMessages());
		
		// assign adapter and listener to it to the first tab (informative)
		ListView listView = (ListView) findViewById(R.id.tab_inf_listview);
		listView.setAdapter(adapterInformalMsg);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				InformativeMessage msg = (InformativeMessage) parent
						.getItemAtPosition(position);
				
				// show single message screen - start new activity
				Intent i = new Intent(BikeActivity.this, SingleMessageActivity.class);
				i.putExtra(Message.SERIALIZABLE_NAME, (Message) msg);
				i.putExtra(Bike.SERIALIZABLE_NAME, b);
				
				startActivity(i);
			}
		});
		

		// assign adapter and listener to the third tab (service)
		listView = (ListView) findViewById(R.id.tab_ser_listview);
		listView.setAdapter(adapterServiceMsg);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				ServiceMessage msg = (ServiceMessage) parent
						.getItemAtPosition(position);
				
				// show single message screen - start new activity
				Intent i = new Intent(BikeActivity.this, SingleMessageActivity.class);
				i.putExtra(Message.SERIALIZABLE_NAME, (Message) msg);
				i.putExtra(Bike.SERIALIZABLE_NAME, b);
				
				startActivity(i);
			}
		});
		
		
		
		

	}

	private class BikeMessagesAdapter <T extends Message> extends ArrayAdapter<T> {

		private ArrayList<T> messagesList;

		public BikeMessagesAdapter(Context context,
				int textViewResourceId, ArrayList<T> messagesList) {
			super(context, textViewResourceId, messagesList);
			this.messagesList = new ArrayList<T>();
			this.messagesList.addAll(messagesList);
		}

		/** that't to meet viewholder pattern that speeds up refreshing of layout*/
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
				convertView = vi.inflate(R.layout.bike_list_item, null);

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

			holder.messageDate.setText(msg.getCreatedAt());
			holder.messageText.setText(msg.getText());
			// prepare messages string
			return convertView;
		}
	}
}
