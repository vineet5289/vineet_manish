package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;

import dao.Tables;
import enum_package.InstituteDaoProcessStatus;
import enum_package.InstituteUserRole;
import enum_package.LoginState;
import enum_package.LoginType;
import enum_package.RequestedStatus;
import play.db.Database;
import play.db.NamedDatabase;
import utils.RandomGenerator;
import views.forms.institute.InstituteFormData;

public class SchoolRegistrationDAO {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public InstituteDaoProcessStatus registerInstitute(InstituteFormData instituteData, String referenceNumber, String authToken,
                                                     Long regInstituteRequestId) throws SQLException {
    InstituteDaoProcessStatus instituteDaoProcessStatus = InstituteDaoProcessStatus.validschool;
    Connection connection = null;
    PreparedStatement headInstituteRegistrationPS = null;
    PreparedStatement instituteRegistrationPS = null;
    PreparedStatement instituteLoginPS = null;
    PreparedStatement selectRegistrationRequestPS = null;
    PreparedStatement updateRegistrationRequestPS = null;
    ResultSet headInstituteRegRS = null;
    ResultSet selectInstituteRegReqRS = null;

    String loginInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
        Tables.Login.table, Tables.Login.userName, Tables.Login.emailId, Tables.Login.password, Tables.Login.passwordState, Tables.Login.role,
        Tables.Login.accessRights, Tables.Login.isActive, Tables.Login.name, Tables.Login.instituteId, Tables.Login.type);

