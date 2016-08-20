package utils;

import java.security.SecureRandom;

public class StringUtils {
	private static final String INVALID_CHAR_PATTERN = "[;\\*]";

	public static String getValidStringValue(String invalidString) {
		if(invalidString == null)
			return "";
		String validStringValue = invalidString.trim().replaceAll(INVALID_CHAR_PATTERN, " ");
		return validStringValue;
	}

	public static String getSchoolRequestRegistrationNumber(String schooleName) {
		RandomGenerator randomGenerator = new RandomGenerator(); 
		SecureRandom secureRandom = new SecureRandom();
		String referenceNumber = randomGenerator.getReferenceNumber(30, secureRandom);

		StringBuilder sb = new StringBuilder();
		String[] charSeq = schooleName.replaceAll("([ \t.&]+)", " ").trim().split("([ \t.&]+)");
		int nameLength = charSeq.length;
		if(nameLength == 1) {
			if(charSeq[0].length() < 3)
				sb.append(charSeq[0]);
			else
				sb.append(charSeq[0].charAt(0) + "" + charSeq[0].charAt(1) + "" + charSeq[0].charAt(2));
		} else if(nameLength == 2) {
			sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0));
		} else if(nameLength > 2) {
			sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0) + "" + charSeq[2].charAt(0));
		}

		sb.append("-").append(referenceNumber);
		return sb.toString().toUpperCase();
	}

	public static String generatePassword() throws Exception {
		String password = "";
		try {
			password = RandomGenerator.nextSessionId(70, new SecureRandom());
		}catch(Exception exception) {
			exception.printStackTrace();
			throw new Exception();
		}
		return password;
	}
}
