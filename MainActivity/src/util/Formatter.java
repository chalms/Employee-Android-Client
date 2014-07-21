package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {
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

	public static Date parseDateTime(String string) {
		// TODO Auto-generated method stub
		return getDateFromString(string);
	}
}
