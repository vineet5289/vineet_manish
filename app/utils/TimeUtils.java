package utils;

import java.text.SimpleDateFormat;
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
