package security;

import java.util.Map;

import enum_package.HeaderKey;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class ActionAuthenticator extends Security.Authenticator{
	@Override
	public String getUsername(Http.Context ctx) {
		SecurityHelper helper = new SecurityHelper();
		Map<HeaderKey, String> userHeaderCredential = helper.getAuthTokenFromRequest(ctx);
		//TODO: find unique token based on this two value
		return null;
	}

	public Result onUnauthorized(Http.Context ctx) {
//		super.onUnauthorized(ctx);
		return redirect("/");
	}
}
