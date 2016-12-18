package security.authorization;

import be.objectify.deadbolt.java.models.Role;

public enum MyRole implements Role {
	admin,
	groupadmin;

	@Override
	public String getName() {
		return name();
	}
}
