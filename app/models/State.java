package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import play.db.DB;


public class State {
	private static Map<Long, String> states =new HashMap<Long, String>();

	@Getter @Setter
	private long id;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String code;

	public static Map<Long, String> getStateList() throws Exception {
		if(states == null || states.isEmpty())
			fetchStateList();
		return states;
	}

	private static synchronized void fetchStateList() throws Exception {
		if(states != null && !states.isEmpty())
			return;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT id, name, code FROM state;";
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				states.put(resultSet.getLong("id"), resultSet.getString("name"));
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new Exception(exception);
		} finally {
			if(resultSet != null && !resultSet.isClosed())
				resultSet.close();

			if(preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();

			if(connection != null && !connection.isClosed())
				connection.close();
		}
	}
}
