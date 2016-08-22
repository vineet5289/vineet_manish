package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import play.db.DB;
import utils.StringUtils;
import views.forms.institute.AddNewInstituteRequest;
import views.forms.institute.InstituteFormData;
import dao.Tables;
import enum_package.RequestedStatus;
import enum_package.InstituteDaoProcessStatus;

public class AddNewSchoolRequestDAO {
	public String generateRequest(AddNewInstituteRequest addNewSchoolRequest) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?);", Tables.InstituteRegistrationRequest.table, Tables.InstituteRegistrationRequest.name, Tables.InstituteRegistrationRequest.email,
				Tables.InstituteRegistrationRequest.phoneNumber, Tables.InstituteRegistrationRequest.officeNumber, Tables.InstituteRegistrationRequest.registrationId,
				Tables.InstituteRegistrationRequest.contactPersonName, Tables.InstituteRegistrationRequest.addressLine1, Tables.InstituteRegistrationRequest.addressLine2,
				Tables.InstituteRegistrationRequest.city, Tables.InstituteRegistrationRequest.state, Tables.InstituteRegistrationRequest.country, Tables.InstituteRegistrationRequest.pinCode,
				Tables.InstituteRegistrationRequest.groupOfInstitute, Tables.InstituteRegistrationRequest.noOfInstitute, Tables.InstituteRegistrationRequest.query,
				Tables.InstituteRegistrationRequest.requestNumber);

		String requestNumber = "";
		try {
			int numberOfInstitute = 0;
			requestNumber = StringUtils.getSchoolRequestRegistrationNumber(addNewSchoolRequest.getInstituteName());
			if(addNewSchoolRequest.getGroupOfInstitute().trim().equalsIgnoreCase("single")) {
				numberOfInstitute = 1;
			}

			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, addNewSchoolRequest.getInstituteName().trim());
			preparedStatement.setString(2, addNewSchoolRequest.getInstituteEmail().trim());
			preparedStatement.setString(3, addNewSchoolRequest.getInstitutePhoneNumber().trim());
			preparedStatement.setString(4, StringUtils.getValidStringValue(addNewSchoolRequest.getInstituteOfficeNumber()));
			preparedStatement.setString(5, StringUtils.getValidStringValue(addNewSchoolRequest.getInstituteRegistrationId()));
			preparedStatement.setString(6, StringUtils.getValidStringValue(addNewSchoolRequest.getContactPersonName()));
			preparedStatement.setString(7, StringUtils.getValidStringValue(addNewSchoolRequest.getInstituteAddressLine1()));
			preparedStatement.setString(8, StringUtils.getValidStringValue(addNewSchoolRequest.getInstituteAddressLine2())); 
			preparedStatement.setString(9, addNewSchoolRequest.getInstituteCity().trim());
			preparedStatement.setString(10, addNewSchoolRequest.getInstituteState().trim());
			preparedStatement.setString(11, addNewSchoolRequest.getInstituteCountry().trim());
			preparedStatement.setString(12, addNewSchoolRequest.getInstitutePinCode().trim());
			preparedStatement.setString(13, addNewSchoolRequest.getGroupOfInstitute().trim());
			preparedStatement.setInt(14, numberOfInstitute);
			preparedStatement.setString(15, StringUtils.getValidStringValue(addNewSchoolRequest.getQuery()));
			preparedStatement.setString(16, requestNumber);
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

