package pl.citybikerandroid.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Just a stupid class containg some instant methods that help me
 * with developing Android APPS
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class HelperToolkit {

	public static AlertDialog.Builder createAlertDialog(Context cx, 
			String title, String message ){
		return new AlertDialog.Builder(cx)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     });
	}
}
