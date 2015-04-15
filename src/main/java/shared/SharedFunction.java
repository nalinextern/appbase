package shared;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import events.DatePickerSelectEventArgs;
import events.DatePickerSelectNotificationListener;
import events.TimePickerSelectEventArgs;
import events.TimePickerSelectNotificationListener;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class SharedFunction {
	
	public static SharedFunction sharedFunction = null;
	public static DatePickerSelectNotificationListener d;
	public static TimePickerSelectNotificationListener t;
	SelectDateFragment selectDateFragment = null;	
	SelectTimeFragment selectTimeFragment = null;
	MessageDialogFragment messageDialog = null;
	
	public static SharedFunction getSharedFunctionInstant(){
		
		if(sharedFunction == null){
			
			sharedFunction = new SharedFunction();
		}
		return sharedFunction;
	}

	public String getDateTime(String dateFormatType) {
		
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		 SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatType, Locale.getDefault());
		 Date date = new Date();
		 return dateFormat.format(date);
   }

	public void showToast(String display){
		
		Toast.makeText(BaseFlyContext.getInstant().getApplicationContext(), display, Toast.LENGTH_SHORT).show();
	}
	
	public void setDate(FragmentManager fm,DatePickerSelectNotificationListener d1){
		
		if(selectDateFragment == null){
			
			selectDateFragment = new SelectDateFragment();
		}
		d = d1; 
		selectDateFragment.show(fm, "DatePicker");
	}
	
	public void setTime(FragmentManager fm,TimePickerSelectNotificationListener t1){
		
		if(selectDateFragment == null){
			
			selectTimeFragment = new SelectTimeFragment();
		}
		t = t1; 
		selectTimeFragment.show(fm, "TimePicker");
	}
	public void cretateMessageDialog(FragmentManager manager,String message){
		
		if (messageDialog == null) {

			messageDialog = new MessageDialogFragment();
		}
		messageDialog.setMessageString(message);
		messageDialog.show(manager, "TimePicker");
	}

	public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    	}
    	
    	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
    		d.onDatePickerSelectChange(new DatePickerSelectEventArgs(this,view,yy, mm+1, dd));
    	}
    }

	
	public class SelectTimeFragment extends DialogFragment implements  TimePickerDialog.OnTimeSetListener {

	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current time as the default values for the picker
	        final Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);

	        // Create a new instance of TimePickerDialog and return it
	        return new TimePickerDialog(getActivity(), this, hour, minute,
	                !DateFormat.is24HourFormat(BaseFlyContext.getInstant().activity));
	    }

	    
	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	       
	    	t.onTimePickerSelectChange(new TimePickerSelectEventArgs(this, view, hourOfDay, minute, ""));
	    }
    }
	
	public class MessageDialogFragment extends DialogFragment {
		
		String message = "";
		
		public void setMessageString(String msg){
			
			message = msg;
		}
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(message)
	               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                   }
	               })
	               .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}
	
	public File getDir(String dirPath){
		
		File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
	            + "/Android/data/"
	            + BaseFlyContext.getInstant().getApplicationContext().getPackageName()
	            + "/Files/"+dirPath); 
		
		  if (! mediaStorageDir.exists()){
		        if (! mediaStorageDir.mkdirs()){
		           
		        }
		    } 
		
		return mediaStorageDir;
	}
	public String getMD5EncodString( String[] valueArray){
		
		String encodeString = null;
		StringBuilder postData = new StringBuilder(); 
		MessageDigest md = null;
		byte[] source = null ;
		 
			 try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			 
			 for(int i = 0; i < valueArray.length ; i++){
				 
				 postData.append(valueArray[i]);
			 }
			 
			 try {
				
				source = postData.toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		encodeString = Base64.encodeToString(md.digest(source), Base64.DEFAULT);
		 
		return encodeString;
	}
	
	public Bitmap getResizedBitmap(Bitmap bm,int newHeight,int newWidth) {
		 
		int width = bm.getWidth();
		int height = bm.getHeight();		 
		float scaleWidth = ((float) newWidth) / width;		 
		float scaleHeight = ((float) newHeight) / height;		  
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight); 
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
		 
	}
}
















