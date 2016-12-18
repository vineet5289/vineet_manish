package security.authorization;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import play.Logger;
import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import dao.impl.RedisSessionDao;
import enum_package.RedisKeyPrefix;

public class AuthorizedUser implements Subject {

	private List<Role> roles;
	private List<Permission> permissions;
	private String userName;

	@Inject private RedisSessionDao redisSessionDao;

	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String getIdentifier() {
		System.out.println("======> INSIDE getIdentifier ");
		return userName;
	}

	// this is just for backword com.. will removed
	public static AuthorizedUser getAuthorizedUser(String userName) {
		System.out.println("====== AuthorizedUser.getAuthorizedUser =======");
		AuthorizedUser authorizedUser = new AuthorizedUser();
		authorizedUser.userName = userName;
		return authorizedUser;
	}

	public static AuthorizedUser getAuthorizedUser(String userName, String userAuth, String loginType, String loginState) {
		Logger.info("AuthorizedUser: getAuthorizedUser method is called.");
		AuthorizedUser authorizedUser = new AuthorizedUser();
		try {
			Map<String, String> basicUserInfo = authorizedUser.redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.bui));
			String authTokenFromRedis = authorizedUser.redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), userAuth);
			Set<String> userRoles = authorizedUser.redisSessionDao.getSetMembers(userName, RedisKeyPrefix.of(RedisKeyPrefix.role));
			authorizedUser.setRole(userRoles);

			Set<String> permissions = authorizedUser.redisSessionDao.getSetMembers(userName, RedisKeyPrefix.of(RedisKeyPrefix.permission));
			authorizedUser.setPermission(permissions);

		} catch (Exception exception) {
			Logger.error("AuthorizedUser: getAuthorizedUser method is call failed.");
			exception.printStackTrace();
		}
		authorizedUser.userName = userName;
		return authorizedUser;
	}

	private void setPermission(Set<String> permission) {
		
	}

	private void setRole(Set<String> role) {
		
	}
}
