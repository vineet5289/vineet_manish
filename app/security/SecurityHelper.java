package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.SessionKey;

public class SecurityHelper {
	public Map<String, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<String, String> userHeaderCredential = new HashMap<String, String>();
//		String[] authTokenHeaderValue = ctx.request().headers().get("X-SRP-TOKEN");
//		String[] userNameHeaderValue = ctx.request().headers().get("SRP-USER-NAME-TOKEN");
		String authTokenValue = ctx.session().get(SessionKey.AUTH_TOKEN.name());
		String userNameValue = ctx.session().get(SessionKey.USER_NAME.name());
		String userRole = ctx.session().get(SessionKey.USER_ROLE.name());

		if(authTokenValue != null && !authTokenValue.isEmpty())
			userHeaderCredential.put(SessionKey.AUTH_TOKEN.name(), authTokenValue);

		if(userNameValue != null && !userNameValue.isEmpty())
			userHeaderCredential.put(SessionKey.USER_NAME.name(), userNameValue);

		if(userRole != null && !userRole.isEmpty())
			userHeaderCredential.put(SessionKey.USER_ROLE.name(), userRole);

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() == 3)
			return userHeaderCredential;

		return null;
	}
}
