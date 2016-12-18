package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.db.Database;
import play.db.NamedDatabase;
import dao.MessageDAO;

public class SqlMessageDAOImpl implements MessageDAO {

	@Inject @NamedDatabase("srp") private Database db;
	private final String messageIdField = "id";
	private final String schoolIdField = "school_id";
	private final String messageField = "message";
	private final String createdAtField = "created_at";
	private final String tableName = "message";

//	@Inject
//	public SqlMessageDAOImpl(Database db) {
//		System.out.println("***** SqlMessageDAOImpl*****1");
//		this.db = db;
//		System.out.println("***** SqlMessageDAOImpl*****1");
//	}

	@Override
	public void save(String json) throws Exception {
		System.out.println("************ " + json);
		System.out.println("SqlMessageDAOImpl.save");
		Connection connection = null;
		PreparedStatement insertStatement = null;
		ObjectMapper mapper = new ObjectMapper();
		
		String insertQuery = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", tableName, schoolIdField, messageField);
		try {
			JsonNode jsonNode = mapper.readTree(json);
			Long schoolId = jsonNode.get("school_id").asLong();
			String message = jsonNode.get("message").asText();

			connection  = db.getConnection();
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setLong(1, schoolId);
			insertStatement.setString(2, message);
			insertStatement.execute();
			connection.commit();
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new Exception(exception);
		} finally {
			if(insertStatement != null)
				insertStatement.close();
			if(connection != null && !connection.isClosed())
				connection.close();
		}
	}

	@Override
	public List<String> findAllMessages() {
		// TODO Auto-generated method stub
		return null;
	}

}
