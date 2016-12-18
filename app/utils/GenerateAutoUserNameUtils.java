package utils;

import java.util.Calendar;

public class GenerateAutoUserNameUtils {

	private static final int lengthOfSchoolId = 3;
	private static final int lengthOfNumberOfEmp = 3;

	private static String getFixedLengthString(Long number, int size) {
		StringBuilder sb = new StringBuilder();
		String numberAsString = String.valueOf(number);
		while(sb.length() + numberAsString.length() < size) {
			sb.append('0');
		}
		sb.append(number);
		return sb.toString();
	}
}
