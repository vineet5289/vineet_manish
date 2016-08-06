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
import views.html.homePage.schoolRequestHomepage;
import views.html.parent.parentHome;
import views.html.teacher.teacherHome;
import views.html.student.studentHome;
import enum_package.RegisterUserType;
import enum_package.Role;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result index() {
		String userName = session().get(SessionKey.USER_NAME.name());
		String userAuthKey = session().get(SessionKey.AUTH_TOKEN.name());

		
		if(true) {
			return ok(dashboard.render(session().get(SessionKey.USER_NAME.name()), "SUPERADMIN"));
		}

		UserLoginDAO userLoginDAO = new UserLoginDAO();
		List<LoginDetails> userDetails = null;
		try {
			userDetails = userLoginDAO.getAllUserRelatedToCurrentUser(userName);
		} catch (SQLException exception) {
			exception.printStackTrace();
			userDetails = new ArrayList<LoginDetails>();
		}
		System.out.println("====>" + userDetails);

//		if(superUserRole.equalsIgnoreCase(Role.TEACHER.name())) {
//			return ok(teacherHome.render(session().get(SessionKey.USER_NAME.name()), session().get(SessionKey.USER_ROLE.name()), userDetails));
//		}
//
//		if(superUserRole.equalsIgnoreCase(Role.GUARDIAN.name())) {
//			return ok(parentHome.render(session().get(SessionKey.USER_NAME.name()), session().get(SessionKey.USER_ROLE.name()), userDetails));
//		}
//		if(superUserRole.equalsIgnoreCase(Role.STUDENT.name())) {
//			return ok(studentHome.render(session().get(SessionKey.USER_NAME.name()), session().get(SessionKey.USER_ROLE.name()), userDetails));
//		}

		return ok(dashboard.render(session().get(SessionKey.USER_NAME.name()), session().get(SessionKey.USER_ROLE.name())));
	}

	public Result preRegistration(String userType) {
		if(userType.trim().equalsIgnoreCase("school")) {
			session().clear();
			session(SessionKey.REG_USER_REQUEST.name(), RegisterUserType.SCHOOL.name());
			return ok(schoolRequestHomepage.render());
		}

		if(userType.trim().equalsIgnoreCase("employee")) {
			session(SessionKey.REG_USER_REQUEST.name(), RegisterUserType.EMPLOYEE.name());
		}

		if(userType.trim().equalsIgnoreCase("other user")) {
			session(SessionKey.REG_USER_REQUEST.name(), RegisterUserType.OTHER_USER.name());
		}

		if(userType.equalsIgnoreCase("error")) {
			System.out.println("error");
		}
		return ok("pre registration");
	}
}
