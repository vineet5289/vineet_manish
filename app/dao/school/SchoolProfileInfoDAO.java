package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import models.SchoolBoard;
import utils.DateUtiles;
import utils.file.ImageUtils;
import views.forms.institute.FirstTimeInstituteUpdateForm;
import views.forms.institute.InstituteFormData;
import views.forms.institute.InstituteGeneralInfoForm;
import views.forms.institute.InstituteHeaderInfoForm;
import views.forms.institute.InstituteShiftAndClassTimingInfoForm;
import dao.Tables;
import enum_package.FileUploadStatus;
import enum_package.InstituteDaoProcessStatus;
import enum_package.LoginType;
import enum_package.LoginState;
import enum_package.SchoolClassEnum;
import enum_package.WeekDayEnum;

public class SchoolProfileInfoDAO {

	@Inject @NamedDatabase("srp") private Database db;

	public InstituteFormData getNumberOfInstituteInGivenGroup(Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		InstituteFormData instituteFormData = new InstituteFormData();
		String selectQuery = String.format("SELECT %s, %s, %s FROM %s WHERE %s=? AND %s=?;", Tables.HeadInstitute.state, Tables.HeadInstitute.noOfInstitute,
				Tables.HeadInstitute.groupOfInstitute, Tables.HeadInstitute.table, Tables.HeadInstitute.isActive, Tables.HeadInstitute.id);
		try {
			connection = db.getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);
			resultSet = selectStatement.executeQuery();
			if(resultSet.next()) {
				instituteFormData.setNoOfInstitute(resultSet.getInt(Tables.HeadInstitute.noOfInstitute));
				instituteFormData.setGroupOfInstitute(resultSet.getString(Tables.HeadInstitute.groupOfInstitute));
				instituteFormData.setInstituteState(resultSet.getString(Tables.HeadInstitute.state));
				instituteFormData.setProcessingStatus(InstituteDaoProcessStatus.validschool);
			} else {
				instituteFormData.setProcessingStatus(InstituteDaoProcessStatus.invalidschool);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			instituteFormData.setProcessingStatus(InstituteDaoProcessStatus.invalidschool);
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(selectStatement != null)
				selectStatement.close();
			if(connection != null)
				connection.close();
		}
		return instituteFormData;
	}

	public InstituteGeneralInfoForm getSchoolGeneralInfoFrom(Long schoolId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		InstituteGeneralInfoForm schoolGeneralInfoFrom = null;
		
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s "
				+ "WHERE %s=? AND %s=?", Tables.Institute.registrationId, Tables.Institute.alternativeEmail, Tables.Institute.officeNumber, Tables.Institute.addressLine1,
				Tables.Institute.addressLine2, Tables.Institute.city, Tables.Institute.state, Tables.Institute.country, Tables.Institute.pinCode, Tables.Institute.boardId,
				Tables.Institute.type, Tables.Institute.currentFinancialYear, Tables.Institute.isHostelFacilitiesAvailable, Tables.Institute.isHostelCompulsory,
				Tables.Institute.noOfShift, Tables.Institute.classFrom, Tables.Institute.classTo, Tables.Institute.officeStartTime, Tables.Institute.officeEndTime,
				Tables.Institute.financialStartDay, Tables.Institute.financialEndDay, Tables.Institute.financialStartMonth, Tables.Institute.financialEndMonth,
				Tables.Institute.financialStartYear, Tables.Institute.financialEndYear, Tables.Institute.table, Tables.Institute.isActive, Tables.Institute.id);
		try {
			connection = db.getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);

