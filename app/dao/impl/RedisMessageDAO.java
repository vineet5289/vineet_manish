package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dao.MessageDAO;
import dao.connection.RedisConnectionPool;
import redis.clients.jedis.Jedis;

public class RedisMessageDAO implements MessageDAO {

	private RedisConnectionPool redisConnectionPool;
	public static final String MESSAGE_SET = "messages";
	public static final String MESSAGE_ID_SEQ = "message.id.seq";

	@Inject
	public RedisMessageDAO(RedisConnectionPool redisConnectionPool) {
		System.out.println("******* RedisMessageDAO ****** 1");
		this.redisConnectionPool = redisConnectionPool;
		System.out.println("******* RedisMessageDAO ****** 2");
	}

	@Override
	public List<String> findAllMessages() {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Map<String, String> messages = jedis.hgetAll(MESSAGE_SET);
		jedis.close();
		return new ArrayList<>(messages.values());
	}

	@Override
	public void save(String json) throws Exception {
		Jedis jedis = redisConnectionPool.getJedisPool().getResource();
		Long nextId = jedis.incr(MESSAGE_ID_SEQ);
		String messageId = "id:" + nextId.toString();
		jedis.hset(MESSAGE_SET, messageId, json);
		jedis.close();
	}

}
