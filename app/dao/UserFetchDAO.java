package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import play.db.DB;
import models.LoginDetails;
import models.UserInfo;

public class UserFetchDAO {
	private final String userNameField = "userName";
	private final String schoolIdField = "school_id";
	private final String nameField = "name";
	private final String tableName = "user_school";

	public List<UserInfo> getAllUser(Long schoolId) throws SQLException {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String selectQuery = String.format("SELECT %s, %s, %s FROM %s WHERE %s=?;", userNameField, schoolIdField, nameField,
				tableName, schoolIdField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, schoolId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String userName = resultSet.getString(userNameField);
				String name = resultSet.getString(nameField);
				UserInfo userInfo = new UserInfo();
				userInfo.setName(name);
				userInfo.setUserName(userName);
				userInfo.setSchoolIds(schoolId.toString());
				userInfos.add(userInfo);
			}
			
		} catch(Exception exception) {
			System.out.println("Problem during user fetch. Please Try again");
			exception.printStackTrace();
			connection.rollback();
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return userInfos;
	}
}
