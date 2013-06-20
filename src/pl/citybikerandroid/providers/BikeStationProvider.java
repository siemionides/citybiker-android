package pl.citybikerandroid.providers;


import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class BikeStationProvider extends ContentProvider {
	String TAG = "BikeStationProvider";
	

    public static String AUTHORITY = "pl.citybikerandroid.providers.BikeStationProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/bikestation");
    
    
    // MIME types used for searching words or looking up a single definition
    public static final String STATION_NAME_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                                                  "/vnd.citybiker.android.searchablebikestations";
    public static final String STATION_ID_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                                                       "/vnd.citybiker.android.searchablebikestations";
	
    private BikeStationDatabase mBikeStationDatabase;
    
    
    // UriMatcher stuff
    private static final int SEARCH_WORDS = 0;
    private static final int GET_WORD = 1;
    private static final int SEARCH_SUGGEST = 2;
    private static final int REFRESH_SHORTCUT = 3;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    /**
     * Builds up a UriMatcher for search suggestion and shortcut refresh queries.
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
        // to get definitions...
        matcher.addURI(AUTHORITY, "bikestations", SEARCH_WORDS);
        matcher.addURI(AUTHORITY, "bikestations/#", GET_WORD);
        // to get suggestions...
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);

        /* The following are unused in this implementation, but if we include
         * {@link SearchManager#SUGGEST_COLUMN_SHORTCUT_ID} as a column in our suggestions table, we
         * could expect to receive refresh queries when a shortcutted suggestion is displayed in
         * Quick Search Box, in which case, the following Uris would be provided and we
         * would return a cursor with a single item representing the refreshed suggestion data.
         */
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT, REFRESH_SHORTCUT);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", REFRESH_SHORTCUT);
        return matcher;
    }
    
    
    
    
    
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		 throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		   switch (sURIMatcher.match(uri)) {
           case SEARCH_WORDS:
               return STATION_NAME_MIME_TYPE;
           case GET_WORD:
               return STATION_ID_MIME_TYPE;
           case SEARCH_SUGGEST:
               return SearchManager.SUGGEST_MIME_TYPE;
           case REFRESH_SHORTCUT:
               return SearchManager.SHORTCUT_MIME_TYPE;
           default:
               throw new IllegalArgumentException("Unknown URL " + uri);
       }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		 mBikeStationDatabase = new BikeStationDatabase(getContext());
	        return true;
	}

	 /**
     * Handles all the dictionary searches and suggestion queries from the Search Manager.
     * When requesting a specific word, the uri alone is required.
     * When searching all of the dictionary for matches, the selectionArgs argument must carry
     * the search query as the first element.
     * All other arguments are ignored.
     */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		 // Use the UriMatcher to see what kind of query we have and format the db query accordingly
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                if (selectionArgs == null) {
                  throw new IllegalArgumentException(
                      "selectionArgs must be provided for the Uri: " + uri);
                }
                return getSuggestions(selectionArgs[0]);
            case SEARCH_WORDS:
                if (selectionArgs == null) {
                  throw new IllegalArgumentException(
                      "selectionArgs must be provided for the Uri: " + uri);
                }
                return search(selectionArgs[0]);
            case GET_WORD:
                return getWord(uri);
            case REFRESH_SHORTCUT:
                return refreshShortcut(uri);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
	}
	
	  private Cursor getSuggestions(String query) {
	      query = query.toLowerCase();
	      String[] columns = new String[] {
	          BaseColumns._ID,
	          BikeStationDatabase.KEY_STATION_NAME,
	          BikeStationDatabase.KEY_STATION_ID,
	       /* SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
	                        (only if you want to refresh shortcuts) */
	          SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID};

	      return mBikeStationDatabase.getWordMatches(query, columns);
	    }
	  
	  
	private Cursor search(String query) {
		query = query.toLowerCase();
		String[] columns = new String[] { BaseColumns._ID,
				BikeStationDatabase.KEY_STATION_NAME,
				BikeStationDatabase.KEY_STATION_ID, };

		return mBikeStationDatabase.getWordMatches(query, columns);
	}

	private Cursor getWord(Uri uri) {
		String rowId = uri.getLastPathSegment();
		String[] columns = new String[] { BikeStationDatabase.KEY_STATION_NAME,
				BikeStationDatabase.KEY_STATION_ID };

		return mBikeStationDatabase.getWord(rowId, columns);
	}
	
	
	private Cursor refreshShortcut(Uri uri) {
		/*
		 * This won't be called with the current implementation, but if we
		 * include {@link SearchManager#SUGGEST_COLUMN_SHORTCUT_ID} as a column
		 * in our suggestions table, we could expect to receive refresh queries
		 * when a shortcutted suggestion is displayed in Quick Search Box. In
		 * which case, this method will query the table for the specific word,
		 * using the given item Uri and provide all the columns originally
		 * provided with the suggestion query.
		 */
		String rowId = uri.getLastPathSegment();
		String[] columns = new String[] { BaseColumns._ID,
				BikeStationDatabase.KEY_STATION_NAME, BikeStationDatabase.KEY_STATION_ID ,
				SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };

		return mBikeStationDatabase.getWord(rowId, columns);
	}
	  
	
	
	  

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

}
