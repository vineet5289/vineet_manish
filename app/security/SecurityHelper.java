package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.HeaderKey;

public class SecurityHelper {
	public Map<HeaderKey, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<HeaderKey, String> userHeaderCredential = new HashMap<HeaderKey, String>();
		String[] authTokenHeaderValue = ctx.request().headers().get("X-SRP-TOKEN");
		String[] userNameHeaderValue = ctx.request().headers().get("SRP-USER-NAME-TOKEN");
		if(authTokenHeaderValue != null && authTokenHeaderValue.length == 1 &&
				authTokenHeaderValue[0] != null && !authTokenHeaderValue[0].isEmpty())
			userHeaderCredential.put(HeaderKey.AUTH_TOKEN, authTokenHeaderValue[0]);

		if(userNameHeaderValue != null && userNameHeaderValue.length == 1 &&
				userNameHeaderValue[0] != null && !userNameHeaderValue[0].isEmpty())
			userHeaderCredential.put(HeaderKey.USER_NAME, userNameHeaderValue[0]);
		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() == 2)
			return userHeaderCredential;

		return null;
	}
}
