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
	public void save(String userName, String redisPrefix, Map<String, String> redisValue) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		jedis.hmset(redisPrefix + userName, redisValue);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		
	}

	@Override
	public Map<String, String> get(String userName, String redisPrefix) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Map<String, String> redisValue = jedis.hgetAll(redisPrefix + userName);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return redisValue;
	}

	@Override
	public boolean save(String userName, String redisPrefix, String field, String value) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Long updatedCount = jedis.hset(redisPrefix + userName, field, value);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return (updatedCount == 0);
	}


	@Override
	public String get(String userName, String redisPrefix, String field) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		String sessionTrackField = jedis.hget(redisPrefix + userName, field);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return sessionTrackField;
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
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
	}

	@Override
	public LoginDetails get(String userName) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Map<String, String> redisValue = jedis.hgetAll(RedisKeyPrefix.buc.name() + ":" + userName);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		LoginDetails loginDetails = new LoginDetails();
		loginDetails.setUserName(redisValue.get(SessionKey.username.name()));
		loginDetails.setAuthToken(redisValue.get(SessionKey.authtoken.name()));
		loginDetails.setType(redisValue.get(SessionKey.logintype.name()));
		loginDetails.setPasswordState(redisValue.get(SessionKey.loginstate.name()));
		loginDetails.setRole(redisValue.get(SessionKey.userrole.name()));
		return loginDetails;
	}

	@Override
	public boolean removed(String userName, String redisPrefix, String... field) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Long deleated = jedis.hdel(redisPrefix + userName, field);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return (deleated == 1);
	}

	@Override
	public boolean removedAll(String userName, String redisPrefix) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Long deleated = jedis.del(redisPrefix + userName);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return (deleated == 1);
	}
}
