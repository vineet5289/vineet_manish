package controllers.login_logout;

import java.util.Map;

import models.LoginDetails;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.forms.LoginForm;
import views.html.viewClass.login;
import controllers.CustomController;
import controllers.routes;
import dao.UserLoginDAO;
import enum_package.SessionKey;

public class LoginController extends CustomController {

	public Result postLogin() {
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
				if(!loginDetails.getError().isEmpty()) {
					flash("error",  "Login credentials not valid.");
					return redirect(controllers.login_logout.routes.LoginController.preLogin());
				}

				session(SessionKey.USER_NAME.name(), userName);
				session(SessionKey.USER_ROLE.name(), loginDetails.getRole().name());
				session(SessionKey.AUTH_TOKEN.name(), loginDetails.getAuthToken());
				session(SessionKey.SCHOOL_ID.name(), loginDetails.getSchoolIdList());
				session(SessionKey.USER_ACCESSRIGHT.name(), loginDetails.getAccessRightList());
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
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		return ok(login.render(loginForm));
	}

	@Security.Authenticated(ActionAuthenticator.class)
	public Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
