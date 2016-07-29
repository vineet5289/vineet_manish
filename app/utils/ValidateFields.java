package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFields {
	private static Pattern emailPattern;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static Pattern userNamePattern;
	private static final String USERNAME_PATTERN = "^[a-z0-9@_-]{5,20}$";

	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static Pattern passwordPattern;

	private static final String ACCESS_RIGHTS_PATTERN = "^[a-z0-9=,]{1,}$";
	private static Pattern accessRightsPattern;

	private static Pattern schoolNamePatter;
	private static final String SCHOOL_NAME_PATTERN = "^[a-z]{1,}[a-z0-9 ._-[\\(.+\\)]\t]*$";

	private static Pattern mobileNumberPatter;
	private static final String MOBILE_NUMBER_PATTERN = "^([+][0-9]{2,3}[- ]?)?\\d{10}$";

	private static Pattern alternativeNumberPatter;
	private static final String ALTERNATIVE_NUMBER_PATTERN = "^[+0]?[0-9]{1,3}[- ]?\\d{5,8}$";

	private static Pattern cityPattern;
	private static final String CITY_PATTERN = "^([ \t]*[a-z]+[ \t]*)+$";

	private static Pattern statePatter;
	private static final String STATE_PATTERN = "^[+0]?[0-9]{1,3}[- ]?\\d{5,8}$";

	private static Pattern countryPatter;
	private static final String COUNTRY_PATTERN = "^[+0]?[0-9]{1,3}[- ]?\\d{5,8}$";

	private static Pattern pincodePatter;
	private static final String PINCODE_PATTERN = "^[1-9]{1}[0-9]{5}$";

	static {
		userNamePattern = Pattern.compile(USERNAME_PATTERN, Pattern.CASE_INSENSITIVE);
		passwordPattern = Pattern.compile(PASSWORD_PATTERN);
		emailPattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		accessRightsPattern = Pattern.compile(ACCESS_RIGHTS_PATTERN, Pattern.CASE_INSENSITIVE);
		schoolNamePatter = Pattern.compile(SCHOOL_NAME_PATTERN, Pattern.CASE_INSENSITIVE);
		mobileNumberPatter = Pattern.compile(MOBILE_NUMBER_PATTERN);
		alternativeNumberPatter = Pattern.compile(ALTERNATIVE_NUMBER_PATTERN);
		cityPattern = Pattern.compile(CITY_PATTERN, Pattern.CASE_INSENSITIVE);
		statePatter = Pattern.compile(STATE_PATTERN, Pattern.CASE_INSENSITIVE);
		countryPatter = Pattern.compile(COUNTRY_PATTERN, Pattern.CASE_INSENSITIVE);
		pincodePatter = Pattern.compile(PINCODE_PATTERN, Pattern.CASE_INSENSITIVE);
	}

	public static boolean isValidPincode(String pincode) {
		if(pincode == null)
			return false;
		Matcher matcher = pincodePatter.matcher(pincode.trim());
		return matcher.matches();
	}

	public static boolean isValidCity(String city) {
		if(city == null)
			return false;
		Matcher matcher = cityPattern.matcher(city);
		return matcher.matches();
	}

	public static boolean isValidState(String state) {
		if(state == null)
			return false;
		Matcher matcher = statePatter.matcher(state);
		return matcher.matches();
	}

	public static boolean isValidCountry(String country) {
		if(country == null)
			return false;
		Matcher matcher = countryPatter.matcher(country);
		return matcher.matches();
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

	public static boolean isValidSchoolName(String name) {
		if(name == null)
			return false;
		Matcher matcher = schoolNamePatter.matcher(name.trim());
		return matcher.matches();
	}

	public static boolean isValidEmailId(String emailId) {
		if(emailId == null)
			return false;

		Matcher matcher = emailPattern.matcher(emailId);
		return matcher.matches();
	}

	public static boolean isValidUserName(String userName) {
		if(userName == null)
			return false;

		Matcher matcher = userNamePattern.matcher(userName);
		return matcher.matches();
	}

	public static boolean isValidAccessRigths(String accessRight) {
		if(accessRight == null)
			return false;

		Matcher matcher = accessRightsPattern.matcher(accessRight);
		return matcher.matches();
	}

	public static boolean isValidPassword(String password) {
//		if(password == null)
//			return false;
//
//		Matcher matcher = passwordPattern.matcher(password);
//		return matcher.matches();
		return true;
	}
}
