package pl.citybikerandroid;

import java.util.ArrayList;

import pl.citybikerandroid.helper.HelperToolkit;
import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.ServiceMessage;
import pl.citybikerandroid.stations.BikeStation;
import pl.citybikerandroid.stations.BikeStationAround;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	
	StationsAroundAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		displayListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
			case R.id.menu_search_bike: {
				AlertDialog.Builder alert = HelperToolkit.createAlertDialog(this,
						"searchBikeAlert", "searchBike button preessed - to be implemented!");
				alert.show();
				break;
			}
			case R.id.menu_search_station:{
				AlertDialog.Builder alert = HelperToolkit.createAlertDialog(this,
						"searchStationAlert", "searchStation button preessed - to be implemented!");
				alert.show();
				break;
			}
		}
		
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	/** Injects the listview with sample fake data*/
	 private void displayListView() {
		 
		  //Array list of countries
		  ArrayList<BikeStationAround> stationAroundList = new ArrayList<BikeStationAround>();
		  
		  BikeStationAround bs = new BikeStationAround("ul Warynskiego - ul. Nowowiejska",6364,BikeStation.MORE_THAN_FOUR);
		  	bs.addInformativeMessage(new InformativeMessage("Bardzo piękny dzień, jest super!"));
		  	bs.addLogisticalMessage(new LogisticalMessage("Będę za 15 minut na 6364", LogisticalMessage.GOING_TO,
		  			new BikeStation(), bs, 3432));
		  	bs.addServiceMessage(new ServiceMessage("Dzwonek nie działa!"));
		  	bs.setDistanceTo(0.2);
		  	stationAroundList.add(bs);
		  
		  	bs = new BikeStationAround("Plac Zbawiciela - Nowowiejska",6376,BikeStation.MORE_THAN_FOUR);
		  	bs.addInformativeMessage(new InformativeMessage(" jest super, dobry dzień!"));
		  	bs.addLogisticalMessage(new LogisticalMessage("Będę za 23 minut na 6376", LogisticalMessage.GOING_TO,
		  			new BikeStation(), bs, 343));
		  	bs.addServiceMessage(new ServiceMessage("Hamulec nie działa!"));
		  	bs.setDistanceTo(0.5);
		  	stationAroundList.add(bs);
		  	
		  	bs = new BikeStationAround("Plac Politechniki - ul. Nowowiejska",63642,3);
		  	bs.addInformativeMessage(new InformativeMessage("Bardzo super dzień, jest super!"));
		  	bs.addLogisticalMessage(new LogisticalMessage("Zajęło mi to naście minut", LogisticalMessage.TIME_BETWEEN,
		  			new BikeStation(), bs, 453));
		  	bs.addServiceMessage(new ServiceMessage("Światło nie działa! nie działa!"));
		  	bs.setDistanceTo(0.8);
		  	stationAroundList.add(bs);
		  	
		 
		  //create an ArrayAdaptar from the String Array
		  adapter = new StationsAroundAdapter(this,
		    R.layout.list_item_station_around, stationAroundList);
		  ListView listView = (ListView) findViewById(R.id.listStationsAroundView);
		  // Assign adapter to ListView
		  listView.setAdapter(adapter);
		 
		 
		  listView.setOnItemClickListener(new OnItemClickListener() {
		   public void onItemClick(AdapterView<?> parent, View view,
		     int position, long id) {
		    // When clicked, show a toast with the TextView text
		    BikeStationAround station = (BikeStationAround) parent.getItemAtPosition(position);
		    Toast.makeText(getApplicationContext(),
		      "Clicked on Row: " + station.getName(), 
		      Toast.LENGTH_LONG).show();
		   }
		  });
		 
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
//		   CheckBox name;
		  }
		 
		  /** */
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		 
		   ViewHolder holder = null;
		   Log.v("ConvertView", String.valueOf(position));
		 
		   if (convertView == null) {
			   LayoutInflater vi = (LayoutInflater)getSystemService(
			     Context.LAYOUT_INFLATER_SERVICE);
			   convertView = vi.inflate(R.layout.list_item_station_around, null);
			 
			   holder = new ViewHolder();
			   holder.stationName = (TextView) convertView.findViewById(R.id.tv_station_name);
			   holder.stationInfo = (TextView) convertView.findViewById(R.id.tv_station_info);
			   holder.stationMessages = (TextView) convertView.findViewById(R.id.tv_station_messages);
			   holder.distanceTo = (TextView) convertView.findViewById(R.id.tv_station_distance);
			   convertView.setTag(holder);
			 
		   } 
		   else {
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
			int stationId = station.getId();
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
		
		private String formatDistanceToText(BikeStationAround station){
			return station.getDistanceTo().toString() + " km";
		}
		  
		  
		  
		  
		 
		 }

}