    String headInstituteInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?,"
            + " ?, ?, ?, ?, ?, ?, ?, ?);", Tables.HeadInstitute.table, Tables.HeadInstitute.name, Tables.HeadInstitute.email, Tables.HeadInstitute.phoneNumber,
        Tables.HeadInstitute.officeNumber, Tables.HeadInstitute.addressLine1, Tables.HeadInstitute.addressLine2, Tables.HeadInstitute.city,
        Tables.HeadInstitute.state, Tables.HeadInstitute.country, Tables.HeadInstitute.pinCode, Tables.HeadInstitute.registrationId, Tables.HeadInstitute.userName,
        Tables.HeadInstitute.groupOfInstitute, Tables.HeadInstitute.noOfInstitute, Tables.HeadInstitute.addInstituteRequestId);

    String instituteInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)  VALUES (?, ?, ?, ?, ?, ?, ?, ?,"
            + "?, ?, ?, ?);", Tables.Institute.table, Tables.Institute.name, Tables.Institute.email, Tables.Institute.phoneNumber, Tables.Institute.officeNumber,
        Tables.Institute.addressLine1, Tables.Institute.addressLine2, Tables.Institute.city, Tables.Institute.state, Tables.Institute.country, Tables.Institute.pinCode,
        Tables.Institute.registrationId, Tables.Institute.headInstituteId);

    String selectSchoolRegistrationRequestQ = String.format("SELECT %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? AND %s=?;", Tables.InstituteRegistrationRequest.id,
        Tables.InstituteRegistrationRequest.name, Tables.InstituteRegistrationRequest.table, Tables.InstituteRegistrationRequest.isActive, Tables.InstituteRegistrationRequest.requestNumber,
        Tables.InstituteRegistrationRequest.authToken, Tables.InstituteRegistrationRequest.email, Tables.InstituteRegistrationRequest.status, Tables.InstituteRegistrationRequest.id);

    String updateSchoolRegistrationRequestQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? limit 1;", Tables.InstituteRegistrationRequest.table, Tables.InstituteRegistrationRequest.isActive,
        Tables.InstituteRegistrationRequest.status, Tables.InstituteRegistrationRequest.id);

    try {
      String bCryptPassword = RandomGenerator.getBCryptPassword(instituteData.getInstitutePassword());
      connection = db.getConnection();
      connection.setAutoCommit(false);

      headInstituteRegistrationPS = connection.prepareStatement(headInstituteInsertQ, Statement.RETURN_GENERATED_KEYS);
      instituteRegistrationPS = connection.prepareStatement(instituteInsertQ, Statement.RETURN_GENERATED_KEYS);
      instituteLoginPS = connection.prepareStatement(loginInsertQ, Statement.RETURN_GENERATED_KEYS);
      updateRegistrationRequestPS = connection.prepareStatement(updateSchoolRegistrationRequestQ);
      selectRegistrationRequestPS = connection.prepareStatement(selectSchoolRegistrationRequestQ, ResultSet.TYPE_FORWARD_ONLY);

      selectRegistrationRequestPS.setBoolean(1, true);
      selectRegistrationRequestPS.setString(2, referenceNumber.trim());
      selectRegistrationRequestPS.setString(3, authToken.trim());
      selectRegistrationRequestPS.setString(4, instituteData.getInstituteEmail().trim());
      selectRegistrationRequestPS.setString(5, RequestedStatus.approved.name());
      selectRegistrationRequestPS.setLong(6, regInstituteRequestId);
      selectInstituteRegReqRS = selectRegistrationRequestPS.executeQuery();
      if (!selectInstituteRegReqRS.next()) {
        instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidreferencenumber;
        System.out.println("select query exception occur inside SchoolRegistrationDAO.registerSchool");
        if (connection != null)
          connection.rollback();
        return instituteDaoProcessStatus;
      }

      long registrationRequestId = selectInstituteRegReqRS.getLong(Tables.InstituteRegistrationRequest.id);
      updateRegistrationRequestPS.setBoolean(1, false);
      updateRegistrationRequestPS.setString(2, RequestedStatus.registered.name());
      updateRegistrationRequestPS.setLong(3, registrationRequestId);
      int updateRowCount = updateRegistrationRequestPS.executeUpdate();

      if (updateRowCount == 0) {
        instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
        System.out.println("update query exception occur inside SchoolRegistrationDAO.registerSchool");
        if (connection != null)
          connection.rollback();
        return instituteDaoProcessStatus;
      }

      headInstituteRegistrationPS.setString(1, instituteData.getInstituteName());
      headInstituteRegistrationPS.setString(2, instituteData.getInstituteEmail());
      headInstituteRegistrationPS.setString(3, instituteData.getInstitutePhoneNumber());
      headInstituteRegistrationPS.setString(4, instituteData.getInstituteOfficeNumber());

      headInstituteRegistrationPS.setString(5, instituteData.getInstituteAddressLine1());
      headInstituteRegistrationPS.setString(6, instituteData.getInstituteAddressLine2());
      headInstituteRegistrationPS.setString(7, instituteData.getInstituteCity());
      headInstituteRegistrationPS.setString(8, instituteData.getInstituteState());
      headInstituteRegistrationPS.setString(9, instituteData.getInstituteCountry());
      headInstituteRegistrationPS.setString(10, instituteData.getInstitutePinCode());
      headInstituteRegistrationPS.setString(11, instituteData.getInstituteRegistrationId());
      headInstituteRegistrationPS.setString(12, instituteData.getInstituteEmail()); //schoolUserName
      headInstituteRegistrationPS.setString(13, instituteData.getGroupOfInstitute());
      int numberOfInstitute = 0;
      if (instituteData.getGroupOfInstitute().equalsIgnoreCase("single")) {
        numberOfInstitute = 1;
      }
      headInstituteRegistrationPS.setInt(14, numberOfInstitute);
      headInstituteRegistrationPS.setLong(15, registrationRequestId);
      headInstituteRegistrationPS.execute();
      headInstituteRegRS = headInstituteRegistrationPS.getGeneratedKeys();
      Long generatedHeadInstituteId = -1L;
      if (headInstituteRegRS.next()) {
        generatedHeadInstituteId = headInstituteRegRS.getLong(1);
      } else {
        instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
        System.out.println("school registration query exception occur inside SchoolRegistrationDAO.registerSchool");
        if (connection != null)
          connection.rollback();
        return instituteDaoProcessStatus;
      }

      int noOfEntryInsertedIntoInstituteTable = 0;
      if (instituteData.getGroupOfInstitute().equalsIgnoreCase("single") && numberOfInstitute == 1) {
        instituteRegistrationPS.setString(1, instituteData.getInstituteName());
        instituteRegistrationPS.setString(2, instituteData.getInstituteEmail());
        instituteRegistrationPS.setString(3, instituteData.getInstitutePhoneNumber());
        instituteRegistrationPS.setString(4, instituteData.getInstituteOfficeNumber());
        instituteRegistrationPS.setString(5, instituteData.getInstituteAddressLine1());
        instituteRegistrationPS.setString(6, instituteData.getInstituteAddressLine2());
        instituteRegistrationPS.setString(7, instituteData.getInstituteCity());
        instituteRegistrationPS.setString(8, instituteData.getInstituteState());
        instituteRegistrationPS.setString(9, instituteData.getInstituteCountry());
        instituteRegistrationPS.setString(10, instituteData.getInstitutePinCode());
        instituteRegistrationPS.setString(11, instituteData.getInstituteRegistrationId());
        instituteRegistrationPS.setLong(12, generatedHeadInstituteId);
        noOfEntryInsertedIntoInstituteTable = instituteRegistrationPS.executeUpdate();
      }

      if (instituteData.getGroupOfInstitute().equalsIgnoreCase("single") && numberOfInstitute == 1
          && noOfEntryInsertedIntoInstituteTable != 1) {
        instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
        System.out.println("school registration query exception occur inside SchoolRegistrationDAO.registerSchool");
        if (connection != null)
          connection.rollback();
        return instituteDaoProcessStatus;
      }

      String loginState = (instituteData.getGroupOfInstitute().equalsIgnoreCase("single") && numberOfInstitute == 1) ?
          LoginState.redirectstate.name() : LoginState.finalstate.name();
      instituteLoginPS.setString(1, instituteData.getInstituteEmail());
      instituteLoginPS.setString(2, instituteData.getInstituteEmail());
      instituteLoginPS.setString(3, bCryptPassword);
      instituteLoginPS.setString(4, loginState);
      instituteLoginPS.setString(5, InstituteUserRole.institutegroupadmin.name());
      instituteLoginPS.setString(6, "ALL=1");
      instituteLoginPS.setBoolean(7, true);
      instituteLoginPS.setString(8, instituteData.getInstituteName());
      instituteLoginPS.setLong(9, generatedHeadInstituteId);
      instituteLoginPS.setString(10, LoginType.headinstitute.name());
      if(instituteLoginPS.executeUpdate() == 1) {
        connection.commit();
        instituteDaoProcessStatus = InstituteDaoProcessStatus.validschool;
      } else {
        connection.rollback();
        instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
      }
    } catch (Exception exception) {
      System.out.println("connection exception happen");
      exception.printStackTrace();
      if (connection != null)
        connection.rollback();
      instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
    } finally {
      if (selectInstituteRegReqRS != null) {
        selectInstituteRegReqRS.close();
      }
      if (headInstituteRegRS != null) {
        headInstituteRegRS.close();
      }
      if (headInstituteRegistrationPS != null) {
        headInstituteRegistrationPS.close();
      }
      if (instituteRegistrationPS != null) {
        instituteRegistrationPS.close();
      }
      if (instituteLoginPS != null) {
        instituteLoginPS.close();
      }
      if (selectRegistrationRequestPS != null) {
        selectRegistrationRequestPS.close();
      }
      if (updateRegistrationRequestPS != null) {
        updateRegistrationRequestPS.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return instituteDaoProcessStatus;
  }
}
