package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dao.Tables;
import models.ClassCurriculumModel;
import play.db.Database;
import play.db.NamedDatabase;
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

  public boolean isProfessorFreeGivenTimeRange(long profId, String startTime, String endTime, long instituteId) throws SQLException {
    Connection connection = null;
    boolean isValid = false;
    try {
      connection = db.getConnection();
      isValid = isProfessorFreeGivenTimeRange(connection, profId, startTime, endTime, instituteId);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if(connection != null) {
        connection.close();
      }
    }
    return isValid;
  }

/*
* TODO: it's check based on profId, profAllocated, slotAllocated, and is_active, need to invistage whether it's required or not
* specailly for attandence
* */

  public boolean isProfessorFreeGivenTimeRange(Connection connection, long profId, String startTime, String endTime, long instituteId) throws SQLException {
    String selectClassCurriculumQuery = String.format("SELECT %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?", Tables.ClassCurriculum.id,
        Tables.ClassCurriculum.startTime, Tables.ClassCurriculum.endTime, Tables.ClassCurriculum.table, Tables.ClassCurriculum.isActive, Tables.ClassCurriculum.slotAllocated,
        Tables.ClassCurriculum.professorAllocated, Tables.ClassCurriculum.instituteId, Tables.ClassCurriculum.professorId);
    PreparedStatement selectClassCurriculumPs = null;
    ResultSet classCurriculumRs = null;
    try {
      selectClassCurriculumPs = connection.prepareStatement(selectClassCurriculumQuery, ResultSet.TYPE_FORWARD_ONLY);
      selectClassCurriculumPs.setBoolean(1, true);
      selectClassCurriculumPs.setBoolean(2, true);
      selectClassCurriculumPs.setBoolean(3, true);
      selectClassCurriculumPs.setLong(4, instituteId);
      selectClassCurriculumPs.setLong(5, profId);
      classCurriculumRs = selectClassCurriculumPs.executeQuery();
      while (classCurriculumRs.next()) {
        String slotStartTime = classCurriculumRs.getString(Tables.ClassCurriculum.startTime);
        String slotEndTime = classCurriculumRs.getString(Tables.ClassCurriculum.endTime);
        if(!TimeUtils.isTimeRangeIntersect(slotStartTime, slotEndTime, startTime, endTime)) {
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
