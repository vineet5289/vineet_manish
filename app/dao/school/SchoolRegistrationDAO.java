package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.SchoolBoard;
import play.db.DB;
import utils.RandomGenerator;
import utils.StringUtils;
import views.forms.school.SchoolFormData;
import enum_package.PasswordState;
import enum_package.RequestedStatus;
import enum_package.Role;

public class SchoolRegistrationDAO {
	private String idField = "id";
	private String nameField = "name";
	private String schoolRegistrationIdField = "school_registration_id";
	private String schoolUserNameField = "school_user_name";
	private String schooleEmailField = "school_email";
	private String addressLine1Field = "address_line1";
	private String addressLine2Field = "address_line2";
	private String cityField = "city";
	private String stateField = "state";
	private String countryField = "country";
	private String pincodeField = "pin_code";
	private String phoneNumberField = "phone_number";
	private String officeNumberField = "office_number";
	private String noOfShiftField = "no_of_shift";
	private String schoolCategoryField = "school_category";
	private String schoolBoardField = "school_board_id";
	private String schoolTypeField = "school_type";
	private String isActiveField = "is_active";
	private String accessRightsField = "access_rights";
	private String loginUserNameField = "user_name";
	private String loginEmailIdField = "email_id";
	private String loginPasswordField = "password";
	private String loginPasswordStateField = "password_state";
	private String loginRoleField = "role";
	private String schoolIdField = "school_id";
	private String addSchoolRequestIdField = "add_school_request_id";
	private String statusField = "status"; 
	private String authTokenField = "auth_token";
	private String schoolNameField = "school_name";
	private String requestNumberField = "request_number";

	private String loginTableName = "login";
	private String schoolTableName = "school";
	private String schoolRegistrationRequestTableName = "school_registration_request";

	public boolean registerSchool(SchoolFormData schoolData, String referenceNumber, String authToken) throws SQLException {
		boolean isSuccessfull = false;
		Connection connection = null;
		PreparedStatement schoolRegistrationPreparedStatement = null;
		PreparedStatement schoolLoginPreparedStatement = null;
		PreparedStatement selectRegistrationRequestPreparedStatement = null;
		PreparedStatement updateRegistrationRequestPreparedStatement = null;
		ResultSet resultSet = null;
		ResultSet selectResultSet = null;
		String insertLoginQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", 
				loginTableName, loginUserNameField, loginEmailIdField, loginPasswordField, loginPasswordStateField, loginRoleField, 
				accessRightsField, isActiveField, nameField, schoolIdField);

