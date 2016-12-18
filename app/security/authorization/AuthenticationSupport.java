package security.authorization;

import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class AuthenticationSupport extends Security.Authenticator{
	@Override
	public String getUsername(final Http.Context context) {
		return "vineet";
	}

	@Override
	 public Result onUnauthorized(Context ctx) {
         return redirect(controllers.login_logout.routes.LoginController.preLogin());
     }
}
