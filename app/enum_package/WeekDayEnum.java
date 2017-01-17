package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WeekDayEnum {
	monday("Monday"),
	tuesday("Tuesday"),
	wednesday("Wednesday"),
	thursday("Thursday"),
	friday("Friday"),
	saturday("Saturday"),
	sunday("Sunday");

  private static final Map<String, Integer> dayOfWeek = new HashMap<String, Integer>(WeekDayEnum.values().length);
  private static final Map<Integer, String> intValueToString = new HashMap<Integer, String>(WeekDayEnum.values().length);
	private static final List<String> displayName = new ArrayList<String>(WeekDayEnum.values().length);
	static {
	  int seq = 1;
		for(WeekDayEnum wd : WeekDayEnum.values()) {
      dayOfWeek.put(wd.value.toLowerCase(), seq);
			displayName.add(wd.name());
      seq++;
		}
	}

	private String value;

	private WeekDayEnum(String value) {
		this.value = value;
	}

  public static String of(int value) {
    return intValueToString.containsKey(value) ? intValueToString.get(value) : "";
  }

  public static int of(String value) {
    return dayOfWeek.containsKey(value) ? dayOfWeek.get(value) : -1;
  }

	public static boolean contains(String value) {
    return value == null || dayOfWeek.get(value.trim().toLowerCase()) == null ? false : true;
	}

	public static List<String> getWeekDisplayName() {
		return displayName;
	}

  public static Map<Integer, String> getDayToWeekMap() {
    return intValueToString;
  }
}