//	public List<NewSchoolApprovedRequest> getAllSchoolNeedToBeApproved() throws Exception {
//		List<NewSchoolApprovedRequest> schools = new ArrayList<NewSchoolApprovedRequest>();
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
//				idField, schoolNameField, schoolEmailField, mobileNumberField, alternativeNumberField, queryField, schoolAddressLine1Field,
//				schoolAddressLine2Field, cityField, stateField, countryField, pinCodeField, statusField, requestNumberField, createdAtField,
//				updatedAtField, contactPersonNameField, tableName, statusField, isActiveField);
//
//		try {
//			connection = DB.getDataSource("srp").getConnection();
//			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
//			preparedStatement.setString(1, RequestedStatus.REQUESTED.name());
//			preparedStatement.setBoolean(2, true);
//
//			resultSet = preparedStatement.executeQuery();
//			while(resultSet.next()) {
//				NewSchoolApprovedRequest newSchoolApprovedRequest = new NewSchoolApprovedRequest();
//				newSchoolApprovedRequest.setId(resultSet.getLong(idField));
//				newSchoolApprovedRequest.setSchoolName(resultSet.getString(schoolNameField));
//				newSchoolApprovedRequest.setSchoolEmail(resultSet.getString(schoolEmailField));
//				newSchoolApprovedRequest.setSchoolMobileNumber(resultSet.getString(mobileNumberField));
//				newSchoolApprovedRequest.setSchoolAlternativeNumber(resultSet.getString(alternativeNumberField));
//				newSchoolApprovedRequest.setQuery(resultSet.getString(queryField));
//				newSchoolApprovedRequest.setSchoolAddressLine1(resultSet.getString(schoolAddressLine1Field));
//				newSchoolApprovedRequest.setSchoolAddressLine2(resultSet.getString(schoolAddressLine2Field));
//				newSchoolApprovedRequest.setCity(resultSet.getString(cityField));
//				newSchoolApprovedRequest.setState(resultSet.getString(stateField));
//				newSchoolApprovedRequest.setCountry(resultSet.getString(countryField));
//				newSchoolApprovedRequest.setPincode(resultSet.getString(pinCodeField));
//				newSchoolApprovedRequest.setStatus(resultSet.getString(statusField));
//				newSchoolApprovedRequest.setReferenceNumber(resultSet.getString(requestNumberField));
//				newSchoolApprovedRequest.setRequestedAt(resultSet.getTimestamp(createdAtField));
//				newSchoolApprovedRequest.setStatusUpdatedAt(resultSet.getTimestamp(updatedAtField));
//				newSchoolApprovedRequest.setContractPersonName(resultSet.getString(contactPersonNameField));
//				schools.add(newSchoolApprovedRequest);
//			}
//		} catch(Exception exception) {
//			exception.printStackTrace();
//			return null;
//		} finally {
//			if(resultSet != null)
//				resultSet.close();
//			if(preparedStatement != null)
//				preparedStatement.close();
//			if(connection != null)
//				connection.close();
//		}
//		return schools; 
//	}

	public InstituteFormData isValidSchoolRegistrationRequest(String requestNumber, String otp, String emailId) throws SQLException {
		InstituteFormData instituteFormData = new InstituteFormData();;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?;",
				Tables.InstituteRegistrationRequest.id, Tables.InstituteRegistrationRequest.name, Tables.InstituteRegistrationRequest.email, Tables.InstituteRegistrationRequest.phoneNumber,
				Tables.InstituteRegistrationRequest.officeNumber, Tables.InstituteRegistrationRequest.registrationId, Tables.InstituteRegistrationRequest.contactPersonName,
				Tables.InstituteRegistrationRequest.addressLine1, Tables.InstituteRegistrationRequest.addressLine2, Tables.InstituteRegistrationRequest.city,
				Tables.InstituteRegistrationRequest.state, Tables.InstituteRegistrationRequest.country, Tables.InstituteRegistrationRequest.pinCode,
				Tables.InstituteRegistrationRequest.groupOfInstitute, Tables.InstituteRegistrationRequest.noOfInstitute, Tables.InstituteRegistrationRequest.table,
				Tables.InstituteRegistrationRequest.isActive, Tables.InstituteRegistrationRequest.requestNumber, Tables.InstituteRegistrationRequest.authToken,
				Tables.InstituteRegistrationRequest.email, Tables.InstituteRegistrationRequest.status);

		try {
			
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet .CONCUR_UPDATABLE);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setString(2, requestNumber.trim());
			preparedStatement.setString(3, otp.trim());			
			preparedStatement.setString(4, emailId);
			preparedStatement.setString(5, RequestedStatus.approved.name());

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				instituteFormData.setRegisterInstituteRequestId(resultSet.getLong(Tables.InstituteRegistrationRequest.id));
				instituteFormData.setInstituteName(resultSet.getString(Tables.InstituteRegistrationRequest.name));
				instituteFormData.setInstituteEmail(resultSet.getString(Tables.InstituteRegistrationRequest.email));
				instituteFormData.setInstitutePhoneNumber(resultSet.getString(Tables.InstituteRegistrationRequest.phoneNumber));
				instituteFormData.setInstituteOfficeNumber(resultSet.getString(Tables.InstituteRegistrationRequest.officeNumber));
				instituteFormData.setInstituteRegistrationId(resultSet.getString(Tables.InstituteRegistrationRequest.registrationId));
				instituteFormData.setInstituteAddressLine1(resultSet.getString(Tables.InstituteRegistrationRequest.addressLine1));
				instituteFormData.setInstituteAddressLine2(resultSet.getString(Tables.InstituteRegistrationRequest.addressLine2));
				instituteFormData.setInstituteCity(resultSet.getString(Tables.InstituteRegistrationRequest.city));
				instituteFormData.setInstituteState(resultSet.getString(Tables.InstituteRegistrationRequest.state));
				instituteFormData.setInstituteCountry(resultSet.getString(Tables.InstituteRegistrationRequest.country));
				instituteFormData.setInstitutePinCode(resultSet.getString(Tables.InstituteRegistrationRequest.pinCode));
				instituteFormData.setGroupOfInstitute(resultSet.getString(Tables.InstituteRegistrationRequest.groupOfInstitute));
				instituteFormData.setNoOfInstitute(resultSet.getInt(Tables.InstituteRegistrationRequest.noOfInstitute));
				instituteFormData.setProcessingStatus(InstituteDaoProcessStatus.validschool);
			} else {
				instituteFormData.setProcessingStatus(InstituteDaoProcessStatus.invalidschool);
			}
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			instituteFormData.setProcessingStatus(InstituteDaoProcessStatus.invalidschool);
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return instituteFormData;
	}
}
