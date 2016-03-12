package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.HeaderKey;

public class SecurityHelper {
	public Map<HeaderKey, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<HeaderKey, String> userHeaderCredential = new HashMap<HeaderKey, String>();
//		String[] authTokenHeaderValue = ctx.request().headers().get("X-SRP-TOKEN");
//		String[] userNameHeaderValue = ctx.request().headers().get("SRP-USER-NAME-TOKEN");
		String authTokenValue = ctx.session().get("X-SRP-TOKEN");
		String userNameValue = ctx.session().get("SRP-USER-NAME-TOKEN");

		if(authTokenValue != null && !authTokenValue.isEmpty())
			userHeaderCredential.put(HeaderKey.AUTH_TOKEN, authTokenValue);

		if(userNameValue != null && !userNameValue.isEmpty())
			userHeaderCredential.put(HeaderKey.USER_NAME, userNameValue);

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() == 2)
			return userHeaderCredential;

		return null;
	}
}
