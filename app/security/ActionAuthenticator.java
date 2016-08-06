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
		
		return userHeaderCredential.get(SessionKey.AUTH_TOKEN.name());
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}

	private Map<String, String> getSessionKeyFromRequest(Http.Context ctx) {
		Map<String, String> userHeaderCredential = new HashMap<String, String>();
		String userName = ctx.session().get(SessionKey.USER_NAME.name());
		String userAuth = ctx.session().get(SessionKey.AUTH_TOKEN.name());
		String loginType = ctx.session().get(SessionKey.LOGIN_TYPE.name());
		String schoolId = ctx.session().get(SessionKey.SCHOOL_ID.name());
		String shiftId = ctx.session().get(SessionKey.SHIFT_ID.name());
		String childId = ctx.session().get(SessionKey.CHILD_ID.name());
		String userRole = ctx.session().get(SessionKey.USER_ROLE.name());
		String userAccessRights = ctx.session().get(SessionKey.USER_ACCESSRIGHT.name());

		if(userName != null && !userName.trim().isEmpty())
			userHeaderCredential.put(SessionKey.USER_NAME.name(), userName.trim());

		if(userAuth != null && !userAuth.trim().isEmpty())
			userHeaderCredential.put(SessionKey.AUTH_TOKEN.name(), userAuth.trim());

		if(loginType != null && !loginType.trim().isEmpty())
			userHeaderCredential.put(SessionKey.LOGIN_TYPE.name(), loginType.trim());

		if(schoolId != null && !schoolId.trim().isEmpty())
			userHeaderCredential.put(SessionKey.SCHOOL_ID.name(), schoolId.trim());

		if(shiftId != null && !shiftId.trim().isEmpty())
			userHeaderCredential.put(SessionKey.SHIFT_ID.name(), shiftId.trim());

		if(childId != null && !childId.trim().isEmpty())
			userHeaderCredential.put(SessionKey.CHILD_ID.name(), childId.trim());

		if(userRole != null && !userRole.trim().isEmpty())
			userHeaderCredential.put(SessionKey.USER_ROLE.name(), userRole.trim());

		if(userAccessRights != null && !userAccessRights.trim().isEmpty())
			userHeaderCredential.put(SessionKey.USER_ACCESSRIGHT.name(), userAccessRights.trim());

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() >= 3)
			return userHeaderCredential;

		return null;
	}
}
