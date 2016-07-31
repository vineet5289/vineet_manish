package dao.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import play.db.DB;
import views.forms.employee.AddEmployeeForm;
import dao.Tables;

public class EmployesDAO {


	public boolean addNewEmpRequest(AddEmployeeForm addEmployeeDetails, String reuestedPersonUserName, String schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement loginPS = null;
		PreparedStatement empInsertPS = null;
		PreparedStatement empSelectPS = null;

		String loginQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Tables.LoginTable.table,
				Tables.LoginTable.userName, Tables.LoginTable.password, Tables.LoginTable.passwordState, Tables.LoginTable.role, Tables.LoginTable.name,
				Tables.LoginTable.emailId, Tables.LoginTable.accessRights, Tables.LoginTable.schoolId);

		String empInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Tables.Employee.table,
				Tables.Employee.name, Tables.Employee.userName, Tables.Employee.schoolId, Tables.Employee.gender, Tables.Employee.phoneNumber, Tables.Employee.empCode,
				Tables.Employee.empEmail, Tables.Employee.departments, Tables.Employee.jobTitles, Tables.Employee.requestedUserName);

		String empSelectQ = String.format("SELECT COUNT(1) FROM %s WHERE %s=?;", Tables.Employee.table, Tables.Employee.schoolId);
		
		String empCode = "";
		try {
			connection = DB.getDataSource("srp").getConnection();
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
}
