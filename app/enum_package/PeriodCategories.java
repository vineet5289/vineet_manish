package enum_package;

import java.util.HashMap;
import java.util.Map;

import utils.StringUtils;

public enum PeriodCategories {
  LECTURE("Lecture"),
  PRACTICAL("Practical"),
  LAB("Lab"),
  LUNCH("Lunch"),
  SPECIALACTIVITY("Special Activity"),
  BREAK("Break");

  private final String value;
  private static final Map<String, String> stringValueToLowerCase = new HashMap<String, String>(PeriodCategories.values().length);
  private static final Map<String, String> lowerCaseToStringValue = new HashMap<String, String>(PeriodCategories.values().length);
  private static final Map<String, Boolean> stringValueToBoolean = new HashMap<String, Boolean>(PeriodCategories.values().length);
  private PeriodCategories(String value) {
    this.value = value;
  }

  static {
    for(PeriodCategories pc : PeriodCategories.values()) {
      lowerCaseToStringValue.put(pc.value.toLowerCase(), pc.value);
      stringValueToLowerCase.put(pc.value, pc.value.toLowerCase());
      if(pc == PeriodCategories.LECTURE || pc == PeriodCategories.PRACTICAL || pc == PeriodCategories.LAB) {
        stringValueToBoolean.put(pc.value, true);
      } else {
        stringValueToBoolean.put(pc.value, false);
      }
    }
  }

  public static boolean contains(String value) {
    return StringUtils.isBlank(value) || !lowerCaseToStringValue.containsKey(value.toLowerCase()) ? false : true;
  }

  public static String get(String value) {
    if(!contains(value)) return "";
    return lowerCaseToStringValue.get(value.toLowerCase());
  }

  public static Map<String, Boolean> getPeriodCategories() {
    return stringValueToBoolean;
  }

  public static boolean isProfSubjRequired(String value) {
    return contains(value) && stringValueToBoolean.get(get(value));
  }
}
