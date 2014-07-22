package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Formatter {
	static int i = 1; 
	public static int getGlobalId() {
		i = i + 1; 
		return i; 
	}
	public static Date getDateFromString(String s) {
		try {
				SimpleDateFormat formatter = null;
				if (s.contains("-")) {				 //Will need to change this
					formatter = new SimpleDateFormat("dd-MMM-yy", Locale.CANADA);
				} else {
					formatter = new SimpleDateFormat("HH:mm", Locale.CANADA);
				}
				Date d = (Date) formatter.parse(s);
				return d; 
			} catch (ParseException ex) {
				return null; 
			}

	}
	
	public static Date getDate(String dateString) {
		Date reportDate = new Date(); 
		if (dateString != null) {
			reportDate = Formatter.getDateFromString(dateString);
			if (Date.class.isInstance(reportDate)) {
				return reportDate; 
			}
		}
		return null; 
	}

	public static Date parseDateTime(String string) {
		// TODO Auto-generated method stub
		return getDateFromString(string);
	}
	
	public Date currentDate() {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date());
		return cal.getTime(); 
	}
}
