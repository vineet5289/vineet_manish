package security.authorization.subjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import play.Logger;
import security.authorization.permissions.UserPermission;
import security.authorization.roles.SecurityRole;
import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import dao.impl.RedisSessionDao;
import enum_package.RedisKeyPrefix;

public class InstituteAuthorizedUser implements Subject {

	private List<Role> roles;
	private List<Permission> permissions;
	private String userName;
//	private RedisSessionDao redisSessionDao;
//
//	@Inject
//	public InstituteAuthorizedUser(RedisSessionDao redisSessionDao) {
//		this.redisSessionDao = redisSessionDao;
//	}

	@Override
	public List<? extends Role> getRoles() {
		Logger.info("InstituteAuthorizedUser: getRoles method is called.");
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		Logger.info("InstituteAuthorizedUser: getPermissions method is called.");
		return permissions;
	}

	@Override
	public String getIdentifier() {
		Logger.info("InstituteAuthorizedUser: getIdentifier method is called.");
		return userName;
	}

	public static InstituteAuthorizedUser getAuthorizedUser(String userName, String userAuth, String loginType, String loginState, RedisSessionDao redisSessionDao) {
		Logger.info("InstituteAuthorizedUser: getAuthorizedUser method is called.");
		InstituteAuthorizedUser authorizedUser = new InstituteAuthorizedUser();
		try {
			Map<String, String> basicUserInfo = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.bui));
			String authTokenFromRedis = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), userAuth);
			System.out.println("basicUserInfo : " + basicUserInfo);
			System.out.println("authTokenFromRedis : " + authTokenFromRedis);
			if(basicUserInfo == null || authTokenFromRedis == null) {
				Logger.error("InstituteAuthorizedUser: getAuthorizedUser either user basic info or auth token is not present in redis."
						+ ", returning null object");
				return null;
			}

			Set<String> userRoles = redisSessionDao.getSetMembers(userName, RedisKeyPrefix.of(RedisKeyPrefix.role));
			System.out.println("userRoles: " + userRoles);
			authorizedUser.setRole(userRoles);

			Set<String> permissions = redisSessionDao.getSetMembers(userName, RedisKeyPrefix.of(RedisKeyPrefix.permission));
			authorizedUser.setPermission(permissions);
			authorizedUser.userName = userName;

		} catch (Exception exception) {
			Logger.error("InstituteAuthorizedUser: getAuthorizedUser method is call failed.");
			exception.printStackTrace();
			return null;
		}

		return authorizedUser;
	}

	private void setPermission(Set<String> permissionsFromCache) {
		permissions = new ArrayList<Permission>();
		if(permissionsFromCache == null || permissionsFromCache.size() == 0) {
			return;
		}
		Iterator<String> iter = permissionsFromCache.iterator();
		while (iter.hasNext()) {
			permissions.add(new UserPermission(iter.next()));
		}
	}

	private void setRole(Set<String> rolesFromCache) {
		roles = new ArrayList<Role>();
		if(rolesFromCache == null || rolesFromCache.size() == 0) {
			return;
		}

		Iterator<String> iter = rolesFromCache.iterator();
		while (iter.hasNext()) {
			roles.add(new SecurityRole(iter.next()));
		}
	}
}
