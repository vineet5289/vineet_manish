package controllers;

import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.forms.LoginForm;

public class SRPController extends CustomController {

	public Result index() {
		return ok("welcome to main page");
	}

	@Security.Authenticated(ActionAuthenticator.class)
	public Result home() {
		return ok("welcome to home page");
	}

//	public Result PostLogin() {
//		Form<LoginForm> formData = Form.form(LoginForm.class).bindFromRequest();
//		if (formData.hasErrors()) {
//			flash("error", "Login credentials not valid.");
//			return badRequest(Login.render("Login", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
//		}
//		else {
//			// email/password OK, so now we set the session variable and only go to authenticated pages.
//			session().clear();
//			session("email", formData.get().email);
//			return redirect(routes.Application.profile());
//		}
//		return ok("");
//	}

	public static Result preLogin() {
		Form<LoginForm> formData = Form.form(LoginForm.class).bindFromRequest();
	    return ok(Login.render("Login", false, false, formData));
	  }
}