package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum MonthEnum {
	January(0),
	February(1),
	March(2),
	April(3),
	May(4),
	June(5),
	July(6),
	August(7),
	September(8),
	October(9),
	November(10),
	December(11);

	private final static Map<Integer, MonthEnum> valueToMonthMapping = new HashMap<Integer, MonthEnum>(MonthEnum.values().length);
	private final static Map<String, Integer> monthToValueMapping = new HashMap<String, Integer>(MonthEnum.values().length);
	static {
		for(MonthEnum me : MonthEnum.values()) {
			valueToMonthMapping.put(me.value, me);
			monthToValueMapping.put(me.name().toLowerCase(), (me.value+1));
		}
	}

	private int value;
	private MonthEnum(int value) {
		this.value = value;
	}

	public static MonthEnum of(int value) {
		MonthEnum result = valueToMonthMapping.get(value);
		if(result == null) {
			throw new IllegalArgumentException("No Month exits for given int value = " + value);
		}
		return result;
	}

	public static int of(String value) {
		Integer result = monthToValueMapping.get(value.toLowerCase());
		if(result == null) {
			throw new IllegalArgumentException("No Month exits for given month name = " + value);
		}
		return result.intValue();
	}
}
