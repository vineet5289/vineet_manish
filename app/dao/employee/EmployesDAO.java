package dao.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import play.db.Database;
import play.db.NamedDatabase;
import utils.EmployeeUtil;
import utils.RandomGenerator;
import views.forms.employee.AddEmployeeForm;
import views.forms.employee.EmployeeDetailsForm;
import dao.Tables;
import dao.dao_operation_status.EmployeeDaoActionStatus;
import enum_package.InstituteUserRole;
import enum_package.LoginState;
import enum_package.LoginType;

public class EmployesDAO {

  @Inject
  @NamedDatabase("srp")
  private Database db;

  public EmployeeDaoActionStatus addNewEmpRequest(AddEmployeeForm addEmployeeDetails,
      String userName, long instituteId) throws SQLException {
    Connection connection = null;
    PreparedStatement loginPS = null;
    PreparedStatement empInsertPS = null;
    PreparedStatement empSelectPS = null;
    ResultSet empSelectRS = null;

    EmployeeDaoActionStatus employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
    String loginQ =
        String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
            Tables.Login.table, Tables.Login.userName, Tables.Login.password,
            Tables.Login.passwordState, Tables.Login.name, Tables.Login.emailId,
            Tables.Login.instituteId, Tables.Login.role, Tables.Login.type);

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

      int numberOfEmp = 1;
      empSelectRS = empSelectPS.executeQuery();

      if (empSelectRS.next()) {
        numberOfEmp += empSelectRS.getInt(1);
      }

      if (addEmployeeDetails.isAutoGenerate()
          || StringUtils.isBlank(addEmployeeDetails.getEmpCode())) {
        empUserName = EmployeeUtil.getEmpUserName(instituteId, numberOfEmp);
        empCode = EmployeeUtil.getEmpCode(numberOfEmp);
      } else {
        empCode = addEmployeeDetails.getEmpCode().trim();
        empUserName = EmployeeUtil.getEmpUserName(instituteId, numberOfEmp);
      }

      empInsertPS.setString(1, addEmployeeDetails.getEmpName().trim());
      empInsertPS.setString(2, empUserName);
      empInsertPS.setLong(3, instituteId);
      empInsertPS.setString(4, addEmployeeDetails.getGender());
      empInsertPS.setString(5, addEmployeeDetails.getEmpPhoneNumber().trim());
      empInsertPS.setString(6, empCode);
      empInsertPS.setString(7, addEmployeeDetails.getEmpEmail());
      empInsertPS.setString(8, addEmployeeDetails.getJobTitle());
      empInsertPS.setString(9, userName);

      String randomPassword = RandomGenerator.getRandomPassword("E-");
      System.out.println("**********randomPassword*************" + randomPassword);
      loginPS.setString(1, empUserName);
      loginPS.setString(2, getEncryptPass(randomPassword));
      loginPS.setString(3, LoginState.firststate.name());
      loginPS.setString(4, addEmployeeDetails.getEmpName().trim());
      loginPS.setString(5, addEmployeeDetails.getEmpEmail());
      loginPS.setLong(6, instituteId);
      loginPS.setString(7, InstituteUserRole.of(InstituteUserRole.employee));
      loginPS.setString(8, LoginType.of(LoginType.emp));
      if (empInsertPS.executeUpdate() == 1 && loginPS.executeUpdate() == 1) {
        connection.commit();
        employeeDaoActionStatus = EmployeeDaoActionStatus.successfullyAdded;
      } else {
        throw new Exception("Employee Registration failed");
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      connection.rollback();
      employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
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
    return employeeDaoActionStatus;
  }

  public List<EmployeeDetailsForm> getAllEmp(Long instituteId, boolean status) throws SQLException {
    List<EmployeeDetailsForm> employees = new ArrayList<EmployeeDetailsForm>();
    Connection connection = null;
    PreparedStatement empSelectPS = null;
    ResultSet resultSet = null;
    String empSelectQ =
        String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.Employee.id,
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

  public EmployeeDaoActionStatus enableDisableEmployee(long instituteId, String empUserName,
      boolean status, String requestedUserName) throws SQLException {
    EmployeeDaoActionStatus employeeDaoActionStatus =
        EmployeeDaoActionStatus.norecordfoundforgivenusername;
    Connection connection = null;
    PreparedStatement empUpdatePS = null;
    PreparedStatement loginUpdatePS = null;
    String empUpdateQ =
        String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? LIMIT 1;",
            Tables.Employee.table, Tables.Employee.isActive, Tables.Employee.requestedUserName,
            Tables.Employee.instituteId, Tables.Employee.userName);

    String loginUpdateQ =
        String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? LIMIT 1;", Tables.Login.table,
            Tables.Login.isActive, Tables.Login.passwordState, Tables.Login.instituteId,
            Tables.Login.userName);
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      empUpdatePS = connection.prepareStatement(empUpdateQ);
      loginUpdatePS = connection.prepareStatement(loginUpdateQ);
      empUpdatePS.setBoolean(1, status);
      empUpdatePS.setString(2, requestedUserName);
      empUpdatePS.setLong(3, instituteId);
      empUpdatePS.setString(4, empUserName);

