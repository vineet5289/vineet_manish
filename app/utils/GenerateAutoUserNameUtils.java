package utils;

import java.util.Calendar;

public class GenerateAutoUserNameUtils {

	private static final int lengthOfSchoolId = 3;
	private static final int lengthOfNumberOfEmp = 3;

	public static String getEmpUserName(Long schoolId, String empId, Long numberOfEmp) {
		StringBuilder sb = new StringBuilder();
		sb.append("E-");
		sb.append(getFixedLengthString(schoolId, lengthOfSchoolId));
		sb.append(empId.toUpperCase());
		sb.append(getFixedLengthString(numberOfEmp, lengthOfNumberOfEmp));		
		return sb.toString();
	}

	public static String getEmpUserName(Long schoolId, Long numberOfEmp) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder();
		sb.append("E-");
		sb.append(getFixedLengthString(schoolId, lengthOfSchoolId));
		sb.append(String.valueOf(year));
		sb.append(getFixedLengthString(numberOfEmp, lengthOfNumberOfEmp));		
		return sb.toString();
	}

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
