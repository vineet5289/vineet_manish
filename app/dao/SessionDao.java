package dao;

import java.util.Map;

import models.CommonUserCredentials;
import models.LoginDetails;

public interface SessionDao {
	public void save(LoginDetails loginDetails) throws Exception;
	public void save(CommonUserCredentials commonUserCredentials) throws Exception;
	public LoginDetails get(String userName) throws Exception;
	public Map<String, String> get(String userName, String redisPrefix) throws Exception;
}
