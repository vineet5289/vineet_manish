package security;

import enum_package.SessionKey;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class InstituteRegisterRequestAuthenticator extends Security.Authenticator{
	@Override
	public String getUsername(Http.Context ctx) {
		String referenceNumber = ctx.session().get(SessionKey.REG_SCHOOL_REQUEST_NUMBER.name());
		String authToken = ctx.session().get(SessionKey.OTP_KEY.name());

		if(referenceNumber == null || referenceNumber.isEmpty()
				|| authToken == null || authToken.isEmpty())
			return null;

		return referenceNumber;
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
