package org.pine.common.util;

import java.net.URL;
import java.net.URLConnection;
/** 
* @author  xier: 
* @date 创建时间：2016年10月12日 下午5:23:35 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static final String DATE_YEAR_STR = "yyyy";
	public static final String DATE_JFP_STR = "yyyyMM";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";

	public static Date parse(String strDate) {
		return parse(strDate, DATE_FULL_STR);
	}

	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			if (pattern != DATE_SMALL_STR)
				return parse(strDate, DATE_SMALL_STR);
			return null;
		}
	}

	public static int compareDateWithNow(Date date1) {
		Date date2 = new Date();
		int rnum = date1.compareTo(date2);
		return rnum;
	}

	public static int compareDateWithNow(long date1) {
		long date2 = dateToUnixTimestamp();
		if (date1 > date2) {
			return 1;
		} else if (date1 < date2) {
			return -1;
		} else {
			return 0;
		}
	}

	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(new Date());
	}

	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	public static String getFullTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(date);
	}

	public static String getNetworkTime(String webUrl, String type) {
		if (type == null || type.isEmpty()) {
			type = DATE_FULL_STR;
		}
		if (webUrl == null || webUrl.isEmpty()) {
			webUrl = "http://www.baidu.com";
		}
		URL url;
		try {
			url = new URL(webUrl);
			URLConnection conn = url.openConnection();
			conn.connect();
			long dateL = conn.getDate();
			Date date = new Date(dateL);
			SimpleDateFormat dateFormat = new SimpleDateFormat(type);
			return dateFormat.format(date);
		} catch (Exception e) {
			return getNowTime(type);
		}

	}

	public static String getJFPTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
		return df.format(new Date());
	}

	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static int getMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	public static String unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}

	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000));
		return days;
	}
}
