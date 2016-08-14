package security;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import enum_package.SessionKey;

public class BasicAuthRequirement extends Security.Authenticator {
	@Override
	public String getUsername(Http.Context ctx) {
		String userName = ctx.session().get(SessionKey.username.name());
		String userAuth = ctx.session().get(SessionKey.authtoken.name());
		String loginCategory = ctx.session().get(SessionKey.logincategory.name());
		
		if(userName == null || userAuth == null || loginCategory == null) {
			return null;
		}

//		// write redis validation

		return userName;
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
