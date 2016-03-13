package dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import enum_package.Role;
import models.LoginDetails;
import play.db.DB;
import utils.RandomGenerator;

public class UserLoginDAO {
	private String tableName = "login"; 
	private String authTokeFieldName = "auth_token";
	private static final RandomGenerator randomGenerator = new RandomGenerator();
	private static final SecureRandom secureRandom = new SecureRandom();

	public LoginDetails isValidUserCredentials(String userName, String password) throws SQLException {
		LoginDetails loginDetails = new LoginDetails();
		String authToken = "";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT * FROM %s WHERE user_name=?;", tableName);
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, userName);

			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next() || !isPasswordMatch(password, resultSet.getString("password"))) {
				loginDetails.setError("UserName/Password is invalid. Please Try again");
				return loginDetails;
			}
			authToken = randomGenerator.nextSessionId(150, secureRandom);
			resultSet.updateString(authTokeFieldName, authToken);
			loginDetails.setAuthToken(authToken);
			loginDetails.setRole(Role.valueOf(resultSet.getString("role")));
			loginDetails.setUserName(userName);
			loginDetails.setError("");
			resultSet.updateRow();
		} catch(Exception exception) {
			loginDetails.setError("Server Problem occure. Please try after some time");
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

		return loginDetails;
	}

	public void logout(String userName) throws SQLException {
		if(userName == null || userName.isEmpty())
			return;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT * FROM %s WHERE user_name=?;", tableName);
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, userName);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				resultSet.updateString(authTokeFieldName, null);
				resultSet.updateRow();
			}
		} catch(Exception exception) {
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
	}

	private boolean isPasswordMatch(String enteredPassword, String dbPassword) throws NoSuchAlgorithmException {
		return RandomGenerator.checkPasswordMatch(dbPassword, enteredPassword);
	}
}
