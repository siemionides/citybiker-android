package pl.citybikerandroid.network;

import java.net.URI;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import pl.citybikerandroid.domain.Location;
import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class LocationRequest implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private final static double ORANGE_LOCATION_WEIGHT = 0.1;

	public interface LocationRequestListener {
		abstract public void onLocationResult(double[] loc, LocationRequest request);
	}

	LocationRequestListener listener;

	Activity activity;
	LocationClient mLocationClient;
	private SpiceManager contentManager = new SpiceManager(
			JacksonSpringAndroidSpiceService.class);
	double[] loc;

	public LocationRequest(Activity activity, LocationRequestListener listener) {
		this.activity = activity;
		mLocationClient = new LocationClient(activity.getApplicationContext(),
				this, this);
		mLocationClient.connect();
		contentManager.start(activity);
		loc = new double[2];
		this.listener = listener;
	}

	public void stop() {
		mLocationClient.disconnect();
		contentManager.shouldStop();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		;
		try {
			returnNoResults();
			connectionResult.startResolutionForResult(activity,
					CONNECTION_FAILURE_RESOLUTION_REQUEST);
		} catch (SendIntentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		android.location.Location mCurrentLocation;
		mCurrentLocation = mLocationClient.getLastLocation();
		loc[0] = mCurrentLocation.getLongitude();
		loc[1] = mCurrentLocation.getLatitude();
		performLocationRequest();
	}

	@Override
	public void onDisconnected() {
		Log.e("Location Updates", "Client disconnected.");
	}

	private void performLocationRequest() {
		LocationNetworkRequest lnr = new LocationNetworkRequest(Location.class);
		contentManager.execute(lnr, "locationAPI", DurationInMillis.ONE_MINUTE,
				new LocationNetworkRequestListener());
	}

	private class LocationNetworkRequest extends
			SpringAndroidSpiceRequest<Location> {

		public LocationNetworkRequest(Class<Location> clazz) {
			super(clazz);
		}

		@Override
		public Location loadDataFromNetwork() throws Exception {

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}
			} };
			try {
				SSLContext context = SSLContext.getInstance("SSL");
				context.init(null, trustAllCerts, null);
				SSLContext.setDefault(context);
				HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
			} catch (Exception e) {
				e.printStackTrace();
			}

			Uri.Builder uriBuilder = Uri
					.parse("https://openmiddleware.pl:443/rest/terminal_location/location")
					.buildUpon();

			String queryJSON = "{\"acceptableAccuracy\":10,\"address\":\"tel:48505774763\",\"requestedAccuracy\":1,\"tolerance\":\"LowDelay\"}";

			uriBuilder.appendQueryParameter("query", queryJSON);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			headers.set("Authorization", "Basic aGFja2F0aG9uNTk6eTQ5WXFQN20=");

			URI url = new URI(uriBuilder.build().toString());
			ResponseEntity<Location> response = getRestTemplate().exchange(url,
					HttpMethod.GET, new HttpEntity<String>(headers),
					Location.class);
			return response.getBody();
		}
	}

	private class LocationNetworkRequestListener implements
			RequestListener<Location> {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			returnNoResults();
		}

		@Override
		public void onRequestSuccess(Location location) {
			loc[0] = (1-ORANGE_LOCATION_WEIGHT)*loc[0] + ORANGE_LOCATION_WEIGHT*location.getResult().getLongitude();
			loc[1] = (1-ORANGE_LOCATION_WEIGHT)*loc[1] + ORANGE_LOCATION_WEIGHT*location.getResult().getLatitude();
			returnResults();
		}

	}

	private void returnNoResults() {
		listener.onLocationResult(new double[] {}, this);
	}
	
	private void returnResults() {
		listener.onLocationResult(loc, this);
	}

	public boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(activity.getApplicationContext());
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			Log.e("Location Updates", "Google Play services is unavailable.");
			return false;
		}
	}

}
