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
import utils.StringUtils;
import views.forms.school.NewSchoolApprovedRequest;
import views.forms.school.SchoolFormData;
import actors.SchoolRequestActorProtocol.ApprovedSchool;
import enum_package.RequestedStatus;

public class AddNewSchoolRequestDAO {
	private String tableName = "school_registration_request";
	private String idField = "id";
	private String schoolNameField = "school_name";
	private String schoolEmailField = "school_email";
	private String mobileNumberField = "mobile_number";
	private String alternativeNumberField = "alternative_number";
	private String schoolRegistrationIdField = "school_registration_id";
	private String queryField = "query";
	private String schoolAddressLine1Field = "school_address_line1";
	private String schoolAddressLine2Field = "school_address_line2";
	private String cityField = "city";
	private String stateField = "state";
	private String countryField = "country";
	private String pinCodeField = "pin_code";
	private String statusField = "status";
	private String authTokenField = "auth_token";
	private String authTokenGenereatedAtField = "auth_token_genereated_at";
	private String requestNumberField = "request_number";
	private String createdAtField = "created_at";
	private String updatedAtField = "updated_at";
	private String alertDoneField = "alert_done";
	private String isActiveField = "is_active";
	private String contractPersonNameField = "contract_person_name";

	public String generateRequest(Map<String, String> addNewSchoolRequestDetails) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?,"
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", tableName, schoolNameField, schoolEmailField, mobileNumberField, alternativeNumberField,
				schoolRegistrationIdField, queryField, schoolAddressLine1Field, schoolAddressLine2Field, cityField, stateField, 
				countryField, pinCodeField, contractPersonNameField, requestNumberField);

		String requestNumber = "";
		try {
			requestNumber = getRequestNumber(addNewSchoolRequestDetails.get("schoolName"));

			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, addNewSchoolRequestDetails.get("schoolName").trim());
			preparedStatement.setString(2, addNewSchoolRequestDetails.get("schoolEmail").trim());
			preparedStatement.setString(3, addNewSchoolRequestDetails.get("schoolMobileNumber").trim());

			String schoolAlternativeNumber = "";
			if(addNewSchoolRequestDetails.get("schoolAlternativeNumber") != null)
				schoolAlternativeNumber = addNewSchoolRequestDetails.get("schoolAlternativeNumber").trim();
			preparedStatement.setString(4, schoolAlternativeNumber);

			preparedStatement.setString(5, StringUtils.getValidStringValue(addNewSchoolRequestDetails.get("schoolRegistrationId")));
			preparedStatement.setString(6, StringUtils.getValidStringValue(addNewSchoolRequestDetails.get("query")));
			preparedStatement.setString(7, StringUtils.getValidStringValue(addNewSchoolRequestDetails.get("schoolAddressLine1")));
			preparedStatement.setString(8, StringUtils.getValidStringValue(addNewSchoolRequestDetails.get("schoolAddressLine2"))); 
			preparedStatement.setString(9, addNewSchoolRequestDetails.get("city").trim());
			preparedStatement.setString(10, addNewSchoolRequestDetails.get("state").trim());
			preparedStatement.setString(11, addNewSchoolRequestDetails.get("country").trim());
			preparedStatement.setString(12, addNewSchoolRequestDetails.get("pincode").trim());
			preparedStatement.setString(13, StringUtils.getValidStringValue(addNewSchoolRequestDetails.get("contractPersonName")));
			preparedStatement.setString(14, requestNumber);

			preparedStatement.execute();
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			requestNumber = "";
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return requestNumber; 
	}

