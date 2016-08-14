package controllers;

import java.util.List;

import models.LoginDetails;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import security.BasicAuthRequirement;
import views.html.homePage.schoolRequestHomepage;
import views.html.viewClass.dashboard;
import dao.UserLoginDAO;
import enum_package.LoginStatus;
import enum_package.LoginTypeEnum;
import enum_package.PasswordState;
import enum_package.RegisterUserType;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Security.Authenticated(BasicAuthRequirement.class)
	public Result index() {
		
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
        response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response().setHeader("EXPIRES", "0");

		String userName = session().get(SessionKey.username.name());
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
		session(SessionKey.loginstate.name(), passwordState);

		String type = loginDetails.getType();
		session(SessionKey.logincategory.name(), type);

		String role = loginDetails.getRole();
		session(SessionKey.userrole.name(), role);

		Long schoolId = loginDetails.getSchoolId();
		if(schoolId != null && schoolId != 0) {
			session(SessionKey.instituteid.name(), Long.toString(schoolId));
		}
		System.out.println("******* 1");
		if(passwordState.equalsIgnoreCase(PasswordState.redirectstate.name())) {
			System.out.println("=========> 1");
			if(type.equalsIgnoreCase(LoginTypeEnum.headinstitute.name()) && role.equalsIgnoreCase("institutegroupadmin"))
				return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
			return ok(dashboard.render(session().get(SessionKey.username.name()), "institutegroupadmin"));
		}
		System.out.println("=========> 2");
		List<LoginDetails> userDetails = null;

		return ok(dashboard.render(session().get(SessionKey.username.name()), session().get(SessionKey.userrole.name())));
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
