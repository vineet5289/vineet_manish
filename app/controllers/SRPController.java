package controllers;

import java.util.List;

import models.LoginDetails;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.html.homePage.schoolRequestHomepage;
import views.html.viewClass.dashboard;
import dao.UserLoginDAO;
import enum_package.LoginTypeEnum;
import enum_package.RegisterUserType;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result index() {
		String userName = session().get(SessionKey.USER_NAME.name());
		String type = session().get(SessionKey.LOGIN_TYPE.name());
		String role = session().get(SessionKey.USER_ROLE.name());

		boolean shouldLogout = true;
		try {
			UserLoginDAO userLoginDAO = new UserLoginDAO();
			LoginDetails loginDetails = userLoginDAO.refreshUserUsingUserName(userName);
			if(loginDetails.getError().isEmpty()) {
				Long schoolId = loginDetails.getSchoolId();
				if(schoolId != null && schoolId != 0) {
					session(SessionKey.SCHOOL_ID.name(), Long.toString(schoolId));
				}
				session(SessionKey.LOGIN_TYPE.name(), loginDetails.getType());
				session(SessionKey.USER_ROLE.name(), loginDetails.getRole().trim());
				shouldLogout = false;
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			flash("error", "Server problem occur during refresh.");
		}

		if(shouldLogout) {
			session().clear();
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		if(type.equalsIgnoreCase(LoginTypeEnum.SCHOOL.name()) && role.equalsIgnoreCase("SUPERADMIN")) {
			return ok(dashboard.render(session().get(SessionKey.USER_NAME.name()), "SUPERADMIN"));
		}

		List<LoginDetails> userDetails = null;
		System.out.println("====>" + userDetails);

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
