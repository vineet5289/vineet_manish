package views.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AccessRightsForm {

	public List<UserAccessRights> accessRights = new ArrayList<UserAccessRights>();

	@Data
	public static class UserAccessRights {
		String userName;
		String accessRights;
		String action;
	}
}
