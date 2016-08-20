package controllers.login_logout;

import javax.inject.Inject;

import models.CommonUserCredentials;
import play.Logger;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.forms.LoginForm;
import views.html.homePage.homepage;
import controllers.CustomController;
import controllers.routes;
import dao.UserLoginDAO;
import dao.impl.RedisSessionDao;
import enum_package.LoginStatus;
import enum_package.LoginType;
import enum_package.SessionKey;


public class LoginController extends CustomController {

	@Inject RedisSessionDao redisSessionDao;

	public Result postLogin(String phone) {
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response().setHeader("EXPIRES", "0");

		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
		if (loginForm == null || loginForm.hasErrors()) {
			flash("error", "Login credentials are not valid.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		session().clear();

		LoginForm loginUserCredentials = loginForm.get();
		String userName = loginUserCredentials.getUserName();
		String password = loginUserCredentials.getPassword();
		try {
			UserLoginDAO userLoginDAO = new UserLoginDAO();
			CommonUserCredentials commonUserCredentials = userLoginDAO.getValidUserCredentials(userName, password, redisSessionDao);
			if(commonUserCredentials.getLoginStatus() != LoginStatus.validuser) {
				flash("error",  LoginStatus.of(commonUserCredentials.getLoginStatus()));
				return redirect(controllers.login_logout.routes.LoginController.preLogin());
			}				


			session(SessionKey.username.name(), userName);
			session(SessionKey.authtoken.name(), commonUserCredentials.getAuthToken());
			session(SessionKey.loginstate.name(), commonUserCredentials.getLoginstate());
			session(SessionKey.logintype.name(), commonUserCredentials.getType());
			session(SessionKey.userrole.name(), commonUserCredentials.getRole().trim());

			flash("success", "Welcome, " + commonUserCredentials.getName());
		} catch (Exception exception){
			flash("error", "Server problem occur. Please try after some time");
			session().clear();
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		String loginType = session().get(SessionKey.logintype.name());
		if(loginType != null && loginType.equals(LoginType.headinstitute.name())) {
			return redirect(routes.SRPController.headInstituteHome());
		} else if(loginType != null && loginType.equals(LoginType.institute.name())) {
			return redirect(routes.SRPController.instituteHome());
		} else if(loginType != null && loginType.equals(LoginType.institute.name())) {
			return redirect(routes.SRPController.studentsHome());
		}

		return redirect(routes.SRPController.index());
	}

	public Result preLogin() {
		Logger.info("info testing log");
		session().clear();
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response().setHeader("EXPIRES", "0");
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		return ok(homepage.render(loginForm));
	}

	@Security.Authenticated(ActionAuthenticator.class)
	public Result logout() {
		String authToken = session().get(SessionKey.authtoken.name());
		String userName = session().get(SessionKey.username.name());

		if(authToken == null || userName == null) {
			session().clear();
			flash("error", "You are already logout.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		session().remove(SessionKey.username.name());
		session().remove(SessionKey.loginstate.name());
		session().clear();

		try {
			UserLoginDAO userLoginDAO = new UserLoginDAO();
			userLoginDAO.logout(userName, authToken, redisSessionDao);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		flash("success", "You've been logged out");
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
