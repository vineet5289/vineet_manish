package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class EmployeeUtil {
  private static Pattern empCodePattern;
  private static final String EMPCODE_PATTERN = "^[a-zA-Z0-9-.]+$";

  private static Pattern jobTitlePattern;
  private static final String JOBTITLE_PATTERN = "^[a-zA-Z0-9 .]+$";

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
}
