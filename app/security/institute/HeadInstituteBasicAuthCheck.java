package security.institute;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import dao.impl.RedisSessionDao;
import enum_package.LoginType;
import enum_package.RedisKeyPrefix;
import enum_package.SessionKey;

public class HeadInstituteBasicAuthCheck extends Security.Authenticator {
	@Inject RedisSessionDao redisSessionDao;
	@Override
	public String getUsername(Http.Context ctx) {
		String userName = ctx.session().get(SessionKey.username.name());
		String userAuth = ctx.session().get(SessionKey.authtoken.name());
		String loginType = ctx.session().get(SessionKey.logintype.name());
		String loginState = ctx.session().get(SessionKey.loginstate.name());

		if(userName == null || userAuth == null || loginType == null || loginState == null
				|| !loginType.equals(LoginType.headinstitute.name())) {
			return null;
		}

		try {
			Map<String, String> otherUserCredentials = redisSessionDao.get(userName , RedisKeyPrefix.of(RedisKeyPrefix.bui));
			Map<String, String> authCredentials = redisSessionDao.get(userName , RedisKeyPrefix.of(RedisKeyPrefix.auth));
			if(otherUserCredentials == null || otherUserCredentials.size() == 0 || authCredentials == null
					|| authCredentials.size() == 0 || !authCredentials.containsKey(userAuth)) {
				System.out.println("invalid user1 , redisrect to login page");
				return null;
			}

			String storedLoginType = otherUserCredentials.get(SessionKey.logintype.name());
			String storedLoginState = otherUserCredentials.get(SessionKey.loginstate.name());
			if(storedLoginType == null || !storedLoginType.equals(loginType)
					|| storedLoginState == null || !storedLoginState.equals(loginState)) {
				System.out.println("invalid user2 , redisrect to login page");
				return null;
			}
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			long loginEpochTime = calendar.getTimeInMillis() / 1000L;
			authCredentials.put(userAuth, Long.toString(loginEpochTime));
			redisSessionDao.save(userName,  RedisKeyPrefix.of(RedisKeyPrefix.auth), userAuth, Long.toString(loginEpochTime));

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} 
		System.out.println("return username " + userName);
		return userName;
	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
