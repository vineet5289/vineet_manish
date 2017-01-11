package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.EmployeeModels;
import play.db.Database;
import play.db.NamedDatabase;

public class UserFetchDAO {
//  private final String userNameField = "user_name";
//  private final String schoolIdField = "school_id";
//  private final String nameField = "name";
//  private final String userSchoolTableName = "user_school";
//  private final String roleField = "role";
//  private final String employeeTableName = "employee";
//  private final String designationField = "designation";
//  private final String empEmailField = "emp_email";
//  private final String joiningDateFeild = "joining_date";
//  private final String leavingDateField = "leaving_date";
//  private final String genderField = "gender";
//  private final String empCategoryField = "emp_category";
//  private final String departmentField = "department";
//  private final String dobField = "dob";
//  private final String addressLine1Field = "address_line1";
//  private final String addressLine2Field = "address_line2";
//  private final String cityField = "city";
//  private final String stateField = "state";
//  private final String pinCodeField = "pin_code";
//  private final String countryField = "country";
//  private final String phoneNumber1Field = "phone_number1";
//  private final String phoneNumber2Field = "phone_number2";
//  private final String createdAt = "created_at";
//  private final String updatedAt = "updated_at";
//  private final String isActive = "is_active";
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public Map<String, List<EmployeeModels>> getAllUser(Long schoolId) throws SQLException {
    Map<String, List<EmployeeModels>> userInfos = new HashMap<String, List<EmployeeModels>>();
//		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
//    PreparedStatement preparedStatement = null;
//    ResultSet resultSet = null;
//    Connection connection = null;
//    String selectQuery = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=?;", userNameField, schoolIdField, nameField, roleField, userSchoolTableName, schoolIdField);
//    try {
//      connection = db.getConnection();
//      preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
//      preparedStatement.setLong(1, schoolId);
//      resultSet = preparedStatement.executeQuery();
//      while (resultSet.next()) {
//        String userName = resultSet.getString(userNameField);
//        String name = resultSet.getString(nameField);
//        String role = resultSet.getString(roleField);
//
//        EmployeeModels employeeModels = new EmployeeModels();
//        employeeModels.setName(name);
//        employeeModels.setUserName(userName);
//        employeeModels.setSchoolIds(schoolId.toString());
//        employeeModels.setRole(role);
//
//        String key = "OTHER";
//        if (role.equalsIgnoreCase(InstituteUserRole.employee.name()) || role.equalsIgnoreCase(InstituteUserRole.student.name()) ||
//            userInfos.containsKey(InstituteUserRole.guardian.name())) {
//          key = role.toUpperCase();
//        }
//
//        if (!userInfos.containsKey(key)) {
//          userInfos.put(key, new ArrayList<EmployeeModels>());
//        }
//
//        List<EmployeeModels> value = userInfos.get(key);
//        value.add(employeeModels);
//        userInfos.put(key, value);
//      }
//
//    } catch (Exception exception) {
//      System.out.println("Problem during user fetch. Please Try again");
//      exception.printStackTrace();
//    } finally {
//      if (resultSet != null)
//        resultSet.close();
//      if (preparedStatement != null)
//        preparedStatement.close();
//      if (connection != null)
//        connection.close();
//    }
    return userInfos;
  }

  public List<EmployeeModels> getAllGuardian(Long schoolId) {
    return null;
  }

  public List<EmployeeModels> getAllStudents(Long schoolId) {
    return null;
  }

  public List<EmployeeModels> getAllOtherUser(Long schoolId) {
    return null;
  }

  public EmployeeModels getEmployeeInfo(String userName, Long schoolId) throws SQLException {
    EmployeeModels employeeModels = null;
//    PreparedStatement preparedStatement = null;
//    ResultSet resultSet = null;
//    Connection connection = null;
//    String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND is_active=true;",
//        userNameField, schoolIdField, nameField, designationField, empEmailField, joiningDateFeild, leavingDateField, genderField, empCategoryField, dobField, departmentField,
//        addressLine1Field, addressLine2Field, cityField, stateField, pinCodeField, countryField, phoneNumber1Field, phoneNumber2Field, employeeTableName,
//        schoolIdField, userNameField);
//    try {
//      connection = db.getConnection();
//      preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
//      preparedStatement.setLong(1, schoolId);
//      preparedStatement.setString(2, userName);
//      resultSet = preparedStatement.executeQuery();
//      if (resultSet.next()) {
//        employeeModels = new EmployeeModels();
//        employeeModels.setName(resultSet.getString(nameField));
//        employeeModels.setSchoolIds(resultSet.getLong(schoolIdField) + "");
//        employeeModels.setUserName(resultSet.getString(userNameField));
//        employeeModels.setDesignation(resultSet.getString(designationField));
//        employeeModels.setEmailId(resultSet.getString(empEmailField));
//        employeeModels.setJoiningDate(resultSet.getDate(joiningDateFeild));
//        employeeModels.setLeavingDate(resultSet.getDate(leavingDateField));
//        employeeModels.setGender(resultSet.getString(genderField));
//        employeeModels.setEmpCategory(resultSet.getString(empCategoryField));
//        employeeModels.setDob(resultSet.getDate(dobField));
//        employeeModels.setDepartment(resultSet.getString(departmentField));
//        employeeModels.setAddressLine1Field(resultSet.getString(addressLine1Field));
//        employeeModels.setAddressLine2Field(resultSet.getString(addressLine2Field));
//        employeeModels.setCity(resultSet.getString(cityField));
//        employeeModels.setState(resultSet.getString(stateField));
//        employeeModels.setPinCode(resultSet.getString(pinCodeField));
//        employeeModels.setCountry(resultSet.getString(countryField));
//        employeeModels.setPhoneNumber1(resultSet.getString(phoneNumber1Field));
//        employeeModels.setPhoneNumber2(resultSet.getString(phoneNumber2Field));
//      }
//    } catch (Exception exception) {
//      exception.printStackTrace();
//      employeeModels = null;
//    } finally {
//      if (resultSet != null)
//        resultSet.close();
//      if (preparedStatement != null)
//        preparedStatement.close();
//      if (connection != null)
//        connection.close();
//    }
    return employeeModels;
  }
}
