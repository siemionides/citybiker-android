package pl.citybikerandroid.activities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpEntity;

import pl.citybikerandroid.Constants;
import pl.citybikerandroid.R;
import pl.citybikerandroid.domain.Message;
import pl.citybikerandroid.domain.Station;
import pl.citybikerandroid.helper.HelperToolkit;
import pl.citybikerandroid.network.SingleAndFieldRequestListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

/**
 * New Mesage for Bike.
 * TODO: obiously it shares tremendous amount of code with {@link NewMessageBikeActivity};
 * Therefore it should be refactored ASAP.
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class NewMessageStationActivity extends Activity {

	private String mCurrentPhotoPath = "";
	
	private CheckBox checkBox_photo;
	
	private ImageView imageView_photo;
	
	private Message newMessage = new Message();
	
	private SpiceManager contentManager = new SpiceManager(
			JacksonSpringAndroidSpiceService.class);
	
	@Override
	protected void onStart() {
		super.onStart();
		contentManager.start(this);
	}

	@Override
	protected void onStop() {
		contentManager.shouldStop();
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_message_station_activity);
		
		
		//handle intent.
		//@+id/tv_new_single_message_bike
		Intent i = getIntent();
		Station s = (Station) i.getSerializableExtra(Station.SERIALIZABLE_NAME);
		
		//inflate textView with StationID
		TextView tv_station_id = (TextView)findViewById(R.id.tv_new_message_station_id);
		if (s == null){
			tv_station_id.setText("665");
		}else{
			tv_station_id.setText(s.getNumber());
		}
		
		// set button listeners
		Button but_photo = (Button) findViewById(R.id.button_new_message_station_attach_photo);
		but_photo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				takePhoto();
			}
		});

		Button but_send = (Button) findViewById(R.id.button_new_message_station_send_message);
		but_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});
		
		
		//add views to instance variables
		this.checkBox_photo = (CheckBox) findViewById(R.id.new_message_station_checkbox_photo);
		this.imageView_photo = (ImageView) findViewById(R.id.new_message_station_imageView);
		
	
	}
	
	private void sendMessage() {
		prepareMessage();
		performRequest();
	}

	private void prepareMessage() {
		TextView stationid = (TextView)findViewById(R.id.tv_new_message_station_id);
		EditText text = (EditText)findViewById(R.id.new_message_station_message_edit_text);
		RadioGroup rgType = (RadioGroup)findViewById(R.id.new_message_station_message_type_radiogroup);
		int typeIdx = rgType.indexOfChild(rgType.findViewById(rgType.getCheckedRadioButtonId()));
		RadioGroup rgSubtype = (RadioGroup)findViewById(R.id.new_message_station_message_subtype_radiogroup);
		int subtypeIdx = rgSubtype.indexOfChild(rgSubtype.findViewById(rgSubtype.getCheckedRadioButtonId()));
		CheckBox cb = (CheckBox)findViewById(R.id.new_message_station_checkbox_photo);
		if (cb.isChecked()) {
			File file = new File(mCurrentPhotoPath);
		    int size = (int) file.length();
		    byte[] photo = new byte[size];
		    try {
		        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
		        buf.read(photo, 0, photo.length);
				newMessage.setPhoto(photo);
				buf.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}	 
		newMessage.setStationId(stationid.getText().toString());
		newMessage.setText(text.getText().toString());
		newMessage.setType(Message.Types.values()[typeIdx].name());
		newMessage.setSubtype(Message.Types.values()[subtypeIdx].name());
	}
	
	private void performRequest() {
		SpringAndroidSpiceRequest<String> request = new SpringAndroidSpiceRequest<String>(String.class) {
			@Override
			public String loadDataFromNetwork() throws Exception {
				Uri.Builder uriBuilder = Uri.parse(
						Constants.HOST_PORT + Constants.MESSAGES_URI).buildUpon();
				String url = uriBuilder.build().toString();
				HttpEntity<Message> request = new HttpEntity<Message>(newMessage, null); 
				return getRestTemplate().postForObject(url, request, String.class);
			}
		};
		contentManager.execute(request, new MessageSendListener(getApplicationContext()));
	}
	
	private class MessageSendListener extends SingleAndFieldRequestListener<String> {

		public MessageSendListener(Context context) {
			super(context);
		}

		@Override
		public void performOnSuccess(String object, Context context) {
			Toast.makeText(context,
					"Message now available under: " + object,
					Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/** method that takes photo*/
	private void takePhoto(){
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File f = null;
		try {
			f = createImageFile();
		} catch (IOException e) {
			e.printStackTrace();
			
			Log.e("error", e.getMessage());
		}
		
		if (f != null) {
			takePictureIntent
					.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		}

		startActivityForResult(takePictureIntent, NewMessageBikeActivity.ACTION_CODE_TAKE_PHOTO);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED){
			return;
		}
		
		switch(requestCode){
		case NewMessageBikeActivity.ACTION_CODE_TAKE_PHOTO:
			HelperToolkit.makeToast(this, "the picture is saved here: "
					+ this.mCurrentPhotoPath);

			// retrieve photo as Bitmap and inject it to ImageView here, add
			// also checkbox;
			// when sending, check if checkBox is checked and send it too
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		 
			int photoW = bmOptions.outWidth;
		    int photoH = bmOptions.outHeight;
		  
		    // Determine how much to scale down the image
		    int scaleFactor = Math.min(photoW/300, photoH/100);
		  
		    // Decode the image file into a Bitmap sized to fill the View
		    bmOptions.inJustDecodeBounds = false;
		    bmOptions.inSampleSize = scaleFactor;
		    bmOptions.inPurgeable = true;
		  
		    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		    this.imageView_photo.setImageBitmap(bitmap);
			
			if(bitmap == null){
				Log.d("error", "Bitmap IS NULL!");
			}
			
			this.checkBox_photo.setVisibility(CheckBox.VISIBLE);
			this.checkBox_photo.setChecked(true);
			
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	/** Creates a image Files, saves it's path to instance variable (mCurrentPhotoPath) 
	 * + returns a file to that file*/
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = 
	        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = NewMessageBikeActivity.JPEG_FILE_PREFIX + timeStamp + "_";
	    Log.d("debug", "album Dir:" + getAlbumDir().getAbsolutePath() );
	    Log.d("debug", "imageFileName:" + imageFileName);
	    File image = File.createTempFile(
	        imageFileName, 
	        NewMessageBikeActivity.JPEG_FILE_SUFFIX, 
	        getAlbumDir()
	    );
	    
	    
	    this.mCurrentPhotoPath = new String(image.getAbsolutePath());
	    Log.d("debug", "Saving mCurrentPhotoPath:" + this.mCurrentPhotoPath);
	    return image;
	}
	
	/** Creates (if not created yet) and returns CityBiker's photo album*/
	private File getAlbumDir(){
		//save the file
		File storageDir = new File(
			    Environment.getExternalStoragePublicDirectory(
			        Environment.DIRECTORY_PICTURES
			    ), 
			    NewMessageBikeActivity.ALBUM_NAME
			);    
		
		//ensure that folder exists
		storageDir.mkdirs();
		
		return storageDir;
	}
	
}
