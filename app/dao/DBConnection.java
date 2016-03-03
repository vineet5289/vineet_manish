package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import play.db.DB;

public class DBConnection {

	public ResultSet executeSelectStatement(PreparedStatement selectStatement) throws SQLException {
		Connection connection = null;
		ResultSet resultSet = null;
		try{
			connection = DB.getDataSource("default").getConnection();
			resultSet = selectStatement.executeQuery();
			return resultSet;
		} catch(Exception exception) {
			return null;
		} finally {
			if(resultSet != null && !resultSet.isClosed())
				resultSet.close();
			if(selectStatement != null && !selectStatement.isClosed())
				selectStatement.close();
			if(connection != null && !connection.isClosed())
				connection.close();
		}
	}


}
