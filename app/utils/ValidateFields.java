package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFields {
	private static Pattern emailPattern;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static Pattern userNamePattern;
	private static final String USERNAME_PATTERN = "^[a-z0-9.@_-]{5,20}$";

	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static Pattern passwordPattern;

	private static final String ACCESS_RIGHTS_PATTERN = "^[a-z0-9=,]{1,}$";
	private static Pattern accessRightsPattern;

	private static Pattern mobileNumberPatter;
	private static final String MOBILE_NUMBER_PATTERN = "^([+][0-9]{2,3}[- ]?)?\\d{10}$";

	private static Pattern alternativeNumberPatter;
	private static final String ALTERNATIVE_NUMBER_PATTERN = "^[+0]?[0-9]{1,3}[- ]?\\d{5,8}$";

	static {
		userNamePattern = Pattern.compile(USERNAME_PATTERN, Pattern.CASE_INSENSITIVE);
		passwordPattern = Pattern.compile(PASSWORD_PATTERN);
		emailPattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		accessRightsPattern = Pattern.compile(ACCESS_RIGHTS_PATTERN, Pattern.CASE_INSENSITIVE);
		mobileNumberPatter = Pattern.compile(MOBILE_NUMBER_PATTERN);
		alternativeNumberPatter = Pattern.compile(ALTERNATIVE_NUMBER_PATTERN);
	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		if(mobileNumber == null)
			return false;
		Matcher matcher = mobileNumberPatter.matcher(mobileNumber.trim());
		return matcher.matches();
	}

	public static boolean isValidAlternativeNumber(String alternativeNumber) {
		Matcher matcher = alternativeNumberPatter.matcher(alternativeNumber.trim());
		return matcher.matches();
	}

	public static boolean isValidEmailId(String emailId) {
		if(emailId == null)
			return false;

		Matcher matcher = emailPattern.matcher(emailId.trim());
		return matcher.matches();
	}

	public static boolean isValidUserName(String userName) {
		if(userName == null)
			return false;

		Matcher userNameMatcher = userNamePattern.matcher(userName);
		Matcher emailMatcher = emailPattern.matcher(userName);
		return (userNameMatcher.matches() || emailMatcher.matches());
	}

	public static boolean isValidAccessRigths(String accessRight) {
		if(accessRight == null)
			return false;

		Matcher matcher = accessRightsPattern.matcher(accessRight);
		return matcher.matches();
	}

	public static boolean isValidPassword(String password) {
		if(password == null)
			return false;

		Matcher matcher = passwordPattern.matcher(password);
		return matcher.matches();
	}
}
