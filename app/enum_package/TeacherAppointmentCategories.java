package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.StringUtils;

public enum TeacherAppointmentCategories {
  PERMANENT("Permanent"),
  TEMPORARY("Temporary"),
  GUEST("Guest");

  private final String value;
  private static final Map<String, TeacherAppointmentCategories> stringValueToEnum = new HashMap<String, TeacherAppointmentCategories>(TeacherAppointmentCategories.values().length);
  private static final Map<TeacherAppointmentCategories, String> enumToStringValue = new HashMap<TeacherAppointmentCategories, String>(TeacherAppointmentCategories.values().length);
  private static final List<String> displayName = new ArrayList<String>();
  private TeacherAppointmentCategories(String value) {
    this.value = value;
  }

  static {
    for(TeacherAppointmentCategories ta : TeacherAppointmentCategories.values()) {
      stringValueToEnum.put(ta.name().toLowerCase(), ta);
      displayName.add(ta.name());
      enumToStringValue.put(ta, ta.name());
    }
  }

  public static boolean contains(String value) {
    return StringUtils.isBlank(value) || !stringValueToEnum.containsKey(value.toLowerCase()) ? false : true;
  }

  public static List<String> getAppointmentCat() {
    return displayName;
  }
}
