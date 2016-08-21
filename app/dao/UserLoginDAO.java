package dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import dao.impl.RedisSessionDao;
import models.CommonUserCredentials;
import models.HeadInstituteLoginDetails;
import models.HeadInstituteLoginDetails.BranchDetails;
import models.LoginDetails;
import play.Logger;
import play.db.DB;
import utils.ParseString;
import utils.RandomGenerator;
import utils.ValidateFields;
import views.forms.AccessRightsForm;
import enum_package.LoginStatus;
import enum_package.RedisKeyPrefix;
import enum_package.Role;
import enum_package.SessionKey;

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

	public CommonUserCredentials getValidUserCredentials(String userName, String password, RedisSessionDao redisSessionDao) throws SQLException {
		CommonUserCredentials userCredentials = new CommonUserCredentials();
		Connection connection = null;
		PreparedStatement loginSelectPS = null;
		PreparedStatement loginUpdatePS = null;
		ResultSet loginResultSet = null;

		String loginSelectQ = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.id, Tables.Login.userName,
				Tables.Login.emailId, Tables.Login.password, Tables.Login.passwordState, Tables.Login.role, Tables.Login.name, Tables.Login.type, Tables.Login.loginSessionCount,
				Tables.Login.table, Tables.Login.userName, Tables.Login.isActive);
		
		String loginUpdateQ = String.format("UPDATE %s SET %s=? WHERE %s=? limit 1;", Tables.Login.table, Tables.Login.loginSessionCount,
				Tables.Login.id);

		try {
			String authToken = geterateAuthToken();
			if(authToken == null || authToken.isEmpty()) {
				userCredentials.setLoginStatus(LoginStatus.servererror);
				return userCredentials;
			}

			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);

			loginSelectPS = connection.prepareStatement(loginSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			loginUpdatePS = connection.prepareStatement(loginUpdateQ);

			loginSelectPS.setString(1, userName);
			loginSelectPS.setBoolean(2, true);
			loginResultSet = loginSelectPS.executeQuery();

			if(!loginResultSet.next() || !isPasswordMatch(password, loginResultSet.getString(Tables.Login.password))) {
				userCredentials.setLoginStatus(LoginStatus.invaliduser);
				connection.commit();
				return userCredentials;
			}
			int loginSessionCount = loginResultSet.getInt(Tables.Login.loginSessionCount);
			Map<String, String> authKeyMap = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth));
			Map<String, String> otherUserCred = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.buc));

			if(loginSessionCount == 0 || authKeyMap == null || authKeyMap.size() == 0
					|| otherUserCred == null || otherUserCred.size() == 0) {
				loginSessionCount = 0;
				authKeyMap = new HashMap<String, String>();
				otherUserCred = new HashMap<String, String>();
			}

			String[] removedKey = null;
			if((loginSessionCount ==  authKeyMap.size() &&  loginSessionCount >= 3)
					|| (loginSessionCount <  authKeyMap.size() && loginSessionCount >= 3)
					|| (loginSessionCount >  authKeyMap.size() &&  authKeyMap.size() >= 3)){
				removedKey = getCorrectSessionIndex(authKeyMap, (authKeyMap.size() - 2));
			}
			if(removedKey != null && removedKey.length != 0) {
				redisSessionDao.removed(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), removedKey);
			}

			loginSessionCount = authKeyMap.size();
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			long loginEpochTime = calendar.getTimeInMillis() / 1000L;
			authKeyMap.put(authToken, Long.toString(loginEpochTime));
			loginSessionCount++;

			loginUpdatePS.setInt(1, loginSessionCount);
			loginUpdatePS.setLong(2, loginResultSet.getLong(Tables.Login.id));
			int updatedRowCount = loginUpdatePS.executeUpdate();
			if(updatedRowCount == 0) {
				userCredentials.setLoginStatus(LoginStatus.servererror);
				return userCredentials;
			}

			otherUserCred.put(SessionKey.username.name(), userName);
			otherUserCred.put(SessionKey.logintype.name(), loginResultSet.getString(Tables.Login.type));
			otherUserCred.put(SessionKey.loginstate.name(), loginResultSet.getString(Tables.Login.passwordState));
			otherUserCred.put(SessionKey.userrole.name(), loginResultSet.getString(Tables.Login.role));

			redisSessionDao.save(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), authKeyMap);
			redisSessionDao.save(userName, RedisKeyPrefix.of(RedisKeyPrefix.buc), otherUserCred);

			userCredentials.setUserName(userName);
			userCredentials.setAuthToken(authToken);
			userCredentials.setRole(loginResultSet.getString(Tables.Login.role));
			userCredentials.setType(loginResultSet.getString(Tables.Login.type));
			userCredentials.setLoginstate(loginResultSet.getString(Tables.Login.passwordState));
			userCredentials.setName(loginResultSet.getString(Tables.Login.name));

			connection.commit();
			userCredentials.setLoginStatus(LoginStatus.validuser);
		} catch(Exception exception) {
			userCredentials.setLoginStatus(LoginStatus.servererror);
			exception.printStackTrace();
			connection.rollback();
		} finally {
			if(loginResultSet != null)
				loginResultSet.close();
			if(loginSelectPS != null)
				loginSelectPS.close();
			if(loginUpdatePS != null)
				loginUpdatePS.close();
			if(connection != null)
				connection.close();
		}
		return userCredentials;
	}

	public boolean logout(String userName, String authKey, RedisSessionDao redisSessionDao) throws SQLException {
		Connection connection = null;
		PreparedStatement loginUpdatePS = null;
		PreparedStatement loginSelectPS = null;
		ResultSet loginResultSet = null;		

		String loginSelectQ = String.format("SELECT %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Login.id, Tables.Login.loginSessionCount,
				Tables.Login.table, Tables.Login.userName, Tables.Login.isActive);

		String loginUpdateQ = String.format("UPDATE %s SET %s=? WHERE %s=? limit 1;", Tables.Login.table, Tables.Login.loginSessionCount,
				Tables.Login.id);

		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);

			loginSelectPS = connection.prepareStatement(loginSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			loginUpdatePS = connection.prepareStatement(loginUpdateQ);

			loginSelectPS.setString(1, userName);
			loginSelectPS.setBoolean(2, true);
			loginResultSet = loginSelectPS.executeQuery();


			if(!loginResultSet.next()) {
				throw new Exception();
			}

			int loginSessionCount = loginResultSet.getInt(Tables.Login.loginSessionCount);
			Map<String, String> authKeyMap = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth));
			Map<String, String> otherUserCred = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.buc));

			if(authKeyMap == null || authKeyMap.size() == 0 || otherUserCred == null || otherUserCred.size() == 0) {
				loginSessionCount = 0;
			} else if(authKeyMap.containsKey(authKey)){
				authKeyMap.remove(authKey);
				loginSessionCount--;
				if(loginSessionCount <= 0 || authKeyMap.size() == 0) {
					loginSessionCount = 0;
					redisSessionDao.removedAll(userName, RedisKeyPrefix.of(RedisKeyPrefix.buc));
					redisSessionDao.removedAll(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth));
				} else {
					redisSessionDao.removed(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), authKey);
				}
			}

			loginUpdatePS.setInt(1, loginSessionCount);
			loginUpdatePS.setLong(2, loginResultSet.getLong(Tables.Login.id));
			loginUpdatePS.executeUpdate();
			connection.commit();
		} catch(Exception exception) {
			exception.printStackTrace();
			connection.rollback();
		} finally {
			if(loginResultSet != null)
				loginResultSet.close();
			if(loginSelectPS != null)
				loginSelectPS.close();
			if(connection != null)
				connection.close();
		}
		return true;
	}

	private String[] getCorrectSessionIndex(Map<String, String> authKeyMap, int numberOfkeyWillRemoved) {
		String[] removedKey = new String[numberOfkeyWillRemoved];
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		for(int i = 0; i < numberOfkeyWillRemoved && i < authKeyMap.size(); i++) {			
			long olderLoginEpochTime = calendar.getTimeInMillis() / 1000L;
			String ketToBeRemoved = "";
			for(Map.Entry<String, String> entry : authKeyMap.entrySet()) {
				long epocTime = Long.valueOf(entry.getValue());
				if(epocTime < olderLoginEpochTime) {
					olderLoginEpochTime = epocTime;
					ketToBeRemoved = entry.getKey();
				}
			}
			authKeyMap.remove(ketToBeRemoved);
			removedKey[i] = ketToBeRemoved;
		}
		return removedKey;
	}

	public HeadInstituteLoginDetails refreshHeadInstituteUserCredentials(String userName, String type, String authToken, RedisSessionDao redisSessionDao) throws SQLException {
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

		String headInstituteSelectQ = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.HeadInstitute.name,
				Tables.HeadInstitute.addressLine1, Tables.HeadInstitute.city, Tables.HeadInstitute.state, Tables.HeadInstitute.country, Tables.HeadInstitute.logoUrl,
				Tables.HeadInstitute.groupOfInstitute, Tables.HeadInstitute.noOfInstitute, Tables.HeadInstitute.preferedName, Tables.HeadInstitute.table,
				Tables.HeadInstitute.isActive, Tables.HeadInstitute.id);

		String instituteSelectQ = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Institute.id, Tables.Institute.addressLine1,
				Tables.Institute.city, Tables.Institute.state, Tables.Institute.country, Tables.Institute.name, Tables.Institute.table, Tables.Institute.isActive,
				Tables.Institute.headInstituteId);

		HeadInstituteLoginDetails headInstituteLoginDetails = new HeadInstituteLoginDetails();;
		try {
			String authKeyRedis = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), authToken);
			if(authKeyRedis == null || authKeyRedis.isEmpty()) {
				headInstituteLoginDetails.setLoginStatus(LoginStatus.servererror);
			}

			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			loginPS = connection.prepareStatement(loginSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			headInstitutePS = connection.prepareStatement(headInstituteSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			institutePS = connection.prepareStatement(instituteSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			loginPS.setString(1, userName);
			loginPS.setBoolean(2, true);
			loginPS.setString(3, type);

			long headInstituteId = 0;
			loginRS = loginPS.executeQuery();
			if(loginRS.next()) {
				headInstituteLoginDetails.setHeadInstituteUserName(userName);
				headInstituteLoginDetails.setHeadInstituteName(loginRS.getString(Tables.Login.name));
				headInstituteLoginDetails.setHeadInstituteLoginType(loginRS.getString(Tables.Login.type));
				headInstituteLoginDetails.setHeadInstituteLoginState(loginRS.getString(Tables.Login.passwordState));
				headInstituteLoginDetails.setHeadInstituteId(loginRS.getLong(Tables.Login.instituteId));
				headInstituteLoginDetails.setHeadInstituteAccessRight(loginRS.getString(Tables.Login.accessRights));
				headInstituteLoginDetails.setHeadInstituteUserRole(loginRS.getString(Tables.Login.role));
				headInstituteLoginDetails.setLoginStatus(LoginStatus.validuser);

				headInstituteId = loginRS.getLong(Tables.Login.instituteId);
			} else {
				Logger.info(String.format("messgae: username:%s is not valid head institute user", userName));
				headInstituteLoginDetails.setLoginStatus(LoginStatus.invaliduser);
			}

			Map<String, String> instituteMapDB = new HashMap<String, String>();

			if(headInstituteId > 0 && headInstituteLoginDetails.getLoginStatus() == LoginStatus.validuser) {
				Logger.info(String.format("messgae: username:%s is valid head institute user. Start fetchin other info for given user.", userName));
				headInstitutePS.setBoolean(1, true);
				headInstitutePS.setLong(2, headInstituteId);
				headInstituteRS = headInstitutePS.executeQuery();

				if(headInstituteRS.next()) {
					headInstituteLoginDetails.setHeadInstituteName(headInstituteRS.getString(Tables.HeadInstitute.name));
					headInstituteLoginDetails.setLogoUrl(headInstituteRS.getString(Tables.HeadInstitute.logoUrl));

					StringBuilder sb = new StringBuilder();
					sb.append(headInstituteRS.getString(Tables.HeadInstitute.addressLine1));	sb.append(", ");
					sb.append(headInstituteRS.getString(Tables.HeadInstitute.city));sb.append(", ");
					sb.append(headInstituteRS.getString(Tables.HeadInstitute.state));	sb.append(", ");
					sb.append(headInstituteRS.getString(Tables.HeadInstitute.country));

					headInstituteLoginDetails.setAddress(sb.toString());
					headInstituteLoginDetails.setGropuOfInstitute(headInstituteRS.getString(Tables.HeadInstitute.groupOfInstitute));
					headInstituteLoginDetails.setNumberOfInstitute(headInstituteRS.getInt(Tables.HeadInstitute.noOfInstitute));
					headInstituteLoginDetails.setHeadInstitutePrefered(headInstituteRS.getString(Tables.HeadInstitute.preferedName));
					
					//information related to redis
					instituteMapDB.put(SessionKey.of(SessionKey.groupofinstitute), headInstituteLoginDetails.getGropuOfInstitute());
					instituteMapDB.put(SessionKey.of(SessionKey.numerofinstituteingroup), Integer.toString(headInstituteLoginDetails.getNumberOfInstitute()));
				} else {
					Logger.debug(String.format("messgae: username:%s is valid head institute user, but ther is not information related to this user.", userName));
					headInstituteLoginDetails.setLoginStatus(LoginStatus.invaliduser);
				}
			}

			if(headInstituteId > 0 && headInstituteLoginDetails.getLoginStatus() == LoginStatus.validuser) {
				institutePS.setBoolean(1, true);
				institutePS.setLong(2, headInstituteId);
				instituteRS = institutePS.executeQuery();
				List<HeadInstituteLoginDetails.BranchDetails> branches = new ArrayList<HeadInstituteLoginDetails.BranchDetails>();
				while(instituteRS.next()) {
					HeadInstituteLoginDetails.BranchDetails branch = new HeadInstituteLoginDetails.BranchDetails();
					branch.setInstituteId(instituteRS.getLong(Tables.Institute.id));
					branch.setInstituteName(instituteRS.getString(Tables.Institute.name));

					StringBuilder sb = new StringBuilder();
					sb.append(instituteRS.getString(Tables.Institute.addressLine1));	sb.append(", ");
					sb.append(instituteRS.getString(Tables.Institute.city));sb.append(", ");
					sb.append(instituteRS.getString(Tables.Institute.state));	sb.append(", ");
					sb.append(instituteRS.getString(Tables.Institute.country));

					branch.setAddress(sb.toString());
					branches.add(branch);
					instituteMapDB.put(Long.toString(branch.getInstituteId()), branch.getInstituteName());
				}
				headInstituteLoginDetails.setBranches(branches);
			}

			// check if all information is present then process futher and update redis
			if(headInstituteLoginDetails.getLoginStatus() == LoginStatus.validuser) {
				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				long epocTime = calendar.getTimeInMillis() / 1000L;

				Map<String, String> instituteMapFromRedis = redisSessionDao.get(userName, RedisKeyPrefix.of(RedisKeyPrefix.hiii));
				if(instituteMapFromRedis == null || instituteMapFromRedis.size() == 0) {
					redisSessionDao.save(userName, RedisKeyPrefix.of(RedisKeyPrefix.hiii), instituteMapDB);
				} else if(instituteMapDB == null || instituteMapDB.size() == 0){
					String[] removedKey = (String[]) instituteMapDB.keySet().toArray();
					redisSessionDao.removed(userName, RedisKeyPrefix.of(RedisKeyPrefix.hiii), removedKey);
				} else {
					ArrayList<String> listOfRemovedBranch = new ArrayList<String>();
					for(Map.Entry<String, String> entry : instituteMapFromRedis.entrySet()) {
						if(!instituteMapDB.containsKey(entry.getKey())) {
							listOfRemovedBranch.add(entry.getKey());
						}
					}
	
					if(listOfRemovedBranch.size() > 0) {
						String[] removedKey = (String[]) listOfRemovedBranch.toArray();
						redisSessionDao.removed(userName, RedisKeyPrefix.of(RedisKeyPrefix.hiii), removedKey);					
					}
					redisSessionDao.save(userName, RedisKeyPrefix.of(RedisKeyPrefix.hiii), instituteMapDB);
				}

				redisSessionDao.save(userName, RedisKeyPrefix.of(RedisKeyPrefix.auth), authKeyRedis, Long.toString(epocTime));
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
				userDetail.setRole(Role.student.name());
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
