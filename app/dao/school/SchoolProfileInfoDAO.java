package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.SchoolBoard;
import dao.Tables;
import play.db.DB;
import views.forms.school.SchoolGeneralInfoFrom;
import views.forms.school.SchoolHeaderInfoForm;
import views.forms.school.SchoolShiftAndClassTimingInfoForm;

public class SchoolProfileInfoDAO {

	public SchoolGeneralInfoFrom getSchoolGeneralInfoFrom(Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		SchoolGeneralInfoFrom schoolGeneralInfoFrom = null;
		
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, "
				+ "%s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?", Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, 
				Tables.School.officeNumber, Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.state,
				Tables.School.country, Tables.School.pinCode, Tables.School.schoolBoardId, Tables.School.schoolType, Tables.School.schoolCurrentFinancialYear,
				Tables.School.schoolCurrentFinancialStartMonth, Tables.School.schoolCurrentFinancialEndMonth, Tables.School.schoolCategory,
				Tables.School.noOfShift, Tables.School.schoolStartClass, Tables.School.schoolEndClass, Tables.School.schoolStartTime, Tables.School.schoolEndTime,
				Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate, Tables.School.table, Tables.School.isActive, Tables.School.id);
		try {
			connection = DB.getDataSource("srp").getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);

			resultSet = selectStatement.executeQuery();
			if(resultSet.next()) {				
				schoolGeneralInfoFrom = new SchoolGeneralInfoFrom();
				schoolGeneralInfoFrom.setSchoolRegistrationId(getString(resultSet.getString(Tables.School.schoolRegistrationId)));
				schoolGeneralInfoFrom.setSchoolAlternativeEmail(getString(resultSet.getString(Tables.School.schoolAlternativeEmail)));
				schoolGeneralInfoFrom.setSchoolAlternativeNumber(getString(resultSet.getString(Tables.School.officeNumber)));
				schoolGeneralInfoFrom.setSchoolAddressLine1(getString(resultSet.getString(Tables.School.addressLine1)));
				schoolGeneralInfoFrom.setSchoolAddressLine2(getString(resultSet.getString(Tables.School.addressLine2)));
				schoolGeneralInfoFrom.setCity(getString(resultSet.getString(Tables.School.city)));
				schoolGeneralInfoFrom.setState(getString(resultSet.getString(Tables.School.state)));
				schoolGeneralInfoFrom.setCountry(getString(resultSet.getString(Tables.School.country)));
				schoolGeneralInfoFrom.setPincode(getString(resultSet.getString(Tables.School.pinCode)));
				schoolGeneralInfoFrom.setSchoolBoardName(SchoolBoard.getBoardNameGivenBoardCode(resultSet.getString(Tables.School.schoolBoardId)));// get borad name
				schoolGeneralInfoFrom.setSchoolType(getString(resultSet.getString(Tables.School.schoolType)));
				schoolGeneralInfoFrom.setSchoolCurrentFinancialYear(getString(resultSet.getString(Tables.School.schoolCurrentFinancialYear)));
				schoolGeneralInfoFrom.setSchoolCurrentFinancialStartMonth(getString(resultSet.getString(Tables.School.schoolCurrentFinancialStartMonth)));
				schoolGeneralInfoFrom.setSchoolCurrentFinancialEndMonth(getString(resultSet.getString(Tables.School.schoolCurrentFinancialEndMonth)));
				schoolGeneralInfoFrom.setSchoolCategory(getString(resultSet.getString(Tables.School.schoolCategory)));
				schoolGeneralInfoFrom.setNoOfShift(resultSet.getInt(Tables.School.noOfShift));
				schoolGeneralInfoFrom.setSchoolStartClass(getString(resultSet.getString(Tables.School.schoolStartClass)));
				schoolGeneralInfoFrom.setSchoolEndClass(getString(resultSet.getString(Tables.School.schoolEndClass)));
				schoolGeneralInfoFrom.setSchoolStartTime(getString(resultSet.getString(Tables.School.schoolStartTime)));
				schoolGeneralInfoFrom.setSchoolStartTime(getString(resultSet.getString(Tables.School.schoolEndTime)));
				schoolGeneralInfoFrom.setSchoolFinancialStartDate(resultSet.getDate(Tables.School.schoolFinancialStartDate));
				schoolGeneralInfoFrom.setSchoolFinancialEndDate(resultSet.getDate(Tables.School.schoolFinancialEndDate));
			}

		} catch (Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			return null;
		} finally {
			if (resultSet != null)
				resultSet.close();

			if (selectStatement != null)
				selectStatement.close();

			if (connection != null)
				connection.close();
		}
		return schoolGeneralInfoFrom;
	}

	public SchoolHeaderInfoForm getSchoolHeaderInfoForm(long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		SchoolHeaderInfoForm schoolHeaderInfoForm = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
						Tables.School.schoolUserName, Tables.School.name, Tables.School.schoolEmail, Tables.School.phoneNumber,
						Tables.School.schoolPreferedName, Tables.School.schoolWebsiteUrl, Tables.School.schoolLogoUrl,
						Tables.School.table, Tables.School.isActive, Tables.School.id);
		try {
			connection = DB.getDataSource("srp").getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);

			resultSet = selectStatement.executeQuery();
			if(resultSet.next()) {
				schoolHeaderInfoForm = new SchoolHeaderInfoForm();
				schoolHeaderInfoForm.setSchoolUserName(resultSet.getString(Tables.School.schoolUserName));
				schoolHeaderInfoForm.setSchoolName(resultSet.getString(Tables.School.name));
				schoolHeaderInfoForm.setSchoolEmail(resultSet.getString(Tables.School.schoolEmail));
				schoolHeaderInfoForm.setSchoolMobileNumber(resultSet.getString(Tables.School.phoneNumber));
				schoolHeaderInfoForm.setSchoolPreferedName(resultSet.getString(Tables.School.schoolPreferedName));
				schoolHeaderInfoForm.setSchoolWebsiteUrl(resultSet.getString(Tables.School.schoolWebsiteUrl));
				schoolHeaderInfoForm.setSchoolLogoUrl(resultSet.getString(Tables.School.schoolLogoUrl));
			}
		} catch (Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			return null;
		} finally {
			if (resultSet != null)
				resultSet.close();

			if (selectStatement != null)
				selectStatement.close();

			if (connection != null)
				connection.close();
		}
		return schoolHeaderInfoForm;
	}

	public SchoolShiftAndClassTimingInfoForm getSchoolShiftAndClassTimingInfoForm()
			throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfoForm = null;
		String selectQuery = String.format("", "");
		try {
			connection = DB.getDataSource("srp").getConnection();
			schoolShiftAndClassTimingInfoForm = new SchoolShiftAndClassTimingInfoForm();
		} catch (Exception exception) {
			System.out.println("connection exception happen");
			exception.printStackTrace();
			return null;
		} finally {
			if (resultSet != null)
				resultSet.close();

			if (selectStatement != null)
				selectStatement.close();

			if (connection != null)
				connection.close();
		}
		return schoolShiftAndClassTimingInfoForm;
	}

	private String getString(String string) {
		if(string == null)
			return "";
		return string;
	}
}
