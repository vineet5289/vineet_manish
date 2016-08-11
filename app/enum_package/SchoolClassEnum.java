package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SchoolClassEnum {
	Kindergarten("Nursery(Kindergarten)"),
	Lkg("Lower Kindergarten(LKG)"),
	Ukg("Upper Kindergarten(UKG)"),
	First("1st Standard"),
	Second("2nd Standard"),
	Third("3rd Standard"),
	Fourth("4th Standard"),
	Fifth("5th Standard"),
	Sixth("6th Standard"),
	Seventh("7th Standard"),
	Eighth("8th Standard"),
	Ninth("9th Standard"),
	Tenth("10th Standard"),
	Eleventh("11th Standard"),
	Twelth("12th Standard");
	
	private String value;
	private SchoolClassEnum(String value) {
		this.value = value;
	}

	private final static Map<SchoolClassEnum, String> classEnumToDisplayNameMapping = new HashMap<SchoolClassEnum, String>(SchoolClassEnum.values().length);
	private final static Map<String, SchoolClassEnum> displayNameClassEnumMapping = new HashMap<String, SchoolClassEnum>(SchoolClassEnum.values().length);
	private static final List<String> classDisplayName = new ArrayList<String>(SchoolClassEnum.values().length);
	static {
		for(SchoolClassEnum sce : SchoolClassEnum.values()) {
			classEnumToDisplayNameMapping.put(sce, sce.value);
			displayNameClassEnumMapping.put(sce.value, sce);
			classDisplayName.add(sce.value);
		}
	}

	public static SchoolClassEnum of(String className) {
		SchoolClassEnum result = displayNameClassEnumMapping.get(className);
		if(result == null) {
			throw new IllegalArgumentException("No Class exits for given value = " + className);
		}
		return result;
	}

	public static String of(SchoolClassEnum classEnumName) {
		String result = classEnumToDisplayNameMapping.get(classEnumName);
		if(result == null) {
			throw new IllegalArgumentException("No Class exits for given value = " + classEnumName);
		}
		return result;
	}

	public static boolean contains(String displayName) {
		SchoolClassEnum result = displayNameClassEnumMapping.get(displayName);
		if(result == null) {
			return false;
		}
		return true;
	}

	public static List<String> getClassDisplayName() {
		return classDisplayName;
	}
}
