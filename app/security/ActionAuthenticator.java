package security;

import java.sql.SQLException;
import java.util.Map;

import dao.UserLoginDAO;
import enum_package.SessionKey;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class ActionAuthenticator extends Security.Authenticator{
	@Override
	public String getUsername(Http.Context ctx) {
		SecurityHelper helper = new SecurityHelper();
		Map<String, String> userHeaderCredential = helper.getAuthTokenFromRequest(ctx);
		if(userHeaderCredential == null || userHeaderCredential.size() != 4) {
			return null;
		}
//		UserLoginDAO userLoginDAO = new UserLoginDAO();
//		try {
//			boolean isUserPresent = userLoginDAO.isUserPresent(userHeaderCredential.get(SessionKey.USER_NAME.name()),
//					userHeaderCredential.get(SessionKey.AUTH_TOKEN.name()));
//			return isUserPresent ? userHeaderCredential.get(SessionKey.USER_NAME.name()) : null;
//		} catch (SQLException exception) {
//			exception.printStackTrace();
//			return null;
//		}
		return userHeaderCredential.get(SessionKey.AUTH_TOKEN.name());
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
