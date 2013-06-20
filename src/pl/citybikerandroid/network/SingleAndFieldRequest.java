package pl.citybikerandroid.network;

import pl.citybikerandroid.Constants;
import android.net.Uri;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class SingleAndFieldRequest<T> extends SpringAndroidSpiceRequest<T> {

	private final Class<T> type;
	private String uri;

	public SingleAndFieldRequest(Class<T> type, String uri, String id,
			String field) {
		super(type);
		this.type = type;
		this.uri = uri 
				+ (id == null || id.trim().isEmpty() ? "/" : "/" + id)
				+ (field == null || field.trim().isEmpty() ? "" : "/" + field);
	}

	@Override
	public T loadDataFromNetwork() throws Exception {
		Uri.Builder uriBuilder = Uri.parse(
				Constants.HOST_PORT + uri).buildUpon();

		String url = uriBuilder.build().toString();

		return getRestTemplate().getForObject(url, type);
	}

	public String createCacheKey() {
		return uri;
	}

}
