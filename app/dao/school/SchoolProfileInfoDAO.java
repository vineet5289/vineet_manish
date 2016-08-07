package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.SchoolBoard;
import play.db.DB;
import views.forms.school.SchoolGeneralInfoFrom;
import views.forms.school.SchoolHeaderInfoForm;
import views.forms.school.SchoolShiftAndClassTimingInfoForm;
import dao.Tables;

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
				StringBuilder sb = new StringBuilder();
				sb.append(SchoolBoard.getBoardNameGivenId(resultSet.getLong(Tables.School.schoolBoardId)));
				sb.append("(");
				sb.append(SchoolBoard.getBoardCodeGivenId(resultSet.getLong(Tables.School.schoolBoardId)));
				sb.append(")");
				schoolGeneralInfoFrom.setSchoolBoardName(sb.toString());// get borad name
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

	public SchoolShiftAndClassTimingInfoForm getSchoolShiftAndClassTimingInfoForm(Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfoForm = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.SchoolShiftInfo.shiftName,
				Tables.SchoolShiftInfo.shiftClassStartTime, Tables.SchoolShiftInfo.shiftClassEndTime, Tables.SchoolShiftInfo.shiftWeekStartDay,
				Tables.SchoolShiftInfo.shiftWeekEndDay, Tables.SchoolShiftInfo.shiftStartClassName, Tables.SchoolShiftInfo.shiftEndClassName,
				Tables.SchoolShiftInfo.shiftAttendenceType, Tables.SchoolShiftInfo.table, Tables.SchoolShiftInfo.isActive, Tables.SchoolShiftInfo.schoolId);
		try {
			connection = DB.getDataSource("srp").getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);
			resultSet = selectStatement.executeQuery();
			int numberOfShift = 0;
			List<SchoolShiftAndClassTimingInfoForm.Shift> shifts = new ArrayList<SchoolShiftAndClassTimingInfoForm.Shift>();
			while(resultSet.next()) {
				SchoolShiftAndClassTimingInfoForm.Shift shift = new SchoolShiftAndClassTimingInfoForm.Shift();
				shift.setShiftName(resultSet.getString(Tables.SchoolShiftInfo.shiftName));
				shift.setShiftClassStartTime(resultSet.getString(Tables.SchoolShiftInfo.shiftClassStartTime));
				shift.setShiftClassEndTime(resultSet.getString(Tables.SchoolShiftInfo.shiftClassEndTime));
				shift.setShiftWeekStartDay(resultSet.getString(Tables.SchoolShiftInfo.shiftWeekStartDay));
				shift.setShiftWeekEndDay(resultSet.getString(Tables.SchoolShiftInfo.shiftWeekEndDay));
				shift.setShiftStartClassName(resultSet.getString(Tables.SchoolShiftInfo.shiftStartClassName));
				shift.setShiftEndClassName(resultSet.getString(Tables.SchoolShiftInfo.shiftEndClassName));
				shift.setShiftAttendenceType(resultSet.getString(Tables.SchoolShiftInfo.shiftAttendenceType));
				numberOfShift++;
			}

			if(numberOfShift > 0) {				
				schoolShiftAndClassTimingInfoForm = new SchoolShiftAndClassTimingInfoForm();
				schoolShiftAndClassTimingInfoForm.setNumberOfShift(numberOfShift);
				schoolShiftAndClassTimingInfoForm.setShifts(shifts);
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
		return schoolShiftAndClassTimingInfoForm;
	}

	public boolean updateSchoolGeneralInfo(SchoolGeneralInfoFrom schoolGeneralInfo) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, "
				+ "%s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?", "");
		boolean isUpdated = false;
		try {
			
		} catch(Exception exception) {
			exception.printStackTrace();
			isUpdated = false;
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(selectStatement != null)
				selectStatement.close();
			if(connection != null)
				connection.close();
		}

		return isUpdated;
	}

	public boolean updateSchoolHeaderInfo(SchoolHeaderInfoForm schoolHeaderInfo) throws SQLException {
		return true;
	}

	public boolean updateSchoolShiftAndClassTimingInfo(SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo) throws SQLException {
		return true;
	}

	private String getString(String string) {
		if(string == null)
			return "";
		return string;
	}
}
