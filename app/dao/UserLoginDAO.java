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

import models.CommonUserCredentials;
import models.HeadInstituteLoginDetails;
import models.LoginDetails;
import play.db.DB;
import utils.ParseString;
import utils.RandomGenerator;
import utils.ValidateFields;
import views.forms.AccessRightsForm;
import enum_package.LoginStatus;
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

	public CommonUserCredentials isValidUserCredentials(String userName, String password) throws SQLException {
		CommonUserCredentials userCredentials = new CommonUserCredentials();
		Connection connection = null;
		PreparedStatement loginPreparedStatement = null;
		ResultSet loginResultSet = null;

		String loginSelectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.userName,
				Tables.Login.emailId, Tables.Login.password, Tables.Login.passwordState, Tables.Login.role, Tables.Login.name, Tables.Login.type,
				Tables.Login.table, Tables.Login.userName, Tables.Login.isActive);

		try {
			String authToken = geterateAuthToken();
			if(authToken == null || authToken.isEmpty()) {
				userCredentials.setLoginStatus(LoginStatus.servererror);
				return userCredentials;
			}

			connection = DB.getDataSource("srp").getConnection();
			loginPreparedStatement = connection.prepareStatement(loginSelectQuery, ResultSet.TYPE_FORWARD_ONLY);
			loginPreparedStatement.setString(1, userName);
			loginPreparedStatement.setBoolean(2, true);
			loginResultSet = loginPreparedStatement.executeQuery();

			if(!loginResultSet.next() || !isPasswordMatch(password, loginResultSet.getString(Tables.Login.password))) {
				userCredentials.setLoginStatus(LoginStatus.invaliduser);
				return userCredentials;
			}

			userCredentials.setRole(loginResultSet.getString(Tables.Login.role));
			userCredentials.setUserName(userName);
			userCredentials.setAuthToken(authToken);
			userCredentials.setType(loginResultSet.getString(Tables.Login.type));
			userCredentials.setLoginstate(Tables.Login.passwordState);
			userCredentials.setLoginStatus(LoginStatus.validuser);
		} catch(Exception exception) {
			userCredentials.setLoginStatus(LoginStatus.servererror);
			exception.printStackTrace();
		} finally {
			if(loginResultSet != null)
				loginResultSet.close();
			if(loginPreparedStatement != null)
				loginPreparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return userCredentials;
	}

	public HeadInstituteLoginDetails refreshHeadInstituteUserCredentials(String userName, String type) throws SQLException {
		Connection connection = null;
		PreparedStatement loginPS = null;
		PreparedStatement headInstitutePS = null;
		PreparedStatement institutePS = null;
		ResultSet loginRS = null;
		ResultSet headInstituteRS = null;
		ResultSet instituteRS = null;

		String loginSelectQ = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=?;", Tables.Login.userName, Tables.Login.role,
				Tables.Login.accessRights, Tables.Login.name, Tables.Login.instituteId, Tables.Login.type, Tables.Login.passwordState, Tables.Login.table,
				Tables.Login.userName, Tables.Login.isActive, Tables.Login.type);

		String headInstituteSelectQ = String.format("SELECT %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.HeadInstitute.groupOfInstitute, Tables.HeadInstitute.noOfInstitute,
				Tables.HeadInstitute.preferedName, Tables.HeadInstitute.table, Tables.HeadInstitute.isActive, Tables.HeadInstitute.id);

		String instituteSelectQ = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Institute.id, Tables.Institute.userName,
				Tables.Institute.name, Tables.Institute.preferedName, Tables.Institute.table, Tables.Institute.isActive, Tables.Institute.headInstituteId);

		HeadInstituteLoginDetails headInstituteLoginDetails = new HeadInstituteLoginDetails();;
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			loginPS = connection.prepareStatement(loginSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			headInstitutePS = connection.prepareStatement(headInstituteSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			institutePS = connection.prepareStatement(instituteSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			loginPS.setString(1, userName);
			loginPS.setBoolean(2, true);
			loginPS.setString(3, type);

			long headInstituteId = 0;
			int numberOfInstitute = 0;
			loginRS = loginPS.executeQuery();
			if(loginRS.next()) {
				headInstituteLoginDetails.setHeadInstituteUserName(userName);
				headInstituteLoginDetails.setHeadInstituteName(loginRS.getString(Tables.Login.name));
				headInstituteLoginDetails.setHeadInstituteLoginType(loginRS.getString(Tables.Login.type));
				headInstituteLoginDetails.setHeadInstituteLoginState(loginRS.getString(Tables.Login.passwordState));
				headInstituteLoginDetails.setHeadInstituteId(loginRS.getLong(Tables.Login.instituteId));
				headInstituteLoginDetails.setHeadInstituteAccessRight(loginRS.getString(Tables.Login.accessRights));
				headInstituteLoginDetails.setHeadInstituteUserRole(loginRS.getString(Tables.Login.role));
				headInstituteId = loginRS.getLong(Tables.Login.instituteId);
			} else {
				headInstituteLoginDetails.setLoginStatus(LoginStatus.invaliduser);
			}

			if(headInstituteId > 0) {
				headInstitutePS.setBoolean(1, true);
				headInstitutePS.setLong(2, headInstituteId);
				headInstituteRS = headInstitutePS.executeQuery();

				if(headInstituteRS.next()) {
					headInstituteLoginDetails.setGropuOfInstitute(headInstituteRS.getString(Tables.HeadInstitute.groupOfInstitute));
					headInstituteLoginDetails.setNumberOfInstitute(headInstituteRS.getInt(Tables.HeadInstitute.noOfInstitute));
					headInstituteLoginDetails.setHeadInstitutePrefered(headInstituteRS.getString(Tables.HeadInstitute.preferedName));
					numberOfInstitute = headInstituteRS.getInt(Tables.HeadInstitute.noOfInstitute);
				} else {
					headInstituteLoginDetails.setLoginStatus(LoginStatus.invaliduser);
				}
			}

			if(numberOfInstitute > 0 && headInstituteId > 0) {
				institutePS.setBoolean(1, true);
				institutePS.setLong(2, headInstituteId);
				instituteRS = institutePS.executeQuery();
				List<HeadInstituteLoginDetails.BranchDetails> branchs = new ArrayList<HeadInstituteLoginDetails.BranchDetails>();
				while(instituteRS.next()) {
					HeadInstituteLoginDetails.BranchDetails branch = new HeadInstituteLoginDetails.BranchDetails();
					branch.setInstituteId(instituteRS.getLong(Tables.Institute.id));
					branch.setInstituteName(instituteRS.getString(Tables.Institute.name));
					branch.setInstitutePrefered(instituteRS.getString(Tables.Institute.preferedName));
					branch.setInstituteUserName(instituteRS.getString(Tables.Institute.userName));
					branchs.add(branch);
				}
				if(branchs.size() > 0)
					headInstituteLoginDetails.setBranchs(branchs);
				headInstituteLoginDetails.setLoginStatus(LoginStatus.validuser);
			}
			connection.commit();
		} catch(Exception exception) {
			exception.printStackTrace();
			headInstituteLoginDetails.setLoginStatus(LoginStatus.servererror);
			connection.rollback();
		} finally {
			if(loginRS != null)
				loginRS.close();

			if(headInstituteRS != null)
				headInstituteRS.close();

			if(instituteRS != null)
				instituteRS.close();

			if(loginPS != null)
				loginPS.close();

			if(headInstitutePS != null)
				headInstitutePS.close();

			if(institutePS != null)
				institutePS.close();

			if(connection != null)
				connection.close();
		}

		return headInstituteLoginDetails;
	}

	public LoginDetails refreshUserUsingUserName(String userName) throws SQLException {
		Connection connection = null;
		PreparedStatement loginPreparedStatement = null;
		ResultSet loginResultSet = null;

		String loginSelectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.id,
				Tables.Login.userName, Tables.Login.emailId, Tables.Login.role, Tables.Login.accessRights, Tables.Login.name,
				Tables.Login.instituteId, Tables.Login.type, Tables.Login.passwordState, Tables.Login.table, Tables.Login.userName, Tables.Login.isActive);

		LoginDetails loginDetails = new LoginDetails();;
		try {
			connection = DB.getDataSource("srp").getConnection();
			loginPreparedStatement = connection.prepareStatement(loginSelectQuery, ResultSet.TYPE_FORWARD_ONLY);
			loginPreparedStatement.setString(1, userName);
			loginPreparedStatement.setBoolean(2, true);
			loginResultSet = loginPreparedStatement.executeQuery();
			if(loginResultSet.next()) {
				loginDetails.setUserName(userName);
				loginDetails.setRole(loginResultSet.getString(Tables.Login.role));
				loginDetails.setAccessRight(loginResultSet.getString(Tables.Login.accessRights));
				loginDetails.setPasswordState(loginResultSet.getString(Tables.Login.passwordState));
				Long superUserSchoolId = loginResultSet.getLong(Tables.Login.instituteId);
				if( superUserSchoolId != null && superUserSchoolId > 0) {
					loginDetails.setSchoolId(superUserSchoolId);
				}
				loginDetails.setType(loginResultSet.getString(Tables.Login.type));
				loginDetails.setLoginStatus(LoginStatus.validuser);
			} else {
				loginDetails.setLoginStatus(LoginStatus.invaliduser);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			loginDetails.setLoginStatus(LoginStatus.servererror);
		} finally {
			if(loginResultSet != null)
				loginResultSet.close();
			if(loginPreparedStatement != null)
				loginPreparedStatement.close();
			if(connection != null)
				connection.close();
		}

		return loginDetails;
	}

	public List<LoginDetails> getAllUserRelatedToCurrentUser(String superUserName) throws SQLException {
		List<LoginDetails> userDetails = new ArrayList<LoginDetails>();
		boolean isFieldSet = true;
		Connection connection = null;
		PreparedStatement userSuperUserPreparedStatement = null;
		ResultSet userSuperUserResultSet = null;
		String userSuperUserSelectQuery = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.userName, Tables.Login.name,
				Tables.Login.instituteId, superUserNameField, userSuperUserTableName, superUserNameField, isActiveField);
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
