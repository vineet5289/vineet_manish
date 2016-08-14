package controllers;

import java.util.List;

import javax.inject.Inject;

import models.HeadInstituteLoginDetails;
import models.LoginDetails;
import play.mvc.Result;
import play.mvc.Security;
import security.BasicAuthRequirement;
import views.html.homePage.schoolRequestHomepage;
import views.html.viewClass.dashboard;
import dao.UserLoginDAO;
import dao.impl.RedisSessionDao;
import enum_package.LoginStatus;
import enum_package.LoginType;
import enum_package.LoginState;
import enum_package.RegisterUserType;
import enum_package.Role;
import enum_package.SessionKey;
import  views.html.viewClass.School.instituteGroupHome;

public class SRPController extends CustomController {

	@Inject RedisSessionDao redisSessionDao;

	@Security.Authenticated(BasicAuthRequirement.class)
	public Result index() {
		
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
        response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response().setHeader("EXPIRES", "0");
        
		String userName = session().get(SessionKey.username.name());
		String loginCategory = session().get(SessionKey.logintype.name());
		String role = session().get(SessionKey.userrole.name());
		UserLoginDAO userLoginDAO = new UserLoginDAO();
		try {
			if(LoginType.headinstitute == LoginType.valueOf(loginCategory)
					&& (role != null && role.equalsIgnoreCase(Role.institutegroupadmin.name()))) {
				HeadInstituteLoginDetails headInstituteLoginDetails = userLoginDAO.refreshHeadInstituteUserCredentials(userName, loginCategory);
				if(headInstituteLoginDetails.getLoginStatus() != LoginStatus.validuser) {
					flash("error", LoginStatus.of(headInstituteLoginDetails.getLoginStatus()));
					//redirect
				}
				
				if(headInstituteLoginDetails.getNumberOfInstitute() == 1) {
					return ok(dashboard.render(session().get(SessionKey.username.name()), "institutegroupadmin"));
				} else {
					
					return ok(instituteGroupHome.render(headInstituteLoginDetails.branchs));
				}
			}
		}catch(Exception exception) {
			exception.printStackTrace();
		}


		LoginDetails loginDetails = null;
		boolean shouldLogout = true;
		try {
			
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
		session(SessionKey.logintype.name(), type);

		role = loginDetails.getRole();
		session(SessionKey.userrole.name(), role);

		Long schoolId = loginDetails.getSchoolId();
		if(schoolId != null && schoolId != 0) {
			session(SessionKey.instituteid.name(), Long.toString(schoolId));
		}
		System.out.println("******* 1");
		if(passwordState.equalsIgnoreCase(LoginState.redirectstate.name())) {
			System.out.println("=========> 1");
			if(type.equalsIgnoreCase(LoginType.headinstitute.name()) && role.equalsIgnoreCase("institutegroupadmin"))
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
			session(SessionKey.reguserrequest.name(), RegisterUserType.SCHOOL.name());
			return ok(schoolRequestHomepage.render());
		}

		if(userType.trim().equalsIgnoreCase("employee")) {
			session(SessionKey.reguserrequest.name(), RegisterUserType.EMPLOYEE.name());
		}

		if(userType.trim().equalsIgnoreCase("other user")) {
			session(SessionKey.reguserrequest.name(), RegisterUserType.OTHER_USER.name());
		}

		if(userType.equalsIgnoreCase("error")) {
			System.out.println("error");
		}
		return ok("pre registration");
	}
}