			resultSet = selectStatement.executeQuery();
			if(resultSet.next()) {				
				schoolGeneralInfoFrom = new InstituteGeneralInfoForm();
				schoolGeneralInfoFrom.setSchoolRegistrationId(getString(resultSet.getString(Tables.Institute.registrationId)));
				schoolGeneralInfoFrom.setSchoolAlternativeEmail(getString(resultSet.getString(Tables.Institute.alternativeEmail)));
				schoolGeneralInfoFrom.setSchoolAlternativeNumber(getString(resultSet.getString(Tables.Institute.officeNumber)));
				schoolGeneralInfoFrom.setSchoolAddressLine1(getString(resultSet.getString(Tables.Institute.addressLine1)));
				schoolGeneralInfoFrom.setSchoolAddressLine2(getString(resultSet.getString(Tables.Institute.addressLine2)));
				schoolGeneralInfoFrom.setCity(getString(resultSet.getString(Tables.Institute.city)));
				schoolGeneralInfoFrom.setState(getString(resultSet.getString(Tables.Institute.state)));
				schoolGeneralInfoFrom.setCountry(getString(resultSet.getString(Tables.Institute.country)));
				schoolGeneralInfoFrom.setPincode(getString(resultSet.getString(Tables.Institute.pinCode)));
				StringBuilder sb = new StringBuilder();
				sb.append(SchoolBoard.getBoardNameGivenId(resultSet.getLong(Tables.Institute.boardId)));
				sb.append("(");
				sb.append(SchoolBoard.getBoardCodeGivenId(resultSet.getLong(Tables.Institute.boardId)));
				sb.append(")");
				schoolGeneralInfoFrom.setSchoolBoardName(sb.toString());// get borad name
				schoolGeneralInfoFrom.setSchoolType(getString(resultSet.getString(Tables.Institute.type)));
				schoolGeneralInfoFrom.setSchoolCurrentFinancialYear(getString(resultSet.getString(Tables.Institute.currentFinancialYear)));
				schoolGeneralInfoFrom.setHostelFacilitiesAvailable(resultSet.getBoolean(Tables.Institute.isHostelFacilitiesAvailable));
				schoolGeneralInfoFrom.setHostelCompulsory(resultSet.getBoolean(Tables.Institute.isHostelCompulsory));
				schoolGeneralInfoFrom.setNoOfShift(resultSet.getInt(Tables.Institute.noOfShift));
				schoolGeneralInfoFrom.setSchoolClassFrom(getString(resultSet.getString(Tables.Institute.classFrom)));
				schoolGeneralInfoFrom.setSchoolClassTo(getString(resultSet.getString(Tables.Institute.classTo)));
				schoolGeneralInfoFrom.setSchoolOfficeStartTime(getString(resultSet.getString(Tables.Institute.officeStartTime)));
				schoolGeneralInfoFrom.setSchoolOfficeEndTime(getString(resultSet.getString(Tables.Institute.officeEndTime)));

				int startDay = resultSet.getInt(Tables.Institute.financialStartDay);
				int startMonth = resultSet.getInt(Tables.Institute.financialStartMonth);
				int startYear = resultSet.getInt(Tables.Institute.financialStartYear);

				int endDay = resultSet.getInt(Tables.Institute.financialEndDay);
				int endMonth = resultSet.getInt(Tables.Institute.financialEndMonth);
				int endYear = resultSet.getInt(Tables.Institute.financialEndYear);
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
						Tables.Institute.userName, Tables.Institute.name, Tables.Institute.email, Tables.Institute.phoneNumber,
						Tables.Institute.preferedName, Tables.Institute.websiteUrl, Tables.Institute.logoUrl,
						Tables.Institute.table, Tables.Institute.isActive, Tables.Institute.id);
		try {
			connection = db.getConnection();
			selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_FORWARD_ONLY);
			selectStatement.setBoolean(1, true);
			selectStatement.setLong(2, schoolId);

