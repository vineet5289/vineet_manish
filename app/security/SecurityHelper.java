package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.SessionKey;

public class SecurityHelper {
	public Map<SessionKey, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<SessionKey, String> userHeaderCredential = new HashMap<SessionKey, String>();
//		String[] authTokenHeaderValue = ctx.request().headers().get("X-SRP-TOKEN");
//		String[] userNameHeaderValue = ctx.request().headers().get("SRP-USER-NAME-TOKEN");
		String authTokenValue = ctx.session().get("SRP-TOKEN");
		String userNameValue = ctx.session().get("SRP-USER-NAME");
		String userRole = ctx.session().get("SRP-USER-ROLE");

		if(authTokenValue != null && !authTokenValue.isEmpty())
			userHeaderCredential.put(SessionKey.AUTH_TOKEN, authTokenValue);

		if(userNameValue != null && !userNameValue.isEmpty())
			userHeaderCredential.put(SessionKey.USER_NAME, userNameValue);

		if(userRole != null && !userRole.isEmpty())
			userHeaderCredential.put(SessionKey.USER_ROLE, userRole);

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() == 3)
			return userHeaderCredential;

		return null;
	}
}
