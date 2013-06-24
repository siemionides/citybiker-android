package pl.citybikerandroid.network;

import android.content.Context;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public abstract class SingleAndFieldRequestListener<T> implements RequestListener<T> {

	private Context mContext;
	
	public SingleAndFieldRequestListener(Context context) {
		this.mContext = context;
	}
	
		@Override
		public void onRequestSuccess(T object) {

			if (object == null) {
				Toast.makeText(mContext,
						"Server claims there is no requested object!",
						Toast.LENGTH_LONG).show();
			}

			performOnSuccess(object, mContext);
		}
		
		abstract public void performOnSuccess(T object, Context context);
		
		@Override
		public void onRequestFailure(SpiceException e) {
			Toast.makeText(mContext,
					"Error during request: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}