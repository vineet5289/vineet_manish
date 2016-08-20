package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum LoginType {
	general("general"),
	guardian("guardian"),
	student("student"),
	institute("institute"),
	headinstitute("headinstitute"),
	emp("emp");

	private String value;
	private LoginType(String value) {
		this.value = value;
	}

	private final static Map<LoginType, String> nameToValue = new HashMap<LoginType, String>(LoginType.values().length);
	private final static Map<String, LoginType> valueToName = new HashMap<String, LoginType>(LoginType.values().length);
	static {
		for(LoginType lt : LoginType.values()) {
			nameToValue.put(lt, lt.value);
			valueToName.put(lt.value, lt);
		}
	}

	public static boolean contain(LoginType key) {
		String result = nameToValue.get(key);
		if(result == null)
			return false;
		return true;
	}

	public static boolean contain(String key) {
		LoginType result = valueToName.get(key);
		if(result == null)
			return false;
		return true;
	}
}
