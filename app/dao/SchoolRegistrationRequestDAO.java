package dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import enum_package.RequestedStatus;
import play.db.DB;
import utils.RandomGenerator;

public class SchoolRegistrationRequestDAO {
	private String table_name = "school_registration_request";
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

	public String approved(String referneceNumber, long id) throws Exception {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=? WHERE id=? AND %s=? limit 1;", table_name, status, authToken,
				authTokenGenereatedAt, requestNumber);
		try {
			connection = DB.getDataSource("school_management_system").getConnection();
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setString(1, RequestedStatus.APPROVED.name());
			preparedStatement.setString(2, ""); //auth_token
			preparedStatement.setTimestamp(3, new Timestamp(now.getTime())); //
			preparedStatement.setLong(4, id);
			preparedStatement.setString(5, referneceNumber);
			preparedStatement.execute();
			connection.commit();
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			connection.rollback();
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return ""; 
	}

	public String registerEmployee() {
		return "";
	}
}
