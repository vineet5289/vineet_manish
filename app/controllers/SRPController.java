package controllers;

import java.util.List;

import models.LoginDetails;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.html.homePage.schoolRequestHomepage;
import views.html.viewClass.dashboard;
import dao.UserLoginDAO;
import enum_package.LoginStatus;
import enum_package.LoginTypeEnum;
import enum_package.PasswordState;
import enum_package.RegisterUserType;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result index() {
		String userName = session().get(SessionKey.USER_NAME.name());
		LoginDetails loginDetails = null;
		boolean shouldLogout = true;
		try {
			UserLoginDAO userLoginDAO = new UserLoginDAO();
			loginDetails = userLoginDAO.refreshUserUsingUserName(userName);
			shouldLogout = false;
		} catch(Exception exception) {
			exception.printStackTrace();
			flash("error", "Server problem occur during refresh.");
		}

		if(shouldLogout || loginDetails == null || loginDetails.getLoginStatus() != LoginStatus.validuser) {
			session().clear();
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		String passwordState = loginDetails.getPasswordState();
		session(SessionKey.LOGIN_STATE.name(), passwordState);

		String type = loginDetails.getType();
		session(SessionKey.LOGIN_TYPE.name(), type);

		String role = loginDetails.getRole();
		session(SessionKey.USER_ROLE.name(), role);

		Long schoolId = loginDetails.getSchoolId();
		if(schoolId != null && schoolId != 0) {
			session(SessionKey.SCHOOL_ID.name(), Long.toString(schoolId));
		}

		if(type.equalsIgnoreCase(LoginTypeEnum.institite.name()) && role.equalsIgnoreCase("SUPERADMIN")) {
			if(passwordState.equalsIgnoreCase(PasswordState.redirectstate.name()))
				return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
			return ok(dashboard.render(session().get(SessionKey.USER_NAME.name()), "SUPERADMIN"));
		}

		List<LoginDetails> userDetails = null;

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
