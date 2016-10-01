package dao;

import java.util.Map;
import java.util.Set;

public interface SessionDao {
	public void save(String userName, String redisPrefix, Map<String, String> redisValue) throws Exception;
	public boolean save(String userName, String redisPrefix, String field, String value) throws Exception;

	public Map<String, String> get(String userName, String redisPrefix) throws Exception;
	public String get(String userName, String redisPrefix, String field) throws Exception;

	public boolean removed(String userName, String redisPrefix, String... field) throws Exception;
	public boolean removedAll(String userName, String redisPrefix) throws Exception;

	public void setSetMembers(String userName, String redisPrefix, String... setMembers);
	public Set<String> getSetMembers(String userName, String redisPrefix);
	public Long removedSetMembers(String userName, String redisPrefix, String... setMembers);
}
