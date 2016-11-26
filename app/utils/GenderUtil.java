package utils;

import org.apache.commons.lang3.StringUtils;

import enum_package.Gender;

public class GenderUtil {
  public static boolean isValidGenderValue(String gender) {
    if (StringUtils.isBlank(gender)) {
      return false;
    }

    if (gender.equalsIgnoreCase(Gender.MALE.getValue())
        || gender.equalsIgnoreCase(Gender.FEMALE.getValue())) {
      return true;
    }
    return false;
  }
}
