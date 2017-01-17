package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AttendanceTypeEnum {
	once("Once in a day"),
	twice("Twice in a day"),
	everyperiod("Every lecture");

	private final static Map<String, String> attendenceTypeToDisplayName = new HashMap<String, String>(AttendanceTypeEnum.values().length);
	private final static Map<String, AttendanceTypeEnum> displayNameToAttendenceType = new HashMap<String, AttendanceTypeEnum>(AttendanceTypeEnum.values().length);
	private final static List<String> attendenceTypeDisplayName = new ArrayList<String>(AttendanceTypeEnum.values().length);
	static {
		for(AttendanceTypeEnum ate : AttendanceTypeEnum.values()) {
			attendenceTypeDisplayName.add(ate.value);
			attendenceTypeToDisplayName.put(ate.name(), ate.value);
			displayNameToAttendenceType.put(ate.value, ate);
		}
	}

	private String value;
	private AttendanceTypeEnum(String value) {
		this.value = value;
	}

	public static AttendanceTypeEnum of(String key) {
		return displayNameToAttendenceType.containsKey(key.trim()) ? displayNameToAttendenceType.get(key.trim()) : AttendanceTypeEnum.once;
	}

	public static String of(AttendanceTypeEnum key) {
		return attendenceTypeToDisplayName.containsKey(key) ? attendenceTypeToDisplayName.get(key) : "";
	}

	public static List<String> getAttendenceTypeDisplayName() {
		return attendenceTypeDisplayName;
	}

	public static Map<String, String> getEnumToDisplayNameMap() {
    return attendenceTypeToDisplayName;
  }

	public static boolean contain(String key) {
    return attendenceTypeToDisplayName.containsKey(key.trim());
	}
}