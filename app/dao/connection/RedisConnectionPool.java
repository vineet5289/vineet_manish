package dao.connection;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.Configuration;
import play.Logger;
import play.Logger.ALogger;
import play.inject.ApplicationLifecycle;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Singleton
public class RedisConnectionPool {
	public static final String REDIS_URI_PROP = "redis.uri";
	public static final String REDIS_HOST = "redis.host";
	public static final String REDIS_PORT = "redis.port";
	public static final String REDIS_MAX_CONNECTION = "redis.max.connection";
	@Inject private JedisPool jedisPool;
	private final ALogger LOG = Logger.of(this.getClass());
	
	private Configuration configuration;

	@Inject
	public RedisConnectionPool(ApplicationLifecycle lifecycle, Configuration configuration) throws URISyntaxException {
		this.configuration = configuration;
		JedisPoolConfig config = new JedisPoolConfig();
		int maxPoolSize = configuration.getInt(REDIS_MAX_CONNECTION, config.getMaxTotal());
		config.setMaxTotal(maxPoolSize);
		String redisUri = configuration.getString(REDIS_URI_PROP);
		String redisHost = configuration.getString(REDIS_HOST);
		int redisPort = configuration.getInt(REDIS_PORT);
		LOG.debug("Redis server uri points to: " + redisUri);
		this.jedisPool = new JedisPool(config, redisHost, redisPort);
		lifecycle.addStopHook(() -> {
			LOG.debug("Jedis pool is shutting down");
			jedisPool.destroy();
			return CompletableFuture.completedFuture(null);
		});
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

}
