package security.authorization.roles;

import be.objectify.deadbolt.java.models.Role;

public class SecurityRole implements Role {

	private String name;

	public SecurityRole(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
