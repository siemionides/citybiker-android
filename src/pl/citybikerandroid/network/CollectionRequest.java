package pl.citybikerandroid.network;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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
	private String body;

	public CollectionRequest(Class<T> type, String uri) {
		super(type);
		this.type = type;
		this.uri = uri;
		this.body = "";
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
	
	public CollectionRequest<T> addBody(String body) {
		this.body = (body == null ? "" : body);
		;
		return this;
	}

	@Override
	public T loadDataFromNetwork() throws Exception {
		Uri.Builder uriBuilder = Uri.parse(Constants.HOST_PORT + uri)
				.buildUpon();

		if (filter != null) uriBuilder.appendQueryParameter("filter", filter);
		if (sort != null) uriBuilder.appendQueryParameter("sort", sort);
		if (limit != null) uriBuilder.appendQueryParameter("limit", limit);
		if (fields != null) uriBuilder.appendQueryParameter("fields", fields);


		String url = uriBuilder.build().toString();
		
		HttpEntity<String> request = new HttpEntity<String>(""); // no body for test purposes
		ResponseEntity<T> response = getRestTemplate().getForEntity(url, type); //exchange(url, HttpMethod.GET, request, type);
		return response.getBody();
	}

	public String createCacheKey() {
		return uri;
	}

}
