package security;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http;
import enum_package.SessionKey;

public class SecurityHelper {
	public Map<String, String> getAuthTokenFromRequest(Http.Context ctx) {
		Map<String, String> userHeaderCredential = new HashMap<String, String>();
		String currentUserName = ctx.session().get(SessionKey.username.name());
		String currentUserAuth = ctx.session().get(SessionKey.authtoken.name());
		String currentUserRole = ctx.session().get(SessionKey.userrole.name());
		String currentSchoolIds = ctx.session().get(SessionKey.instituteid.name());

		if(currentUserAuth != null && !currentUserAuth.isEmpty())
			userHeaderCredential.put(SessionKey.authtoken.name(), currentUserAuth);

		if(currentUserName != null && !currentUserName.isEmpty())
			userHeaderCredential.put(SessionKey.username.name(), currentUserName);

		if(currentUserRole != null && !currentUserRole.isEmpty())
			userHeaderCredential.put(SessionKey.userrole.name(), currentUserRole);

		if(currentSchoolIds != null && !currentSchoolIds.isEmpty())
			userHeaderCredential.put(SessionKey.instituteid.name(), currentSchoolIds);

		if(!userHeaderCredential.isEmpty() && userHeaderCredential.size() >= 6)
			return userHeaderCredential;

		return null;
	}
}
