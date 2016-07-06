package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.SessionKey;

public class SecurityHelper {
	public Map<String, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<String, String> userHeaderCredential = new HashMap<String, String>();
		String currentUserAuth = ctx.session().get(SessionKey.CURRENT_AUTH_TOKEN.name());
		String currentUserName = ctx.session().get(SessionKey.CURRENT_USER_NAME.name());
		String currentUserRole = ctx.session().get(SessionKey.CURRENT_USER_ROLE.name());
		String currentSchoolIds = ctx.session().get(SessionKey.CURRENT_SCHOOL_ID.name());

		String superUserAuth = ctx.session().get(SessionKey.SUPER_AUTH_TOKEN.name());
		String superUserName = ctx.session().get(SessionKey.SUPER_USER_NAME.name());
		String superUserRole = ctx.session().get(SessionKey.SUPER_USER_ROLE.name());
		String superSchoolIds = ctx.session().get(SessionKey.SUPER_SCHOOL_ID.name());

		if(currentUserAuth != null && !currentUserAuth.isEmpty())
			userHeaderCredential.put(SessionKey.CURRENT_AUTH_TOKEN.name(), currentUserAuth);

		if(currentUserName != null && !currentUserName.isEmpty())
			userHeaderCredential.put(SessionKey.CURRENT_USER_NAME.name(), currentUserName);

		if(currentUserRole != null && !currentUserRole.isEmpty())
			userHeaderCredential.put(SessionKey.CURRENT_USER_ROLE.name(), currentUserRole);

		if(currentSchoolIds != null && !currentSchoolIds.isEmpty())
			userHeaderCredential.put(SessionKey.CURRENT_SCHOOL_ID.name(), currentSchoolIds);


		if(superUserAuth != null && !superUserAuth.isEmpty())
			userHeaderCredential.put(SessionKey.SUPER_AUTH_TOKEN.name(), superUserAuth);

		if(superUserName != null && !superUserName.isEmpty())
			userHeaderCredential.put(SessionKey.SUPER_USER_NAME.name(), superUserName);

		if(superUserRole != null && !superUserRole.isEmpty())
			userHeaderCredential.put(SessionKey.SUPER_USER_ROLE.name(), superUserRole);

		if(superSchoolIds != null && !superSchoolIds.isEmpty())
			userHeaderCredential.put(SessionKey.SUPER_SCHOOL_ID.name(), superSchoolIds);

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() >= 6)
			return userHeaderCredential;

		return null;
	}
}
