package controllers.login_logout;

import javax.inject.Inject;

import models.CommonUserCredentials;
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
import enum_package.SessionKey;


public class LoginController extends CustomController {

	@Inject RedisSessionDao redisSessionDao;

	public Result postLogin(String phone) {
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response().setHeader("EXPIRES", "0");

		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
		if (loginForm == null || loginForm.hasErrors()) {
			flash("error", "Login credentials not valid.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		session().clear();

		LoginForm loginUserCredentials = loginForm.get();
		UserLoginDAO userLoginDAO = new UserLoginDAO();
		String userName = loginUserCredentials.getUserName();
		String password = loginUserCredentials.getPassword();

		try {
			CommonUserCredentials commonUserCredentials = userLoginDAO.isValidUserCredentials(userName, password);
			if(commonUserCredentials.getLoginStatus() != LoginStatus.validuser) {
				flash("error",  LoginStatus.of(commonUserCredentials.getLoginStatus()));
				return redirect(controllers.login_logout.routes.LoginController.preLogin());
			}				

			session(SessionKey.username.name(), userName);
			session(SessionKey.authtoken.name(), commonUserCredentials.getAuthToken());
			session(SessionKey.loginstate.name(), commonUserCredentials.getLoginstate());
			session(SessionKey.logintype.name(), commonUserCredentials.getType());
			session(SessionKey.userrole.name(), commonUserCredentials.getRole().trim());

			redisSessionDao.save(commonUserCredentials);
			flash("success", "Welcome, " + commonUserCredentials.getName());
		} catch (Exception exception){
			flash("error", "Server problem occur. Please try after some time");
			session().clear();
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}
		return redirect(routes.SRPController.index());
	}

	public Result preLogin() {
		session().clear();
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response().setHeader("EXPIRES", "0");
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		return ok(homepage.render(loginForm));
	}

	@Security.Authenticated(ActionAuthenticator.class)
	public Result logout() {
		session().remove(SessionKey.username.name());
		session().remove(SessionKey.loginstate.name());
		session().clear();
		flash("success", "You've been logged out");
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
