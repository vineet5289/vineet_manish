package dao.connection;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.Logger;
import play.Logger.ALogger;
import play.Play;
import play.inject.ApplicationLifecycle;
import play.libs.F;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Singleton
public class RedisConnectionPool {

	public static final String REDIS_URI_PROP = "redis.uri";
	public static final String REDIS_MAX_CONNECTION = "redis.max.connection";
	@Inject private JedisPool jedisPool;
	private final ALogger LOG = Logger.of(this.getClass());
	

	@Inject
	public RedisConnectionPool(ApplicationLifecycle lifecycle) throws URISyntaxException {
		JedisPoolConfig config = new JedisPoolConfig();
		int maxPoolSize = Play.application().configuration().getInt(REDIS_MAX_CONNECTION, config.getMaxTotal());
		config.setMaxTotal(maxPoolSize);
		String redisUri = Play.application().configuration().getString(REDIS_URI_PROP);
		LOG.debug("Redis server uri points to: " + redisUri);
//		this.jedisPool = new JedisPool(config, new URI(redisUri));
		lifecycle.addStopHook(() -> {
			LOG.debug("Jedis pool is shutting down");
			jedisPool.destroy();
			return F.Promise.pure(null);
		});
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

}
