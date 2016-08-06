package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.SessionKey;

public class SecurityHelper {
	public Map<String, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<String, String> userHeaderCredential = new HashMap<String, String>();
		String currentUserName = ctx.session().get(SessionKey.USER_NAME.name());
		String currentUserAuth = ctx.session().get(SessionKey.AUTH_TOKEN.name());
		String currentUserRole = ctx.session().get(SessionKey.USER_ROLE.name());
		String currentSchoolIds = ctx.session().get(SessionKey.SCHOOL_ID.name());

		if(currentUserAuth != null && !currentUserAuth.isEmpty())
			userHeaderCredential.put(SessionKey.AUTH_TOKEN.name(), currentUserAuth);

		if(currentUserName != null && !currentUserName.isEmpty())
			userHeaderCredential.put(SessionKey.USER_NAME.name(), currentUserName);

		if(currentUserRole != null && !currentUserRole.isEmpty())
			userHeaderCredential.put(SessionKey.USER_ROLE.name(), currentUserRole);

		if(currentSchoolIds != null && !currentSchoolIds.isEmpty())
			userHeaderCredential.put(SessionKey.SCHOOL_ID.name(), currentSchoolIds);

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() >= 6)
			return userHeaderCredential;

		return null;
	}
}
