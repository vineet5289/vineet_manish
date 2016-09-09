package enum_package;

import java.util.HashMap;
import java.util.Map;

import be.objectify.deadbolt.java.models.Role;

public enum InstituteUserRole implements Role{
	institutegroupadmin("institutegroupadmin"),
	instituteadmin("instituteadmin"),
	employee("employee"),
	student("student"),
	guardian("guardian"),
	other("other");

	private String value;
	private final static Map<InstituteUserRole, String> roleToValue = new HashMap<>(InstituteUserRole.values().length);
	private InstituteUserRole(String value) {
		this.value = value;
	}

	static {
		for(InstituteUserRole role : InstituteUserRole.values()) {
			roleToValue.put(role, role.value);
		}
	}

	public static String of(InstituteUserRole role) {
		String result = roleToValue.get(role);
		if(result == null)
			return "";
		return result;
	}

	@Override
	public String getName() {
		return value;
	}
}
