package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WeekDayEnum {
	sunday("Sunday"),
	monday("Monday"),
	tuesday("Tuesday"),
	wednesday("Wednesday"),
	thursday("Thursday"),
	friday("Friday"),
	saturday("saturday");

	private static final Map<String, WeekDayEnum> valueToEnumMap = new HashMap<String, WeekDayEnum>(WeekDayEnum.values().length);
  private static final Map<Integer, String> dayOfWeek = new HashMap<Integer, String>(WeekDayEnum.values().length);
	private static final Map<WeekDayEnum, String> enumTOValueMap = new HashMap<WeekDayEnum, String>(WeekDayEnum.values().length);
	private static final List<String> displayName = new ArrayList<String>(WeekDayEnum.values().length);
	static {
	  int seq = 1;
		for(WeekDayEnum wd : WeekDayEnum.values()) {
			valueToEnumMap.put(wd.value.toLowerCase(), wd);
      dayOfWeek.put(seq, wd.value);
			enumTOValueMap.put(wd, wd.value);
			displayName.add(wd.name());
		}
	}

	private String value;

	private WeekDayEnum(String value) {
		this.value = value;
	}

	public static WeekDayEnum of(String value) {
		WeekDayEnum result = valueToEnumMap.get(value.trim().toLowerCase());
		return result;
	}

  public static String of(int value) {
    return dayOfWeek.containsKey(value) ? dayOfWeek.get(value) : "";
  }

	public static boolean contains(String value) {
    return valueToEnumMap.get(value.trim().toLowerCase()) == null ? false : true;
	}

	public static List<String> getWeekDisplayName() {
		return displayName;
	}
}
