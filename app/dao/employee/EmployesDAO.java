package dao.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import dao.TableDetails;

public class EmployesDAO {


	public boolean addNewEmpRequest(Map<String, String> addEmployeeFormData, String reuestedPersonUserName, String schoolId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String loginQuery = String.format("INSERT INTO %s", TableDetails.LoginTable.tableName);

		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?,"
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", tableName, schoolNameField, schoolEmailField, mobileNumberField, alternativeNumberField,
				schoolRegistrationIdField, queryField, schoolAddressLine1Field, schoolAddressLine2Field, cityField, stateField, 
				countryField, pinCodeField, contactPersonNameField, requestNumberField);

		String requestNumber = "";
		try {

		} catch(Exception exception) {

		} finally {

		}
		return true;
	}
}
