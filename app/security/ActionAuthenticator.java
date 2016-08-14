package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import enum_package.SessionKey;

public class ActionAuthenticator extends Security.Authenticator{

	@Override
	public String getUsername(Http.Context ctx) {
		Map<String, String> userHeaderCredential = getSessionKeyFromRequest(ctx);
		if(userHeaderCredential == null || userHeaderCredential.size() < 3) {
			return null;
		}

		// write redis validation
		
		return userHeaderCredential.get(SessionKey.authtoken.name());
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}

	private Map<String, String> getSessionKeyFromRequest(Http.Context ctx) {
		Map<String, String> userHeaderCredential = new HashMap<String, String>();
		String userName = ctx.session().get(SessionKey.username.name());
		String userAuth = ctx.session().get(SessionKey.authtoken.name());
		String loginType = ctx.session().get(SessionKey.logincategory.name());
		String schoolId = ctx.session().get(SessionKey.instituteid.name());
		String shiftId = ctx.session().get(SessionKey.shiftid.name());
		String childId = ctx.session().get(SessionKey.childid.name());
		String userRole = ctx.session().get(SessionKey.userrole.name());
		String userAccessRights = ctx.session().get(SessionKey.useraccessright.name());

		if(userName != null && !userName.trim().isEmpty())
			userHeaderCredential.put(SessionKey.username.name(), userName.trim());

		if(userAuth != null && !userAuth.trim().isEmpty())
			userHeaderCredential.put(SessionKey.authtoken.name(), userAuth.trim());

		if(loginType != null && !loginType.trim().isEmpty())
			userHeaderCredential.put(SessionKey.logincategory.name(), loginType.trim());

		if(schoolId != null && !schoolId.trim().isEmpty())
			userHeaderCredential.put(SessionKey.instituteid.name(), schoolId.trim());

		if(shiftId != null && !shiftId.trim().isEmpty())
			userHeaderCredential.put(SessionKey.shiftid.name(), shiftId.trim());

		if(childId != null && !childId.trim().isEmpty())
			userHeaderCredential.put(SessionKey.childid.name(), childId.trim());

		if(userRole != null && !userRole.trim().isEmpty())
			userHeaderCredential.put(SessionKey.userrole.name(), userRole.trim());

		if(userAccessRights != null && !userAccessRights.trim().isEmpty())
			userHeaderCredential.put(SessionKey.useraccessright.name(), userAccessRights.trim());

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() >= 3)
			return userHeaderCredential;

		return null;
	}
}
