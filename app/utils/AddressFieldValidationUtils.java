package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressFieldValidationUtils {
	private static Pattern cityPattern;
	private static final String CITY_PATTERN = "^([ \t]*[a-z]+[ \t]*)+$";

	private static Pattern statePatter;
	private static final String STATE_PATTERN = "^([ \t]*[a-z. \t]+[ \t]*)+$";

	private static Pattern countryPatter;
	private static final String COUNTRY_PATTERN = "^([ \t]*[a-z.]+[ \t]*)+$";

	private static Pattern pincodePatter;
	private static final String PINCODE_PATTERN = "^[1-9]{1}[0-9]{5}$";

	static {
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
}
