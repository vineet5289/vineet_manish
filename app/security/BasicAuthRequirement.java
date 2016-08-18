package security;

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
		System.out.println("=============> 1");
		String userName = ctx.session().get(SessionKey.username.name());
		String userAuth = ctx.session().get(SessionKey.authtoken.name());
		String loginType = ctx.session().get(SessionKey.logintype.name());
		String loginState = ctx.session().get(SessionKey.loginstate.name());
		System.out.println("=============>authtoken " + userAuth);

		if(userName == null || userAuth == null || loginType == null || loginState == null) {
			return null;
		}
		try {
			System.out.println("=============> 2");
			String authTokenFromRedis = redisSessionDao.get(userName , RedisKeyPrefix.of(RedisKeyPrefix.auth), userAuth);
			System.out.println("=============> authTokenFromRedis" + authTokenFromRedis);
			if(authTokenFromRedis == null || authTokenFromRedis.isEmpty()) {
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
