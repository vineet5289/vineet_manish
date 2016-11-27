package controllers;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import models.HeadInstituteLoginDetails;
import play.Logger;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;
import security.BasicAuthRequirement;
import security.institute.HeadInstituteBasicAuthCheck;
import views.forms.UserHomePageForm;
import views.html.homePage.schoolRequestHomepage;
import views.html.viewClass.dashboard;
import views.html.viewClass.School.instituteGroupHome;
import dao.UserLoginDAO;
import dao.impl.RedisSessionDao;
import enum_package.LoginState;
import enum_package.LoginStatus;
import enum_package.LoginType;
import enum_package.RegisterUserType;
import enum_package.InstituteUserRole;
import enum_package.SessionKey;

public class SRPController extends CustomController {

	@Inject
	private FormFactory formFactory;

	@Inject RedisSessionDao redisSessionDao;

	@Inject private UserLoginDAO userLoginDAO;

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
		return ok(dashboard.render("uservineet", session().get(SessionKey.userrole.name())));
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result headInstituteHome() {
		String userName = session().get(SessionKey.username.name());
		String authToken = session().get(SessionKey.authtoken.name());
		String loginType = session().get(SessionKey.logintype.name());
		String role = session().get(SessionKey.userrole.name());

		HeadInstituteLoginDetails headInstituteLoginDetails = null;
		try {
			if((role != null && role.equalsIgnoreCase(InstituteUserRole.of(InstituteUserRole.institutegroupadmin)))) {
				headInstituteLoginDetails = userLoginDAO.refreshHeadInstituteUserCredentials(userName, loginType, authToken, redisSessionDao);
			}
		}catch(Exception exception) {
			Logger.error(String.format("exception: server exception occur for username:%s, message:", userName, exception.getMessage()));
			flash("error", LoginStatus.of(headInstituteLoginDetails.getLoginStatus()));
			return redirect(controllers.login_logout.routes.LoginController.logout());
		}

		if(headInstituteLoginDetails.getLoginStatus() != LoginStatus.validuser) {
			Logger.info(String.format("message: username:%s, is not valid user.", userName));
			flash("error", LoginStatus.of(headInstituteLoginDetails.getLoginStatus()));
			return redirect(controllers.login_logout.routes.LoginController.logout());
		}

		long instituteId = 1; // change here as 0 , because in production it shoud be zero
		if(headInstituteLoginDetails.getGropuOfInstitute().equalsIgnoreCase("single")) {
			instituteId = headInstituteLoginDetails.getHeadInstituteId();
		}

		/* 
		 * 1. school type single, add instituteid as session variable
		 * 2. school type group, removed instituteid from sesson variable
		*/
		session(SessionKey.of(SessionKey.headinstituteid), Long.toString(headInstituteLoginDetails.getHeadInstituteId()));
		session(SessionKey.of(SessionKey.instituteid), Long.toString(instituteId));

		if(headInstituteLoginDetails.getGropuOfInstitute().equalsIgnoreCase("single")
				&& headInstituteLoginDetails.getHeadInstituteLoginState().equals(LoginState.redirectstate.name())) {

			Logger.info(String.format("user:%s institute type is single, redirecting it to get mandatory info.", userName));
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		} else if(headInstituteLoginDetails.getGropuOfInstitute().equals("single")) {

			Logger.info(String.format("user:%s institute type is single, redirecting it to home page.", userName));
			return ok(dashboard.render(session().get(SessionKey.username.name()), headInstituteLoginDetails.getHeadInstituteName()));
		} else {

			Logger.info(String.format("user:%s institute type is group, redirecting it to dashboard.", userName));
			return ok(instituteGroupHome.render(headInstituteLoginDetails.getBranches(), headInstituteLoginDetails.getHeadInstituteUserName(), headInstituteLoginDetails.getLogoUrl()));
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

//	@Security.Authenticated(BasicAuthRequirement.class)
	/*
	 * TODO:
	 * 1. get all field from redis and session and if not present in redis then quesy
	 * DB. Update redis as well as set this field in UserHomePageForm
	 * */
    public Result employeeHome() {
      UserHomePageForm userHomePageForm = new UserHomePageForm();
      String userName = session().get(SessionKey.of(SessionKey.username));
      String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
      if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(instituteIdFromSession)) {
        long instituteId = Long.parseLong(instituteIdFromSession);
        userHomePageForm.setImageUrl("");
        userHomePageForm.setUserName(userName);
        userHomePageForm.setUserId(1l);
        userHomePageForm.setName("");
        userHomePageForm.setInstituteId(instituteId);
        userHomePageForm.setInstituteName("");
        userHomePageForm.setInstituteLogoUrl("");
        //in case of every this is correct.... upload user
      } else {
        // some error occur. logout user
      }
      return ok("userHomePageForm");
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
