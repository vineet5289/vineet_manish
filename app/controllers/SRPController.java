package controllers;

import models.HeadInstituteLoginDetails;
import play.mvc.Result;
import play.mvc.Security;
import security.BasicAuthRequirement;
import security.institute.HeadInstituteBasicAuthCheck;
import views.html.homePage.schoolRequestHomepage;
import views.html.viewClass.dashboard;
import views.html.viewClass.School.instituteGroupHome;
import dao.UserLoginDAO;
import enum_package.LoginState;
import enum_package.LoginStatus;
import enum_package.LoginType;
import enum_package.RegisterUserType;
import enum_package.Role;
import enum_package.SessionKey;

public class SRPController extends CustomController {

//	@Inject RedisSessionDao redisSessionDao;

	@Security.Authenticated(BasicAuthRequirement.class)
	public Result index() {

		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response().setHeader("EXPIRES", "0");

		String loginType = session().get(SessionKey.logintype.name());
		if(loginType.equals(LoginType.headinstitute.name())) {
			return redirect(routes.SRPController.headInstituteHome());
		} else if(loginType.equals(LoginType.institute.name())) {
			return redirect(routes.SRPController.instituteHome());
		} else if(loginType.equals(LoginType.institute.name())) {
			return redirect(routes.SRPController.studentsHome());
		}

		//		LoginDetails loginDetails = null;
		//		boolean shouldLogout = true;
		//		try {
		//			
		//			loginDetails = userLoginDAO.refreshUserUsingUserName(userName);
		//			shouldLogout = false;
		//		} catch(Exception exception) {
		//			exception.printStackTrace();
		//			flash("error", "Server problem occur during refresh.");
		//		}
		//
		//		if(shouldLogout || loginDetails == null || loginDetails.getLoginStatus() != LoginStatus.validuser) {
		//			session().clear();
		//			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		//		}
		//
		//		String passwordState = loginDetails.getPasswordState();
		//		session(SessionKey.loginstate.name(), passwordState);
		//
		//		String type = loginDetails.getType();
		//		session(SessionKey.logintype.name(), type);
		//
		//		role = loginDetails.getRole();
		//		session(SessionKey.userrole.name(), role);
		//
		//		Long schoolId = loginDetails.getSchoolId();
		//		if(schoolId != null && schoolId != 0) {
		//			session(SessionKey.instituteid.name(), Long.toString(schoolId));
		//		}
		//		System.out.println("******* 1");
		//		if(passwordState.equalsIgnoreCase(LoginState.redirectstate.name())) {
		//			System.out.println("=========> 1");
		//			if(type.equalsIgnoreCase(LoginType.headinstitute.name()) && role.equalsIgnoreCase("institutegroupadmin"))
		//				return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		//			return ok(dashboard.render(session().get(SessionKey.username.name()), "institutegroupadmin"));
		//		}
		//		System.out.println("=========> 2");
		//		List<LoginDetails> userDetails = null;

		return ok(dashboard.render("uservineet", session().get(SessionKey.userrole.name())));
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result headInstituteHome() {
		String userName = session().get(SessionKey.username.name());
		String loginType = session().get(SessionKey.logintype.name());
		String role = session().get(SessionKey.userrole.name());

		UserLoginDAO userLoginDAO = new UserLoginDAO();
		HeadInstituteLoginDetails headInstituteLoginDetails = null;
		try {
			if((role != null && role.equalsIgnoreCase(Role.institutegroupadmin.name()))) {
				headInstituteLoginDetails = userLoginDAO.refreshHeadInstituteUserCredentials(userName, loginType);
			}
		}catch(Exception exception) {
			exception.printStackTrace();
			headInstituteLoginDetails = new HeadInstituteLoginDetails();
			headInstituteLoginDetails.setLoginStatus(LoginStatus.servererror);
		}

		if(headInstituteLoginDetails.getLoginStatus() != LoginStatus.validuser) {
			flash("error", LoginStatus.of(headInstituteLoginDetails.getLoginStatus()));
			session().clear();
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		session(SessionKey.instituteid.name(), Long.toString(headInstituteLoginDetails.getHeadInstituteId()));

		if(headInstituteLoginDetails.getGropuOfInstitute().equals("single")
				&& headInstituteLoginDetails.getHeadInstituteLoginState().equals(LoginState.redirectstate.name())) {
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		} else if(headInstituteLoginDetails.getGropuOfInstitute().equals("single")) {
			return ok(dashboard.render(session().get(SessionKey.username.name()), "institutegroupadmin"));
		} else {
			return ok(instituteGroupHome.render(headInstituteLoginDetails.getBranches(), "vineet"));
		}
	}

	@Security.Authenticated(BasicAuthRequirement.class)
	public Result instituteHome() {
		return ok("");
	}

	@Security.Authenticated(BasicAuthRequirement.class)
	public Result studentsHome() {
		return ok("");
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
