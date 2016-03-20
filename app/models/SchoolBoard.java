package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import play.db.DB;

public class SchoolBoard {
	private static List<String> schoolBoards = new ArrayList<String>();

	@Getter @Setter
	private long id;

	@Getter @Setter
	private String boardName;

	@Getter @Setter
	private String boardCode;

	public static List<String> getSchoolboardList() throws Exception {
		if(schoolBoards == null || schoolBoards.isEmpty())
			fetchBoardList();

		return schoolBoards;
	}

	private static synchronized void fetchBoardList() throws Exception {
		if(schoolBoards != null && !schoolBoards.isEmpty())
			return;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT id, board_name FROM board;";
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				schoolBoards.add(resultSet.getString("board_name"));
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





