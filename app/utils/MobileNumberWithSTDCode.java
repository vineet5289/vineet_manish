package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileNumberWithSTDCode {
	private static Pattern mobileNumberPatter;
	private static final String MOBILE_NUMBER_PATTERN = "\\d{10}$";
	static {
		mobileNumberPatter = Pattern.compile(MOBILE_NUMBER_PATTERN);
	}
	public static String getMobileNumberWithSTDCode(String mobileNumber) {
		if(mobileNumber == null || mobileNumber.isEmpty())
			return "";
		Matcher matcher = mobileNumberPatter.matcher(mobileNumber.trim());
		if(matcher.matches()) {
			return ("+91" + mobileNumber);
		}
		return mobileNumber.replaceAll("([ \t-\\+]+)", "").trim();
	}
}
