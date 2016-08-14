package dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import redis.clients.jedis.Jedis;
import models.CommonUserCredentials;
import models.LoginDetails;
import dao.SessionDao;
import dao.connection.RedisConnectionPool;
import enum_package.RedisKeyPrefix;
import enum_package.SessionKey;

public class RedisSessionDao implements SessionDao {
	private RedisConnectionPool redisConnectionPool;	
	@Inject
	public RedisSessionDao(RedisConnectionPool redisConnectionPool) {
		this.redisConnectionPool = redisConnectionPool;
	}

	@Override
	public void save(CommonUserCredentials commonUserCredentials) throws Exception {
		Map<String, String> redisValue = new HashMap<String, String>();
		redisValue.put(SessionKey.username.name(), commonUserCredentials.getUserName());
		redisValue.put(SessionKey.authtoken.name(), commonUserCredentials.getAuthToken());
		redisValue.put(SessionKey.logintype.name(), commonUserCredentials.getType());
		redisValue.put(SessionKey.loginstate.name(), commonUserCredentials.getLoginstate());
		redisValue.put(SessionKey.userrole.name(), commonUserCredentials.getRole());
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		jedis.hmset(RedisKeyPrefix.buc.name() + ":" + commonUserCredentials.getUserName(), redisValue);
		jedis.close();
		
	}

	@Override
	public Map<String, String> get(String userName, String redisPrefix) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Map<String, String> redisValue = jedis.hgetAll(redisPrefix + ":" + userName);
		jedis.close();
		return redisValue;
	}

	@Override
	public void save(LoginDetails loginDetails) throws Exception {
		Map<String, String> redisValue = new HashMap<String, String>();
		redisValue.put(SessionKey.username.name(), loginDetails.getUserName());
		redisValue.put(SessionKey.authtoken.name(), loginDetails.getAuthToken());
		redisValue.put(SessionKey.logintype.name(), loginDetails.getType());
		redisValue.put(SessionKey.loginstate.name(), loginDetails.getPasswordState());
		redisValue.put(SessionKey.userrole.name(), loginDetails.getRole());
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		jedis.hmset("session:" + loginDetails.getUserName(), redisValue);
		jedis.close();
	}

	@Override
	public LoginDetails get(String userName) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Map<String, String> redisValue = jedis.hgetAll(RedisKeyPrefix.buc.name() + ":" + userName);
		jedis.close();
		LoginDetails loginDetails = new LoginDetails();
		loginDetails.setUserName(redisValue.get(SessionKey.username.name()));
		loginDetails.setAuthToken(redisValue.get(SessionKey.authtoken.name()));
		loginDetails.setType(redisValue.get(SessionKey.logintype.name()));
		loginDetails.setPasswordState(redisValue.get(SessionKey.loginstate.name()));
		loginDetails.setRole(redisValue.get(SessionKey.userrole.name()));
		return loginDetails;
	}
}
