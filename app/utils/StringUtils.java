package utils;

public class StringUtils {
	private static final String INVALID_CHAR_PATTERN = "[;\t[  ]+\\*]";

	public static String getValidStringValue(String invalidString) {
		if(invalidString == null)
			return "";
		String validStringValue = invalidString.trim().replaceAll(INVALID_CHAR_PATTERN, " ");
		return validStringValue;
	}
}
