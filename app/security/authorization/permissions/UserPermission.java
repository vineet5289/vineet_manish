package security.authorization.permissions;

import be.objectify.deadbolt.java.models.Permission;
public class UserPermission implements Permission {

	private String value;

	public UserPermission(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
