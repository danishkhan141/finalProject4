package in.co.rays.project_4.util;

import in.co.rays.project_4.util.DataValidator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data Utility class to format data from one format to another
 *
 * @author Danish Khan
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class DataUtility {

	/**
	 * Application Date Format
	 */
	public static final String APP_DATE_FORMAT = "dd/MM/yyyy";

	public static final String APP_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static final String APP_DATE_FORMAT_SEARCH = "yyyy-mm-dd";

	/**
	 * Date formatter
	 */
	private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	private static final SimpleDateFormat searchformatter = new SimpleDateFormat(APP_DATE_FORMAT_SEARCH);

	/**
	 * Trims and trailing and leading spaces of a String
	 *
	 * @param val
	 * @return
	 */
	public static String getString(String val) {
		System.out.println("DataUtility.getString(String) line 44.....");
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	/**
	 * Converts and Object to String
	 *
	 * @param val
	 * @return
	 */
	public static String getStringData(Object val) {
		System.out.println("DataUtility.getStringData(Object) line 59.....");
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	/**
	 * Converts String into Integer
	 *
	 * @param val
	 * @return
	 */

	public static int getInt(String val) {
		System.out.println("DataUtility.getInt(String) line 75.....");
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	/**
	 * Converts String into Long
	 *
	 * @param val
	 * @return
	 */
	public static long getLong(String val) {
		System.out.println("DataUtility.getLong() line 90.....");
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

	/**
	 * Converts String into Date
	 *
	 * @param val
	 * @return
	 */
	public static Date getDate(String val) {
		System.out.println("DataUtility.getDate() line 105.....");
		Date date = null;
		try {
			date = formatter.parse(val);	
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * Converts Date into String
	 *
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		System.out.println("DataUtility.getDateString(Date) line 122.....");
		try {
			return formatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	public static String getDateStringSearch(Date date) {
		System.out.println("DataUtility.getDatesearchString(Date) line 131.....");
		try {
			return searchformatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	public static Date getDateSearch(String val) {
		System.out.println("DataUtility.getDateSearch(String) line 140.....");
		Date date = null;
		try {
			date = searchformatter.parse(val);
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * Gets date after n number of days
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDate(Date date, int day) {
		System.out.println("DataUtility.getDate(Date, int) line 158.....");
		return null;
	}

	/**
	 * Converts String into Time
	 *
	 * @param val
	 * @return
	 */
	public static Timestamp getTimestamp(String val) {
		System.out.println("DataUtility.getTimeStamp(String) line 169.....");
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	public static Timestamp getTimestamp(long l) {
		System.out.println("DataUtility.getTimestamp(long) line 171.....");
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	public static Timestamp getCurrentTimestamp() {
		System.out.println("DataUtility.getCurrentTimestamp() line 182.....");
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
		}
		return timeStamp;
	}

	public static long getTimestamp(Timestamp tm) {
		System.out.println("DataUtility.getTimestamp(Timestamp) line 191.....");
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println("DataUtility.main(String[]) line 199.....");
	}

}
