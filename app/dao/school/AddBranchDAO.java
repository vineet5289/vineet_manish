package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import actors.SchoolRequestActorProtocol;
import utils.RandomGenerator;
import utils.StringUtils;
import views.forms.institute.AddInstituteBranchForm;
import dao.Tables;
import enum_package.InstituteDaoProcessStatus;
import enum_package.LoginState;
import enum_package.LoginType;
import enum_package.InstituteUserRole;

public class AddBranchDAO {

	@Inject @NamedDatabase("srp") private Database db;

	public SchoolRequestActorProtocol.AddInstituteBranch addBranch(AddInstituteBranchForm addInstituteBranch, Long headInstituteId) throws SQLException {
		int numberOfInstitute = 0;
		long instituteId = 0;
		String password = "";
		String headInstituteEmailId = "";

		SchoolRequestActorProtocol.AddInstituteBranch addInstituteBranchProtocol = new SchoolRequestActorProtocol.AddInstituteBranch();
		addInstituteBranchProtocol.setProcessStatus(InstituteDaoProcessStatus.branchsuccessfullyadded);

		Connection connection = null;
//		PreparedStatement loginInsertPS = null;
		PreparedStatement instituteInsertPS = null;
		PreparedStatement headInstituteSelectPS = null;
		PreparedStatement headInstituteUpdatePS = null;

		ResultSet headInstituteSelectRS = null;
		ResultSet instituteInsertRS = null;

//		String loginInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", Tables.Login.table,
//				Tables.Login.userName, Tables.Login.emailId, Tables.Login.password, Tables.Login.passwordState, Tables.Login.instituteId, Tables.Login.type,
//				Tables.Login.name, Tables.Login.role, Tables.Login.accessRights);

		String instituteInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
				Tables.Institute.table, Tables.Institute.name, Tables.Institute.email, Tables.Institute.phoneNumber, Tables.Institute.officeNumber,
				Tables.Institute.addressLine1, Tables.Institute.addressLine2, Tables.Institute.city, Tables.Institute.state, Tables.Institute.country, Tables.Institute.pinCode,
				Tables.Institute.registrationId, Tables.Institute.headInstituteId);

		String headInstituteSelectQ = String.format("SELECT %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.HeadInstitute.email, Tables.HeadInstitute.groupOfInstitute,
				Tables.HeadInstitute.noOfInstitute, Tables.HeadInstitute.table, Tables.HeadInstitute.isActive, Tables.HeadInstitute.id);

