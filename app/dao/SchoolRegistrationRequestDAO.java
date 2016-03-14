package dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;

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

	public String generateRequest() throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String referenceNumber = randomGenerator.getReferenceNumber(150, secureRandom);
		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s"
				+ ", %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", table_name, schoolName, principalName,
				principalEmail, schoolAddress, contact, schoolRegistrationId, query, status, authToken,
				authTokenGenereatedAt, requestNumber);
		try {
			connection = DB.getDataSource("school_management_system").getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, ""); //schoolName
			preparedStatement.setString(2, ""); //principalName
			preparedStatement.setString(3, ""); //principalEmail
			preparedStatement.setString(4, ""); //schoolAddress
			preparedStatement.setString(5, ""); //contact
			preparedStatement.setString(6, ""); //schoolRegistrationId ====
			preparedStatement.setString(7, ""); //query
			preparedStatement.setString(8, RequestedStatus.REGISTERED.name());
			preparedStatement.setString(9, null);//auth_toke
			preparedStatement.setTimestamp(11, null);//date
			//referenceNumber = "school_name" + referenceNumber; add school name here
			preparedStatement.setString(12, referenceNumber);
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
}
