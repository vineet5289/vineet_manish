package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReferenceKeyValidationUtils {
	private static Pattern referenceKeyPattern;
	private static final String REFERENCE_KEY_PATTERN = "^[a-z]{1,}-[a-z0-9]+$";

	private static Pattern otpKeyPattern;
	private static final String OTP_KEY_PATTERN = "^[0-9]{6,}$";

	static {
		referenceKeyPattern = Pattern.compile(REFERENCE_KEY_PATTERN, Pattern.CASE_INSENSITIVE);
		otpKeyPattern = Pattern.compile(OTP_KEY_PATTERN);
	}

	public static boolean isValidReferenceKey(String referenceKey) {
		if(referenceKey == null)
			return false;
		Matcher matcher = referenceKeyPattern.matcher(referenceKey.trim());
		return matcher.matches();
	}

	public static boolean isValidOTP(String otp) {
		if(otp == null)
			return false;
		Matcher matcher = otpKeyPattern.matcher(otp.trim());
		return matcher.matches();
	}
}
