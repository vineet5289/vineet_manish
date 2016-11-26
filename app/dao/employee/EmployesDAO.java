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

import org.apache.commons.lang3.StringUtils;

import play.db.Database;
import play.db.NamedDatabase;
import utils.EmployeeUtil;
import utils.RandomGenerator;
import views.forms.employee.AddEmployeeForm;
import views.forms.employee.EmployeeDetailsForm;
import dao.Tables;
import enum_package.LoginState;

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
    ResultSet empSelectRS = null;

    boolean executedSuccessfully = false;
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
    String empUserName = "";
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      empSelectPS = connection.prepareStatement(empSelectQ, ResultSet.TYPE_FORWARD_ONLY);
      empInsertPS = connection.prepareStatement(empInsertQ);
      loginPS = connection.prepareStatement(loginQ);
      empSelectPS.setLong(1, instituteId);

      int numberOfEmp = 0;
      empSelectRS = empSelectPS.executeQuery();

      if (empSelectRS.next()) {
        numberOfEmp = empSelectRS.getInt(1);
      }

      if (addEmployeeDetails.isAutoGenerate()
          || StringUtils.isBlank(addEmployeeDetails.getEmpCode())) {
        empUserName = EmployeeUtil.getEmpUserName(instituteId, numberOfEmp);
        empCode = empUserName;
      } else {
        empCode = addEmployeeDetails.getEmpCode().trim();
        empUserName = EmployeeUtil.getEmpUserName(instituteId, empCode, numberOfEmp);
      }

      empInsertPS.setString(1, addEmployeeDetails.getEmpName().trim());
      empInsertPS.setString(2, empUserName);
      empInsertPS.setLong(3, instituteId);
      empInsertPS.setString(4, addEmployeeDetails.getGender().trim().toUpperCase());
      empInsertPS.setString(5, addEmployeeDetails.getEmpPhoneNumber().trim());
      empInsertPS.setString(6, empCode);
      empInsertPS.setString(7, addEmployeeDetails.getEmpEmail());
      empInsertPS.setString(8, addEmployeeDetails.getJobTitle());
      empInsertPS.setString(9, userName);
      if (empInsertPS.executeUpdate() > 0) {
        loginPS.setString(1, empUserName);
        loginPS.setString(2, RandomGenerator.getBCryptPassword(empUserName));
        loginPS.setString(3, LoginState.firststate.name());
        loginPS.setString(4, addEmployeeDetails.getEmpName().trim());
        loginPS.setString(5, addEmployeeDetails.getEmpEmail());
        loginPS.setLong(6, instituteId);
        if (loginPS.executeUpdate() > 0) {
          connection.commit();
          executedSuccessfully = true;
        } else {
          throw new Exception("Employee Registration failed");
        }
      } else {
        throw new Exception("Employee Registration failed");
      }
    } catch (Exception exception) {
      connection.rollback();
      executedSuccessfully = false;
    } finally {
      if (empSelectRS != null)
        empSelectRS.close();
      if (loginPS != null)
        loginPS.close();
      if (empInsertPS != null)
        loginPS.close();
      if (empSelectPS != null)
        loginPS.close();
      if (connection != null)
        connection.close();
    }
    return executedSuccessfully;
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
