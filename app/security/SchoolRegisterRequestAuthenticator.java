package security;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class SchoolRegisterRequestAuthenticator extends Security.Authenticator{
	@Override
	public String getUsername(Http.Context ctx) {
		String referenceNumber = ctx.session().get("REFERENCE-NUMBER");
		String authToken = ctx.session().get("AUTH-TOKEN");

		if(referenceNumber == null || referenceNumber.isEmpty()
				|| authToken == null || authToken.isEmpty())
			return null;

		return referenceNumber;
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.routes.SRPController.preLogin());
	}
}
