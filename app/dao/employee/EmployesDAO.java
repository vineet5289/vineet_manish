package dao.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import views.forms.employee.AddEmployeeForm;
import views.forms.employee.EmployeeDetailsForm;
import dao.Tables;

public class EmployesDAO {

  @Inject
  @NamedDatabase("srp")
  private Database db;

  public boolean addNewEmpRequest(AddEmployeeForm addEmployeeDetails, String userName,
      long instituteId) throws SQLException {
    Connection connection = null;
    PreparedStatement loginPS = null;
    PreparedStatement empInsertPS = null;
    PreparedStatement empSelectPS = null;

    String loginQ =
        String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?);",
            Tables.Login.table, Tables.Login.userName, Tables.Login.password,
            Tables.Login.passwordState, Tables.Login.name, Tables.Login.emailId,
            Tables.Login.instituteId);

    String empInsertQ =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                Tables.Employee.table, Tables.Employee.name, Tables.Employee.userName,
                Tables.Employee.instituteId, Tables.Employee.gender, Tables.Employee.phoneNumber,
                Tables.Employee.empCode, Tables.Employee.empEmail, Tables.Employee.jobTitles,
                Tables.Employee.requestedUserName);

    String empSelectQ =
        String.format("SELECT COUNT(1) FROM %s WHERE %s=?;", Tables.Employee.table,
            Tables.Employee.instituteId);

    String empCode = "";
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      if (addEmployeeDetails.getEmpCode() == null
          || addEmployeeDetails.getEmpCode().trim().isEmpty()) {

        // empCode =
      }

      connection.commit();
    } catch (Exception exception) {
      connection.rollback();
    } finally {
      if (loginPS != null)
        loginPS.close();
      if (connection != null)
        connection.close();
    }
    return true;
  }

  public List<EmployeeDetailsForm> getAllEmp(Long instituteId, boolean status) throws SQLException {
    List<EmployeeDetailsForm> employees = new ArrayList<EmployeeDetailsForm>();
    Connection connection = null;
    PreparedStatement empSelectPS = null;
    ResultSet resultSet = null;
    String empSelectQ =
        String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s=? AND s%=?;", Tables.Employee.id,
            Tables.Employee.name, Tables.Employee.userName, Tables.Employee.jobTitles,
            Tables.Employee.empCode, Tables.Employee.table, Tables.Employee.isActive,
            Tables.Employee.instituteId);
    try {
      connection = db.getConnection();
      empSelectPS = connection.prepareStatement(empSelectQ, ResultSet.TYPE_FORWARD_ONLY);
      empSelectPS.setBoolean(1, status);
      empSelectPS.setLong(2, instituteId);
      resultSet = empSelectPS.executeQuery();
      while (resultSet.next()) {
        EmployeeDetailsForm employeeDetailsForm = new EmployeeDetailsForm();
        employeeDetailsForm.setId(resultSet.getLong(Tables.Employee.id));
        employeeDetailsForm.setInstituteId(instituteId);
        employeeDetailsForm.setName(resultSet.getString(Tables.Employee.name));
        employeeDetailsForm.setUserName(resultSet.getString(Tables.Employee.userName));
        employeeDetailsForm.setJobTitles(resultSet.getString(Tables.Employee.jobTitles));
        employeeDetailsForm.setEmpCode(resultSet.getString(Tables.Employee.empCode));
        employeeDetailsForm.setActiveEmployee(status);
        employees.add(employeeDetailsForm);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      employees = null;
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (empSelectPS != null) {
        empSelectPS.close();
      }
      if (connection != null)
        connection.close();
    }
    return employees;
  }
}
