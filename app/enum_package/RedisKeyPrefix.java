package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum RedisKeyPrefix {
	buc("buc:"),
	auth("auth:"),
	hiii("hiii:"),
	role("role:"),
	permission("permission:");

	private String value;

	private final static Map<RedisKeyPrefix, String> redisKeyPrefixToValue = new HashMap<RedisKeyPrefix, String>(RedisKeyPrefix.values().length);
	private final static Map<String, RedisKeyPrefix> valueToRedisKeyPrefix = new HashMap<String, RedisKeyPrefix>(RedisKeyPrefix.values().length);
	static {
		for(RedisKeyPrefix rkp : RedisKeyPrefix.values()) {
			redisKeyPrefixToValue.put(rkp, rkp.value);
			valueToRedisKeyPrefix.put(rkp.value, rkp);
		}
	}
	private RedisKeyPrefix(String value) {
		this.value = value;
	}

	public static RedisKeyPrefix of(String key) {
		RedisKeyPrefix result = valueToRedisKeyPrefix.get(key);
		return result;
	}

	public static String of(RedisKeyPrefix key) {
		String result = redisKeyPrefixToValue.get(key);
		if(result == null) {
			return "";
		}
		return result;
	}
}
