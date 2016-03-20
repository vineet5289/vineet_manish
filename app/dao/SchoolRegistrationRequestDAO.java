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
	private String table_name = "school_registration_request";
	private String id = "id";
	private String schoolName = "school_name";
	private String principalName = "principal_name";
	private String principalEmail = "principal_email";
	private String schoolAddress = "school_address";
	private String contact = "contact";
	private String schoolRegistrationId = "school_registration_id";
	private String query = "query";
	private String requestedAt = "requested_at"; // default present in database
	private String status = "status";
	private String statusUpdatedAt = "status_updated_at";
	private String authToken = "auth_token";
	private String authTokenGenereatedAt = "auth_token_genereated_at";
	private String requestNumber = "request_number";
	private String alertDone = "alert_done";
	private String isActive = "is_active";

	private RandomGenerator randomGenerator = new RandomGenerator(); 
	private SecureRandom secureRandom = new SecureRandom();

	public String generateRequest(Map<String, String> addNewSchoolRequestDetails) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String referenceNumber = randomGenerator.getReferenceNumber(30, secureRandom);
		
		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", table_name, schoolName, principalName,
				principalEmail, schoolAddress, contact, schoolRegistrationId, query, status, requestNumber);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, addNewSchoolRequestDetails.get("schoolName"));
			preparedStatement.setString(2, addNewSchoolRequestDetails.get("principalName"));
			preparedStatement.setString(3, addNewSchoolRequestDetails.get("principalEmail"));
			preparedStatement.setString(4, addNewSchoolRequestDetails.get("schoolAddress"));
			preparedStatement.setString(5, addNewSchoolRequestDetails.get("contact"));
			preparedStatement.setString(6, addNewSchoolRequestDetails.get("schoolRegistrationId"));
			preparedStatement.setString(7, addNewSchoolRequestDetails.get("query")); //query
			preparedStatement.setString(8, RequestedStatus.REQUESTED.name());
			referenceNumber = addNewSchoolRequestDetails.get("schoolName") + referenceNumber;
			preparedStatement.setString(9, referenceNumber);
			preparedStatement.execute();
			connection.commit();
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			connection.rollback();
			referenceNumber = null;
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return referenceNumber; 
	}

	public ApprovedSchool approved(String referneceNumberValue, long idValue) throws Exception {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ApprovedSchool approvedSchool = null;

		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
				id, principalName, principalEmail, contact, authToken, requestNumber, alertDone, authTokenGenereatedAt, 
				status, table_name, requestNumber, id);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setString(1, referneceNumberValue);
			preparedStatement.setLong(2, idValue);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				approvedSchool = new ApprovedSchool();
				approvedSchool.setId(resultSet.getLong(id));
				
				String authTokeValue = randomGenerator.getReferenceNumber(30, secureRandom);
				approvedSchool.setAuthToke(authTokeValue);
				resultSet.updateString(authToken, authTokeValue);
				resultSet.updateTimestamp(authTokenGenereatedAt, new Timestamp(now.getTime()));

				approvedSchool.setContract(resultSet.getString(contact));
				approvedSchool.setPrincipleEmail(resultSet.getString(principalEmail));
				approvedSchool.setPrincipleName(resultSet.getString(principalName));
				approvedSchool.setReferenceNumber(resultSet.getString(requestNumber));

				resultSet.updateString(status, RequestedStatus.APPROVED.name());
				resultSet.updateBoolean(alertDone, true);
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
				id, schoolName, principalName, principalEmail, contact, schoolAddress, schoolRegistrationId, query, requestedAt, 
				status, statusUpdatedAt, requestNumber, table_name, status);

		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setString(1, RequestedStatus.REQUESTED.name());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				models.NewSchoolApprovedRequest newSchoolApprovedRequest = new models.NewSchoolApprovedRequest();
				newSchoolApprovedRequest.setId(resultSet.getLong(id));
				newSchoolApprovedRequest.setSchoolName(resultSet.getString(schoolName));
				newSchoolApprovedRequest.setPrincipalName(resultSet.getString(principalName));
				newSchoolApprovedRequest.setPrincipalEmail(resultSet.getString(principalEmail));
				newSchoolApprovedRequest.setContact(resultSet.getString(contact));
				newSchoolApprovedRequest.setSchoolAddress(resultSet.getString(schoolAddress));
				newSchoolApprovedRequest.setSchoolRegistrationId(resultSet.getString(schoolRegistrationId));
				newSchoolApprovedRequest.setQuery(resultSet.getString(query));
				newSchoolApprovedRequest.setRequestedAt(resultSet.getTimestamp(requestedAt));
				newSchoolApprovedRequest.setStatus(RequestedStatus.valueOf(resultSet.getString(status)));
				newSchoolApprovedRequest.setStatusUpdatedAt(resultSet.getTimestamp(statusUpdatedAt));
				newSchoolApprovedRequest.setRequestNumber(resultSet.getString(requestNumber));
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
				id, authToken, requestNumber, authTokenGenereatedAt, table_name, requestNumber, authToken, status, isActive);
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

}
