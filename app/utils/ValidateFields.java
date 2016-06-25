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

	static {
		userNamePattern = Pattern.compile(USERNAME_PATTERN, Pattern.CASE_INSENSITIVE);
		passwordPattern = Pattern.compile(PASSWORD_PATTERN);
		emailPattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
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

	public static boolean isValidPassword(String password) {
//		if(password == null)
//			return false;
//
//		Matcher matcher = passwordPattern.matcher(password);
//		return matcher.matches();
		return true;
	}
}
