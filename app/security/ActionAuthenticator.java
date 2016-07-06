package security;

import java.util.Map;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import enum_package.SessionKey;

public class ActionAuthenticator extends Security.Authenticator{
	@Override
	public String getUsername(Http.Context ctx) {
		SecurityHelper helper = new SecurityHelper();
		Map<String, String> userHeaderCredential = helper.getAuthTokenFromRequest(ctx);
		if(userHeaderCredential == null || userHeaderCredential.size() < 6) {
			return null;
		}
/*
 * write function that will check redis and validate
 * 1. super_username + auth
 * 2. current_username + auth
 * 3. mapping from supperusername  => current_username
 * 4. redis contain userName as key and value ('SU', authkey) or ('S', 'authkey')
 * 5. SU=> authkey generated for current user during SU login
 * 6. 'S' => authkey generated when this user login
 * */		
		return userHeaderCredential.get(SessionKey.CURRENT_AUTH_TOKEN.name());
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
