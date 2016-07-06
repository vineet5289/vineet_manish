package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UserLoginDAO;
import models.LoginDetails;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.html.viewClass.dashboard;
import enum_package.Role;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result index() {
		String superUserName = session().get(SessionKey.SUPER_USER_NAME.name());
		String superUserRole = session().get(SessionKey.SUPER_USER_ROLE.name());
		if(superUserRole != null && superUserRole.equalsIgnoreCase(Role.SUPERADMIN.name())) {
			return ok(dashboard.render(session().get(SessionKey.CURRENT_USER_NAME.name()), session().get(SessionKey.CURRENT_USER_ROLE.name())));
		}
		UserLoginDAO userLoginDAO = new UserLoginDAO();
		List<LoginDetails> userDetails = null;
		try {
			userDetails = userLoginDAO.getAllUserRelatedToCurrentUser(superUserName);
		} catch (SQLException exception) {
			exception.printStackTrace();
			userDetails = new ArrayList<LoginDetails>();
		}
		
		return ok(dashboard.render(session().get(SessionKey.CURRENT_USER_NAME.name()), session().get(SessionKey.CURRENT_USER_ROLE.name())));
	}
}
