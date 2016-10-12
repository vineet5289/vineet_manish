package dao.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import views.forms.employee.AddEmployeeForm;
import views.forms.employee.EmployeeDetailsForm;
import dao.Tables;

public class EmployesDAO {

	@Inject @NamedDatabase("srp") private Database db;

	public boolean addNewEmpRequest(AddEmployeeForm addEmployeeDetails, String reuestedPersonUserName, String instituteId) throws SQLException {
		Connection connection = null;
		PreparedStatement loginPS = null;
		PreparedStatement empInsertPS = null;
		PreparedStatement empSelectPS = null;

		String loginQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?);", Tables.Login.table,
				Tables.Login.userName, Tables.Login.password, Tables.Login.passwordState, Tables.Login.name, Tables.Login.emailId,
				Tables.Login.instituteId);

		String empInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", Tables.Employee.table,
				Tables.Employee.name, Tables.Employee.userName, Tables.Employee.instituteId, Tables.Employee.gender, Tables.Employee.phoneNumber,
				Tables.Employee.empCode, Tables.Employee.empEmail, Tables.Employee.jobTitles, Tables.Employee.requestedUserName);

		String empSelectQ = String.format("SELECT COUNT(1) FROM %s WHERE %s=?;", Tables.Employee.table, Tables.Employee.instituteId);
		
		String empCode = "";
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			if(addEmployeeDetails.getEmpCode() == null || addEmployeeDetails.getEmpCode().trim().isEmpty()) {
				
//				empCode = 
			}
			
			connection.commit();
		} catch(Exception exception) {
			connection.rollback();
		} finally {
			if(loginPS != null)
				loginPS.close();
			if(connection != null)
				connection.close();
		}
		return true;
	}

	public Map<Boolean, EmployeeDetailsForm> getAllEmp(String instituteId) throws SQLException {
		Map<Boolean, EmployeeDetailsForm> employees = new HashMap<Boolean, EmployeeDetailsForm>();
		Connection connection = null;
		PreparedStatement empSelectPS = null;
		ResultSet resultSet = null;
		String empSelectQ = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,"
				+ "%s, %s, %s, %s FROM %s WHERE %s=?;", Tables.Employee.id, Tables.Employee.name, Tables.Employee.userName,
				Tables.Employee.gender, Tables.Employee.phoneNumber, Tables.Employee.empEmail, Tables.Employee.alternativeNumber,
				Tables.Employee.empAlternativeEmail, Tables.Employee.jobTitles, Tables.Employee.dob, Tables.Employee.addressLine1,
				Tables.Employee.addressLine2, Tables.Employee.city, Tables.Employee.state, Tables.Employee.country, Tables.Employee.pinCode,
				Tables.Employee.empCode, Tables.Employee.empPreferedName, Tables.Employee.joiningDate, Tables.Employee.leavingDate,
				Tables.Employee.createdAt, Tables.Employee.isActive, Tables.Employee.requestedUserName, Tables.Employee.table,
				Tables.Employee.instituteId);
		try {
			connection = db.getConnection();
			empSelectPS = connection.prepareStatement(empSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			empSelectPS.setString(1, instituteId);
			resultSet = empSelectPS.executeQuery();
			while(resultSet.next()) {
				EmployeeDetailsForm employeeDetailsForm = new EmployeeDetailsForm();
//				employeeDetailsForm.setId();
//				employeeDetailsForm.setInstituteId();
//				employeeDetailsForm.setRequestedUserName();
//
//				employeeDetailsForm.setName();
//				employeeDetailsForm.setGender();
//				employeeDetailsForm.setJobTitles();
//
//				employeeDetailsForm.setUserName();
//				employeeDetailsForm.setPhoneNumber();
//				employeeDetailsForm.setEmpCode();
//				employeeDetailsForm.setEmpPreferedName();
//				employeeDetailsForm.setEmpAlternativeEmail();
//				employeeDetailsForm.setAlternativeNumber();
//				employeeDetailsForm.setEmpEmail();
//				employeeDetailsForm.setDob();
//				employeeDetailsForm.setJoiningDate();
//				employeeDetailsForm.setAddressLine1();
//				employeeDetailsForm.setAddressLine2();
//				employeeDetailsForm.setCity();
//				employeeDetailsForm.setState();
//				employeeDetailsForm.setPinCode();
//				employeeDetailsForm.setCountry();
//
//				employeeDetailsForm.setLeavingDate();
			}
			
		} catch(Exception exception) {
		} finally {
			if(connection != null)
				connection.close();
		}
		return employees;
	}
}
