package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
	private static Pattern timePattern;
	private static final String TIME_PATTERN = "^((0[1-9])|(1[0-2])|([0-9])):([0-5][0-9]) ((A)|(P))M$";
  private static SimpleDateFormat formatter =new SimpleDateFormat("MMMM dd, yyyy");
	private Map<String, Integer> timeIn24HourFormat = new HashMap<String, Integer>();
	static {
		timePattern = Pattern.compile(TIME_PATTERN, Pattern.CASE_INSENSITIVE);
	}

	public static boolean isValidTime(String time) {
		if(StringUtils.isBlank(time))
			return false;
		Matcher matcher = timePattern.matcher(time.trim());
		return matcher.matches();
	}

	public static boolean isValidTimeRange(String startTime, String endTime) {
	  if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime))
        return false;
	  String[] startParam = startTime.trim().split(" ");
	  String[] endParam = endTime.trim().split(" ");
	  if(startParam.length != 2 || endParam.length != 2)
	    return false;
	  if(startParam[1].equalsIgnoreCase("AM") && endParam[1].equalsIgnoreCase("PM"))
	    return true;

	  if(startParam[1].equalsIgnoreCase(endParam[1])) {
	    String[] startHHMM = startParam[0].split(":");
	    String[] endHHMM = endParam[0].split(":");
	    if(startHHMM.length != 2 || endHHMM.length != 2) {
	      return false;
	    }
	    int startHour = Integer.parseInt(startHHMM[0]);
	    int startMin = Integer.parseInt(startHHMM[1]);
	    int endHour = Integer.parseInt(endHHMM[0]);
	    int endMin = Integer.parseInt(endHHMM[1]);
	    if(startHour < endHour || (startHour == endHour && startMin < endMin)) {
	      return true;
	    }
	  }
	  return false;
	}

  /*
  * first 2 parameter are from DB and last 2 parameter are from api
  * */
	public static boolean isTimeRangeIntersect(String startTime1, String endTime1, String startTime2, String endTime2) {
		if(StringUtils.isBlank(startTime1) || StringUtils.isBlank(endTime1)
				|| StringUtils.isBlank(startTime2) || StringUtils.isBlank(endTime2)) {
			return false;
		}
		// ((st2 < st1 && et2 <= st1) || (st2 >= et1 && et2 > et1))
		boolean isValid = ((isValidTimeRange(startTime2, startTime1) && (endTime2.equalsIgnoreCase(startTime1) || isValidTimeRange(endTime2, startTime1)))
				|| ((startTime2.equalsIgnoreCase(endTime1) || isValidTimeRange(endTime1, startTime2)) && isValidTimeRange(endTime1, endTime2)));
		return isValid;
	}

	/*
	* first 4 parameter are from DB and last 4 parameter are from api
	* TODO: get day and find next first date that will come on that day and then validate output
	* */
	public static boolean isTimeRangeIntersect(String startTime1, String endTime1, String startDate1, String endDate1,
                                             String startTime2, String endTime2, String startDate2, String endDate2) {
    Date stDate1 = getDate(startDate1);
    Date edDate1 = getDate(endDate1);
    Date stDate2 = getDate(startDate2);
    Date edDate2 = getDate(endDate2);
    if(startDate1 == null && startDate2 == null && endDate1 == null && endDate2 == null) {
      return isTimeRangeIntersect(startTime1, endTime1, startTime2, endTime2);
    }

    try {
      edDate1 = (edDate1 != null) ? edDate1 : incrementDate(startDate1, 365);
      edDate2 = (edDate2 != null) ? edDate2 : incrementDate(startDate2, 365);
    } catch (Exception exception) {
      exception.printStackTrace();
      return true;
    }

    Date today = new Date();
    if(stDate2 == null) {
      return  !(stDate1.equals(today) || (stDate1.before(today)) && edDate1.after(today)) || isTimeRangeIntersect(startTime1, endTime1, startTime2, endTime2);
    }

    if(stDate1 == null) {
      return isTimeRangeIntersect(startTime1, endTime1, startTime2, endTime2);
    }

    return !isDateIntersect(stDate1, edDate1, stDate2, edDate2) || isTimeRangeIntersect(startTime1, endTime1, startTime2, endTime2);
	}

	public static boolean isDateIntersect(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
	  if(startDate1.after(endDate2) || startDate2.after(endDate1)) {
	   return true;
    }
    return false;
  }

	public static String validDate(String date) {
	  if(StringUtils.isBlank(date)) {
	    return "";
    }
    try {
      date = date.trim().replaceAll("[ ]+", " ");
      Date output = (Date) formatter.parse(date);
      return date;
    } catch (Exception exception) {
      return "";
    }
	}

  public static Date getDate(String date) {
    if(StringUtils.isBlank(date)) {
      return null;
    }
    Date output = null;
    try {
      date = date.trim().replaceAll("[ ]+", " ");
      output = (Date) formatter.parse(date);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return output;
  }

  public static Date incrementDate(String date, int numberOfDay) throws ParseException {
    Calendar c = Calendar.getInstance();
    c.setTime(formatter.parse(date));
    c.add(Calendar.DATE, numberOfDay);
    return c.getTime();
  }

  public static boolean validDateRange(String startDate, String endDate) {
    if(StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
      return false;
    }

    boolean isValidRange = false;
    try {
      startDate = startDate.trim().replaceAll("[ ]+", " ");
      endDate = endDate.trim().replaceAll("[ ]+", " ");
      Date startFormatedDate = (Date) formatter.parse(startDate);
      Date endFormatedDate = (Date) formatter.parse(endDate);
      isValidRange = startFormatedDate.after(endFormatedDate) ? false : true;
    } catch (Exception exception) {
      isValidRange = false;
    }
    return isValidRange;
  }
}
