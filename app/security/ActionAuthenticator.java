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
		System.out.println("ActionAuthenticator 1");
		SecurityHelper helper = new SecurityHelper();
		System.out.println("ActionAuthenticator 2");
		Map<String, String> userHeaderCredential = helper.getAuthTokenFromRequest(ctx);
		System.out.println("ActionAuthenticator 3");
		if(userHeaderCredential == null || userHeaderCredential.size() != 3) {
			System.out.println("ActionAuthenticator 4");
			return null;
		}
		System.out.println("size =" + userHeaderCredential.size());
		System.out.println("USER_NAME= " + userHeaderCredential.get(SessionKey.USER_NAME.name()));
		System.out.println("AUTH_TOKEN=" +userHeaderCredential.get(SessionKey.AUTH_TOKEN.name()));
		System.out.println("USER_ROLE=" +userHeaderCredential.get(SessionKey.USER_ROLE.name()));
		UserLoginDAO userLoginDAO = new UserLoginDAO();
		try {
			boolean isUserPresent = userLoginDAO.isUserPresent(userHeaderCredential.get(SessionKey.USER_NAME.name()),
					userHeaderCredential.get(SessionKey.AUTH_TOKEN.name()));
			return isUserPresent ? userHeaderCredential.get(SessionKey.USER_NAME.name()) : null;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.routes.SRPController.preLogin());
	}
}
