package pl.citybikerandroid.activities;

import pl.citybikerandroid.R;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

/** Since we rely on SearchView wighet, it has to be separated activity */
public class SearchableStationActivity extends ListActivity {
	
	private ArrayAdapter<String> adapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   // setContentView(R.layout.s);
	    Log.d("debug", "SearchableStationActivity onCreate!");

	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	  
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      Log.d("debug", "query:" + query);
	      doMySearch(query);
	    }
	}

	private void doMySearch(String query) {
		String[] values = new String[]{"first returned", "second returned"};
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
	}
}
