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
import views.html.*;

public class SRPController extends CustomController {

	public Result index() {
		System.out.println("**************");
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		Form<RegisterSchool> registerForm = Form.form(RegisterSchool.class);
		return ok(index.render(loginForm, registerForm));
	}

	@Security.Authenticated(ActionAuthenticator.class)
	public Result home() {
		
		return ok(dashboard4.render());
	}

	public Result postLogin() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
		Form<RegisterSchool> registerForm = Form.form(RegisterSchool.class);
		if (loginForm== null || loginForm.hasErrors()) {
			flash("error", "Login credentials not valid.");
			return redirect(routes.SRPController.index());
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
					return redirect(routes.SRPController.index());
				}
				session("SRP-USER-NAME", userName);
				session("SRP-USER-ROLE", loginDetails.getRole().name());
				session("SRP-TOKEN", loginDetails.getAuthToken());
			} catch (Exception exception){
				flash("error", "Server problem occur. Please try after some time");
				return redirect(routes.SRPController.index());
			}

			return redirect(routes.SRPController.home());
		}
	}

	public Result preLogin() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		Form<RegisterSchool> registerForm = Form.form(RegisterSchool.class).bindFromRequest();
		return ok(index.render(loginForm, registerForm));
	}

	public Result logout() {
		String authToken = session().get(SessionKey.AUTH_TOKEN);
		String userName = session().get(SessionKey.USER_NAME);
		UserLoginDAO userLoginDAO = new UserLoginDAO();
		try{
			userLoginDAO.logout(userName);
		} catch (Exception exception){
			exception.printStackTrace();
		}
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.SRPController.index());
	}
}
