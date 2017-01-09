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

  public long createCurriculumForClass(Connection connection, long classId) throws SQLException {
    String selectClassCurriculumQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=?");
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
    return -1l;
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
      classCurriculumRs = selectClassCurriculumPs.executeQuery();
      if(classCurriculumRs.isBeforeFirst()) {
        return true;
      }

      while (classCurriculumRs.next()) {
        String slotStartTime = classCurriculumRs.getString(Tables.ClassCurriculum.startTime);
        String slotEndTime = classCurriculumRs.getString(Tables.ClassCurriculum.endTime);
        String slotStartDate = classCurriculumRs.getString(Tables.ClassCurriculum.startDate);
        String slotEndDate = classCurriculumRs.getString(Tables.ClassCurriculum.endDate);
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
      if (selectClassCurriculumPs != null) {
        selectClassCurriculumPs.close();
      }
    }
    return true;
  }
}
