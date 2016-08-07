package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import models.SchoolBoard;
import play.db.DB;
import utils.DateUtiles;
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
				+ "%s, %s, %s FROM %s WHERE %s=? AND %s=?", Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, 
				Tables.School.officeNumber, Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.state,
				Tables.School.country, Tables.School.pinCode, Tables.School.schoolBoardId, Tables.School.schoolType, Tables.School.schoolCurrentFinancialYear,
				Tables.School.schoolCategory, Tables.School.noOfShift, Tables.School.schoolClassFrom, Tables.School.schoolClassTo,
				Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime, Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate,
				Tables.School.table, Tables.School.isActive, Tables.School.id);
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
				schoolGeneralInfoFrom.setSchoolCategory(getString(resultSet.getString(Tables.School.schoolCategory)));
				schoolGeneralInfoFrom.setNoOfShift(resultSet.getInt(Tables.School.noOfShift));
				schoolGeneralInfoFrom.setSchoolClassFrom(getString(resultSet.getString(Tables.School.schoolClassFrom)));
				schoolGeneralInfoFrom.setSchoolClassTo(getString(resultSet.getString(Tables.School.schoolClassTo)));
				schoolGeneralInfoFrom.setSchoolOfficeStartTime(getString(resultSet.getString(Tables.School.schoolOfficeStartTime)));
				schoolGeneralInfoFrom.setSchoolOfficeEndTime(getString(resultSet.getString(Tables.School.schoolOfficeEndTime)));
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

	public boolean updateSchoolGeneralInfo(SchoolGeneralInfoFrom schoolGeneralInfo, Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? "
				+ "WHERE %s=? AND %s=?;", Tables.School.table, Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, Tables.School.officeNumber,
				Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.pinCode, Tables.School.schoolCategory, Tables.School.schoolClassFrom,
				Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime, Tables.School.schoolOfficeWeekStartDay, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolCurrentFinancialYear, Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate, Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			connection = DB.getDataSource("srp").getConnection();
			updateStatement = connection.prepareStatement(updateQuery);

			Date startDate = schoolGeneralInfo.getSchoolFinancialStartDate();
			Date endDate = schoolGeneralInfo.getSchoolFinancialEndDate();
			String currentFinancial = DateUtiles.getFinancialYear(startDate, endDate);

			updateStatement.setString(1, getString(schoolGeneralInfo.getSchoolRegistrationId()));
			updateStatement.setString(2, getString(schoolGeneralInfo.getSchoolAlternativeEmail()));
			updateStatement.setString(3, getString(schoolGeneralInfo.getSchoolAlternativeNumber()));
			updateStatement.setString(4, getString(schoolGeneralInfo.getSchoolAddressLine1()));
			updateStatement.setString(5, getString(schoolGeneralInfo.getSchoolAddressLine2()));
			updateStatement.setString(6, getString(schoolGeneralInfo.getCity()));
			updateStatement.setString(7, getString(schoolGeneralInfo.getPincode()));
			updateStatement.setString(8, getString(schoolGeneralInfo.getSchoolCategory()));
			updateStatement.setString(9, getString(schoolGeneralInfo.getSchoolClassFrom()));
			updateStatement.setString(10, getString(schoolGeneralInfo.getSchoolClassTo()));
			updateStatement.setString(11, getString(schoolGeneralInfo.getSchoolOfficeStartTime()));
			updateStatement.setString(12, getString(schoolGeneralInfo.getSchoolOfficeEndTime()));
			updateStatement.setString(13, getString(schoolGeneralInfo.getSchoolOfficeWeekStartDay()).toUpperCase());
			updateStatement.setString(14, getString(schoolGeneralInfo.getSchoolOfficeWeekEndDay()).toUpperCase());
			updateStatement.setString(15, getString(currentFinancial));
			updateStatement.setDate(16, new java.sql.Date(startDate.getTime()));
			updateStatement.setDate(17, new java.sql.Date(endDate.getTime()));
			updateStatement.setBoolean(18, true);
			updateStatement.setLong(19, schoolId);

			rowUpdated = updateStatement.executeUpdate();
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(updateStatement != null)
				updateStatement.close();
			if(connection != null)
				connection.close();
		}

		return !(rowUpdated == 0);
	}

	public boolean updateSchoolHeaderInfo(SchoolHeaderInfoForm schoolHeaderInfo, Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.School.table, Tables.School.name, Tables.School.phoneNumber,
				Tables.School.schoolPreferedName, Tables.School.schoolWebsiteUrl, Tables.School.schoolLogoUrl, Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			connection = DB.getDataSource("srp").getConnection();
			updateStatement = connection.prepareStatement(updateQuery);

			updateStatement.setString(1, schoolHeaderInfo.getSchoolName());
			updateStatement.setString(2, schoolHeaderInfo.getSchoolMobileNumber());
			updateStatement.setString(3, schoolHeaderInfo.getSchoolPreferedName());
			updateStatement.setString(4, schoolHeaderInfo.getSchoolWebsiteUrl());
			updateStatement.setString(5, schoolHeaderInfo.getSchoolLogoUrl());
			updateStatement.setBoolean(6, true);
			updateStatement.setLong(7, schoolId);
			rowUpdated = updateStatement.executeUpdate();
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(updateStatement != null)
				updateStatement.close();
			if(connection != null)
				connection.close();
		}

		return !(rowUpdated == 0);
	}

	public boolean updateSchoolShiftAndClassTimingInfo(SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? "
				+ "WHERE %s=? AND %s=?;", Tables.School.table, Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, Tables.School.officeNumber,
				Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.pinCode, Tables.School.schoolCategory, Tables.School.schoolClassFrom,
				Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime, Tables.School.schoolOfficeWeekStartDay, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolCurrentFinancialYear, Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate, Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			
		} catch(Exception exception) {
			
		} finally {
			
		}

		return !(rowUpdated == 0);
	}

	private String getString(String string) {
		if(string == null)
			return "";
		
		return string.replaceAll(";", "");
	}
}