//	public ApprovedSchool approved(String referneceNumberValue, long idValue) throws Exception {
//		RandomGenerator randomGenerator = new RandomGenerator(); 
//		SecureRandom secureRandom = new SecureRandom();
//		Calendar calendar = Calendar.getInstance();
//		java.util.Date now = calendar.getTime();
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		ApprovedSchool approvedSchool = null;
//
//		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
//				idField, principalNameField, principalEmailField, mobileNumberField, authTokenField, requestNumberField, alertDoneField, authTokenGenereatedAtField, 
//				statusField, tableName, requestNumberField, idField);
//		try {
//			connection = DB.getDataSource("srp").getConnection();
//			connection.setAutoCommit(false);
//			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
//			preparedStatement.setString(1, referneceNumberValue);
//			preparedStatement.setLong(2, idValue);
//			resultSet = preparedStatement.executeQuery();
//			if(resultSet.next()) {
//				approvedSchool = new ApprovedSchool();
//				approvedSchool.setId(resultSet.getLong(idField));
//
//				String authTokeValue = randomGenerator.getReferenceNumber(30, secureRandom);
//				approvedSchool.setAuthToke(authTokeValue);
//				resultSet.updateString(authTokenField, authTokeValue);
//				resultSet.updateTimestamp(authTokenGenereatedAtField, new Timestamp(now.getTime()));
//
//				approvedSchool.setContract(resultSet.getString(mobileNumberField));
//				approvedSchool.setPrincipleEmail(resultSet.getString(principalEmailField));
//				approvedSchool.setPrincipleName(resultSet.getString(principalNameField));
//				approvedSchool.setReferenceNumber(resultSet.getString(requestNumberField));
//
//				resultSet.updateString(statusField, RequestedStatus.APPROVED.name());
//				resultSet.updateBoolean(alertDoneField, true);
//				resultSet.updateRow();
//			}
//			connection.commit();
//		} catch(Exception exception) {
//			System.out.println("connection exception happen");
//			exception.printStackTrace();
//			if(connection != null)
//				connection.rollback();
//			return null;
//		} finally {
//			if(resultSet != null)
//				resultSet.close();
//			if(preparedStatement != null)
//				preparedStatement.close();
//			if(connection != null)
//				connection.close();
//		}
//		return approvedSchool; 
//	}

	public List<NewSchoolApprovedRequest> getAllSchoolNeedToBeApproved() throws Exception {
		List<NewSchoolApprovedRequest> schools = new ArrayList<NewSchoolApprovedRequest>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
				idField, schoolNameField, schoolEmailField, mobileNumberField, alternativeNumberField, queryField, schoolAddressLine1Field,
				schoolAddressLine2Field, cityField, stateField, countryField, pinCodeField, statusField, requestNumberField, createdAtField,
				updatedAtField, contractPersonNameField, tableName, statusField, isActiveField);

		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setString(1, RequestedStatus.REQUESTED.name());
			preparedStatement.setBoolean(2, true);

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				NewSchoolApprovedRequest newSchoolApprovedRequest = new NewSchoolApprovedRequest();
				newSchoolApprovedRequest.setId(resultSet.getLong(idField));
				newSchoolApprovedRequest.setSchoolName(resultSet.getString(schoolNameField));
				newSchoolApprovedRequest.setSchoolEmail(resultSet.getString(schoolEmailField));
				newSchoolApprovedRequest.setSchoolMobileNumber(resultSet.getString(mobileNumberField));
				newSchoolApprovedRequest.setSchoolAlternativeNumber(resultSet.getString(alternativeNumberField));
				newSchoolApprovedRequest.setQuery(resultSet.getString(queryField));
				newSchoolApprovedRequest.setSchoolAddressLine1(resultSet.getString(schoolAddressLine1Field));
				newSchoolApprovedRequest.setSchoolAddressLine2(resultSet.getString(schoolAddressLine2Field));
				newSchoolApprovedRequest.setCity(resultSet.getString(cityField));
				newSchoolApprovedRequest.setState(resultSet.getString(stateField));
				newSchoolApprovedRequest.setCountry(resultSet.getString(countryField));
				newSchoolApprovedRequest.setPincode(resultSet.getString(pinCodeField));
				newSchoolApprovedRequest.setStatus(resultSet.getString(statusField));
				newSchoolApprovedRequest.setReferenceNumber(resultSet.getString(requestNumberField));
				newSchoolApprovedRequest.setRequestedAt(resultSet.getTimestamp(createdAtField));
				newSchoolApprovedRequest.setStatusUpdatedAt(resultSet.getTimestamp(updatedAtField));
				newSchoolApprovedRequest.setContractPersonName(resultSet.getString(contractPersonNameField));
				schools.add(newSchoolApprovedRequest);
			}
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

	public SchoolFormData isValidSchoolRegistrationRequest(String requestNumber, String otp, String emailId) throws SQLException {
		SchoolFormData schoolFormData = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?;",
				idField, schoolNameField, schoolEmailField, mobileNumberField, alternativeNumberField, schoolRegistrationIdField, schoolAddressLine1Field,
				schoolAddressLine2Field, cityField, stateField, countryField, pinCodeField, authTokenField, authTokenGenereatedAtField, tableName, isActiveField,
				requestNumberField, authTokenField, schoolEmailField, statusField);

		try {
			schoolFormData = new SchoolFormData();
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setString(2, requestNumber.trim());
			preparedStatement.setString(3, otp.trim());			
			preparedStatement.setString(4, emailId);
			preparedStatement.setString(5, RequestedStatus.APPROVED.name());

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				schoolFormData.setSchoolName(resultSet.getString(schoolNameField));
				schoolFormData.setSchoolEmail(resultSet.getString(schoolEmailField));
				schoolFormData.setSchoolUserName(resultSet.getString(schoolEmailField));
				schoolFormData.setSchoolMobileNumber(resultSet.getString(mobileNumberField));
				schoolFormData.setSchoolAlternativeNumber(resultSet.getString(alternativeNumberField));
				schoolFormData.setSchoolRegistrationId(resultSet.getString(schoolRegistrationIdField));
				schoolFormData.setSchoolAddressLine1(resultSet.getString(schoolAddressLine1Field));
				schoolFormData.setSchoolAddressLine2(resultSet.getString(schoolAddressLine2Field));
				schoolFormData.setCity(resultSet.getString(cityField));
				schoolFormData.setState(resultSet.getString(stateField));
				schoolFormData.setCountry(resultSet.getString(countryField));
				schoolFormData.setPincode(resultSet.getString(pinCodeField));
				schoolFormData.setValidSchool(true);
			} else {
				schoolFormData.setValidSchool(false);
			}
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return schoolFormData;
	}

	private String getRequestNumber(String schooleName) {
		RandomGenerator randomGenerator = new RandomGenerator(); 
		SecureRandom secureRandom = new SecureRandom();
		String referenceNumber = randomGenerator.getReferenceNumber(30, secureRandom);

		StringBuilder sb = new StringBuilder();
		String[] charSeq = schooleName.trim().split("([ \t]+)");
		int nameLength = charSeq.length;
		if(nameLength == 1) {
			if(charSeq[0].length() < 3)
				sb.append(charSeq[0]);
			else
				sb.append(charSeq[0].charAt(0) + "" + charSeq[0].charAt(1) + "" + charSeq[0].charAt(2));
		} else if(nameLength == 2) {
			sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0));
		} else if(nameLength > 2) {
			sb.append(charSeq[0].charAt(0) + "" + charSeq[1].charAt(0) + "" + charSeq[2].charAt(0));
		}

		sb.append("-").append(referenceNumber);
		return sb.toString().toUpperCase();
	}
}
