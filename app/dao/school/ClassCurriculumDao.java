package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dao.Tables;
import enum_package.WeekDayEnum;
import models.ClassCurriculumModel;
import play.db.Database;
import play.db.NamedDatabase;
import utils.StringUtils;
import utils.TimeUtils;

public class ClassCurriculumDao {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public boolean createCurriculumForClass(Connection connection, long classProfId, long subId, long timetableId, String profSlotCat,
                                          String day, String startTime, String endTime, String startDate, String endDate, String updatedBy) throws SQLException {
    String selectClassCurriculumQ = String.format("SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? LIMIT 1;", Tables.ClassCurriculum.id,
        Tables.ClassCurriculum.table, Tables.ClassCurriculum.isActive, Tables.ClassCurriculum.slotAllocated, Tables.ClassCurriculum.subjectId,
        Tables.ClassCurriculum.classProfessorId);

    String insertClassCurriculumQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?);", Tables.ClassCurriculum.table, Tables.ClassCurriculum.classProfessorId, Tables.ClassCurriculum.subjectId, Tables.ClassCurriculum.timetableId,
        Tables.ClassCurriculum.professorSlotCategory, Tables.ClassCurriculum.slotAllocated, Tables.ClassCurriculum.day, Tables.ClassCurriculum.startTime,
        Tables.ClassCurriculum.endTime, Tables.ClassCurriculum.startDate, Tables.ClassCurriculum.endDate, Tables.ClassCurriculum.updatedBy);

    String updateClassCurriculumQ = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? LIMIT 1;", Tables.ClassCurriculum.table,
        Tables.ClassCurriculum.timetableId, Tables.ClassCurriculum.professorSlotCategory, Tables.ClassCurriculum.slotAllocated, Tables.ClassCurriculum.day,
        Tables.ClassCurriculum.startTime, Tables.ClassCurriculum.endTime, Tables.ClassCurriculum.startDate, Tables.ClassCurriculum.endDate,
        Tables.ClassCurriculum.updatedBy, Tables.ClassCurriculum.id);

    PreparedStatement selectClassCurriculumPS = null;
    PreparedStatement insertClassCurriculumPS = null;
    PreparedStatement updateClassCurriculumPS = null;
    ResultSet selectRS = null;
    boolean isUpdate = false;
    try {
      selectClassCurriculumPS = connection.prepareStatement(selectClassCurriculumQ);
      selectClassCurriculumPS.setBoolean(1, true);
      selectClassCurriculumPS.setBoolean(2, false);
      selectClassCurriculumPS.setLong(3, subId);
      selectClassCurriculumPS.setLong(4, classProfId);
      selectRS = selectClassCurriculumPS.executeQuery();
      if(selectRS.next()) {
        long rowId = selectRS.getLong(1);
        updateClassCurriculumPS = connection.prepareStatement(updateClassCurriculumQ);
        updateClassCurriculumPS.setLong(1, timetableId);
        updateClassCurriculumPS.setString(2, "permanent");//TODO: change and use profSlotCat
        updateClassCurriculumPS.setBoolean(3, true);
        updateClassCurriculumPS.setString(4, day);
        updateClassCurriculumPS.setString(5, startTime);
        updateClassCurriculumPS.setString(6, endTime);
        updateClassCurriculumPS.setString(7, startDate);
        updateClassCurriculumPS.setString(8, endDate);
        updateClassCurriculumPS.setString(9, updatedBy);
        updateClassCurriculumPS.setLong(10, rowId);
        isUpdate = updateClassCurriculumPS.executeUpdate() == 1;
      } else {
        insertClassCurriculumPS = connection.prepareStatement(insertClassCurriculumQ);
        insertClassCurriculumPS.setLong(1, classProfId);
        insertClassCurriculumPS.setLong(2, subId);
        insertClassCurriculumPS.setLong(3, timetableId);
        insertClassCurriculumPS.setString(4, "permanent");//TODO: change and use profSlotCat
        insertClassCurriculumPS.setBoolean(5, true);
        insertClassCurriculumPS.setString(6, day);
        insertClassCurriculumPS.setString(7, startTime);
        insertClassCurriculumPS.setString(8, endTime);
        insertClassCurriculumPS.setString(9, startDate);
        insertClassCurriculumPS.setString(10, endDate);
        insertClassCurriculumPS.setString(11, updatedBy);
        isUpdate = insertClassCurriculumPS.executeUpdate() == 1;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      isUpdate = false;
    } finally {
      if (selectRS != null) {
        selectRS.close();
      }
      if (selectClassCurriculumPS != null) {
        selectClassCurriculumPS.close();
      }
      if (insertClassCurriculumPS != null) {
        insertClassCurriculumPS.close();
      }
      if (updateClassCurriculumPS != null) {
        updateClassCurriculumPS.close();
      }
    }
    return isUpdate;
  }

  public long createCurriculumForSection(Connection connection, long classId) {
    String selectClassCurriculumQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=?");

    return -1l;
  }

  public List<ClassCurriculumModel> getProfessorCurriculum(Connection connection, long profId) throws SQLException {
    List<ClassCurriculumModel> classCurriculumModels = new ArrayList<ClassCurriculumModel>();
    String selectClassCurriculumQuery = String.format("SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=?");
    PreparedStatement selectClassCurriculumPs = null;
    try {
      selectClassCurriculumPs = connection.prepareStatement(selectClassCurriculumQuery);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (selectClassCurriculumPs != null) {
        selectClassCurriculumPs.close();
      }
    }
    return classCurriculumModels;
  }

  public List<ClassCurriculumModel> getClassCurriculum(Connection connection, long classId) throws SQLException {
    List<ClassCurriculumModel> classCurriculumModels = new ArrayList<ClassCurriculumModel>();
    String selectClassCurriculumQuery = String.format("SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=?");
    PreparedStatement selectClassCurriculumPs = null;
    try {
      selectClassCurriculumPs = connection.prepareStatement(selectClassCurriculumQuery);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (selectClassCurriculumPs != null) {
        selectClassCurriculumPs.close();
      }
    }
    return classCurriculumModels;
  }

  public List<ClassCurriculumModel> getSectionCurriculum(Connection connection, long sectionId) throws SQLException {
    List<ClassCurriculumModel> classCurriculumModels = new ArrayList<ClassCurriculumModel>();
    String selectClassCurriculumQuery = String.format("SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=?");
    PreparedStatement selectClassCurriculumPs = null;
    try {
      selectClassCurriculumPs = connection.prepareStatement(selectClassCurriculumQuery);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (selectClassCurriculumPs != null) {
        selectClassCurriculumPs.close();
      }
    }
    return classCurriculumModels;
  }

  public boolean isProfessorFreeGivenTimeRange(long profId, String startTime, String endTime, String day, String startDate, String endDate, long instituteId) throws SQLException {
    if(!WeekDayEnum.contains(day) || !TimeUtils.isValidTimeRange(startTime, endTime)
        || (StringUtils.isNotBlank(startDate) && TimeUtils.validDate(startDate).isEmpty())
        || (StringUtils.isNotBlank(endDate) && TimeUtils.validDate(endDate).isEmpty())) {
      return false;
    }

    Connection connection = null;
    boolean isValid = false;
    try {
      connection = db.getConnection();
      isValid = isProfessorFreeGivenTimeRange(connection, profId, startTime, endTime, day, startDate, endDate, instituteId);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if(connection != null) {
        connection.close();
      }
    }
    return isValid;
  }

  public boolean isProfessorFreeGivenTimeRange(Connection connection, long profId, String startTime, String endTime, String day, String startDate, String endDate, long instituteId) throws SQLException {
    String selectClassCurriculumQuery = String.format("SELECT cc.%s AS id, %s, %s, %s, %s FROM %s AS cp, %s AS cc  WHERE cp.%s=? AND cc.%s=? AND cc.%s=? AND cp.%s=? AND cp.%s=? AND cp.%s=cc.%s AND %s=?",
        Tables.ClassCurriculum.id, Tables.ClassCurriculum.startTime, Tables.ClassCurriculum.endTime, Tables.ClassCurriculum.startDate, Tables.ClassCurriculum.endDate, Tables.ClassProfessor.table,
        Tables.ClassCurriculum.table, Tables.ClassProfessor.isActive, Tables.ClassCurriculum.isActive, Tables.ClassCurriculum.slotAllocated, Tables.ClassProfessor.instituteId, Tables.ClassProfessor.professorId,
        Tables.ClassProfessor.id, Tables.ClassCurriculum.classProfessorId, Tables.ClassCurriculum.day);
    PreparedStatement selectClassCurriculumPs = null;
    ResultSet classCurriculumRs = null;
    try {
      selectClassCurriculumPs = connection.prepareStatement(selectClassCurriculumQuery, ResultSet.TYPE_FORWARD_ONLY);
      selectClassCurriculumPs.setBoolean(1, true);
      selectClassCurriculumPs.setBoolean(2, true);
      selectClassCurriculumPs.setBoolean(3, true);
      selectClassCurriculumPs.setLong(4, instituteId);
      selectClassCurriculumPs.setLong(5, profId);
      selectClassCurriculumPs.setString(6, day);
      System.out.println(selectClassCurriculumPs.toString());
      classCurriculumRs = selectClassCurriculumPs.executeQuery();
      if(!classCurriculumRs.isBeforeFirst()) {
        System.out.println("===========1");
        return true;
      }

      while (classCurriculumRs.next()) {
        String slotStartTime = classCurriculumRs.getString(Tables.ClassCurriculum.startTime);
        String slotEndTime = classCurriculumRs.getString(Tables.ClassCurriculum.endTime);
        String slotStartDate = classCurriculumRs.getString(Tables.ClassCurriculum.startDate);
        String slotEndDate = classCurriculumRs.getString(Tables.ClassCurriculum.endDate);
        System.out.println("===========2");
        //TODO: currentily if suppose today is monday and any daterange fall after mondday is not working correctly. update
        if(!TimeUtils.isTimeRangeIntersect(slotStartTime, slotEndTime, slotStartDate, slotEndDate, startTime, endTime, startDate, endDate)) {
          return false;
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (selectClassCurriculumPs != null) {
        selectClassCurriculumPs.close();
      }
      if (classCurriculumRs != null) {
        classCurriculumRs.close();
      }
    }
    System.out.println("===========3");
    return true;
  }
}
