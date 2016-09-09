package dao;

import java.util.Map;
import java.util.Set;

import models.LoginDetails;

public interface SessionDao {
	public void save(String userName, String redisPrefix, Map<String, String> redisValue) throws Exception;
	public Map<String, String> get(String userName, String redisPrefix) throws Exception;

	public void save(LoginDetails loginDetails) throws Exception;
	public boolean save(String userName, String redisPrefix, String field, String value) throws Exception;

	public LoginDetails get(String userName) throws Exception;
	public String get(String userName, String redisPrefix, String field) throws Exception;

	public boolean removed(String userName, String redisPrefix, String... field) throws Exception;
	public boolean removedAll(String userName, String redisPrefix) throws Exception;

	public Set<String> getRoles(String userName, String redisPrefix);
	public Set<String> getPermission(String userName, String redisPrefix);
}