      loginUpdatePS.setBoolean(1, status);
      loginUpdatePS.setString(2, LoginState.blockedstate.name());
      loginUpdatePS.setLong(3, instituteId);
      loginUpdatePS.setString(4, empUserName);

      if (empUpdatePS.executeUpdate() == 1 && loginUpdatePS.executeUpdate() == 1) {
        connection.commit();
        employeeDaoActionStatus = EmployeeDaoActionStatus.successfullyDeleted;
      } else {
        connection.rollback();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      connection.rollback();
      employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
    } finally {
      if (loginUpdatePS != null)
        loginUpdatePS.close();
      if (empUpdatePS != null)
        empUpdatePS.close();
      if (connection != null)
        connection.close();
    }
    return employeeDaoActionStatus;
  }

  public EmployeeDaoActionStatus updateEmployeeInfo(EmployeeDetailsForm employeeDetails,
      String section, String type) throws SQLException {
    EmployeeDaoActionStatus employeeDaoActionStatus =
        EmployeeDaoActionStatus.norecordfoundforgivenusername;
    if (section.equalsIgnoreCase("general") && type.equalsIgnoreCase("self")) {
      employeeDaoActionStatus = generalInfoUpdateByEmployee(employeeDetails);
    }
    return employeeDaoActionStatus;
  }

  private EmployeeDaoActionStatus generalInfoUpdateByEmployee(EmployeeDetailsForm employeeDetails)
      throws SQLException {
    EmployeeDaoActionStatus employeeDaoActionStatus =
        EmployeeDaoActionStatus.norecordfoundforgivenusername;
    Connection connection = null;
    PreparedStatement empUpdatePS = null;
    PreparedStatement loginUpdatePS = null;
    String empUpdateQ =
        String.format(
            "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, "
                + "%s=?, %s=?, %s=? WHERE %s=? AND %s=? AND %s=? LIMIT 1;", Tables.Employee.table,
            Tables.Employee.name, Tables.Employee.gender, Tables.Employee.phoneNumber,
            Tables.Employee.empPreferedName, Tables.Employee.empAlternativeEmail,
            Tables.Employee.alternativeNumber, Tables.Employee.dob, Tables.Employee.addressLine1,
            Tables.Employee.addressLine2, Tables.Employee.city, Tables.Employee.state,
            Tables.Employee.country, Tables.Employee.pinCode, Tables.Employee.requestedUserName,
            Tables.Employee.userName, Tables.Employee.instituteId, Tables.Employee.isActive);

    String loginUpdateQ =
        String.format("UPDATE %s SET %s=? WHERE %s=? AND %s=? AND %s=? LIMIT 1;",
            Tables.Login.table, Tables.Login.name, Tables.Login.userName, Tables.Login.instituteId,
            Tables.Login.isActive);
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      empUpdatePS = connection.prepareStatement(empUpdateQ);
      loginUpdatePS = connection.prepareStatement(loginUpdateQ);

      empUpdatePS.setString(1, employeeDetails.getName());
      empUpdatePS.setString(2, employeeDetails.getGender());
      empUpdatePS.setString(3, employeeDetails.getPhoneNumber());
      empUpdatePS.setString(4, employeeDetails.getEmpPreferedName());
      empUpdatePS.setString(5, employeeDetails.getEmpAlternativeEmail());
      empUpdatePS.setString(6, employeeDetails.getAlternativeNumber());
      empUpdatePS.setString(7, employeeDetails.getDob());
      empUpdatePS.setString(8, employeeDetails.getAddressLine1());
      empUpdatePS.setString(9, employeeDetails.getAddressLine2());
      empUpdatePS.setString(10, employeeDetails.getCity());
      empUpdatePS.setString(11, employeeDetails.getState());
      empUpdatePS.setString(12, employeeDetails.getCountry());
      empUpdatePS.setString(13, employeeDetails.getPinCode());
      empUpdatePS.setString(14, employeeDetails.getUserName());
      empUpdatePS.setString(15, employeeDetails.getUserName());
      empUpdatePS.setLong(16, employeeDetails.getInstituteId());
      empUpdatePS.setBoolean(17, true);

      loginUpdatePS.setString(1, employeeDetails.getName());
      loginUpdatePS.setString(2, employeeDetails.getUserName());
      loginUpdatePS.setLong(3, employeeDetails.getInstituteId());
      loginUpdatePS.setBoolean(4, true);

      if (empUpdatePS.executeUpdate() == 1 && loginUpdatePS.executeUpdate() == 1) {
        connection.commit();
        employeeDaoActionStatus = EmployeeDaoActionStatus.successfullyUpdated;
      } else {
        connection.rollback();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      connection.rollback();
      employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
    } finally {
      if (loginUpdatePS != null)
        loginUpdatePS.close();
      if (empUpdatePS != null)
        empUpdatePS.close();
      if (connection != null)
        connection.close();
    }
    return employeeDaoActionStatus;
  }

  private String getEncryptPass(String password) {
    return RandomGenerator.getBCryptPassword(password);
  }
}
