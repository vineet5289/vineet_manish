package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;

import dao.Tables;
import play.db.Database;
import play.db.NamedDatabase;
import utils.StringUtils;
import views.forms.institute.timetable.TimeTableForm;

public class TimeTableDao {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public boolean add(TimeTableForm timetableDetails, String section) throws SQLException {
    if (StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {
      return addTimeTableClass(timetableDetails);
    } else {
      return addTimeTableClass(timetableDetails);
    }
  }

  public TimeTableForm view(TimeTableForm timetableDetails, String section) throws SQLException {
    TimeTableForm timeTableData = null;
    if (StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {

    } else {

    }
    return timeTableData;
  }

  public boolean edit(TimeTableForm timetableDetails, String section) throws SQLException {
    if (StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {
      return addTimeTableClass(timetableDetails);
    } else {
      return addTimeTableClass(timetableDetails);
    }
  }

//  private TimeTableForm viewBySection(long instituteId, long classId, long secId) throws SQLException {
//    String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? " +
//            "ORDER BY %s ASC, %s ASC;", Tables.Timetable.id, Tables.Timetable.day, Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName,
//        Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration, Tables.Timetable.numberOfDays, Tables.Timetable.instituteId,
//        Tables.Timetable.classId, Tables.Timetable.sectionId, Tables.Timetable.professorId, Tables.Timetable.subjectId, Tables.Timetable.sameAsPreviousPeriod,
//        Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.sectionId, Tables.Timetable.periodNo,
//        Tables.Timetable.daySeq);
//    Connection connection = null;
//    ResultSet resultSet = null;
//    PreparedStatement selectPS = null;
//    try {
//      connection = db.getConnection();
//      selectPS = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
//      selectPS.setBoolean(1, true);
//      selectPS.setLong(2, instituteId);
//      selectPS.setLong(3, classId);
//      selectPS.setLong(4, secId);
//      resultSet = selectPS.executeQuery();
//      Map<Integer, TimeTableForm.Periods> periodData = new TreeMap<Integer, TimeTableForm.Periods>();
//      while (resultSet.next()) {
//        int periodNo = resultSet.getInt(Tables.Timetable.periodNo);
//        TimeTableForm.Periods.DayWise dayWise = new TimeTableForm.Periods.DayWise();
//        dayWise.setDay(resultSet.getString(Tables.Timetable.day));
//        dayWise.setDayNumber(resultSet.getInt(Tables.Timetable.daySeq));
//        dayWise.setProfessorId(resultSet.getLong(Tables.Timetable.professorId));
//        dayWise.setProfessorName(resultSet.getString(Tables.Timetable.));
//        dayWise.setPreviousPeriod(resultSet.getBoolean(Tables.Timetable.sameAsPreviousPeriod));
//        dayWise.setSubjectId(resultSet.getLong(Tables.Timetable.subjectId));
//        dayWise.setSubjectName(resultSet.getString());
//
//        if (!periodData.containsKey(periodNo)) {
//          TimeTableForm.Periods period = new TimeTableForm.Periods();
//          period.setPeriodNo(resultSet.getInt(Tables.Timetable.periodNo));
//          period.setPeriodName(resultSet.getString(Tables.Timetable.periodName));
//          period.setPeriodStartTime(resultSet.getString(Tables.Timetable.startTime));
//          period.setPeriodEndTime(resultSet.getString(Tables.Timetable.endTime));
//          period.setDayWiseSchd(new ArrayList<TimeTableForm.Periods.DayWise>());
//          periodData.put(periodNo, period);
//        }
//        periodData.get(periodNo).getDayWiseSchd().add(dayWise);
//      }
//    } catch (Exception exception) {
//      exception.printStackTrace();
//    } finally {
//      if (resultSet != null) {
//        resultSet.close();
//      }
//      if (selectPS != null) {
//        selectPS.close();
//      }
//      if (connection != null) {
//        connection.close();
//      }
//    }
//
//  }

  private boolean addTimeTableClass(TimeTableForm timetableDetails) throws SQLException {
    String periodQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, " +
            "? AS dseq, ? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS dura, ? AS noOfDays, ? AS instid, ? AS classId, " +
            "? AS ttableupdatedby, ? AS same) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? " +
            ") LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day, Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName,
        Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration, Tables.Timetable.numberOfDays, Tables.Timetable.instituteId,
        Tables.Timetable.classId, Tables.Timetable.timeTableUpdatedBy,
        Tables.Timetable.sameAsPreviousPeriod, Tables.Timetable.periodNo, Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId,
        Tables.Timetable.classId, Tables.Timetable.periodNo, Tables.Timetable.day);

    String lunchQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, ? AS dseq, " +
            "? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS instid, ? AS dura, ? AS noOfDays, ? AS classId, ? AS ttableupdatedby) AS tmp " +
            "WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day,
        Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName, Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration,
        Tables.Timetable.numberOfDays, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.timeTableUpdatedBy,
        Tables.Timetable.periodNo, Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId,
        Tables.Timetable.periodNo, Tables.Timetable.day);
    Connection connection = null;
    PreparedStatement insertPeriodPs = null;
    PreparedStatement insertLunchPs = null;
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      insertPeriodPs = connection.prepareStatement(periodQuery);
      insertLunchPs = connection.prepareStatement(lunchQuery);
      for (TimeTableForm.Periods period : timetableDetails.getPeriods()) {
        for (TimeTableForm.Periods.DayWise dayWise : period.getDayWiseSchd()) {
          if (period.getPeriodNo() == -1 && period.getPeriodName().equalsIgnoreCase("lunch")) {
            insertLunchPs.setString(1, dayWise.getDay());
            insertLunchPs.setInt(2, dayWise.getDayNumber());
            insertLunchPs.setInt(3, period.getPeriodNo());
            insertLunchPs.setString(4, period.getPeriodName());
            insertLunchPs.setString(5, period.getPeriodStartTime());
            insertLunchPs.setString(6, period.getPeriodEndTime());
            insertLunchPs.setInt(7, timetableDetails.getDuration());
            insertLunchPs.setInt(8, timetableDetails.getNumberOfDays());
            insertLunchPs.setLong(9, timetableDetails.getInstituteId());
            insertLunchPs.setLong(10, timetableDetails.getClassId());
            insertLunchPs.setString(11, timetableDetails.getTimeTableEditedBy());
            insertLunchPs.setBoolean(12, true);
            insertLunchPs.setLong(13, timetableDetails.getInstituteId());
            insertLunchPs.setLong(14, timetableDetails.getClassId());
            insertLunchPs.setInt(15, period.getPeriodNo());
            insertLunchPs.setString(16, dayWise.getDay());
            insertLunchPs.addBatch();
          } else {
            insertPeriodPs.setString(1, dayWise.getDay());
            insertPeriodPs.setInt(2, dayWise.getDayNumber());
            insertPeriodPs.setInt(3, period.getPeriodNo());
            insertPeriodPs.setString(4, period.getPeriodName());
            insertPeriodPs.setString(5, period.getPeriodStartTime());
            insertPeriodPs.setString(6, period.getPeriodEndTime());
            insertPeriodPs.setInt(7, timetableDetails.getDuration());
            insertPeriodPs.setInt(8, timetableDetails.getNumberOfDays());
            insertPeriodPs.setLong(9, timetableDetails.getInstituteId());
            insertPeriodPs.setLong(10, timetableDetails.getClassId());
            insertPeriodPs.setString(11, timetableDetails.getTimeTableEditedBy());
            insertPeriodPs.setBoolean(12, dayWise.isPreviousPeriod());
            insertPeriodPs.setBoolean(13, true);
            insertPeriodPs.setLong(14, timetableDetails.getInstituteId());
            insertPeriodPs.setLong(15, timetableDetails.getClassId());
            insertPeriodPs.setInt(16, period.getPeriodNo());
            insertPeriodPs.setString(17, dayWise.getDay());
            insertPeriodPs.addBatch();
          }
        }
      }

      int[] insertPeriodRs = insertPeriodPs.executeBatch();
      int[] insertLunchRs = insertLunchPs.executeBatch();
      if (!isBatchInserted(insertPeriodRs) || !isBatchInserted(insertLunchRs)) {
        connection.rollback();
        return false;
      }
      connection.commit();
      return true;
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
      if (connection != null) {
        connection.close();
      }
    }
  }

//  private boolean addTimeTableSection(TimeTableForm timetableDetails) throws SQLException {
//    String periodQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, " +
//            "? AS dseq, ? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS dura, ? AS noOfDays, ? AS instid, ? AS classId, ? AS secId, ? AS profId, " +
//            "? AS subId, ? AS ttableupdatedby, ? AS same) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? " +
//            "AND %s=?) LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day, Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName,
//        Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration, Tables.Timetable.numberOfDays, Tables.Timetable.instituteId,
//        Tables.Timetable.classId, Tables.Timetable.sectionId, Tables.Timetable.professorId, Tables.Timetable.subjectId, Tables.Timetable.timeTableUpdatedBy,
//        Tables.Timetable.sameAsPreviousPeriod, Tables.Timetable.periodNo, Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId,
//        Tables.Timetable.classId, Tables.Timetable.sectionId, Tables.Timetable.periodNo, Tables.Timetable.day);
//
//    String lunchQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? AS day, ? AS dseq, " +
//            "? AS pNo, ? AS pname, ? AS stime, ? AS etime, ? AS instid, ? AS dura, ? AS noOfDays, ? AS classId, ? AS secId, ? AS ttableupdatedby) AS tmp " +
//            "WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1;", Tables.Timetable.table, Tables.Timetable.day,
//        Tables.Timetable.daySeq, Tables.Timetable.periodNo, Tables.Timetable.periodName, Tables.Timetable.startTime, Tables.Timetable.endTime, Tables.Timetable.duration,
//        Tables.Timetable.numberOfDays, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.sectionId, Tables.Timetable.timeTableUpdatedBy,
//        Tables.Timetable.periodNo, Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.sectionId,
//        Tables.Timetable.periodNo, Tables.Timetable.day);
//    Connection connection = null;
//    PreparedStatement insertPeriodPs = null;
//    PreparedStatement insertLunchPs = null;
//    try {
//      connection = db.getConnection();
//      connection.setAutoCommit(false);
//      insertPeriodPs = connection.prepareStatement(periodQuery);
//      insertLunchPs = connection.prepareStatement(lunchQuery);
//      for (TimeTableForm.Periods period : timetableDetails.getPeriods()) {
//        for (TimeTableForm.Periods.DayWise dayWise : period.getDayWiseSchd()) {
//          if (period.getPeriodNo() == -1 && period.getPeriodName().equalsIgnoreCase("lunch")) {
//            insertLunchPs.setString(1, dayWise.getDay());
//            insertLunchPs.setInt(2, dayWise.getDayNumber());
//            insertLunchPs.setInt(3, period.getPeriodNo());
//            insertLunchPs.setString(4, period.getPeriodName());
//            insertLunchPs.setString(5, period.getPeriodStartTime());
//            insertLunchPs.setString(6, period.getPeriodEndTime());
//            insertLunchPs.setInt(7, timetableDetails.getDuration());
//            insertLunchPs.setInt(8, timetableDetails.getNumberOfDays());
//            insertLunchPs.setLong(9, timetableDetails.getInstituteId());
//            insertLunchPs.setLong(10, timetableDetails.getClassId());
//            insertLunchPs.setLong(11, timetableDetails.getSectionId());
//            insertLunchPs.setString(12, timetableDetails.getTimeTableEditedBy());
//            insertLunchPs.setBoolean(13, true);
//            insertLunchPs.setLong(14, timetableDetails.getInstituteId());
//            insertLunchPs.setLong(15, timetableDetails.getClassId());
//            insertLunchPs.setLong(16, timetableDetails.getSectionId());
//            insertLunchPs.setInt(17, period.getPeriodNo());
//            insertLunchPs.setString(18, dayWise.getDay());
//            insertLunchPs.addBatch();
//          } else {
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
//            insertPeriodPs.setLong(11, timetableDetails.getSectionId());
//            insertPeriodPs.setLong(12, dayWise.getProfessorId());
//            insertPeriodPs.setLong(13, dayWise.getSubjectId());
//            insertPeriodPs.setString(14, timetableDetails.getTimeTableEditedBy());
//            insertPeriodPs.setBoolean(15, dayWise.isPreviousPeriod());
//            insertPeriodPs.setBoolean(16, true);
//            insertPeriodPs.setLong(17, timetableDetails.getInstituteId());
//            insertPeriodPs.setLong(18, timetableDetails.getClassId());
//            insertPeriodPs.setLong(19, timetableDetails.getSectionId());
//            insertPeriodPs.setInt(20, period.getPeriodNo());
//            insertPeriodPs.setString(21, dayWise.getDay());
//            insertPeriodPs.addBatch();
//          }
//        }
//      }
//
//      int[] insertPeriodRs = insertPeriodPs.executeBatch();
//      int[] insertLunchRs = insertLunchPs.executeBatch();
//      if (!isBatchInserted(insertPeriodRs) || !isBatchInserted(insertLunchRs)) {
//        connection.rollback();
//        return false;
//      }
//      connection.commit();
//      return true;
//    } catch (Exception exception) {
//      connection.rollback();
//      exception.printStackTrace();
//      return false;
//    } finally {
//      if (insertPeriodPs != null) {
//        insertPeriodPs.close();
//      }
//      if (insertLunchPs != null) {
//        insertLunchPs.close();
//      }
//      if (connection != null) {
//        connection.close();
//      }
//    }
//  }

  private boolean isBatchInserted(int[] results) {
    for (int result : results) {
      if (result <= 0) {
        return false;
      }
    }
    return true;
  }
}
