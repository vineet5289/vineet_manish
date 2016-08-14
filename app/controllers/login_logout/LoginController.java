package controllers.login_logout;

import java.util.Map;

import javax.inject.Inject;

import models.LoginDetails;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import security.ActionAuthenticator;
import views.forms.LoginForm;
import views.html.homePage.homepage;
import controllers.CustomController;
import controllers.routes;
import dao.UserLoginDAO;
import dao.connection.RedisConnectionPool;
import enum_package.LoginStatus;
import enum_package.SessionKey;


public class LoginController extends CustomController {

	@Inject RedisConnectionPool redisConnectionPool;

	public Result postLogin(String phone) {
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");  // HTTP 1.1
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response().setHeader("EXPIRES", "0");

		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
		if (loginForm == null || loginForm.hasErrors()) {
			flash("error", "Login credentials not valid.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}
		else {
			session().clear();
			Map<String, String> userDetails = loginForm.data();
			UserLoginDAO userLoginDAO = new UserLoginDAO();
			String userName = userDetails.get("userName");
			String password = userDetails.get("password");

			try {
				LoginDetails loginDetails = userLoginDAO.isValidUserCredentials(userName, password);
				if(loginDetails.getLoginStatus() != LoginStatus.validuser) {
					flash("error",  LoginStatus.of(loginDetails.getLoginStatus()));
					return redirect(controllers.login_logout.routes.LoginController.preLogin());
				}				

				session(SessionKey.username.name(), userName);
				session(SessionKey.authtoken.name(), loginDetails.getAuthToken());
				session(SessionKey.loginstate.name(), loginDetails.getPasswordState());
				session(SessionKey.logincategory.name(), loginDetails.getType());
				session(SessionKey.userrole.name(), loginDetails.getRole().trim());
				Jedis jedis = redisConnectionPool.getJedisPool().getResource();
				jedis.set("test", "tes");
				Long schoolId = loginDetails.getSchoolId();
				if(schoolId != null && schoolId != 0) {
					session(SessionKey.instituteid.name(), Long.toString(schoolId));
				}

			} catch (Exception exception){
				flash("error", "Server problem occur. Please try after some time");
				session().clear();
				return redirect(controllers.login_logout.routes.LoginController.preLogin());
			}
			return redirect(routes.SRPController.index());
		}
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
