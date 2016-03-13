package security;

import java.util.Map;


import enum_package.SessionKey;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class ActionAuthenticator extends Security.Authenticator{
	@Override
	public String getUsername(Http.Context ctx) {
		SecurityHelper helper = new SecurityHelper();
		Map<SessionKey, String> userHeaderCredential = helper.getAuthTokenFromRequest(ctx);
		if(userHeaderCredential == null)
			return null;
		return userHeaderCredential.get(SessionKey.USER_NAME);
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.routes.SRPController.index());
	}
}