			resultSet = selectStatement.executeQuery();
			if(resultSet.next()) {
				schoolHeaderInfoForm = new InstituteHeaderInfoForm();
				schoolHeaderInfoForm.setSchoolUserName(resultSet.getString(Tables.Institute.userName));
				schoolHeaderInfoForm.setSchoolName(resultSet.getString(Tables.Institute.name));
				schoolHeaderInfoForm.setSchoolEmail(resultSet.getString(Tables.Institute.email));
				schoolHeaderInfoForm.setSchoolMobileNumber(resultSet.getString(Tables.Institute.phoneNumber));
				schoolHeaderInfoForm.setSchoolPreferedName(resultSet.getString(Tables.Institute.preferedName));
				schoolHeaderInfoForm.setSchoolWebsiteUrl(resultSet.getString(Tables.Institute.websiteUrl));
				schoolHeaderInfoForm.setSchoolLogoUrl(resultSet.getString(Tables.Institute.logoUrl));
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
			connection = db.getConnection();
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
				+ "%s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.Institute.table, Tables.Institute.registrationId, Tables.Institute.alternativeEmail, Tables.Institute.officeNumber,
				Tables.Institute.addressLine1, Tables.Institute.addressLine2, Tables.Institute.city, Tables.Institute.pinCode, Tables.Institute.isHostelFacilitiesAvailable, Tables.Institute.isHostelCompulsory,
				Tables.Institute.classFrom, Tables.Institute.classTo, Tables.Institute.officeStartTime, Tables.Institute.officeEndTime, Tables.Institute.officeWeekStartDay,
				Tables.Institute.officeEndTime, Tables.Institute.currentFinancialYear, Tables.Institute.financialStartDay, Tables.Institute.financialEndDay,
				Tables.Institute.financialStartMonth, Tables.Institute.financialEndMonth, Tables.Institute.financialStartYear, Tables.Institute.financialEndYear,
				Tables.Institute.isActive, Tables.Institute.id);

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

