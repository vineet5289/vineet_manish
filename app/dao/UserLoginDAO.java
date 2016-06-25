package dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.LoginDetails;
import play.db.DB;
import utils.ParseString;
import utils.RandomGenerator;
import utils.ValidateFields;
import views.forms.AccessRightsForm;
import enum_package.Role;

public class UserLoginDAO {
	private String tableName = "login"; 
	private String idField = "id";
	private String emailIdField = "email_id";
	private String userNameField = "user_name";
	private String passwordField = "password";
	private String passwordStateField = "password_state";
	private String createdAt = "created_at";
	private String updatedAt = "updated_at";
	private String roleField = "role";
	private String accessRightsField = "access_rights";
	private String isActiveField = "is_active";
	private String schoolIdField = "school_id";

	public LoginDetails isValidUserCredentials(String userName, String password) throws SQLException {
		LoginDetails loginDetails = new LoginDetails();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", userNameField,
				emailIdField, passwordField, passwordStateField, schoolIdField, roleField, accessRightsField, isActiveField, tableName,
				userNameField, isActiveField);
		boolean isFieldSet = true;
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setString(1, userName);
			preparedStatement.setBoolean(2, true);

			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next() || !isPasswordMatch(password, resultSet.getString(passwordField))) {
				loginDetails.setError("UserName/Password is invalid. Please Try again");
				return loginDetails;
			}

			loginDetails.setRole(Role.valueOf(resultSet.getString(roleField)));
			loginDetails.setUserName(userName);
			loginDetails.setError("");

			String authToken = geterateAuthToken();
			if(authToken == null || authToken.isEmpty())
				isFieldSet = false;
			loginDetails.setAuthToken(authToken);

			loginDetails.setSchoolIdList(resultSet.getString(schoolIdField));
			loginDetails.setAccessRightList(resultSet.getString(accessRightsField));
		} catch(Exception exception) {
			loginDetails.setError("Server Problem occure. Please try after some time");
			exception.printStackTrace();
			connection.rollback();
			isFieldSet = false;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}

		if(!isFieldSet)
			return null;

		return loginDetails;
	}

	public boolean updateUserAccessRight(List<AccessRightsForm.UserAccessRights> accessRights) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectUpdateQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, FROM %s WHERE %s=? AND %s=true;", idField,userNameField,
				schoolIdField, roleField, accessRightsField, isActiveField, tableName, userNameField, isActiveField);
		boolean isSuccessful = true;
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			for(AccessRightsForm.UserAccessRights accessRight : accessRights) {
				String userName = accessRight.getUserName();
				String newAccessRights = accessRight.getAccessRights();
				String action = accessRight.getAction();

				if(!ValidateFields.isValidUserName(userName) || !ValidateFields.isValidAccessRigths(newAccessRights)) {
					System.out.println("Access rigths or userName is not valid for user = " + userName);
					continue;
				}

				preparedStatement = connection.prepareStatement(selectUpdateQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				preparedStatement.setString(1, userName);
				try {
					resultSet = preparedStatement.executeQuery();
					if(resultSet != null && resultSet.next()) {
						String oldAccessRights = resultSet.getString(accessRightsField);
						String updatedAccessRights = mergeAccessRight(action,newAccessRights, oldAccessRights);
						resultSet.updateString(accessRightsField, updatedAccessRights);
						resultSet.updateRow();
					}
				} catch(Exception exception) {
					isSuccessful = false;
					throw new Exception("update access rights is failed for some user");
				}
			}
			connection.commit();
		} catch(Exception exception) {
			exception.printStackTrace();
			connection.rollback();
			isSuccessful = false;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return isSuccessful;
	}


	public boolean isUserPresent(String userName, String authKey) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT * FROM %s WHERE user_name=? AND auth_token=?;", tableName);
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, authKey);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
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
		return false;
	}

	private boolean isPasswordMatch(String enteredPassword, String dbPassword) throws NoSuchAlgorithmException {
		return RandomGenerator.checkPasswordMatch(dbPassword, enteredPassword);
	}

	private String geterateAuthToken() {
		String authToken = "";
		try {
			authToken = RandomGenerator.nextSessionId(150, new SecureRandom());
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		return authToken;
	}

	private String mergeAccessRight(String action, String newAccessString, String dbAccessString) {
		Map<String, String> newAccessRight = ParseString.getMap(newAccessString);
		Map<String, String> oldAccessRight = ParseString.getMap(dbAccessString);
		Map<String, String> afterMergeAccessRight = null;
		if(action != null && action.equalsIgnoreCase("add")) {
			afterMergeAccessRight = addAccessRights(newAccessRight, oldAccessRight);
		} else if(action != null && action.equalsIgnoreCase("minus")) {
			afterMergeAccessRight = removeAccessRights(newAccessRight, oldAccessRight);
		}
		return ParseString.getString(afterMergeAccessRight);
	}

	private Map<String, String> addAccessRights(Map<String, String> rights1, Map<String, String> rights2) {
		Map<String, String> afterMergeAccessRight = new HashMap<String, String>();
		afterMergeAccessRight.putAll(rights2);
		afterMergeAccessRight.putAll(rights1);
		return afterMergeAccessRight;
	}

	private Map<String, String> removeAccessRights(Map<String, String> rights1, Map<String, String> rights2) {
		Map<String, String> afterMergeAccessRight = new HashMap<String, String>();
		afterMergeAccessRight.putAll(rights2);
		for(Map.Entry<String, String> entry : rights1.entrySet()) {
			String key = entry.getKey();
			afterMergeAccessRight.remove(key);
		}
		return afterMergeAccessRight;
	}
}
