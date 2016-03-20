 package security;

import java.util.Map;

import enum_package.SessionKey;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Results;
import play.mvc.Http.Context;
import play.mvc.Result;

public class SecurityController extends Action.Simple{

	@Override
	public Promise<Result> call(Context ctx) throws Throwable {
		SecurityHelper helper = new SecurityHelper();
		Map<String, String> userHeaderCredential = helper.getAuthTokenFromRequest(ctx);
		//TODO:  same as ActionAuthenticator

		Result unauthorized = Results.unauthorized("unauthorized user");
		return F.Promise.pure(unauthorized);
	}

}
