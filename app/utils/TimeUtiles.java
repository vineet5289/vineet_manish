package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class TimeUtiles {
	private static Pattern timePattern;
	private static final String TIME_PATTERN = "^((0[1-9])|(1[0-2])|([0-9])):([0-5][0-9]) ((A)|(P))M$";

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
	  String[] startParam = startTime.split(" ");
	  String[] endParam = startTime.split(" ");
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
}
