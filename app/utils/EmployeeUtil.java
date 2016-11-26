package utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class EmployeeUtil {
  private static Pattern empCodePattern;
  private static final String EMPCODE_PATTERN = "^[a-zA-Z0-9-.]+$";

  private static Pattern jobTitlePattern;
  private static final String JOBTITLE_PATTERN = "^[a-zA-Z0-9 .]+$";

  private static final int lengthOfSchoolId = 3;
  private static final int lengthOfNumberOfEmp = 3;

  static {
    empCodePattern = Pattern.compile(EMPCODE_PATTERN, Pattern.CASE_INSENSITIVE);
    jobTitlePattern = Pattern.compile(JOBTITLE_PATTERN, Pattern.CASE_INSENSITIVE);
  }

  public static boolean isValidEmpCodePattern(String empCode) {
    if (StringUtils.isBlank(empCode)) {
      return false;
    }

    Matcher matcher = empCodePattern.matcher(empCode.trim());
    return matcher.matches();
  }

  public static boolean isValidJobTitlePattern(String jobTitle) {
    if (StringUtils.isBlank(jobTitle)) {
      return false;
    }

    Matcher matcher = jobTitlePattern.matcher(jobTitle.trim());
    return matcher.matches();
  }

  public static String getEmpUserName(Long schoolId, String empId, long numberOfEmp) {
      StringBuilder sb = new StringBuilder();
      sb.append("E-");
      sb.append(getFixedLengthString(schoolId, lengthOfSchoolId));
      sb.append(empId.toUpperCase());
      sb.append(getFixedLengthString(numberOfEmp, lengthOfNumberOfEmp));      
      return sb.toString();
  }

  public static String getEmpUserName(Long schoolId, long numberOfEmp) {
      int year = Calendar.getInstance().get(Calendar.YEAR);
      StringBuilder sb = new StringBuilder();
      sb.append("E-");
      sb.append(getFixedLengthString(schoolId, lengthOfSchoolId));
      sb.append(String.valueOf(year));
      sb.append(getFixedLengthString(numberOfEmp, lengthOfNumberOfEmp));      
      return sb.toString();
  }

  private static String getFixedLengthString(long number, int size) {
      StringBuilder sb = new StringBuilder();
      String numberAsString = String.valueOf(number);
      while(sb.length() + numberAsString.length() < size) {
          sb.append('0');
      }
      sb.append(number);
      return sb.toString();
  }
}
