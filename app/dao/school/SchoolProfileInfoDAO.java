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
import utils.ShiftGenerator;
import views.forms.institute.FirstTimeInstituteUpdateForm;
import views.forms.institute.InstituteGeneralInfoForm;
import views.forms.institute.InstituteHeaderInfoForm;
import views.forms.institute.InstituteShiftAndClassTimingInfoForm;
import dao.Tables;
import enum_package.LoginTypeEnum;
import enum_package.PasswordState;
import enum_package.SchoolClassEnum;
import enum_package.WeekDayEnum;

public class SchoolProfileInfoDAO {

	public InstituteGeneralInfoForm getSchoolGeneralInfoFrom(Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		InstituteGeneralInfoForm schoolGeneralInfoFrom = null;
		
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s "
				+ "WHERE %s=? AND %s=?", Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, Tables.School.officeNumber, Tables.School.addressLine1,
				Tables.School.addressLine2, Tables.School.city, Tables.School.state, Tables.School.country, Tables.School.pinCode, Tables.School.schoolBoardId,
				Tables.School.schoolType, Tables.School.schoolCurrentFinancialYear, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory,
				Tables.School.noOfShift, Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolFinancialStartDay, Tables.School.schoolFinancialEndDay, Tables.School.schoolFinancialStartMonth, Tables.School.schoolFinancialEndMonth,
				Tables.School.schoolFinancialStartYear, Tables.School.schoolFinancialEndYear, Tables.School.table, Tables.School.isActive, Tables.School.id);
		try {
			connection = DB.getDataSource("srp").getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);

