package events;

import java.util.EventObject;

import android.widget.DatePicker;

@SuppressWarnings("serial")
public class DatePickerSelectEventArgs extends EventObject{
	
	public DatePicker pickerView;
	public int year;
	public int month;
	public int date;

	public DatePickerSelectEventArgs(Object source,DatePicker view, int yy, int mm, int dd) {
		super(source);

		pickerView = view;
		year = yy;
		month = mm;
		date = dd;
	}

}
