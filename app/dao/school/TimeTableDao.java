package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dao.Tables;
import models.TimetableModel;
import play.db.Database;
import play.db.NamedDatabase;
import utils.StringUtils;
import views.forms.institute.timetable.TimeTableForm;

public class TimeTableDao {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  @Inject
  private ClassProfessorDao classProfessorDao;

  @Inject
  private ClassCurriculumDao classCurriculumDao;

  public boolean add(TimeTableForm timetableDetails, String section) throws SQLException {
    Connection connection = null;
    boolean isAdded = false;
    try {
      if (StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {
        isAdded = addTimeTableClass(timetableDetails, connection);
      } else {
        isAdded =  addTimeTableSection(timetableDetails, connection);
      }
    }catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if(connection != null) {
        connection.close();
      }
    }
    return false;
  }

  public TimeTableForm view(TimeTableForm timetableDetails, String section) throws SQLException {
    TimeTableForm timeTableData = null;
    if (StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {

    } else {

    }
    return timeTableData;
  }

  public boolean edit(TimeTableForm timetableDetails, String section) throws SQLException {
    Connection connection = null;
    if (StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {
      return addTimeTableClass(timetableDetails, connection);
    } else {
      return addTimeTableClass(timetableDetails, connection);
    }
  }

  public List<TimetableModel> get(long instituteId, long classId, long secId, String sec) throws SQLException {
    List<TimetableModel> timetableModel = new ArrayList<TimetableModel>();
    if (StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) {
      timetableModel = viewByClass(instituteId, classId);
    } else {

    }
    return timetableModel;
  }

  private List<TimetableModel> viewByClass(long instituteId, long classId) throws SQLException {
    List<TimetableModel> timetableModel = new ArrayList<TimetableModel>();
    String query = String.format("SELECT tt.%s, tt.%s, tt.%s, tt.%s, tt.%s, tt.%s, tt.%s, tt.%s, tt.%s, tt.%s, cp.%s, cc.%s, emp.%s, sub.%s " +
            "FROM %s tt, %s emp, %s sub, %s cc, %s cp WHERE tt.%s=? AND tt.%s=? AND tt.%s=? AND cc.%s=? AND cp.%s=? AND sub.%s=? AND emp.%s=? " +
            "AND tt.%s=cc.%s AND cc.%s=sub.%s AND cc.%s=cp.%s AND cp.%s=emp.%s ORDER BY %s ASC, %s ASC;", Tables.Timetable.id, Tables.Timetable.day,
        Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName, Tables.Timetable.startTime, Tables.Timetable.endTime,
        Tables.Timetable.duration, Tables.Timetable.sameAsPreviousPeriod, Tables.Timetable.timeTableUpdatedBy, Tables.ClassProfessor.professorId,
        Tables.ClassCurriculum.subjectId, Tables.Employee.name, Tables.Subject.subjectName, Tables.Timetable.table, Tables.Employee.table,
        Tables.Subject.table, Tables.ClassCurriculum.table, Tables.ClassProfessor.table, Tables.Timetable.isActive, Tables.Timetable.instituteId,
        Tables.Timetable.classId, Tables.ClassCurriculum.isActive, Tables.ClassProfessor.isActive, Tables.Subject.isActive, Tables.Employee.isActive,
        Tables.Timetable.id, Tables.ClassCurriculum.timetableId, Tables.ClassCurriculum.subjectId, Tables.Subject.id, Tables.ClassCurriculum.classProfessorId,
        Tables.ClassProfessor.id, Tables.ClassProfessor.professorId, Tables.Employee.id, Tables.Timetable.periodNo, Tables.Timetable.daySeq);
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement selectPS = null;
    try {
      connection = db.getConnection();
      selectPS = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
      selectPS.setBoolean(1, true);
      selectPS.setLong(2, instituteId);
      selectPS.setLong(3, classId);
      selectPS.setBoolean(4, true);
      selectPS.setBoolean(5, true);
      selectPS.setBoolean(6, true);
      selectPS.setBoolean(7, true);
      resultSet = selectPS.executeQuery();
      while (resultSet.next()) {
        TimetableModel timetable = new TimetableModel();
        timetable.setTId(resultSet.getLong(Tables.Timetable.id));
        timetable.setDay(resultSet.getString(Tables.Timetable.day));
        timetable.setDaySeq(resultSet.getInt(Tables.Timetable.daySeq));
        timetable.setPeriodNo(resultSet.getInt(Tables.Timetable.periodNo));
        timetable.setPeriodName(resultSet.getString(Tables.Timetable.periodName));
        timetable.setStartTime(resultSet.getString(Tables.Timetable.startTime));
        timetable.setEndTime(resultSet.getString(Tables.Timetable.endTime));
        timetable.setDuration(resultSet.getInt(Tables.Timetable.duration));
        timetable.setSameAsPreviousPeriod(resultSet.getBoolean(Tables.Timetable.sameAsPreviousPeriod));
        timetable.setTimeTableUpdatedBy(resultSet.getString(Tables.Timetable.timeTableUpdatedBy));
        timetable.setProfId(resultSet.getLong(Tables.ClassProfessor.professorId));
        timetable.setProfName(resultSet.getString(Tables.Employee.name));
        timetable.setSubId(resultSet.getLong(Tables.ClassCurriculum.subjectId));
        timetable.setSubName(resultSet.getString(Tables.Subject.subjectName));
        timetable.setInsId(instituteId);
        timetable.setCId(classId);
        timetableModel.add(timetable);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (selectPS != null) {
        selectPS.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return timetableModel;
  }

  private boolean addTimeTableClass(TimeTableForm timetableDetails, Connection connection ) throws SQLException {

    String periodQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, " +
            "? AS dseq, ? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS dura, ? AS noOfDays, ? AS instid, ? AS classId, " +
            "? AS ttableupdatedby, ? AS same) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? " +
            ") LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day, Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName,
        Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration, Tables.Timetable.numberOfDays, Tables.Timetable.instituteId,
        Tables.Timetable.classId, Tables.Timetable.timeTableUpdatedBy, Tables.Timetable.sameAsPreviousPeriod, Tables.Timetable.periodNo,
        Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.periodNo, Tables.Timetable.day);

    String lunchQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, ? AS dseq, " +
            "? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS dura, ? AS noOfDays, ? AS instid, ? AS classId, ? AS ttableupdatedby) AS tmp " +
            "WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day,
        Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName, Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration,
        Tables.Timetable.numberOfDays, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.timeTableUpdatedBy, Tables.Timetable.periodNo,
        Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.periodNo, Tables.Timetable.day);
    PreparedStatement insertPeriodPs = null;
    PreparedStatement insertLunchPs = null;
    ResultSet insertRs = null;
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      insertPeriodPs = connection.prepareStatement(periodQuery, Statement.RETURN_GENERATED_KEYS);
      insertLunchPs = connection.prepareStatement(lunchQuery);
      boolean isInsertSuc = true;
//      for (TimeTableForm.Periods period : timetableDetails.getPeriods()) {
//        for (TimeTableForm.Periods.DayWise dayWise : period.getDayWiseSchd()) {
//          if (period.getPeriodNo() == -1 && period.getPeriodName().equalsIgnoreCase("lunch")) {
//            System.out.println("*******************************************");
//            insertLunchPs.setString(1, dayWise.getDay());
//            insertLunchPs.setInt(2, dayWise.getDayNumber());
//            insertLunchPs.setInt(3, period.getPeriodNo());
//            insertLunchPs.setString(4, period.getPeriodName());
//            insertLunchPs.setString(5, period.getPeriodStartTime());
//            insertLunchPs.setString(6, period.getPeriodEndTime());
//            insertLunchPs.setInt(7, period.getDuration());
//            insertLunchPs.setInt(8, timetableDetails.getNumberOfDays());
//            insertLunchPs.setLong(9, timetableDetails.getInstituteId());
//            insertLunchPs.setLong(10, timetableDetails.getClassId());
//            insertLunchPs.setString(11, timetableDetails.getTimeTableEditedBy());
//            insertLunchPs.setBoolean(12, true);
//            insertLunchPs.setLong(13, timetableDetails.getInstituteId());
//            insertLunchPs.setLong(14, timetableDetails.getClassId());
//            insertLunchPs.setInt(15, period.getPeriodNo());
//            insertLunchPs.setString(16, dayWise.getDay());
//            if(insertLunchPs.executeUpdate() <= 0) {
//              throw new Exception("Some error occur during lunch insert");
//            }
//          } else {
//            long profClassId = classProfessorDao.getId(connection, timetableDetails.getInstituteId(),
//                dayWise.getProfessorId(), timetableDetails.getClassId(), timetableDetails.getTimeTableEditedBy(), dayWise.getProfCat());
//            if(profClassId <= 0) {
//              throw new Exception("ProfId is null");
//            }
//            if(!classCurriculumDao.isProfessorFreeGivenTimeRange(connection, dayWise.getProfessorId(), period.getPeriodStartTime(),
//                period.getPeriodEndTime(), dayWise.getDay(), "", "", timetableDetails.getInstituteId())) {
//              throw new Exception("Prof is not free.. reverting all transaction " + dayWise.getProfessorId() + ", " + period.getPeriodStartTime() +
//              ", " + period.getPeriodEndTime() + ", " + dayWise.getDay() + ", " + period.getPeriodNo() + ", " + dayWise.getDay());
//            }
//            insertPeriodPs.setString(1, dayWise.getDay());
//            insertPeriodPs.setInt(2, dayWise.getDayNumber());
//            insertPeriodPs.setInt(3, period.getPeriodNo());
//            insertPeriodPs.setString(4, period.getPeriodName());
//            insertPeriodPs.setString(5, period.getPeriodStartTime());
//            insertPeriodPs.setString(6, period.getPeriodEndTime());
//            insertPeriodPs.setInt(7, period.getDuration());
//            insertPeriodPs.setInt(8, timetableDetails.getNumberOfDays());
//            insertPeriodPs.setLong(9, timetableDetails.getInstituteId());
//            insertPeriodPs.setLong(10, timetableDetails.getClassId());
//            insertPeriodPs.setString(11, timetableDetails.getTimeTableEditedBy());
//            insertPeriodPs.setBoolean(12, dayWise.isPreviousPeriod());
//            insertPeriodPs.setBoolean(13, true);
//            insertPeriodPs.setLong(14, timetableDetails.getInstituteId());
//            insertPeriodPs.setLong(15, timetableDetails.getClassId());
//            insertPeriodPs.setInt(16, period.getPeriodNo());
//            insertPeriodPs.setString(17, dayWise.getDay());
//            insertPeriodPs.executeUpdate();
//            insertRs = insertPeriodPs.getGeneratedKeys();
//            if(insertRs.next()) {
//              long timetableId = insertRs.getLong(1);
//              isInsertSuc = classCurriculumDao.createCurriculumForClass(connection, profClassId, dayWise.getSubjectId(), timetableId, dayWise.getProfCat(),
//                  dayWise.getDay(), period.getPeriodStartTime(), period.getPeriodEndTime(), "", "", timetableDetails.getTimeTableEditedBy());
//              if(!isInsertSuc) {
//                throw new Exception("Some error occur during class Curriculum insert");
//              }
//            } else {
//              throw new Exception("Some error occur during period insert");
//            }
//          }
//        }
//      }
      connection.commit();
    } catch (Exception exception) {
      connection.rollback();
      exception.printStackTrace();
      return false;
    } finally {
      if (insertPeriodPs != null) {
        insertPeriodPs.close();
      }
      if (insertLunchPs != null) {
        insertLunchPs.close();
      }
    }
    return true;
  }

  private boolean addTimeTableSection(TimeTableForm timetableDetails, Connection connection) throws SQLException {

    String periodQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, " +
            "? AS dseq, ? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS dura, ? AS noOfDays, ? AS instid, ? AS classId, ? AS secId, " +
            "? AS ttableupdatedby, ? AS same) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? AND %s=?;" +
            ") LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day, Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName,
        Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration, Tables.Timetable.numberOfDays, Tables.Timetable.instituteId,
        Tables.Timetable.classId, Tables.Timetable.sectionId, Tables.Timetable.timeTableUpdatedBy, Tables.Timetable.sameAsPreviousPeriod,
        Tables.Timetable.periodNo, Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId,
        Tables.Timetable.sectionId, Tables.Timetable.periodNo, Tables.Timetable.day);

//    String lunchQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, ? AS dseq, " +
//            "? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS dura, ? AS noOfDays, ? AS instid, ? AS classId, ? AS secId, ? AS ttableupdatedby) AS tmp " +
//            "WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day,
//        Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName, Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration,
//        Tables.Timetable.numberOfDays, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.timeTableUpdatedBy, Tables.Timetable.periodNo,
//        Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.periodNo, Tables.Timetable.day);
    PreparedStatement insertPeriodPs = null;
//    PreparedStatement insertLunchPs = null;
    ResultSet insertRs = null;
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      insertPeriodPs = connection.prepareStatement(periodQuery, Statement.RETURN_GENERATED_KEYS);
//      insertLunchPs = connection.prepareStatement(lunchQuery);
      boolean isInsertSuc = true;
//      for (TimeTableForm.Periods period : timetableDetails.getPeriods()) {
//        for (TimeTableForm.Periods.DayWise dayWise : period.getDayWiseSchd()) {
//          if (period.getPeriodNo() == -1 && period.getPeriodName().equalsIgnoreCase("lunch")) {
//            System.out.println("*******************************************");
////            insertLunchPs.setString(1, dayWise.getDay());
////            insertLunchPs.setInt(2, dayWise.getDayNumber());
////            insertLunchPs.setInt(3, period.getPeriodNo());
////            insertLunchPs.setString(4, period.getPeriodName());
////            insertLunchPs.setString(5, period.getPeriodStartTime());
////            insertLunchPs.setString(6, period.getPeriodEndTime());
////            insertLunchPs.setInt(7, timetableDetails.getDuration());
////            insertLunchPs.setInt(8, timetableDetails.getNumberOfDays());
////            insertLunchPs.setLong(9, timetableDetails.getInstituteId());
////            insertLunchPs.setLong(10, timetableDetails.getClassId());
////            insertLunchPs.setString(11, timetableDetails.getTimeTableEditedBy());
////            insertLunchPs.setBoolean(12, true);
////            insertLunchPs.setLong(13, timetableDetails.getInstituteId());
////            insertLunchPs.setLong(14, timetableDetails.getClassId());
////            insertLunchPs.setInt(15, period.getPeriodNo());
////            insertLunchPs.setString(16, dayWise.getDay());
////            if(insertLunchPs.executeUpdate() <= 0) {
////              throw new Exception("Some error occur during lunch insert");
////            }
//          } else {
//            long profClassId = classProfessorDao.getId(connection, timetableDetails.getInstituteId(),
//                dayWise.getProfessorId(), timetableDetails.getClassId(), timetableDetails.getTimeTableEditedBy(), dayWise.getProfCat());
//            if(profClassId <= 0) {
//              throw new Exception("ProfId is null");
//            }
//            if(!classCurriculumDao.isProfessorFreeGivenTimeRange(connection, dayWise.getProfessorId(), period.getPeriodStartTime(),
//                period.getPeriodEndTime(), dayWise.getDay(), "", "", timetableDetails.getInstituteId())) {
//              throw new Exception("Prof is not free.. reverting all transaction " + dayWise.getProfessorId() + ", " + period.getPeriodStartTime() +
//                  ", " + period.getPeriodEndTime() + ", " + dayWise.getDay() + ", " + period.getPeriodNo() + ", " + dayWise.getDay());
//            }
//            insertPeriodPs.setString(1, dayWise.getDay());
//            insertPeriodPs.setInt(2, dayWise.getDayNumber());
//            insertPeriodPs.setInt(3, period.getPeriodNo());
//            insertPeriodPs.setString(4, period.getPeriodName());
//            insertPeriodPs.setString(5, period.getPeriodStartTime());
//            insertPeriodPs.setString(6, period.getPeriodEndTime());
//            insertPeriodPs.setInt(7, timetableDetails.getDuration());
//            insertPeriodPs.setInt(8, timetableDetails.getNumberOfDays());
//            insertPeriodPs.setLong(9, timetableDetails.getInstituteId());
//            insertPeriodPs.setLong(10, timetableDetails.getClassId());
//            insertPeriodPs.setString(11, timetableDetails.getTimeTableEditedBy());
//            insertPeriodPs.setBoolean(12, dayWise.isPreviousPeriod());
//            insertPeriodPs.setBoolean(13, true);
//            insertPeriodPs.setLong(14, timetableDetails.getInstituteId());
//            insertPeriodPs.setLong(15, timetableDetails.getClassId());
//            insertPeriodPs.setInt(16, period.getPeriodNo());
//            insertPeriodPs.setString(17, dayWise.getDay());
//            insertPeriodPs.executeUpdate();
//            insertRs = insertPeriodPs.getGeneratedKeys();
//            if(insertRs.next()) {
//              long timetableId = insertRs.getLong(1);
//              isInsertSuc = classCurriculumDao.createCurriculumForClass(connection, profClassId, dayWise.getSubjectId(), timetableId, dayWise.getProfCat(),
//                  dayWise.getDay(), period.getPeriodStartTime(), period.getPeriodEndTime(), "", "", timetableDetails.getTimeTableEditedBy());
//              if(!isInsertSuc) {
//                throw new Exception("Some error occur during class Curriculum insert");
//              }
//            } else {
//              throw new Exception("Some error occur during period insert");
//            }
//          }
//        }
//      }
      connection.commit();
    } catch (Exception exception) {
      connection.rollback();
      exception.printStackTrace();
      return false;
    } finally {
      if (insertPeriodPs != null) {
        insertPeriodPs.close();
      }
//      if (insertLunchPs != null) {
//        insertLunchPs.close();
//      }
    }
    return true;
  }

  private boolean isBatchInserted(int[] results) {
    for (int result : results) {
      if (result <= 0) {
        return false;
      }
    }
    return true;
  }
}
