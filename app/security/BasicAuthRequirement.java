package security;

import javax.inject.Inject;

import models.LoginDetails;
import dao.impl.RedisSessionDao;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import enum_package.LoginType;
import enum_package.LoginState;
import enum_package.SessionKey;

public class BasicAuthRequirement extends Security.Authenticator {
	@Inject RedisSessionDao redisSessionDao;
	@Override
	public String getUsername(Http.Context ctx) {
		String userName = ctx.session().get(SessionKey.username.name());
		String userAuth = ctx.session().get(SessionKey.authtoken.name());
		String loginCategory = ctx.session().get(SessionKey.logintype.name());
		String loginState = ctx.session().get(SessionKey.loginstate.name());
		if(userName == null || userAuth == null || loginCategory == null || loginState == null) {
			return null;
		}
		try {
			LoginDetails loginDetails = redisSessionDao.get(userName);
			if(loginDetails.getUserName() == null || loginDetails.getAuthToken() == null
					|| !loginDetails.getUserName().equals(userName) || !loginDetails.getAuthToken().equals(userAuth)
					|| !LoginState.contain(SessionKey.loginstate.name()) || !LoginType.contain(SessionKey.logintype.name())) {
				return null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} 
		return userName;
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
