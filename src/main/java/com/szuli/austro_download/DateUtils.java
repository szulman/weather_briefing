package com.szuli.austro_download;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
   
	
	public static String SIEBEL_DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static String SYSTEM_READABLE_DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
	public static String FILE_MANAGER_FORMAT = "yyyy-MM-dd HH-mm-ss";
	public static String HHMMSS_FORMAT = "HH:mm:ss";
	
	   
	public static String currentMillis2_HH_mm_ss(long millis) {
		SimpleDateFormat format = new SimpleDateFormat(HHMMSS_FORMAT);
		Date date = millisToDate(millis);
		return format.format(date);
	}
	
	
	public static String currentMillis2_FileManagerFormat(long millis) {
		SimpleDateFormat format = new SimpleDateFormat(FILE_MANAGER_FORMAT);
		Date date = millisToDate(millis);
		return format.format(date);
	}
	
	   
	public static String getSiebelTimeStamp() {
		return date2SiebelDateFormat(Calendar.getInstance().getTime());	
	}

	
	public static String getSiebelTimeStamp(String timeZone) {
		return date2SiebelDateFormat(Calendar.getInstance().getTime(), timeZone);	
	}

   
	public static String date2FriendlyDateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(SIEBEL_DATE_FORMAT);
		return format.format(date);
	}
	

	public static String date2SiebelDateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(SIEBEL_DATE_FORMAT);
		return format.format(date);
	}
	
	
	public static String date2SiebelDateFormat(Date date, String timeZone) {
		SimpleDateFormat format = new SimpleDateFormat(SIEBEL_DATE_FORMAT);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		return format.format(date);
	}
	
	
	public static Date siebelTimeStamp2Date(String siebelTimeStamp) {
		SimpleDateFormat format = new SimpleDateFormat(SIEBEL_DATE_FORMAT);
		try {
			return format.parse(siebelTimeStamp);
		} catch (ParseException e) {
			return null;
			//e.printStackTrace();
		}
		//return null;
	}
	
	
	public static long getTimeInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	
	public static Date millisToDate(long millis) {
		return new Date(millis);
	}
	
	
	public static String getSystemReadableTimeStamp() {
		return date2SystemReadableString(Calendar.getInstance().getTime());	
	}
   
	
	public static String date2SystemReadableString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(SYSTEM_READABLE_DATE_FORMAT);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		String timeStamp = format.format(date);
		return timeStamp;
	}
}
