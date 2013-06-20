package pl.citybikerandroid.network;

import pl.citybikerandroid.Constants;
import android.net.Uri;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class CollectionRequest<T> extends SpringAndroidSpiceRequest<T> {

	private final Class<T> type;
	private String uri;
	private String filter;
	private String sort;
	private String limit;
	private String fields;

	public CollectionRequest(Class<T> type, String uri) {
		super(type);
		this.type = type;
		this.uri = uri;
		this.filter = "";
		this.sort = "";
		this.limit = "";
		this.fields = "";
	}

	public CollectionRequest<T> addFilter(String filter) {
		this.filter = (filter == null ? "" : filter);
		return this;
	}

	public CollectionRequest<T> addSort(String sort) {
		this.sort = (sort == null ? "" : sort);
		return this;
	}

	public CollectionRequest<T> addLimit(String limit) {
		this.limit = (limit == null ? "" : limit);
		return this;
	}

	public CollectionRequest<T> addFields(String fields) {
		this.fields = (fields == null ? "" : fields);
		;
		return this;
	}

	@Override
	public T loadDataFromNetwork() throws Exception {
		Uri.Builder uriBuilder = Uri.parse(Constants.HOST_PORT + uri)
				.buildUpon();

		uriBuilder.appendQueryParameter("filter", filter);
		uriBuilder.appendQueryParameter("sort", sort);
		uriBuilder.appendQueryParameter("limit", limit);
		uriBuilder.appendQueryParameter("fields", fields);

		String url = uriBuilder.build().toString();
		return getRestTemplate().getForObject(url, type);
	}

	public String createCacheKey() {
		return uri;
	}

}