		String insertSchoolRegistrationQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", schoolTableName, nameField, schoolRegistrationIdField, 
				schoolUserNameField, schooleEmailField, addressLine1Field, addressLine2Field, cityField, stateField, pincodeField, phoneNumberField,
				officeNumberField, countryField, schoolBoardField, schoolTypeField, addSchoolRequestIdField);

		String selectSchoolRegistrationRequest = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?;",
				idField, schoolNameField, schoolRegistrationIdField, isActiveField, schoolRegistrationRequestTableName,isActiveField,
				requestNumberField, authTokenField, schooleEmailField, statusField);

		String updateSchoolRegistrationRequest = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? limit 1;", schoolRegistrationRequestTableName, isActiveField,
				statusField, idField);

		try {
			String bCryptPassword = RandomGenerator.getBCryptPassword(schoolData.getPassword());

			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);

			schoolRegistrationPreparedStatement = connection.prepareStatement(insertSchoolRegistrationQuery, Statement.RETURN_GENERATED_KEYS);
			schoolLoginPreparedStatement = connection.prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS);
			updateRegistrationRequestPreparedStatement = connection.prepareStatement(updateSchoolRegistrationRequest);
			selectRegistrationRequestPreparedStatement = connection.prepareStatement(selectSchoolRegistrationRequest, ResultSet.TYPE_FORWARD_ONLY);

			selectRegistrationRequestPreparedStatement.setBoolean(1, true);
			selectRegistrationRequestPreparedStatement.setString(2, referenceNumber.trim());
			selectRegistrationRequestPreparedStatement.setString(3, authToken.trim());
			selectRegistrationRequestPreparedStatement.setString(4, schoolData.getSchoolEmail().trim());			
			selectRegistrationRequestPreparedStatement.setString(5, RequestedStatus.APPROVED.name());
			selectResultSet = selectRegistrationRequestPreparedStatement.executeQuery();
			if(!selectResultSet.next()) {
				System.out.println("select query exception occur inside SchoolRegistrationDAO.registerSchool");
				return false;
			}

			Long registrationRequestId = selectResultSet.getLong(idField);
			updateRegistrationRequestPreparedStatement.setBoolean(1, false);
			updateRegistrationRequestPreparedStatement.setString(2, RequestedStatus.REGISTERED.name());
			updateRegistrationRequestPreparedStatement.setLong(3, registrationRequestId);
			int updateRowCount = updateRegistrationRequestPreparedStatement.executeUpdate();

			if(updateRowCount == 0) {
				System.out.println("update query exception occur inside SchoolRegistrationDAO.registerSchool");
				return false;
			}

			schoolRegistrationPreparedStatement.setString(1, schoolData.getSchoolName().trim());//schoolName

			String schoolRegistrationId = "";
			if( schoolData.getSchoolRegistrationId() != null)
				schoolRegistrationId = schoolData.getSchoolRegistrationId().trim();
			schoolRegistrationPreparedStatement.setString(2, schoolRegistrationId); //schoolRegistrationId

			schoolRegistrationPreparedStatement.setString(3, schoolData.getSchoolUserName().trim()); //schoolUserName
			schoolRegistrationPreparedStatement.setString(4, schoolData.getSchoolEmail().trim()); //schooleEmail

			schoolRegistrationPreparedStatement.setString(5, StringUtils.getValidStringValue(schoolData.getSchoolAddressLine1())); //addressLine1
			schoolRegistrationPreparedStatement.setString(6, StringUtils.getValidStringValue(schoolData.getSchoolAddressLine2())); //addressLine2
			schoolRegistrationPreparedStatement.setString(7, schoolData.getCity().trim()); //city
			schoolRegistrationPreparedStatement.setString(8, schoolData.getState().trim()); //state
			schoolRegistrationPreparedStatement.setString(9, schoolData.getPincode().trim()); //pincode

			schoolRegistrationPreparedStatement.setString(10, schoolData.getSchoolMobileNumber().trim()); //phoneNumber
			String alternativeNumber = "";
			if( schoolData.getSchoolAlternativeNumber() != null)
				schoolRegistrationId = schoolData.getSchoolAlternativeNumber().trim();
			schoolRegistrationPreparedStatement.setString(11, alternativeNumber); //officeNumber
			
			schoolRegistrationPreparedStatement.setString(12, schoolData.getCountry().trim()); //country
			Long boardId = SchoolBoard.getBoardIdGivenAffiliatedTo(schoolData.getSchoolBoard().trim());
			schoolRegistrationPreparedStatement.setLong(13, boardId); //schoolBoard
			schoolRegistrationPreparedStatement.setString(14, schoolData.getSchoolType().trim().toUpperCase()); //schoolType
			schoolRegistrationPreparedStatement.setLong(15, registrationRequestId);
			schoolRegistrationPreparedStatement.executeUpdate();
			
			resultSet = schoolRegistrationPreparedStatement.getGeneratedKeys();
			Long generatedSchoolId = -1L;
			if(resultSet.next()) {
				 generatedSchoolId = resultSet.getLong(1);
			} else {
				System.out.println("school registration query exception occur inside SchoolRegistrationDAO.registerSchool");
				return false;
			}
				
			schoolLoginPreparedStatement.setString(1, schoolData.getSchoolUserName().trim());
			schoolLoginPreparedStatement.setString(2, schoolData.getSchoolEmail().trim());
			schoolLoginPreparedStatement.setString(3, bCryptPassword);
			schoolLoginPreparedStatement.setString(4, PasswordState.CORRECT_PASSWORD.name());
			schoolLoginPreparedStatement.setString(5, Role.SUPERADMIN.name());
			schoolLoginPreparedStatement.setString(6, "ALL=1");
			schoolLoginPreparedStatement.setBoolean(7, true);
			schoolLoginPreparedStatement.setString(8, schoolData.getSchoolName().trim());
			schoolLoginPreparedStatement.setLong(9, generatedSchoolId);
			schoolLoginPreparedStatement.execute();
			connection.commit();
			isSuccessfull = true;
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			isSuccessfull = false;
		} finally {
			if(resultSet != null)
				resultSet.close();

			if(schoolRegistrationPreparedStatement != null)
				schoolRegistrationPreparedStatement.close();

			if(schoolLoginPreparedStatement != null)
				schoolLoginPreparedStatement.close();

			if(selectRegistrationRequestPreparedStatement != null)
				selectRegistrationRequestPreparedStatement.close();

			if(updateRegistrationRequestPreparedStatement != null)
				updateRegistrationRequestPreparedStatement.close();

			if(connection != null)
				connection.close();
		}
		return isSuccessfull;
	}
}
