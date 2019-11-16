package sms;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public final class Test {
	final static Logger logger = Logger.getLogger(Test.class);
	 /*@Value("#{'${international.lang}'.split(',')}") 
	 private static List<String> myList;
	public static void main(String[] args) throws ParseException {
		List<String> a=new ArrayList<String>();
		a.add("xx");
		a.add("");
		a.add("xxx");
		a.add("xxx");
		a.removeAll(Collections.singleton(null));

		a.add("");
		a.add("xxx");
		logger.debug(a.size());
		a.removeAll(Collections.singleton(""));
		logger.debug(a.size());
		logger.debug(a.size());
		Set<String> dublicate=new HashSet<String>(a);
		String a1=dublicate.toString();
		logger.debug(a1);
		LocalDate date = LocalDate.now();
		//Using Joda
		Date date = new Date();
		DateTime d=new DateTime(date);
		String nameOfWeekday = d.dayOfWeek().getAsText(Locale.ENGLISH);
		logger.debug(nameOfWeekday);
		logger.debug(myList);
		
		Date date = new Date();
		logger.debug("Date: " + date);

		DateFormat df = new SimpleDateFormat("hh:'00' a");//Am or PM
		DateFormat df = new SimpleDateFormat("hh:mm");
		String hour = df.format(date);
		logger.debug("Hour: " + hour);
		
		

		// August 12, 2010
		String oldDateString = "22-01-2015 13:20:56";
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(oldDateString);
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);
		logger.debug(newDateString);
		Date ssss=sdf.parse(newDateString);
		logger.debug(ssss);
		java.sql.Date sql = new java.sql.Date(ssss.getTime());
		logger.debug(sql);
		Date dsssss=sql;
		logger.debug(dsssss);
		String dateStr = "Jul 27, 2011 20:35:29";
		DateFormat readFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss");
		DateFormat writeFormat = new SimpleDateFormat("HH:mm");
		Date date2 = null;
		try {
		    date2 = readFormat.parse(dateStr);
		    logger.debug("--"+date2);
		} catch (ParseException e) {
		}

		if (date2 != null) {
		    String formattedDate = writeFormat.format(date2);
		    logger.debug("-1-"+formattedDate);
		}
    }*/
		
	public static void main(String... args) throws Exception{
		GoogleAPI.setHttpReferrer("http://code.google.com/p/google-api-translate-java/");

		
		String text = Translate.execute("date", Language.ENGLISH, Language.INDONESIAN);
		System.out.println(text); // 
	}
	
	}