			connection = db.getConnection();
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
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.Institute.table, Tables.Institute.name, Tables.Institute.phoneNumber,
				Tables.Institute.preferedName, Tables.Institute.websiteUrl, Tables.Institute.logoUrl, Tables.Institute.isActive, Tables.Institute.id);

		int rowUpdated = 0;
		try {
			connection = db.getConnection();
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
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", Tables.Institute.table,
				Tables.Institute.noOfShift, Tables.Institute.isHostelFacilitiesAvailable, Tables.Institute.isHostelCompulsory, Tables.Institute.officeWeekStartDay,
				Tables.Institute.officeWeekEndDay, Tables.Institute.classFrom, Tables.Institute.classTo, Tables.Institute.officeStartTime, Tables.Institute.officeEndTime,
				Tables.Institute.financialStartDay, Tables.Institute.financialEndDay, Tables.Institute.isActive, Tables.Institute.id);

		int rowUpdated = 0;
		try {
			
		} catch(Exception exception) {
			
		} finally {
			
		}

		return !(rowUpdated == 0);
	}

	public InstituteDaoProcessStatus updateSchoolMandInfo(FirstTimeInstituteUpdateForm firstTimeInstituteUpdate, Long instituteId, String userName) throws SQLException {
		InstituteDaoProcessStatus instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidschool;
		Connection connection = null;
		PreparedStatement updateStmtSchoolMadInfoPS = null;
		PreparedStatement loginPS = null;
		PreparedStatement shiftInfoPS = null;

		String updateLoginQ = String.format("UPDATE %s SET %s=? WHERE %s=? AND %s=? AND %s=? AND %s=?;", Tables.Login.table, Tables.Login.passwordState,
				Tables.Login.isActive, Tables.Login.userName, Tables.Login.passwordState, Tables.Login.type);
		String updateInstituteQ = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;",
				Tables.Institute.table, Tables.Institute.noOfShift, Tables.Institute.isHostelFacilitiesAvailable, Tables.Institute.isHostelCompulsory, Tables.Institute.officeWeekStartDay,
				Tables.Institute.officeWeekEndDay, Tables.Institute.classFrom, Tables.Institute.classTo, Tables.Institute.officeStartTime, Tables.Institute.officeEndTime,
				Tables.Institute.financialStartDay, Tables.Institute.financialEndDay, Tables.Institute.financialStartMonth, Tables.Institute.financialEndMonth,
				Tables.Institute.financialStartYear, Tables.Institute.financialEndYear, Tables.Institute.currentFinancialYear, Tables.Institute.dateFormat, Tables.Institute.boardId,
				Tables.Institute.type, Tables.Institute.isActive, Tables.Institute.id);
		
		String shiftInsertQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", Tables.SchoolShiftInfo.table,
				Tables.SchoolShiftInfo.shiftName, Tables.SchoolShiftInfo.shiftClassStartTime, Tables.SchoolShiftInfo.shiftClassEndTime, Tables.SchoolShiftInfo.shiftWeekStartDay,
				Tables.SchoolShiftInfo.shiftWeekEndDay, Tables.SchoolShiftInfo.shiftStartClassFrom, Tables.SchoolShiftInfo.shiftEndClassTo, Tables.SchoolShiftInfo.shiftAttendenceType,
				Tables.SchoolShiftInfo.schoolId);

		int rowSchoolInfoUpdated = 0;
		int rowLoginUpdated = 0;
		try {
			if(firstTimeInstituteUpdate.getShifts() == null || firstTimeInstituteUpdate.getNumberOfShift() != firstTimeInstituteUpdate.getShifts().size()) {
				System.out.println("Please cselect correct date range");
				instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidshift;
				return instituteDaoProcessStatus;
			}

			String[] startDate = DateUtiles.parseDate(firstTimeInstituteUpdate.getInstituteFinancialStartDate());
			String[] endDate = DateUtiles.parseDate(firstTimeInstituteUpdate.getInstituteFinancialEndDate());
			if(startDate == null || endDate == null) {
				instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidvalue;
				System.out.println("Please cselect correct date range");
				return instituteDaoProcessStatus;
			}

			int startMonth = DateUtiles.getMonth(startDate[0]);
			int endMonth = DateUtiles.getMonth(endDate[0]);
			int startDay = Integer.parseInt(startDate[1]);
			int endDay = Integer.parseInt(endDate[1]);
			int startYear = Integer.parseInt(startDate[2]);
			int endYear = Integer.parseInt(endDate[2]);
			String schoolCurrentFinancialYear = DateUtiles.getFinancialYear(startDate[0], startYear, endDate[0], endYear);

			connection = db.getConnection();
			connection.setAutoCommit(false);
			updateStmtSchoolMadInfoPS = connection.prepareStatement(updateInstituteQ);
			loginPS = connection.prepareStatement(updateLoginQ);
			shiftInfoPS = connection.prepareStatement(shiftInsertQ);
			
			for(InstituteShiftAndClassTimingInfoForm.Shift shiftInfo : firstTimeInstituteUpdate.getShifts()) {
				shiftInfoPS.setString(1, shiftInfo.getShiftName());
				shiftInfoPS.setString(2, shiftInfo.getShiftClassStartTime());
				shiftInfoPS.setString(3, shiftInfo.getShiftClassEndTime());
				shiftInfoPS.setString(4, shiftInfo.getShiftWeekStartDay());
				shiftInfoPS.setString(5, shiftInfo.getShiftWeekEndDay());
				shiftInfoPS.setString(6, shiftInfo.getShiftStartClassFrom());
				shiftInfoPS.setString(7, shiftInfo.getShiftEndClassTo());
				shiftInfoPS.setString(8, shiftInfo.getShiftAttendenceType());
				shiftInfoPS.setLong(9, instituteId);
				shiftInfoPS.addBatch();
			}

			loginPS.setString(1, LoginState.finalstate.name());
			loginPS.setBoolean(2, true);
			loginPS.setString(3, userName.trim());
			loginPS.setString(4, LoginState.redirectstate.name());
			loginPS.setString(5, LoginType.institute.name());

			updateStmtSchoolMadInfoPS.setInt(1, firstTimeInstituteUpdate.getNumberOfShift());
			updateStmtSchoolMadInfoPS.setBoolean(2, firstTimeInstituteUpdate.isHostelFacilitiesAvailable());
			updateStmtSchoolMadInfoPS.setBoolean(3, firstTimeInstituteUpdate.isHostelCompulsory());
			updateStmtSchoolMadInfoPS.setString(4, WeekDayEnum.of(firstTimeInstituteUpdate.getInstituteOfficeWeekEndDay()).name());
			updateStmtSchoolMadInfoPS.setString(5, WeekDayEnum.of(firstTimeInstituteUpdate.getInstituteOfficeWeekEndDay()).name());
			updateStmtSchoolMadInfoPS.setString(6, SchoolClassEnum.of(firstTimeInstituteUpdate.getInstituteClassFrom()).name());
			updateStmtSchoolMadInfoPS.setString(7, SchoolClassEnum.of(firstTimeInstituteUpdate.getInstituteClassTo()).name());
			updateStmtSchoolMadInfoPS.setString(8, firstTimeInstituteUpdate.getInstituteOfficeStartTime());
			updateStmtSchoolMadInfoPS.setString(9, firstTimeInstituteUpdate.getInstituteOfficeEndTime());
			updateStmtSchoolMadInfoPS.setInt(10, startDay);
			updateStmtSchoolMadInfoPS.setInt(11, endDay);
			updateStmtSchoolMadInfoPS.setInt(12, startMonth);
			updateStmtSchoolMadInfoPS.setInt(13, endMonth);
			updateStmtSchoolMadInfoPS.setInt(14, startYear);
			updateStmtSchoolMadInfoPS.setInt(15, endYear);
			updateStmtSchoolMadInfoPS.setString(16, schoolCurrentFinancialYear);
			updateStmtSchoolMadInfoPS.setString(17, firstTimeInstituteUpdate.getInstituteDateFormat());
			updateStmtSchoolMadInfoPS.setLong(18, SchoolBoard.getBoardIdGivenAffiliatedTo(firstTimeInstituteUpdate.getInstituteBoard()));
			updateStmtSchoolMadInfoPS.setString(19, firstTimeInstituteUpdate.getInstituteType().trim().toLowerCase());
			updateStmtSchoolMadInfoPS.setBoolean(20, true);
			updateStmtSchoolMadInfoPS.setLong(21, instituteId);

			int[] batchUpdateResult = shiftInfoPS.executeBatch();
			rowSchoolInfoUpdated = updateStmtSchoolMadInfoPS.executeUpdate();
			rowLoginUpdated = loginPS.executeUpdate();
			if(rowSchoolInfoUpdated ==0 || rowLoginUpdated == 0) {
				instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidvalue;
				throw new Exception();
			}
			connection.setAutoCommit(true);
			instituteDaoProcessStatus = InstituteDaoProcessStatus.validschool;
		} catch(Exception exception) {
			exception.printStackTrace();
			rowSchoolInfoUpdated = 0;
			connection.rollback();
			instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
		} finally {
			if(updateStmtSchoolMadInfoPS != null)
				updateStmtSchoolMadInfoPS.close();

			if(shiftInfoPS != null)
				shiftInfoPS.close();

			if(connection != null)
				connection.close();
		}
		
		return instituteDaoProcessStatus;
	}

	public FileUploadStatus updateProfileImageUrl(String url, Long id, String userName) {
		FileUploadStatus fileUploadStatus = FileUploadStatus.unsuccessfullyProfilePicUpdate;
		int numberOfRowUpdated = 0;
		Connection connection = null;
		PreparedStatement updateHeadInstitutePS = null;
		String updateHeadInstituteQ = String.format("UPDATE %s SET %s=? WHERE %s=? AND %s=? AND %s=? limit 1;", Tables.HeadInstitute.table, Tables.HeadInstitute.logoUrl,
				Tables.HeadInstitute.isActive, Tables.HeadInstitute.userName, Tables.HeadInstitute.id);
		try {
			connection = db.getConnection();
			updateHeadInstitutePS = connection.prepareStatement(updateHeadInstituteQ);
			updateHeadInstitutePS.setString(1, url);
			updateHeadInstitutePS.setBoolean(2, true);
			updateHeadInstitutePS.setString(3, userName);
			updateHeadInstitutePS.setLong(4, id);
			numberOfRowUpdated = updateHeadInstitutePS.executeUpdate();
		} catch(Exception exception) {
			exception.printStackTrace();
			fileUploadStatus = FileUploadStatus.unsuccessfullyProfilePicUpdate;
		}

		if(numberOfRowUpdated == 0) {
			fileUploadStatus = FileUploadStatus.unsuccessfullyProfilePicUpdate;
		} else {
			fileUploadStatus = FileUploadStatus.successfullyProfilePicUpdate;
		}
		return fileUploadStatus;
	}

	private String getString(String string) {
		if(string == null)
			return "";
		return string.replaceAll(";", "");
	}
}
