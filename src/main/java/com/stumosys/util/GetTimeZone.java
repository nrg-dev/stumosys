package com.stumosys.util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GetTimeZone {

	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getIndoTime(String timezone)
	{
		TimeZone jakartaZone1 = TimeZone.getTimeZone(timezone);
		sdf.setTimeZone(jakartaZone1);
		System.out.println("hour     = " + sdf.format(new Date()));//.get(Calendar.HOUR_OF_DAY));
		return String.valueOf(sdf.format(new Date()));
	}
	
	public static String getIndonesiaTimeHour(String timezone)
	{
		TimeZone jakartaZone1 = TimeZone.getTimeZone(timezone);
		sdf.setTimeZone(jakartaZone1);
		System.out.println("Lombak time--------->" + sdf.format(new Date()));		
		return String.valueOf(sdf.format(new Date()));
	}
	
	public static Timestamp getLombakTime(String timezone)
	{
		try {
		TimeZone jakartaZone1 = TimeZone.getTimeZone(timezone);
		sdf.setTimeZone(jakartaZone1);
		System.out.println("getLombakTime-Lombak time--------->" + sdf.format(new Date()));		
		return Timestamp.valueOf(sdf.format(new Date()));
		}catch(Exception e) {
			System.out.println("Exception -->"+e.getMessage());
			return Timestamp.valueOf(sdf.format(new Date()));
		}
		finally {
			
		}
		
	}
	
	
	public static String getTimeHour(String timezone)
	{
		TimeZone jakartaZone1 = TimeZone.getTimeZone(timezone);
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(jakartaZone1);
		System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}

	public static String getTimeMin(String timezone)
	{
		TimeZone jakartaZone1 = TimeZone.getTimeZone(timezone);
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(jakartaZone1);
		System.out.println("Minits     = " + calendar.get(Calendar.MINUTE));
		return String.valueOf(calendar.get(Calendar.MINUTE));
	}
	
	public static Date getDate(String timezone)
	{
		  Date date=new Date();
		  return date;
	}
	
}	/*public static void main(String args[]) {
		try {
			
			//TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
			
			TimeZone jakartaZone1 = TimeZone.getTimeZone("Asia/Jakarta");
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeZone(jakartaZone1);
			logger.debug("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
			logger.debug("Minits     = " + calendar.get(Calendar.MINUTE));

			
			//TimeZone timeZone2 = TimeZone.getTimeZone("Europe/Copenhagen");

			

			//long timeCPH = calendar.getTimeInMillis();
			////logger.debug("timeCPH  = " + timeCPH);
			//logger.debug("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));

			//calendar.setTimeZone(timeZone1);
			

			

			//long timeLA = calendar.getTimeInMillis();
			//logger.debug("timeLA   = " + timeLA);
			

		}catch(Exception e) {
			e.getMessage();
		}
	}*/

