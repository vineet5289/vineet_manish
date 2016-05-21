package dao;

import java.util.List;

public interface MessageDAO {

	public void save(String json) throws Exception;

	List<String> findAllMessages();

}
