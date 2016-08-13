package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import play.db.DB;
import utils.RandomGenerator;
import utils.StringUtils;
import views.forms.institute.InstituteFormData;
import dao.Tables;
import enum_package.InstituteDaoProcessStatus;
import enum_package.LoginTypeEnum;
import enum_package.PasswordState;
import enum_package.RequestedStatus;
import enum_package.Role;

public class SchoolRegistrationDAO {

	public InstituteDaoProcessStatus registerInstitute(InstituteFormData schoolData, String referenceNumber, String authToken) throws SQLException {
		InstituteDaoProcessStatus instituteDaoProcessStatus = InstituteDaoProcessStatus.validschool;
		Connection connection = null;
		PreparedStatement headInstituteRegistrationPS = null;
		PreparedStatement instituteRegistrationPS = null;
		PreparedStatement instituteLoginPS = null;
		PreparedStatement selectRegistrationRequestPS = null;
		PreparedStatement updateRegistrationRequestPS = null;
		ResultSet resultSet = null;
		ResultSet selectResultSet = null;

		String insertLoginQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", 
				Tables.Login.table, Tables.Login.userName, Tables.Login.emailId, Tables.Login.password, Tables.Login.passwordState, Tables.Login.role, 
				Tables.Login.accessRights, Tables.Login.isActive, Tables.Login.name, Tables.Login.instituteId, Tables.Login.type);

