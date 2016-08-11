package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtiles {
	private static Pattern timePattern;
	private static final String TIME_PATTERN = "^((0[1-9])|(1[0-2])|([0-9])):([0-6]([0]|[5])) ((A)|(P))M$";

	static {
		timePattern = Pattern.compile(TIME_PATTERN, Pattern.CASE_INSENSITIVE);
	}

	public static boolean isValidTime(String time) {
		if(time == null)
			return false;
		Matcher matcher = timePattern.matcher(time.trim());
		return matcher.matches();
	}
}
