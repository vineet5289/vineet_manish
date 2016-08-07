package dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private String idField = "id";
	private String userNameField = "user_name";
	private String roleField = "role";
	private String accessRightsField = "access_rights";
	private String isActiveField = "is_active";
	private String nameField = "name";
	private String schoolIdField = "school_id";
	private String superUserNameField = "super_user_name";

	private String loginTableName = "login";
	private String userSuperUserTableName = "user_super_user";

	public LoginDetails isValidUserCredentials(String userName, String password) throws SQLException {
		LoginDetails loginDetails = new LoginDetails();
		Connection connection = null;
		PreparedStatement loginPreparedStatement = null;
		ResultSet loginResultSet = null;

		String loginSelectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.id,
				Tables.Login.userName, Tables.Login.emailId, Tables.Login.password, Tables.Login.passwordState, Tables.Login.role,
				Tables.Login.accessRights, Tables.Login.name, Tables.Login.schoolId, Tables.Login.type, Tables.Login.table, Tables.Login.userName,
				Tables.Login.isActive);

		boolean isFieldSet = true;
		try {
			connection = DB.getDataSource("srp").getConnection();
			loginPreparedStatement = connection.prepareStatement(loginSelectQuery, ResultSet.TYPE_FORWARD_ONLY);
			loginPreparedStatement.setString(1, userName);
			loginPreparedStatement.setBoolean(2, true);
			loginResultSet = loginPreparedStatement.executeQuery();

			if(!loginResultSet.next() || !isPasswordMatch(password, loginResultSet.getString(Tables.Login.password))) {
				loginDetails.setError("UserName/Password is invalid. Please Try again");
				return loginDetails;
			}

			String authToken = geterateAuthToken();
			if(authToken == null || authToken.isEmpty())
				isFieldSet = false;

			loginDetails.setRole(loginResultSet.getString(Tables.Login.role));
			loginDetails.setUserName(userName);
			loginDetails.setError("");
			loginDetails.setAuthToken(authToken);
			loginDetails.setAccessRight(loginResultSet.getString(Tables.Login.accessRights));
			Long superUserSchoolId = loginResultSet.getLong(Tables.Login.schoolId);
			if( superUserSchoolId != null && superUserSchoolId > 0) {
				loginDetails.setSchoolId(superUserSchoolId);
			}
			loginDetails.setType(loginResultSet.getString(Tables.Login.type));
		} catch(Exception exception) {
			loginDetails.setError("Server Problem occure. Please try after some time");
			exception.printStackTrace();
			isFieldSet = false;
		} finally {
			if(loginResultSet != null)
				loginResultSet.close();
			if(loginPreparedStatement != null)
				loginPreparedStatement.close();
			if(connection != null)
				connection.close();
		}

		if(!isFieldSet)
			return null;

		return loginDetails;
	}

	public LoginDetails refreshUserUsingUserName(String userName) throws SQLException {
		LoginDetails loginDetails = new LoginDetails();
		Connection connection = null;
		PreparedStatement loginPreparedStatement = null;
		ResultSet loginResultSet = null;

		String loginSelectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.id,
				Tables.Login.userName, Tables.Login.emailId, Tables.Login.role, Tables.Login.accessRights, Tables.Login.name,
				Tables.Login.schoolId, Tables.Login.type, Tables.Login.table, Tables.Login.userName, Tables.Login.isActive);

		boolean isFieldSet = true;
		try {
			connection = DB.getDataSource("srp").getConnection();
			loginPreparedStatement = connection.prepareStatement(loginSelectQuery, ResultSet.TYPE_FORWARD_ONLY);
			loginPreparedStatement.setString(1, userName);
			loginPreparedStatement.setBoolean(2, true);
			loginResultSet = loginPreparedStatement.executeQuery();
			
			loginDetails.setUserName(userName);
			loginDetails.setRole(loginResultSet.getString(Tables.Login.role));
			loginDetails.setError("");
			loginDetails.setAccessRight(loginResultSet.getString(Tables.Login.accessRights));
			Long superUserSchoolId = loginResultSet.getLong(Tables.Login.schoolId);
			if( superUserSchoolId != null && superUserSchoolId > 0) {
				loginDetails.setSchoolId(superUserSchoolId);
			}
			loginDetails.setType(loginResultSet.getString(Tables.Login.type));
		} catch(Exception exception) {
			loginDetails.setError("Server Problem occure. Please try after some time");
			exception.printStackTrace();
			isFieldSet = false;
		} finally {
			if(loginResultSet != null)
				loginResultSet.close();
			if(loginPreparedStatement != null)
				loginPreparedStatement.close();
			if(connection != null)
				connection.close();
		}

		if(!isFieldSet)
			return null;

		return loginDetails;
	}

	public List<LoginDetails> getAllUserRelatedToCurrentUser(String superUserName) throws SQLException {
		List<LoginDetails> userDetails = new ArrayList<LoginDetails>();
		boolean isFieldSet = true;
		Connection connection = null;
		PreparedStatement userSuperUserPreparedStatement = null;
		ResultSet userSuperUserResultSet = null;
		String userSuperUserSelectQuery = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", userNameField, nameField, schoolIdField,
				superUserNameField, userSuperUserTableName, superUserNameField, isActiveField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			userSuperUserPreparedStatement = connection.prepareStatement(userSuperUserSelectQuery, ResultSet.TYPE_FORWARD_ONLY);
			userSuperUserPreparedStatement.setString(1, superUserName);
			userSuperUserPreparedStatement.setBoolean(2, true);
			userSuperUserResultSet = userSuperUserPreparedStatement.executeQuery();
			while (userSuperUserResultSet.next()) {
				String userAuthToken = geterateAuthToken();
				if(userAuthToken == null || userAuthToken.isEmpty())
					isFieldSet = false;

				LoginDetails userDetail = new LoginDetails();
				userDetail.setAuthToken(userAuthToken);
				userDetail.setRole(Role.STUDENT.name());
				userDetail.setUserName(userSuperUserResultSet.getString(userNameField));
				userDetail.setSchoolId(userSuperUserResultSet.getLong(schoolIdField));
				userDetails.add(userDetail);
			}

		} catch(Exception exception) {
			exception.printStackTrace();
			connection.rollback();
			isFieldSet = false;
		} finally {
			if(userSuperUserResultSet != null)
				userSuperUserResultSet.close();
			if(userSuperUserPreparedStatement != null)
				userSuperUserPreparedStatement.close();
			if(connection != null)
				connection.close();
		}

		if(!isFieldSet)
			return null;

		return userDetails;
	}

	public boolean updateUserAccessRight(List<AccessRightsForm.UserAccessRights> accessRights) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectUpdateQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, FROM %s WHERE %s=? AND %s=true;", idField,userNameField,
				schoolIdField, roleField, accessRightsField, isActiveField, loginTableName, userNameField, isActiveField);
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
		String selectQuery = String.format("SELECT * FROM %s WHERE user_name=? AND auth_token=?;", loginTableName);
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
