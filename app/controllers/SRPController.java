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
import views.html.parent.parentHome;
import views.html.teacher.teacherHome;
import views.html.student.studentHome;
import enum_package.Role;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result index() {
		String superUserName = session().get(SessionKey.SUPER_USER_NAME.name());
		String superUserRole = session().get(SessionKey.SUPER_USER_ROLE.name());
		String superUserAuthKey = session().get(SessionKey.SUPER_AUTH_TOKEN.name());
		String superUserAccessRight = session().get(SessionKey.SUPER_USER_ACCESSRIGHT.name());
		String superUserSchoolId = session().get(SessionKey.SUPER_SCHOOL_ID.name());

		session(SessionKey.CURRENT_USER_NAME.name(), superUserName);
		session(SessionKey.CURRENT_USER_ROLE.name(), superUserRole);
		session(SessionKey.CURRENT_AUTH_TOKEN.name(), superUserAuthKey);
		if(superUserAccessRight != null && !superUserAccessRight.isEmpty())
			session(SessionKey.CURRENT_USER_ACCESSRIGHT.name(), superUserAccessRight);

		if(superUserSchoolId != null && !superUserSchoolId.isEmpty()) {
			session(SessionKey.CURRENT_SCHOOL_ID.name(), superUserSchoolId);
		}

		if(superUserRole.equalsIgnoreCase(Role.SUPERADMIN.name())) {
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
		System.out.println("====>" + userDetails);
		
		if(superUserRole.equalsIgnoreCase(Role.TEACHER.name())) {
			return ok(teacherHome.render(session().get(SessionKey.CURRENT_USER_NAME.name()), session().get(SessionKey.CURRENT_USER_ROLE.name()), userDetails));
		}

		if(superUserRole.equalsIgnoreCase(Role.GUARDIAN.name())) {
			return ok(parentHome.render(session().get(SessionKey.CURRENT_USER_NAME.name()), session().get(SessionKey.CURRENT_USER_ROLE.name()), userDetails));
		}
		if(superUserRole.equalsIgnoreCase(Role.STUDENT.name())) {
			return ok(studentHome.render(session().get(SessionKey.CURRENT_USER_NAME.name()), session().get(SessionKey.CURRENT_USER_ROLE.name()), userDetails));
		}

		return ok(dashboard.render(session().get(SessionKey.CURRENT_USER_NAME.name()), session().get(SessionKey.CURRENT_USER_ROLE.name())));
	}
}