			resultSet = selectStatement.executeQuery();
			if(resultSet.next()) {				
				schoolGeneralInfoFrom = new InstituteGeneralInfoForm();
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

				int startDay = resultSet.getInt(Tables.School.schoolFinancialStartDay);
				int startMonth = resultSet.getInt(Tables.School.schoolFinancialStartMonth);
				int startYear = resultSet.getInt(Tables.School.schoolFinancialStartYear);

				int endDay = resultSet.getInt(Tables.School.schoolFinancialEndDay);
				int endMonth = resultSet.getInt(Tables.School.schoolFinancialEndMonth);
				int endYear = resultSet.getInt(Tables.School.schoolFinancialEndYear);
				String schoolStratDate = DateUtiles.getDate(startDay, startMonth, startYear);
				String schoolEndDate = DateUtiles.getDate(endDay, endMonth, endYear);

				schoolGeneralInfoFrom.setSchoolFinancialStartDate(schoolStratDate);
				schoolGeneralInfoFrom.setSchoolFinancialEndDate(schoolEndDate);
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

	public InstituteHeaderInfoForm getSchoolHeaderInfoForm(long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		InstituteHeaderInfoForm schoolHeaderInfoForm = null;
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
				schoolHeaderInfoForm = new InstituteHeaderInfoForm();
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

	public InstituteShiftAndClassTimingInfoForm getSchoolShiftAndClassTimingInfoForm(Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		InstituteShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfoForm = null;
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.SchoolShiftInfo.shiftName,
				Tables.SchoolShiftInfo.shiftClassStartTime, Tables.SchoolShiftInfo.shiftClassEndTime, Tables.SchoolShiftInfo.shiftWeekStartDay,
				Tables.SchoolShiftInfo.shiftWeekEndDay, Tables.SchoolShiftInfo.shiftStartClassFrom, Tables.SchoolShiftInfo.shiftEndClassTo,
				Tables.SchoolShiftInfo.shiftAttendenceType, Tables.SchoolShiftInfo.table, Tables.SchoolShiftInfo.isActive, Tables.SchoolShiftInfo.schoolId);
		try {
			connection = DB.getDataSource("srp").getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);
			resultSet = selectStatement.executeQuery();
			int numberOfShift = 0;
			List<InstituteShiftAndClassTimingInfoForm.Shift> shifts = new ArrayList<InstituteShiftAndClassTimingInfoForm.Shift>();
			while(resultSet.next()) {
				InstituteShiftAndClassTimingInfoForm.Shift shift = new InstituteShiftAndClassTimingInfoForm.Shift();
				shift.setShiftName(resultSet.getString(Tables.SchoolShiftInfo.shiftName));
				shift.setShiftClassStartTime(resultSet.getString(Tables.SchoolShiftInfo.shiftClassStartTime));
				shift.setShiftClassEndTime(resultSet.getString(Tables.SchoolShiftInfo.shiftClassEndTime));
				shift.setShiftWeekStartDay(resultSet.getString(Tables.SchoolShiftInfo.shiftWeekStartDay));
				shift.setShiftWeekEndDay(resultSet.getString(Tables.SchoolShiftInfo.shiftWeekEndDay));
				shift.setShiftStartClassFrom(resultSet.getString(Tables.SchoolShiftInfo.shiftStartClassFrom));
				shift.setShiftEndClassTo(resultSet.getString(Tables.SchoolShiftInfo.shiftEndClassTo));
				shift.setShiftAttendenceType(resultSet.getString(Tables.SchoolShiftInfo.shiftAttendenceType));
				shifts.add(shift);
				numberOfShift++;
			}

			if(numberOfShift > 0) {				
				schoolShiftAndClassTimingInfoForm = new InstituteShiftAndClassTimingInfoForm();
				schoolShiftAndClassTimingInfoForm.setNumberOfShift(numberOfShift);
				schoolShiftAndClassTimingInfoForm.setShifts(shifts);
				if(numberOfShift == 1)
					schoolShiftAndClassTimingInfoForm.setTabDisplayName("Class & Timining Info");
				else
					schoolShiftAndClassTimingInfoForm.setTabDisplayName("Shiftwise Class & Timining Info");
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

	public boolean updateSchoolGeneralInfo(InstituteGeneralInfoForm schoolGeneralInfo, Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, "
				+ "%s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.School.table, Tables.School.schoolRegistrationId, Tables.School.schoolAlternativeEmail, Tables.School.officeNumber,
				Tables.School.addressLine1, Tables.School.addressLine2, Tables.School.city, Tables.School.pinCode, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory,
				Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime, Tables.School.schoolOfficeWeekStartDay,
				Tables.School.schoolOfficeEndTime, Tables.School.schoolCurrentFinancialYear, Tables.School.schoolFinancialStartDay, Tables.School.schoolFinancialEndDay,
				Tables.School.schoolFinancialStartMonth, Tables.School.schoolFinancialEndMonth, Tables.School.schoolFinancialStartYear, Tables.School.schoolFinancialEndYear,
				Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			String[] startDate = DateUtiles.parseDate(schoolGeneralInfo.getSchoolFinancialStartDate());
			String[] endDate = DateUtiles.parseDate(schoolGeneralInfo.getSchoolFinancialEndDate());
			if(startDate == null || endDate == null) {
				System.out.println("Please cselect correct date range");
				return false;
			}
			int startMonth = DateUtiles.getMonth(startDate[0]);
			int endMonth = DateUtiles.getMonth(endDate[0]);
			int startDay = Integer.parseInt(startDate[1]);
			int endDay = Integer.parseInt(endDate[1]);
			int startYear = Integer.parseInt(startDate[2]);
			int endYear = Integer.parseInt(endDate[2]);
			String schoolCurrentFinancialYear = DateUtiles.getFinancialYear(startDate[0], startYear, endDate[0], endYear);

			connection = DB.getDataSource("srp").getConnection();
			updateStatement = connection.prepareStatement(updateQuery);
			
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
			updateStatement.setString(16, getString(schoolCurrentFinancialYear));
			updateStatement.setInt(17, startDay);
			updateStatement.setInt(18, endDay);
			updateStatement.setInt(19, startMonth);
			updateStatement.setInt(20, endMonth);
			updateStatement.setInt(21, startYear);
			updateStatement.setInt(22, endYear);
			updateStatement.setBoolean(23, true);
			updateStatement.setLong(24, schoolId);

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

	public boolean updateSchoolHeaderInfo(InstituteHeaderInfoForm schoolHeaderInfo, Long schoolId) throws SQLException {
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

	public boolean updateSchoolShiftAndClassTimingInfo(InstituteShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo, Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.School.table,
				Tables.School.noOfShift, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory, Tables.School.schoolOfficeWeekStartDay,
				Tables.School.schoolOfficeWeekEndDay, Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolFinancialStartDay, Tables.School.schoolFinancialEndDay, Tables.School.isActive, Tables.School.id);

		int rowUpdated = 0;
		try {
			
		} catch(Exception exception) {
			
		} finally {
			
		}

		return !(rowUpdated == 0);
	}

	public boolean updateSchoolMandInfo(FirstTimeInstituteUpdateForm firstTimeSchoolUpdate, Long schoolId, String userName) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStmtSchoolMadInfo = null;
		PreparedStatement updateLogin = null;
		PreparedStatement insertShiftInfo = null;
		String updateLoginQuery = String.format("UPDATE %s SET %s=? WHERE %s=? AND %s=? AND %s=? AND %s=?;", Tables.Login.table, Tables.Login.passwordState,
				Tables.Login.isActive, Tables.Login.userName, Tables.Login.passwordState, Tables.Login.type);
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;",
				Tables.School.table, Tables.School.noOfShift, Tables.School.isHostelFacilitiesAvailable, Tables.School.isHostelCompulsory, Tables.School.schoolOfficeWeekStartDay,
				Tables.School.schoolOfficeWeekEndDay, Tables.School.schoolClassFrom, Tables.School.schoolClassTo, Tables.School.schoolOfficeStartTime, Tables.School.schoolOfficeEndTime,
				Tables.School.schoolFinancialStartDay, Tables.School.schoolFinancialEndDay, Tables.School.schoolFinancialStartMonth, Tables.School.schoolFinancialEndMonth,
				Tables.School.schoolFinancialStartYear, Tables.School.schoolFinancialEndYear, Tables.School.schoolCurrentFinancialYear, Tables.School.schoolDateFormat,Tables.School.isActive,
				Tables.School.id);
		
//		String shiftInsertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", Tables.SchoolShiftInfo.table,
//				Tables.SchoolShiftInfo.shiftName, Tables.SchoolShiftInfo.shiftClassStartTime, Tables.SchoolShiftInfo.shiftClassEndTime, Tables.SchoolShiftInfo.shiftWeekStartDay,
//				Tables.SchoolShiftInfo.shiftWeekEndDay, Tables.SchoolShiftInfo.shiftStartClassFrom, Tables.SchoolShiftInfo.shiftEndClassTo, Tables.SchoolShiftInfo.shiftAttendenceType,
//				Tables.SchoolShiftInfo.schoolId);


		int rowSchoolInfoUpdated = 0;
		int rowLoginUpdated = 0;
		try {
//			SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfoForm = ShiftGenerator.generateShift(firstTimeSchoolUpdate);
			String[] startDate = DateUtiles.parseDate(firstTimeSchoolUpdate.getSchoolFinancialStartDate());
			String[] endDate = DateUtiles.parseDate(firstTimeSchoolUpdate.getSchoolFinancialEndDate());
			if(startDate == null || endDate == null) {
				System.out.println("Please cselect correct date range");
				return false;
			}
			int startMonth = DateUtiles.getMonth(startDate[0]);
			int endMonth = DateUtiles.getMonth(endDate[0]);
			int startDay = Integer.parseInt(startDate[1]);
			int endDay = Integer.parseInt(endDate[1]);
			int startYear = Integer.parseInt(startDate[2]);
			int endYear = Integer.parseInt(endDate[2]);
			String schoolCurrentFinancialYear = DateUtiles.getFinancialYear(startDate[0], startYear, endDate[0], endYear);

			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			updateStmtSchoolMadInfo = connection.prepareStatement(updateQuery);
			updateLogin = connection.prepareStatement(updateLoginQuery);
//			insertShiftInfo = connection.prepareStatement(shiftInsertQuery);
			
//			for(int i = 0; i < schoolShiftAndClassTimingInfoForm.getNumberOfShift(); i++) {
//				insertShiftInfo.setString(1, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftName());
//				insertShiftInfo.setString(2, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftClassStartTime());
//				insertShiftInfo.setString(3, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftClassEndTime());
//				insertShiftInfo.setString(4, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftWeekStartDay());
//				insertShiftInfo.setString(5, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftWeekEndDay());
//				insertShiftInfo.setString(6, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftStartClassFrom());
//				insertShiftInfo.setString(7, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftEndClassTo());
//				insertShiftInfo.setString(8, schoolShiftAndClassTimingInfoForm.getShifts().get(i).getShiftAttendenceType());
//				insertShiftInfo.setLong(9, schoolId);
//				insertShiftInfo.addBatch();
//			}

			updateLogin.setString(1, PasswordState.finalstate.name());
			updateLogin.setBoolean(2, true);
			updateLogin.setString(3, userName.trim());
			updateLogin.setString(4, PasswordState.redirectstate.name());
			updateLogin.setString(5, LoginTypeEnum.SCHOOL.name());

			updateStmtSchoolMadInfo.setInt(1, firstTimeSchoolUpdate.getNumberOfShift());
			updateStmtSchoolMadInfo.setBoolean(2, firstTimeSchoolUpdate.isHostelFacilitiesAvailable());
			updateStmtSchoolMadInfo.setBoolean(3, firstTimeSchoolUpdate.isHostelCompulsory());
			updateStmtSchoolMadInfo.setString(4, WeekDayEnum.of(firstTimeSchoolUpdate.getSchoolOfficeWeekStartDay()).name());
			updateStmtSchoolMadInfo.setString(5, WeekDayEnum.of(firstTimeSchoolUpdate.getSchoolOfficeWeekEndDay()).name());
			updateStmtSchoolMadInfo.setString(6, SchoolClassEnum.of(firstTimeSchoolUpdate.getSchoolClassFrom()).name());
			updateStmtSchoolMadInfo.setString(7, SchoolClassEnum.of(firstTimeSchoolUpdate.getSchoolClassTo()).name());
			updateStmtSchoolMadInfo.setString(8, firstTimeSchoolUpdate.getSchoolOfficeStartTime());
			updateStmtSchoolMadInfo.setString(9, firstTimeSchoolUpdate.getSchoolOfficeEndTime());
			updateStmtSchoolMadInfo.setInt(10, startDay);
			updateStmtSchoolMadInfo.setInt(11, endDay);
			updateStmtSchoolMadInfo.setInt(12, startMonth);
			updateStmtSchoolMadInfo.setInt(13, endMonth);
			updateStmtSchoolMadInfo.setInt(14, startYear);
			updateStmtSchoolMadInfo.setInt(15, endYear);
			updateStmtSchoolMadInfo.setString(16, schoolCurrentFinancialYear);
			updateStmtSchoolMadInfo.setString(17, firstTimeSchoolUpdate.getSchoolDateFormat());
			updateStmtSchoolMadInfo.setBoolean(18, true);
			updateStmtSchoolMadInfo.setLong(19, schoolId);

//			int[] batchUpdateResult = insertShiftInfo.executeBatch();
			rowSchoolInfoUpdated = updateStmtSchoolMadInfo.executeUpdate();
			rowLoginUpdated = updateLogin.executeUpdate();

			connection.setAutoCommit(true);
		} catch(Exception exception) {
			exception.printStackTrace();
			rowSchoolInfoUpdated = 0;
			connection.rollback();
		} finally {
			if(updateStmtSchoolMadInfo != null)
				updateStmtSchoolMadInfo.close();

			if(insertShiftInfo != null)
				insertShiftInfo.close();

			if(connection != null)
				connection.close();
		}
		return (rowSchoolInfoUpdated != 0 && rowLoginUpdated != 0);
	}

	private String getString(String string) {
		if(string == null)
			return "";
		return string.replaceAll(";", "");
	}
}
