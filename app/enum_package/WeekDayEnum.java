package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WeekDayEnum {
	Sunday(1),
	Monday(2),
	Tuesday(3),
	Wednesday(4),
	Thrusday(5),
	Friday(6),
	Saturday(7);

	private static final Map<Integer, WeekDayEnum> weekDayValueMapping = new HashMap<Integer, WeekDayEnum>(WeekDayEnum.values().length);
	private static final List<String> displayName = new ArrayList<String>(WeekDayEnum.values().length);
	static {
		for(WeekDayEnum wd : WeekDayEnum.values()) {
			weekDayValueMapping.put(wd.dayValue, wd);
			displayName.add(wd.name());
		}
	}

	private int dayValue;

	private WeekDayEnum(int dayValue) {
		this.dayValue = dayValue;
	}

	public static WeekDayEnum of(int dayValue) {
		WeekDayEnum result = weekDayValueMapping.get(dayValue);
		if(result == null) {
			throw new IllegalArgumentException("No Week day exits for given int value = " + dayValue);
		}
		return result;
	}

	public static List<String> getWeekDisplayName() {
		return displayName;
	}
}
