package dao.impl;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import redis.clients.jedis.Jedis;
import dao.SessionDao;
import dao.connection.RedisConnectionPool;

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
	public boolean save(String userName, String redisPrefix, String field, String value) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Long updatedCount = jedis.hset(redisPrefix + userName, field, value);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return (updatedCount == 0);
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
	public String get(String userName, String redisPrefix, String field) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		String sessionTrackField = jedis.hget(redisPrefix + userName, field);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return sessionTrackField;
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

	@Override
	public Set<String> getSetMembers(String userName, String redisPrefix) {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Set<String> setValue = jedis.smembers(redisPrefix + userName);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return setValue;
	}

	@Override
	public void setSetMembers(String userName, String redisPrefix, String... setMembers) {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		jedis.sadd(redisPrefix + userName, setMembers);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
	}

	@Override
	public Long removedSetMembers(String userName, String redisPrefix, String... setMembers) {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		long memberRemoved = jedis.srem(redisPrefix + userName, setMembers);
		redisConnectionPool.getJedisPool().returnResource(jedis);
		jedis.close();
		return memberRemoved;
	}
}
