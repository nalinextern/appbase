package events;

import java.util.EventObject;

import android.widget.TimePicker;

@SuppressWarnings("serial")
public class TimePickerSelectEventArgs extends EventObject {

	public TimePicker pickerView;
	public int hr;
	public int mn;
	public String amOrPm;
	
	public TimePickerSelectEventArgs(Object source,TimePicker view, int h, int m, String d) {
		super(source);


		pickerView = view;
		hr = h;
		mn = m;
		amOrPm = d;
	}


}