		String insertHeadInstituteQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Tables.HeadInstitute.table, Tables.HeadInstitute.name, Tables.HeadInstitute.email, 
				Tables.HeadInstitute.phoneNumber, Tables.HeadInstitute.officeNumber, Tables.HeadInstitute.addressLine1, Tables.HeadInstitute.addressLine2,
				Tables.HeadInstitute.city, Tables.HeadInstitute.state, Tables.HeadInstitute.country, Tables.HeadInstitute.pinCode, Tables.HeadInstitute.registrationId,
				Tables.HeadInstitute.userName, Tables.HeadInstitute.groupOfInstitute, Tables.HeadInstitute.noOfInstitute, Tables.HeadInstitute.addInstituteRequestId);

		String insertInstituteQ =  String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ "?, ?, ?, ?);", Tables.Institute.table, Tables.Institute.name, Tables.Institute.email, Tables.Institute.phoneNumber, Tables.Institute.officeNumber,
				Tables.Institute.addressLine1, Tables.Institute.addressLine2, Tables.Institute.city, Tables.Institute.state, Tables.Institute.country, Tables.Institute.pinCode,
				Tables.Institute.registrationId, Tables.Institute.userName, Tables.Institute.headInstituteId);

		String selectSchoolRegistrationRequestQ = String.format("SELECT %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?;", Tables.InstituteRegistrationRequest.id,
				Tables.InstituteRegistrationRequest.name, Tables.InstituteRegistrationRequest.table,Tables.InstituteRegistrationRequest.isActive, Tables.InstituteRegistrationRequest.requestNumber,
				Tables.InstituteRegistrationRequest.authToken, Tables.InstituteRegistrationRequest.email, Tables.InstituteRegistrationRequest.status);

		String updateSchoolRegistrationRequestQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? limit 1;", Tables.InstituteRegistrationRequest.table, Tables.InstituteRegistrationRequest.isActive,
				Tables.InstituteRegistrationRequest.status, Tables.InstituteRegistrationRequest.id);

		try {
			String bCryptPassword = RandomGenerator.getBCryptPassword(schoolData.getInstitutePassword());
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);

			headInstituteRegistrationPS = connection.prepareStatement(insertHeadInstituteQ, Statement.RETURN_GENERATED_KEYS);
			instituteRegistrationPS = connection.prepareStatement(insertInstituteQ, Statement.RETURN_GENERATED_KEYS);
			instituteLoginPS = connection.prepareStatement(insertLoginQ, Statement.RETURN_GENERATED_KEYS);
			updateRegistrationRequestPS = connection.prepareStatement(updateSchoolRegistrationRequestQ);
			selectRegistrationRequestPS = connection.prepareStatement(selectSchoolRegistrationRequestQ, ResultSet.TYPE_FORWARD_ONLY);

			selectRegistrationRequestPS.setBoolean(1, true);
			selectRegistrationRequestPS.setString(2, referenceNumber.trim());
			selectRegistrationRequestPS.setString(3, authToken.trim());
			selectRegistrationRequestPS.setString(4, schoolData.getInstituteEmail().trim());			
			selectRegistrationRequestPS.setString(5, RequestedStatus.approved.name());
			selectResultSet = selectRegistrationRequestPS.executeQuery();
			if(!selectResultSet.next()) {
				instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidreferencenumber;
				System.out.println("select query exception occur inside SchoolRegistrationDAO.registerSchool");
				if(connection != null)
					connection.rollback();
				return instituteDaoProcessStatus;
			}

			Long registrationRequestId = selectResultSet.getLong(Tables.InstituteRegistrationRequest.id);
			updateRegistrationRequestPS.setBoolean(1, false);
			updateRegistrationRequestPS.setString(2, RequestedStatus.registered.name());
			updateRegistrationRequestPS.setLong(3, registrationRequestId);
			int updateRowCount = updateRegistrationRequestPS.executeUpdate();

			if(updateRowCount == 0) {
				instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
				System.out.println("update query exception occur inside SchoolRegistrationDAO.registerSchool");
				if(connection != null)
					connection.rollback();
				return instituteDaoProcessStatus;
			}

			headInstituteRegistrationPS.setString(1, schoolData.getInstituteName().trim());//schoolName
			headInstituteRegistrationPS.setString(2, schoolData.getInstituteEmail().trim()); //schooleEmail
			headInstituteRegistrationPS.setString(3, schoolData.getInstitutePhoneNumber().trim()); //phoneNumber
			String alternativeNumber = "";
			if( schoolData.getInstituteOfficeNumber() != null)
				alternativeNumber = schoolData.getInstituteOfficeNumber().trim();
			headInstituteRegistrationPS.setString(4, alternativeNumber); //officeNumber

			headInstituteRegistrationPS.setString(5, StringUtils.getValidStringValue(schoolData.getInstituteAddressLine1())); //addressLine1
			headInstituteRegistrationPS.setString(6, StringUtils.getValidStringValue(schoolData.getInstituteAddressLine2())); //addressLine2
			headInstituteRegistrationPS.setString(7, schoolData.getInstituteCity().trim()); //city
			headInstituteRegistrationPS.setString(8, schoolData.getInstituteState().trim()); //state
			headInstituteRegistrationPS.setString(9, schoolData.getInstituteCountry().trim()); //country
			headInstituteRegistrationPS.setString(10, schoolData.getInstitutePinCode().trim()); //pincode

			String schoolRegistrationId = "";
			if( schoolData.getInstituteRegistrationId() != null)
				schoolRegistrationId = schoolData.getInstituteRegistrationId().trim();
			headInstituteRegistrationPS.setString(11, schoolRegistrationId); //schoolRegistrationId
			headInstituteRegistrationPS.setString(12, schoolData.getInstituteUserName().trim()); //schoolUserName
			headInstituteRegistrationPS.setString(13, schoolData.getGroupOfInstitute().trim().toLowerCase());
			headInstituteRegistrationPS.setInt(14, schoolData.getNoOfInstitute());
			headInstituteRegistrationPS.setLong(15, registrationRequestId);
			headInstituteRegistrationPS.execute();
			resultSet = headInstituteRegistrationPS.getGeneratedKeys();
			Long generatedHeadInstituteId = -1L;
			if(resultSet.next()) {
				 generatedHeadInstituteId = resultSet.getLong(1);
			} else {
				instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
				System.out.println("school registration query exception occur inside SchoolRegistrationDAO.registerSchool");
				if(connection != null)
					connection.rollback();
				return instituteDaoProcessStatus;
			}


			if(schoolData.getGroupOfInstitute().equalsIgnoreCase("single") && schoolData.getNoOfInstitute() == 1) {
				instituteRegistrationPS.setString(1, schoolData.getInstituteName().trim());//schoolName
				instituteRegistrationPS.setString(2, schoolData.getInstituteEmail().trim()); //schooleEmail
				instituteRegistrationPS.setString(3, schoolData.getInstitutePhoneNumber().trim()); //phoneNumber
				instituteRegistrationPS.setString(4, alternativeNumber); //officeNumber

				instituteRegistrationPS.setString(5, StringUtils.getValidStringValue(schoolData.getInstituteAddressLine1())); //addressLine1
				instituteRegistrationPS.setString(6, StringUtils.getValidStringValue(schoolData.getInstituteAddressLine2())); //addressLine2
				instituteRegistrationPS.setString(7, schoolData.getInstituteCity().trim()); //city
				instituteRegistrationPS.setString(8, schoolData.getInstituteState().trim()); //state
				instituteRegistrationPS.setString(9, schoolData.getInstituteCountry().trim()); //country
				instituteRegistrationPS.setString(10, schoolData.getInstitutePinCode().trim()); //pincode
				instituteRegistrationPS.setString(11, schoolRegistrationId); //schoolRegistrationId
				instituteRegistrationPS.setString(12, schoolData.getInstituteUserName().trim()); //schoolUserName
				instituteRegistrationPS.setLong(13, generatedHeadInstituteId);
				instituteRegistrationPS.execute();
			}

			instituteLoginPS.setString(1, schoolData.getInstituteUserName().trim());
			instituteLoginPS.setString(2, schoolData.getInstituteEmail().trim());
			instituteLoginPS.setString(3, bCryptPassword);
			instituteLoginPS.setString(4, PasswordState.redirectstate.name());
			instituteLoginPS.setString(5, Role.institutegroupadmin.name());
			instituteLoginPS.setString(6, "ALL=1");
			instituteLoginPS.setBoolean(7, true);
			instituteLoginPS.setString(8, schoolData.getInstituteName().trim());
			instituteLoginPS.setLong(9, generatedHeadInstituteId);
			instituteLoginPS.setString(10, LoginTypeEnum.headinstitute.name());
			instituteLoginPS.execute();
			connection.commit();
			instituteDaoProcessStatus = InstituteDaoProcessStatus.validschool;
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
		} finally {
			if(resultSet != null)
				resultSet.close();

			if(headInstituteRegistrationPS != null)
				headInstituteRegistrationPS.close();

			if(instituteRegistrationPS != null)
				instituteRegistrationPS.close();

			if(instituteLoginPS != null)
				instituteLoginPS.close();

			if(selectRegistrationRequestPS != null)
				selectRegistrationRequestPS.close();

			if(updateRegistrationRequestPS != null)
				updateRegistrationRequestPS.close();

			if(connection != null)
				connection.close();
		}
		return instituteDaoProcessStatus;
	}
}
