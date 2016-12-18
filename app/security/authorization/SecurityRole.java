package security.authorization;

import be.objectify.deadbolt.java.models.Role;

public class SecurityRole implements Role {

	private String name;
	@Override
	public String getName() {
		return name;
	}

	public static SecurityRole getMyRole(String username) {
		System.out.println("inside security role");
		SecurityRole securityRole = new SecurityRole();
		securityRole.name="admin";
		return securityRole;
	}
}
