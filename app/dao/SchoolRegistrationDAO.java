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
	private String id = "id";
	private String schoolName = "name";
	private String schoolRegistrationId = "school_registration_id";
	private String schoolUserName = "school_user_name";
	private String schooleEmail = "schoole_email";
	private String addressLine1 = "address_line1";
	private String addressLine2 = "address_line2";
	private String city = "city";
	private String state = "state";
	private String pincode = "pin_code"; 
	private String phoneNumber = "phone_number";
	private String officeNumber = "office_number";
	private String country = "country";
	private String noOfShift = "no_of_shift";
	private String schoolCategory = "school_category";
	private String schoolBoard = "school_board";
	private String schoolType = "school_type";
	private String createdAt = "created_at";
	private String updatedAt = "updated_at";
	private String isActive = "is_active";

	//employee mendatory field
	private String principleName = "name";
	private String principleUserName = "user_name";
	private String principalEmail = "emp_email";
	private String phoneNumber1 = "phone_number1";

	//login table field
	private String loginUserName = "user_name";
	private String loginEmailId = "email_id";
	private String loginPassword = "password";
	private String loginPasswordState = "password_state";
	private String loginRole = "role";

	private String schoolId = "school_id";

	public boolean registerSchool(SchoolFormData schoolData) throws SQLException {
		boolean isSuccessfull = false;
		Connection connection = null;
		PreparedStatement schoolRegistrationPreparedStatement = null;
		PreparedStatement schoolLoginPreparedStatement = null;
		PreparedStatement empRegistrationPreparedStatement = null;
		PreparedStatement empLoginPreparedStatement = null;
		ResultSet resultSet = null;
		String insertLoginQuery = String.format("INSERT INTO login (%s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?);", 
				loginUserName, loginEmailId, loginPassword, loginPasswordState, loginRole, schoolId);

		String insertSchoolRegistrationQuery = String.format("INSERT INTO school (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, "
				+ "%s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", schoolName, schoolRegistrationId, schoolUserName,
				schooleEmail, addressLine1, addressLine2, city, state, pincode, phoneNumber, officeNumber, country, noOfShift, schoolCategory,
				schoolBoard, schoolType);

		String insertEmpRegistrationQuery = String.format("INSERT INTO employee (%s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?);",
				principleName, principleUserName, principalEmail, phoneNumber1, schoolId);

		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);

			schoolRegistrationPreparedStatement = connection.prepareStatement(insertSchoolRegistrationQuery, Statement.RETURN_GENERATED_KEYS);
			schoolLoginPreparedStatement = connection.prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS);
			empRegistrationPreparedStatement = connection.prepareStatement(insertEmpRegistrationQuery, Statement.RETURN_GENERATED_KEYS);
			empLoginPreparedStatement = connection.prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS);

			//school register + login insert // CORRECT_PASSWORD, SUPERADMIN
			schoolRegistrationPreparedStatement.setString(1, schoolData.getSchoolName());//schoolName
			schoolRegistrationPreparedStatement.setString(2, schoolData.getSchoolRegistration()); //schoolRegistrationId
			schoolRegistrationPreparedStatement.setString(3, schoolData.getSchoolUserName()); //schoolUserName
			schoolRegistrationPreparedStatement.setString(4, schoolData.getSchooleEmail()); //schooleEmail
			schoolRegistrationPreparedStatement.setString(5, schoolData.getAddressLine1()); //addressLine1
			schoolRegistrationPreparedStatement.setString(6, schoolData.getAddressLine2()); //addressLine2
			schoolRegistrationPreparedStatement.setString(7, schoolData.getCity()); //city
			schoolRegistrationPreparedStatement.setString(8, schoolData.getState()); //state
			schoolRegistrationPreparedStatement.setString(9, schoolData.getPincode()); //pincode
			schoolRegistrationPreparedStatement.setString(10, schoolData.getSchoolMobileNumber()); //phoneNumber
			schoolRegistrationPreparedStatement.setString(11, schoolData.getSchoolOfficeNumber()); //officeNumber
			schoolRegistrationPreparedStatement.setString(12, schoolData.getSchoolCountry()); //country
			schoolRegistrationPreparedStatement.setInt(13, 1);  //noOfShift  *****************************************
			schoolRegistrationPreparedStatement.setString(14, schoolData.getSchoolCategory()); //schoolCategory
			schoolRegistrationPreparedStatement.setString(15, schoolData.getSchoolBoard()); //schoolBoard
			schoolRegistrationPreparedStatement.setString(16, schoolData.getSchoolType()); //schoolType
			schoolRegistrationPreparedStatement.executeUpdate();
			
			resultSet = schoolRegistrationPreparedStatement.getGeneratedKeys();
			Long generatedSchoolId = -1L;
			if(resultSet.next()) {
				 generatedSchoolId = resultSet.getLong(1);
			}
				
			System.out.println("school primary key= " + generatedSchoolId);

			schoolLoginPreparedStatement.setString(1, schoolData.getSchoolUserName());
			schoolLoginPreparedStatement.setString(2, schoolData.getSchooleEmail());
			schoolLoginPreparedStatement.setString(3, schoolData.getSchoolPassword());
			schoolLoginPreparedStatement.setString(4, PasswordState.CORRECT_PASSWORD.name()); //loginPasswordState
			schoolLoginPreparedStatement.setString(5, Role.SUPERADMIN.name());
			schoolLoginPreparedStatement.setLong(6, generatedSchoolId);
			schoolLoginPreparedStatement.execute();

			//employee register + login insert // First, ADMIN
			empRegistrationPreparedStatement.setString(1, schoolData.getPrincipleName()); //principleName
			empRegistrationPreparedStatement.setString(2, schoolData.getPrincipleUserName()); //principleUserName
			empRegistrationPreparedStatement.setString(3, schoolData.getPrincipleEmail()); //principalEmail
			empRegistrationPreparedStatement.setString(4, schoolData.getPrincipleMobileNumber()); //phoneNumber1
			empRegistrationPreparedStatement.setLong(5, generatedSchoolId); //schoolId
			empRegistrationPreparedStatement.execute();
			
			empLoginPreparedStatement.setString(1, schoolData.getPrincipleUserName());
			empLoginPreparedStatement.setString(2, schoolData.getPrincipleEmail());
			empLoginPreparedStatement.setString(3, schoolData.getPrinciplePassword());
			empLoginPreparedStatement.setString(4, PasswordState.FIRST_TIME.name()); //loginPasswordState
			empLoginPreparedStatement.setString(5, Role.ADMIN.name());
			empLoginPreparedStatement.setLong(6, generatedSchoolId);
			empLoginPreparedStatement.execute();
			
			//make invalie school_registration_request
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
