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
		System.out.println("*****index******");
		System.out.println(session().get(SessionKey.USER_NAME.name()));
		System.out.println(session().get(SessionKey.USER_ROLE.name()));
		System.out.println(session().get(SessionKey.AUTH_TOKEN.name()));
		System.out.println("******index*****");
		return ok(profile.render(session().get(SessionKey.USER_NAME.name()), session().get(SessionKey.USER_ROLE.name())));
	}

	public Result postLogin() {
		System.out.println("postLogin*********");
		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
//		Form<AddNewSchoolRequest> registerForm = Form.form(AddNewSchoolRequest.class);
		if (loginForm== null || loginForm.hasErrors()) {
			flash("error", "Login credentials not valid.");
			return redirect(routes.SRPController.preLogin());
			//			return badRequest(index.render(loginForm, registerForm));
		}
		else {
			System.out.println("post login 1");
			session().clear();
			Map<String, String> userDetails = loginForm.data();
			UserLoginDAO userLoginDAO = new UserLoginDAO();
			String userName = userDetails.get("userName");
			String password = userDetails.get("password");
			System.out.println("post login 2");
			try {
				LoginDetails loginDetails = userLoginDAO.isValidUserCredentials(userName, password);
				System.out.println("post login 3");
				if(!loginDetails.getError().isEmpty()) {
					System.out.println("post login 4");
					flash("error", loginDetails.getError());
					return redirect(routes.SRPController.preLogin());
				}
				System.out.println("post login 4");
				session(SessionKey.USER_NAME.name(), userName);
				session(SessionKey.USER_ROLE.name(), loginDetails.getRole().name());
				session(SessionKey.AUTH_TOKEN.name(), loginDetails.getAuthToken());
			} catch (Exception exception){
				System.out.println("post login 5");
				flash("error", "Server problem occur. Please try after some time");
				return redirect(routes.SRPController.preLogin());
			}
			System.out.println("post login 6");
			return redirect(routes.SRPController.index());
		}
	}

	public Result preLogin() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class);
		return ok(index.render(loginForm));
	}

	public Result logout() {
		System.out.println(session().get(SessionKey.USER_NAME.name()));
		System.out.println(session().get(SessionKey.USER_ROLE.name()));
		System.out.println(session().get(SessionKey.AUTH_TOKEN.name()));

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
