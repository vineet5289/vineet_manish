package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFields {
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
      + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  private static final String USERNAME_PATTERN = "^[a-z0-9.@_-]{5,20}$";
  private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?=\\S+$).{6,}$";
  private static final String ACCESS_RIGHTS_PATTERN = "^[a-z0-9=,]{1,}$";
  private static final String MOBILE_NUMBER_PATTERN = "^([+][0-9]{2,3}[- ]?)?\\d{10}$";
  private static final String ALTERNATIVE_NUMBER_PATTERN = "^[+0]?[0-9]{1,3}[- ]?\\d{5,8}$";
  private static final String DATE_FORMAT = "^((d{2}/M{2}/y{4})|(d{2}/y{4}/M{2})|(M{2}/d{2}/y{4})|(M{2}/y{4}/d{2})|"
      + "(y{4}/M{2}/d{2})|(y{4}/d{2}/M{2}))$";
  private static final String VALID_STRING_PATTERN = "^[a-z]{1,}[a-z0-9 ._-[\\(.+\\)]\t&]*$";
  private static Pattern emailPattern;
  private static Pattern userNamePattern;
  private static Pattern passwordPattern;
  private static Pattern accessRightsPattern;
  private static Pattern mobileNumberPatter;
  private static Pattern alternativeNumberPatter;
  private static Pattern dateFormatPattern;
  private static Pattern validStringPatter;

  static {
    userNamePattern = Pattern.compile(USERNAME_PATTERN, Pattern.CASE_INSENSITIVE);
    passwordPattern = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);
    emailPattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    accessRightsPattern = Pattern.compile(ACCESS_RIGHTS_PATTERN, Pattern.CASE_INSENSITIVE);
    mobileNumberPatter = Pattern.compile(MOBILE_NUMBER_PATTERN);
    alternativeNumberPatter = Pattern.compile(ALTERNATIVE_NUMBER_PATTERN);
    dateFormatPattern = Pattern.compile(DATE_FORMAT);
    validStringPatter = Pattern.compile(VALID_STRING_PATTERN, Pattern.CASE_INSENSITIVE);
  }

  public static boolean isValidDateFormat(String dateFormat) {
    if (dateFormat == null)
      return false;
    Matcher matcher = dateFormatPattern.matcher(dateFormat.trim());
    return matcher.matches();
  }

  public static boolean isValidMobileNumber(String mobileNumber) {
    if (StringUtils.isBlank(mobileNumber)) {
      return false;
    }
    Matcher matcher = mobileNumberPatter.matcher(mobileNumber.trim());
    return matcher.matches();
  }

  public static boolean isValidAlternativeNumber(String alternativeNumber) {
    Matcher matcher = alternativeNumberPatter.matcher(alternativeNumber.trim());
    return matcher.matches();
  }

  public static boolean isValidEmailId(String emailId) {
    if (StringUtils.isBlank(emailId)) {
      return false;
    }

    Matcher matcher = emailPattern.matcher(emailId.trim());
    return matcher.matches();
  }

  public static boolean isValidUserName(String userName) {
    if (userName == null)
      return false;

    Matcher userNameMatcher = userNamePattern.matcher(userName);
    Matcher emailMatcher = emailPattern.matcher(userName);
    return (userNameMatcher.matches() || emailMatcher.matches());
  }

  public static boolean isValidAccessRigths(String accessRight) {
    if (accessRight == null)
      return false;

    Matcher matcher = accessRightsPattern.matcher(accessRight);
    return matcher.matches();
  }

  public static boolean isValidPassword(String password) {
    if (password == null)
      return false;

    Matcher matcher = passwordPattern.matcher(password);
    return matcher.matches();
  }

  public static boolean isValidStringField(String name) {
    if (name == null)
      return false;
    Matcher matcher = validStringPatter.matcher(name.trim());
    return matcher.matches();
  }
}
