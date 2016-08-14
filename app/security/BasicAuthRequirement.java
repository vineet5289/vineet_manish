package security;

import java.util.Map;

import javax.inject.Inject;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import dao.impl.RedisSessionDao;
import enum_package.RedisKeyPrefix;
import enum_package.SessionKey;

public class BasicAuthRequirement extends Security.Authenticator {
	@Inject RedisSessionDao redisSessionDao;
	@Override
	public String getUsername(Http.Context ctx) {
		String userName = ctx.session().get(SessionKey.username.name());
		String userAuth = ctx.session().get(SessionKey.authtoken.name());
		String loginType = ctx.session().get(SessionKey.logintype.name());
		String loginState = ctx.session().get(SessionKey.loginstate.name());
		if(userName == null || userAuth == null || loginType == null || loginState == null) {
			return null;
		}
		try {
			Map<String, String> userBasicCredentials = redisSessionDao.get(userName , RedisKeyPrefix.buc.name());

			if(userBasicCredentials == null || userBasicCredentials.size() == 0) {
				return null;
			}
			String storeUserName = userBasicCredentials.get(SessionKey.username.name());
			String storeAuthToke = userBasicCredentials.get(SessionKey.authtoken.name());
			String storeLoginType = userBasicCredentials.get(SessionKey.logintype.name());
			String storeLoginState = userBasicCredentials.get(SessionKey.loginstate.name());
			if( storeUserName == null || storeAuthToke == null || !storeUserName.equals(userName)
					|| !storeAuthToke.equals(userAuth) || storeLoginType == null || !storeLoginType.equals(loginType)
					|| storeLoginState == null || !storeLoginState.equals(loginState)) {
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