		String headInstituteUpdateQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? LIMIT 1;", Tables.HeadInstitute.table, Tables.HeadInstitute.groupOfInstitute,
				Tables.HeadInstitute.noOfInstitute, Tables.HeadInstitute.isActive, Tables.HeadInstitute.id);

		try {
			password = StringUtils.generatePassword();
			String bCryptPassword = RandomGenerator.getBCryptPassword(password);

			connection = db.getConnection();
			connection.setAutoCommit(false);
//			loginInsertPS = connection.prepareStatement(loginInsertQ);
			instituteInsertPS = connection.prepareStatement(instituteInsertQ, Statement.RETURN_GENERATED_KEYS);
			headInstituteSelectPS = connection.prepareStatement(headInstituteSelectQ, ResultSet.TYPE_FORWARD_ONLY);
			headInstituteUpdatePS = connection.prepareStatement(headInstituteUpdateQ);

			headInstituteSelectPS.setBoolean(1, true);
			headInstituteSelectPS.setLong(2, headInstituteId);
			headInstituteSelectRS = headInstituteSelectPS.executeQuery();
			if(headInstituteSelectRS.next()) {
				numberOfInstitute = headInstituteSelectRS.getInt(Tables.HeadInstitute.noOfInstitute);
				headInstituteEmailId = headInstituteSelectRS.getString(Tables.HeadInstitute.email);
			} else {
				System.out.println("exception happen during head institute select");
				addInstituteBranchProtocol.setProcessStatus(InstituteDaoProcessStatus.requestedinstitutednotfound);
				return addInstituteBranchProtocol;
			}
			numberOfInstitute++;

			headInstituteUpdatePS.setString(1, "group");
			headInstituteUpdatePS.setInt(2, numberOfInstitute);
			headInstituteUpdatePS.setBoolean(3, true);
			headInstituteUpdatePS.setLong(4, headInstituteId);
			int noOfRowUpdated = headInstituteUpdatePS.executeUpdate();
			if(noOfRowUpdated == 0) {
				addInstituteBranchProtocol.setProcessStatus(InstituteDaoProcessStatus.servererror);
				return addInstituteBranchProtocol;
			}

			instituteInsertPS.setString(1, addInstituteBranch.getInstituteName());
			instituteInsertPS.setString(2, headInstituteEmailId);
			instituteInsertPS.setString(3, addInstituteBranch.getInstitutePhoneNumber());
			instituteInsertPS.setString(4, addInstituteBranch.getInstituteOfficeNumber());
			instituteInsertPS.setString(5, addInstituteBranch.getInstituteAddressLine1());
			instituteInsertPS.setString(6, addInstituteBranch.getInstituteAddressLine2());
			instituteInsertPS.setString(7, addInstituteBranch.getInstituteCity());
			instituteInsertPS.setString(8, addInstituteBranch.getInstituteState());
			instituteInsertPS.setString(9, addInstituteBranch.getInstituteCountry());
			instituteInsertPS.setString(10, addInstituteBranch.getInstitutePinCode());
			instituteInsertPS.setString(11, addInstituteBranch.getInstituteRegistrationId());
			instituteInsertPS.setLong(12, headInstituteId);
			instituteInsertPS.execute();
			instituteInsertRS = instituteInsertPS.getGeneratedKeys();
			
			if(instituteInsertRS.next()) {
				instituteId = instituteInsertRS.getLong(1);
			} else {
				addInstituteBranchProtocol.setProcessStatus(InstituteDaoProcessStatus.servererror);
				return addInstituteBranchProtocol;
			}

//			loginInsertPS.setString(1, addInstituteBranch.getInstituteEmail().trim());
//			loginInsertPS.setString(2, addInstituteBranch.getInstituteEmail().trim());
//			loginInsertPS.setString(3, bCryptPassword);
//			loginInsertPS.setString(4, LoginState.firststate.name());
//			loginInsertPS.setLong(5, instituteId);
//			loginInsertPS.setString(6, LoginType.institute.name());
//			loginInsertPS.setString(7, addInstituteBranch.getInstituteName());
//			loginInsertPS.setString(8, Role.instituteadmin.name());
//			loginInsertPS.setString(9, "ALL=1");
//			loginInsertPS.execute();

			connection.commit();

			addInstituteBranchProtocol.setInstitutePassword(password);
			addInstituteBranchProtocol.setInstituteBranchName(addInstituteBranch.getInstituteName());
			addInstituteBranchProtocol.setInstituteEmail(headInstituteEmailId);
			addInstituteBranchProtocol.setInstituteContract(addInstituteBranch.getInstitutePhoneNumber());

			addInstituteBranchProtocol.setProcessStatus(InstituteDaoProcessStatus.branchsuccessfullyadded);
		} catch(Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			addInstituteBranchProtocol.setProcessStatus(InstituteDaoProcessStatus.servererror);
			connection.rollback();
		} finally {
//			if(loginInsertPS != null)
//				loginInsertPS.close();

			if(instituteInsertPS != null)
				instituteInsertPS.close();

			if(headInstituteSelectPS != null)
				headInstituteSelectPS.close();

			if(headInstituteUpdatePS != null)
				headInstituteUpdatePS.close();

			if(headInstituteSelectRS != null)
				headInstituteSelectRS.close();

			if(connection != null)
				connection.close();
		}

		return addInstituteBranchProtocol;
	}
}
