package sms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateCheckUsingJoda {
	final static Logger logger = Logger.getLogger(DateCheckUsingJoda.class);
	public static void main(String[] args) throws ParseException {
		int diff=0;
		int finaedays=7;
		int fine_amont=100;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
		String dateInString = "29-JUN-2016";
		Date d=new Date();
		DateTime dt=new DateTime(d);
		logger.debug(dt);
	
		DateTime dt1=formatter.parseDateTime(dateInString);
		logger.debug(dt1);
		logger.debug(Days.daysBetween(dt.toLocalDate(), dt1.toLocalDate()).getDays());
		 diff=Days.daysBetween(dt.toLocalDate(), dt1.toLocalDate()).getDays();
		
		 if(diff == 0){
			 logger.debug("No fine");
			 fine_amont=0;
		 }else {
			 fine_amont*=diff;
		 }
		 
		logger.debug(fine_amont);
	}

}
