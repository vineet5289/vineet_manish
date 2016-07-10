package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enum_package.PasswordState;
import enum_package.Role;
import play.db.DB;
import views.forms.SchoolFormData;

public class SchoolRegistrationDAO {
	//school registration field
	private String idField = "id";
	private String schoolNameField = "name";
	private String schoolRegistrationIdField = "school_registration_id";
	private String schoolUserNameField = "school_user_name";
	private String schooleEmailField = "schoole_email";
	private String addressLine1Field = "address_line1";
	private String addressLine2Field = "address_line2";
	private String cityField = "city";
	private String stateField = "state";
	private String pincodeField = "pin_code"; 
	private String phoneNumberField = "phone_number";
	private String officeNumberField = "office_number";
	private String countryField = "country";
	private String noOfShiftField = "no_of_shift";
	private String schoolCategoryField = "school_category";
	private String schoolBoardField = "school_board";
	private String schoolTypeField = "school_type";
	private String isActiveField = "is_active";
	private String accessRightsField = "access_rights";
	private String loginUserNameField = "user_name";
	private String loginEmailIdField = "email_id";
	private String loginPasswordField = "password";
	private String loginPasswordStateField = "password_state";
	private String loginRoleField = "role";
	private String schoolIdField = "school_id";

	private String loginTableName = "login";
	private String schoolTableName = "login";

	public boolean registerSchool(SchoolFormData schoolData) throws SQLException {
		boolean isSuccessfull = false;
		Connection connection = null;
		PreparedStatement schoolRegistrationPreparedStatement = null;
		PreparedStatement schoolLoginPreparedStatement = null;
		PreparedStatement empRegistrationPreparedStatement = null;
		PreparedStatement empLoginPreparedStatement = null;
		ResultSet resultSet = null;
		String insertLoginQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", 
				loginTableName, loginUserNameField, loginEmailIdField, loginPasswordField, loginPasswordStateField, loginRoleField, 
				accessRightsField, isActiveField, schoolNameField, schoolIdField);

		String insertSchoolRegistrationQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,"
				+ " %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", schoolTableName, schoolNameField, schoolRegistrationIdField, 
				schoolUserNameField, schooleEmailField, addressLine1Field, addressLine2Field, cityField, stateField, pincodeField, phoneNumberField,
				officeNumberField, countryField, noOfShiftField, schoolCategoryField, schoolBoardField, schoolTypeField);

//		String insertEmpRegistrationQuery = String.format("INSERT INTO employee (%s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?);",
//				principleName, principleUserName, principalEmail, phoneNumber1, schoolId);

		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);

			schoolRegistrationPreparedStatement = connection.prepareStatement(insertSchoolRegistrationQuery, Statement.RETURN_GENERATED_KEYS);
			schoolLoginPreparedStatement = connection.prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS);
//			empRegistrationPreparedStatement = connection.prepareStatement(insertEmpRegistrationQuery, Statement.RETURN_GENERATED_KEYS);
			empLoginPreparedStatement = connection.prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS);

			//school register + login insert // CORRECT_PASSWORD, SUPERADMIN
			schoolRegistrationPreparedStatement.setString(1, schoolData.getSchoolName());//schoolName
			schoolRegistrationPreparedStatement.setString(2, schoolData.getSchoolRegistration()); //schoolRegistrationId
			schoolRegistrationPreparedStatement.setString(3, schoolData.getSchoolUserName()); //schoolUserName
			schoolRegistrationPreparedStatement.setString(4, schoolData.getSchoolEmail()); //schooleEmail
			schoolRegistrationPreparedStatement.setString(5, schoolData.getAddressLine1()); //addressLine1
			schoolRegistrationPreparedStatement.setString(6, schoolData.getAddressLine2()); //addressLine2
			schoolRegistrationPreparedStatement.setString(7, schoolData.getCity()); //city
			schoolRegistrationPreparedStatement.setString(8, schoolData.getState()); //state
			schoolRegistrationPreparedStatement.setString(9, schoolData.getPincode()); //pincode
			schoolRegistrationPreparedStatement.setString(10, schoolData.getSchoolMobileNumber()); //phoneNumber
			schoolRegistrationPreparedStatement.setString(11, schoolData.getSchoolOfficeNumber()); //officeNumber
			schoolRegistrationPreparedStatement.setString(12, schoolData.getSchoolCountry()); //country
			schoolRegistrationPreparedStatement.setInt(13, schoolData.getNoOfShift());  //noOfShift  *****************************************
			schoolRegistrationPreparedStatement.setString(14, schoolData.getSchoolCategory()); //schoolCategory
			schoolRegistrationPreparedStatement.setString(15, schoolData.getSchoolBoard()); //schoolBoard
			schoolRegistrationPreparedStatement.setString(16, schoolData.getSchoolType()); //schoolType
			schoolRegistrationPreparedStatement.setBoolean(17, true);
			schoolRegistrationPreparedStatement.executeUpdate();
			
			resultSet = schoolRegistrationPreparedStatement.getGeneratedKeys();
			Long generatedSchoolId = -1L;
			if(resultSet.next()) {
				 generatedSchoolId = resultSet.getLong(1);
			}
				
			System.out.println("school primary key= " + generatedSchoolId);

			schoolLoginPreparedStatement.setString(1, schoolData.getSchoolUserName());
			schoolLoginPreparedStatement.setString(2, schoolData.getSchoolEmail());
			schoolLoginPreparedStatement.setString(3, schoolData.getSchoolPassword());
			schoolLoginPreparedStatement.setString(4, PasswordState.CORRECT_PASSWORD.name());
			schoolLoginPreparedStatement.setString(5, Role.SUPERADMIN.name());
			schoolLoginPreparedStatement.setString(6, "ALL=1");
			schoolLoginPreparedStatement.setBoolean(7, true);
			schoolLoginPreparedStatement.setString(8, schoolData.getSchoolName());
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

			if(empRegistrationPreparedStatement != null)
				empRegistrationPreparedStatement.close();

			if(empLoginPreparedStatement != null)
				empLoginPreparedStatement.close();

			if(connection != null)
				connection.close();
		}
		return isSuccessfull;
	}
}
