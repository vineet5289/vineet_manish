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
import views.forms.school.FirstTimeSchoolUpdateForm;
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
		
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s "
				+ "WHERE %s=? AND %s=?", Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, Tables.School.officeNumber,
				Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.state, Tables.School.country,
				Tables.School.pinCode, Tables.School.schoolBoardId, Tables.School.schoolType, Tables.School.schoolCurrentFinancialYear,
				Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory, Tables.School.noOfShift, Tables.School.schoolClassFrom,
				Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime, Tables.School.schoolFinancialStartDate,
				Tables.School.schoolFinancialEndDate, Tables.School.table, Tables.School.isActive, Tables.School.id);
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
				schoolGeneralInfoFrom.setHostelFacilitiesAvailable(resultSet.getBoolean(Tables.School.isHostelFacilitiesAvailable));
				schoolGeneralInfoFrom.setHostelCompulsory(resultSet.getBoolean(Tables.School.isHostelCompulsory));
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
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? "
				+ "WHERE %s=? AND %s=?;", Tables.School.table, Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, Tables.School.officeNumber,
				Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.pinCode, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory,
				Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime, Tables.School.schoolOfficeWeekStartDay,
				Tables.School.schoolOfficeEndTime, Tables.School.schoolCurrentFinancialYear, Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate, Tables.School.isActive,
				Tables.School.id);

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
			updateStatement.setBoolean(8, schoolGeneralInfo.isHostelFacilitiesAvailable());
			updateStatement.setBoolean(9, schoolGeneralInfo.isHostelCompulsory());
			updateStatement.setString(10, getString(schoolGeneralInfo.getSchoolClassFrom()));
			updateStatement.setString(11, getString(schoolGeneralInfo.getSchoolClassTo()));
			updateStatement.setString(12, getString(schoolGeneralInfo.getSchoolOfficeStartTime()));
			updateStatement.setString(13, getString(schoolGeneralInfo.getSchoolOfficeEndTime()));
			updateStatement.setString(14, getString(schoolGeneralInfo.getSchoolOfficeWeekStartDay()).toUpperCase());
			updateStatement.setString(15, getString(schoolGeneralInfo.getSchoolOfficeWeekEndDay()).toUpperCase());
			updateStatement.setString(16, getString(currentFinancial));
			updateStatement.setDate(17, new java.sql.Date(startDate.getTime()));
			updateStatement.setDate(18, new java.sql.Date(endDate.getTime()));
			updateStatement.setBoolean(19, true);
			updateStatement.setLong(20, schoolId);

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

	public boolean updateSchoolShiftAndClassTimingInfo(SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo, Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.School.table,
				Tables.School.noOfShift, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory, Tables.School.schoolOfficeWeekStartDay,
				Tables.School.schoolOfficeWeekEndDay, Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate, Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			
		} catch(Exception exception) {
			
		} finally {
			
		}

		return !(rowUpdated == 0);
	}

	public boolean updateSchoolMandInfo(FirstTimeSchoolUpdateForm firstTimeSchoolUpdate, Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.School.table,
				Tables.School.noOfShift, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory, Tables.School.schoolOfficeWeekStartDay,
				Tables.School.schoolOfficeWeekEndDay, Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolFinancialStartDate, Tables.School.schoolFinancialEndDate, Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			connection = DB.getDataSource("srp").getConnection();
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.setInt(1, firstTimeSchoolUpdate.getNumberOfShift());
			updateStatement.setBoolean(2, firstTimeSchoolUpdate.isHostelFacilitiesAvailable());
			updateStatement.setBoolean(3, firstTimeSchoolUpdate.isHostelCompulsory());
			updateStatement.setString(4, firstTimeSchoolUpdate.getSchoolOfficeWeekStartDay().trim());
			updateStatement.setString(5, firstTimeSchoolUpdate.getSchoolOfficeWeekEndDay().trim());
			updateStatement.setString(6, firstTimeSchoolUpdate.getSchoolClassFrom().trim());
			updateStatement.setString(7, firstTimeSchoolUpdate.getSchoolClassTo().trim());
			updateStatement.setString(8, firstTimeSchoolUpdate.getSchoolOfficeStartTime());
			updateStatement.setString(9, firstTimeSchoolUpdate.getSchoolOfficeEndTime());
			updateStatement.setDate(10, new java.sql.Date(firstTimeSchoolUpdate.getSchoolFinancialStartDate().getTime()));
			updateStatement.setDate(11, new java.sql.Date(firstTimeSchoolUpdate.getSchoolFinancialEndDate().getTime()));
			updateStatement.setBoolean(12, true);
			updateStatement.setLong(13, schoolId);
			rowUpdated = updateStatement.executeUpdate();
		} catch(Exception exception) {
			exception.printStackTrace();
			rowUpdated = 0;
		} finally {
			if(updateStatement != null)
				updateStatement.close();
			if(connection != null)
				connection.close();
		}
		return !(rowUpdated == 0);
	}

	private String getString(String string) {
		if(string == null)
			return "";
		
		return string.replaceAll(";", "");
	}
}
