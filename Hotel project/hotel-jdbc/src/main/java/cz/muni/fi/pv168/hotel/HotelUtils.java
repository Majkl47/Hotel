package cz.muni.fi.pv168.hotel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotelUtils {
	
    final static Logger log = LoggerFactory.getLogger(HotelUtils.class);

    public static String convertDateToString(Date date)
    {
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String formattedDate = formatter.format(date);
    	return formattedDate;
    }
    
    public static Date convertStringToDate(String dateString) {
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date myDate = null;
	try {
            myDate = formatter.parse(dateString);
	} catch (ParseException e) {
            log.error("Failed to parse string to date", e);
        }
        return myDate;
    }
}
