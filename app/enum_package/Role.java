package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum Role {
	institutegroupadmin("institutegroupadmin"),
	instituteadmin("instituteadmin"),
	teacher("teacher"),
	student("student"),
	guardian("guardian"),
	other("other");

	private String value;
	private final static Map<Role, String> roleToValue = new HashMap<>(Role.values().length);
	private Role(String value) {
		this.value = value;
	}

	static {
		for(Role role : Role.values()) {
			roleToValue.put(role, role.value);
		}
	}

	public static String of(Role role) {
		String result = roleToValue.get(role);
		if(result == null)
			return "";
		return result;
	}
}
