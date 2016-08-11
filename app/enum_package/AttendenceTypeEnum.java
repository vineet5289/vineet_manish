package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AttendenceTypeEnum {
	once("Once in a day"),
	twice("Twice in a day"),
	everyperiod("Every lecture");

	private final static Map<AttendenceTypeEnum, String> attendenceTypeToDisplayName = new HashMap<AttendenceTypeEnum, String>(AttendenceTypeEnum.values().length);
	private final static Map<String, AttendenceTypeEnum> displayNameToAttendenceType = new HashMap<String, AttendenceTypeEnum>(AttendenceTypeEnum.values().length);
	private final static List<String> attendenceTypeDisplayName = new ArrayList<String>(AttendenceTypeEnum.values().length);
	static {
		for(AttendenceTypeEnum ate : AttendenceTypeEnum.values()) {
			attendenceTypeDisplayName.add(ate.name());
			attendenceTypeToDisplayName.put(ate, ate.value);
			displayNameToAttendenceType.put(ate.value, ate);
		}
	}

	private String value;
	private AttendenceTypeEnum(String value) {
		this.value = value;
	}

	public static AttendenceTypeEnum of(String key) {
		AttendenceTypeEnum result = displayNameToAttendenceType.get(key);
		if(result == null) {
			throw new IllegalArgumentException("No attendence type exits for given value = " + key);
		}
		return result;
	}

	public static String of(AttendenceTypeEnum key) {
		String result = attendenceTypeToDisplayName.get(key);
		if(result == null) {
			throw new IllegalArgumentException("No attendence type exits for given value = " + key);
		}
		return result;
	}

	public static List<String> getAttendenceTypeDisplayName() {
		return attendenceTypeDisplayName;
	}

	public static boolean contain(String key) {
		AttendenceTypeEnum result = displayNameToAttendenceType.get(key.trim());
		if(result == null) {
			return false;
		}
		return true;
	}
}