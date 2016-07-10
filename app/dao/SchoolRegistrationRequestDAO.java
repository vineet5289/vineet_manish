package dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import play.db.DB;
import utils.RandomGenerator;
import actors.SchoolRequestActorProtocol.ApprovedSchool;
import enum_package.RequestedStatus;

public class SchoolRegistrationRequestDAO {
	private String tableName = "school_registration_request";
	private String idField = "id";
	private String schoolNameField = "school_name";
	private String principalNameField = "principal_name";
	private String principalEmailField = "principal_email";
	private String schoolEmailField = "school_email";
	private String schoolAddressField = "school_address";
	private String contactField = "contact";
	private String schoolRegistrationIdField = "school_registration_id";
	private String queryField = "query";
	private String requestedAtField = "requested_at"; // default present in database
	private String statusField = "status";
	private String statusUpdatedAtField = "status_updated_at";
	private String authTokenField = "auth_token";
	private String authTokenGenereatedAtField = "auth_token_genereated_at";
	private String requestNumberField = "request_number";
	private String alertDoneField = "alert_done";
	private String isActiveField = "is_active";


	public String generateRequest(Map<String, String> addNewSchoolRequestDetails) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?,"
				+ "?, ?, ?, ?);", tableName, schoolNameField, principalNameField, principalEmailField, schoolAddressField,
				schoolEmailField, contactField, schoolRegistrationIdField, queryField, statusField, requestNumberField);
		String referenceNumber = "";
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, addNewSchoolRequestDetails.get("schoolName"));
			preparedStatement.setString(2, addNewSchoolRequestDetails.get("principalName"));
			preparedStatement.setString(3, addNewSchoolRequestDetails.get("principalEmail"));
			preparedStatement.setString(4, addNewSchoolRequestDetails.get("schoolAddress")); //address
			preparedStatement.setString(5, addNewSchoolRequestDetails.get("schoolEmail"));
			preparedStatement.setString(6, addNewSchoolRequestDetails.get("contact"));
			preparedStatement.setString(7, addNewSchoolRequestDetails.get("schoolRegistrationId")); // registration 
			preparedStatement.setString(8, addNewSchoolRequestDetails.get("query")); //query validation
			preparedStatement.setString(9, RequestedStatus.REQUESTED.name());

			referenceNumber = getReferenceNumber(addNewSchoolRequestDetails.get("schoolName"));
			preparedStatement.setString(10, referenceNumber);
			preparedStatement.execute();
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			referenceNumber = "";
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return referenceNumber; 
	}

	public ApprovedSchool approved(String referneceNumberValue, long idValue) throws Exception {
		RandomGenerator randomGenerator = new RandomGenerator(); 
		SecureRandom secureRandom = new SecureRandom();
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ApprovedSchool approvedSchool = null;

		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
				idField, principalNameField, principalEmailField, contactField, authTokenField, requestNumberField, alertDoneField, authTokenGenereatedAtField, 
				statusField, tableName, requestNumberField, idField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setString(1, referneceNumberValue);
			preparedStatement.setLong(2, idValue);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				approvedSchool = new ApprovedSchool();
				approvedSchool.setId(resultSet.getLong(idField));

				String authTokeValue = randomGenerator.getReferenceNumber(30, secureRandom);
				approvedSchool.setAuthToke(authTokeValue);
				resultSet.updateString(authTokenField, authTokeValue);
				resultSet.updateTimestamp(authTokenGenereatedAtField, new Timestamp(now.getTime()));

				approvedSchool.setContract(resultSet.getString(contactField));
				approvedSchool.setPrincipleEmail(resultSet.getString(principalEmailField));
				approvedSchool.setPrincipleName(resultSet.getString(principalNameField));
				approvedSchool.setReferenceNumber(resultSet.getString(requestNumberField));

				resultSet.updateString(statusField, RequestedStatus.APPROVED.name());
				resultSet.updateBoolean(alertDoneField, true);
				resultSet.updateRow();
			}
			connection.commit();
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			return null;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return approvedSchool; 
	}

	public List<models.NewSchoolApprovedRequest> getAllSchoolNeedToBeApproved() throws Exception {
		List<models.NewSchoolApprovedRequest> schools = new ArrayList<models.NewSchoolApprovedRequest>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=?;",
				idField, schoolNameField, principalNameField, principalEmailField, contactField, schoolAddressField, schoolRegistrationIdField, queryField, requestedAtField, 
				statusField, statusUpdatedAtField, requestNumberField, tableName, statusField);

		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setString(1, RequestedStatus.REQUESTED.name());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				models.NewSchoolApprovedRequest newSchoolApprovedRequest = new models.NewSchoolApprovedRequest();
				newSchoolApprovedRequest.setId(resultSet.getLong(idField));
				newSchoolApprovedRequest.setSchoolName(resultSet.getString(schoolNameField));
				newSchoolApprovedRequest.setPrincipalName(resultSet.getString(principalNameField));
				newSchoolApprovedRequest.setPrincipalEmail(resultSet.getString(principalEmailField));
				newSchoolApprovedRequest.setContact(resultSet.getString(contactField));
				newSchoolApprovedRequest.setSchoolAddress(resultSet.getString(schoolAddressField));
				newSchoolApprovedRequest.setSchoolRegistrationId(resultSet.getString(schoolRegistrationIdField));
				newSchoolApprovedRequest.setQuery(resultSet.getString(queryField));
				newSchoolApprovedRequest.setRequestedAt(resultSet.getTimestamp(requestedAtField));
				newSchoolApprovedRequest.setStatus(RequestedStatus.valueOf(resultSet.getString(statusField)));
				newSchoolApprovedRequest.setStatusUpdatedAt(resultSet.getTimestamp(statusUpdatedAtField));
				newSchoolApprovedRequest.setRequestNumber(resultSet.getString(requestNumberField));
				schools.add(newSchoolApprovedRequest);
			}
			connection.commit();
		} catch(Exception exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return schools; 
	}

	public boolean isValidUserByOtpAndReferenceKey(String referenceKey, String otp) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean isValidUser = false;
		String selectQuery = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=?;",
				idField, authTokenField, requestNumberField, authTokenGenereatedAtField, tableName, requestNumberField, authTokenField, statusField, isActiveField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setString(1, referenceKey);
			preparedStatement.setString(2, otp);
			preparedStatement.setString(3, RequestedStatus.APPROVED.name());
			preparedStatement.setBoolean(4, true);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				isValidUser = true;
			}
			connection.commit();
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			isValidUser = false;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return isValidUser;
	}

	private String getReferenceNumber(String schooleName) {
		RandomGenerator randomGenerator = new RandomGenerator(); 
		SecureRandom secureRandom = new SecureRandom();
		String referenceNumber = randomGenerator.getReferenceNumber(30, secureRandom);

		StringBuilder sb = new StringBuilder();
		String[] charSeq = schooleName.trim().split(" ");
		int nameLength = charSeq.length;
		if(nameLength == 1) {
			if(charSeq[0].length() < 3)
				sb.append(charSeq[0] + "-" + referenceNumber);
			else
				sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0) + "" + charSeq[2].charAt(0) + "-" + referenceNumber);
		} else if(nameLength == 2) {
			sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0) + "-" + referenceNumber);
		} else if(nameLength > 2) {
			sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0) + "" + charSeq[2].charAt(0) + "-" + referenceNumber);
		}

		return sb.toString();
	}
}
