package security.authorization;

import be.objectify.deadbolt.java.models.Permission;

public class UserPermission implements Permission {

	private String value;

	@Override
	public String getValue() {
		return "student.edit";
	}

	public static UserPermission findUserPermission(String username) {
		System.out.println("******* inside UserPermission.findUserPermission ******** ");
		UserPermission userPermission = new UserPermission();
		userPermission.value = "students.edit";
		return userPermission;
	}
}
