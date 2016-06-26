package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import play.db.DB;
import models.UserInfo;

public class UserFetchDAO {
	private final String userNameField = "user_name";
	private final String schoolIdField = "school_id";
	private final String nameField = "name";
	private final String userSchoolTableName = "user_school";

	private final String employeeTableName = "employee";
	private final String designationField = "designation";
	private final String empEmailField = "emp_email";
	private final String joiningDateFeild = "joining_date";
	private final String leavingDateField = "leaving_date";
	private final String genderField = "gender";
	private final String empCategoryField = "emp_category";
	private final String departmentField = "department";
	private final String dobField = "dob";
	private final String addressLine1Field = "address_line1";
	private final String addressLine2Field = "address_line2";
	private final String cityField = "city";
	private final String stateField = "state";
	private final String pinCodeField = "pin_code";
	private final String countryField = "country";
	private final String phoneNumber1Field = "phone_number1";
	private final String phoneNumber2Field = "phone_number2";
	private final String createdAt = "created_at";
	private final String updatedAt = "updated_at";
	private final String isActive = "is_active";

	public List<UserInfo> getAllUser(Long schoolId) throws SQLException {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String selectQuery = String.format("SELECT %s, %s, %s FROM %s WHERE %s=?;", userNameField, schoolIdField, nameField,
				userSchoolTableName, schoolIdField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, schoolId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String userName = resultSet.getString(userNameField);
				String name = resultSet.getString(nameField);
				UserInfo userInfo = new UserInfo();
				userInfo.setName(name);
				userInfo.setUserName(userName);
				userInfo.setSchoolIds(schoolId.toString());
				userInfos.add(userInfo);
			}
			
		} catch(Exception exception) {
			System.out.println("Problem during user fetch. Please Try again");
			exception.printStackTrace();
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return userInfos;
	}

	public List<UserInfo> getAllTeachers(Long schoolId) {
		return null;
	}

	public List<UserInfo> getAllGuardian(Long schoolId) {
		return null;
	}

	public List<UserInfo> getAllStudents(Long schoolId) {
		return null;
	}

	public List<UserInfo> getAllOtherUser(Long schoolId) {
		return null;
	}

	public UserInfo getEmployeeInfo(String userName, Long schoolId) throws SQLException {
		UserInfo userInfo = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND is_active=true;",
				userNameField, schoolIdField, nameField, designationField, empEmailField, joiningDateFeild, leavingDateField, genderField, empCategoryField, dobField, departmentField,
				addressLine1Field, addressLine2Field, cityField, stateField, pinCodeField, countryField, phoneNumber1Field, phoneNumber2Field, employeeTableName,
				schoolIdField, userNameField);
		try{
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, schoolId);
			preparedStatement.setString(2, userName);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				userInfo = new UserInfo();
				userInfo.setName(resultSet.getString(nameField));
				userInfo.setSchoolIds(resultSet.getLong(schoolIdField) + "");
				userInfo.setUserName(resultSet.getString(userNameField));
				userInfo.setDesignation(resultSet.getString(designationField));
				userInfo.setEmailId(resultSet.getString(empEmailField));
				userInfo.setJoiningDate(resultSet.getDate(joiningDateFeild));
				userInfo.setLeavingDate(resultSet.getDate(leavingDateField));
				userInfo.setGender(resultSet.getString(genderField));
				userInfo.setEmpCategory(resultSet.getString(empCategoryField));
				userInfo.setDob(resultSet.getDate(dobField));
				userInfo.setDepartment(resultSet.getString(departmentField));
				userInfo.setAddressLine1Field(resultSet.getString(addressLine1Field));
				userInfo.setAddressLine2Field(resultSet.getString(addressLine2Field));
				userInfo.setCity(resultSet.getString(cityField));
				userInfo.setState(resultSet.getString(stateField));
				userInfo.setPinCode(resultSet.getString(pinCodeField));
				userInfo.setCountry(resultSet.getString(countryField));
				userInfo.setPhoneNumber1(resultSet.getString(phoneNumber1Field));
				userInfo.setPhoneNumber2(resultSet.getString(phoneNumber2Field));
			}
		}catch(Exception exception) {
			exception.printStackTrace();
			userInfo = null;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		return userInfo;
	}
}
