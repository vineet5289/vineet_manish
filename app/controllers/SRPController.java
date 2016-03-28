package controllers;

import java.util.Map;

import models.LoginDetails;
import dao.UserLoginDAO;
import enum_package.SessionKey;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.forms.*;
import views.html.profile;
import views.html.index;

public class SRPController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result index() {
		response().setHeader("Cache-Control", "no-cache");
		return ok(profile.render(session().get(SessionKey.USER_NAME.name()), session().get(SessionKey.USER_ROLE.name())));
	}

	public Result postLogin() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
		//		Form<AddNewSchoolRequest> registerForm = Form.form(AddNewSchoolRequest.class);
		if (loginForm== null || loginForm.hasErrors()) {
			flash("error", "Login credentials not valid.");
			return redirect(routes.SRPController.preLogin());
			//			return badRequest(index.render(loginForm, registerForm));
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
					flash("error", loginDetails.getError());
					return redirect(routes.SRPController.preLogin());
				}
				session(SessionKey.USER_NAME.name(), userName);
				session(SessionKey.USER_ROLE.name(), loginDetails.getRole().name());
				session(SessionKey.AUTH_TOKEN.name(), loginDetails.getAuthToken());
			} catch (Exception exception){
				flash("error", "Server problem occur. Please try after some time");
				return redirect(routes.SRPController.preLogin());
			}
			response().setHeader("Cache-Control", "no-cache");
			return redirect(routes.SRPController.index());
		}
	}

	public Result preLogin() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		return ok(index.render(loginForm));
	}

	public Result logout() {
		String authToken = session().get(SessionKey.AUTH_TOKEN.name());
		String userName = session().get(SessionKey.USER_NAME.name());
		UserLoginDAO userLoginDAO = new UserLoginDAO();
		try{
			userLoginDAO.logout(userName);
		} catch (Exception exception){
			exception.printStackTrace();
		}
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.SRPController.preLogin());
	}
}
