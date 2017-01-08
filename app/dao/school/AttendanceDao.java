package dao.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import dao.Tables;
import play.db.Database;
import play.db.NamedDatabase;
import utils.StringUtils;
import views.forms.institute.AttendanceForm;

public class AttendanceDao {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public boolean add(AttendanceForm attendanceForm, String sec, boolean today) {
    try {
      if(StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) {
        return addClassAttendance(attendanceForm);
      } else if(sec.equalsIgnoreCase("yes")) {
        return addSectionAttendance(attendanceForm);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return false;
  }

  private boolean addSectionAttendance(AttendanceForm attendanceForm) throws SQLException {
    String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? as aDate, " +
            "? as insId, %s as cId, %s as secId, %s as pId, %s as suId, %s as stAtt, %s as atb, %s as aub, %s as nofps, %s as nofas, %s as tnos) " +
            "AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? AND %s=?) AND EXISTS (SELECT %s FROM %s " +
            "WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1", Tables.Attendance.table, Tables.Attendance.date, Tables.Attendance.instituteId,
        Tables.Attendance.classId, Tables.Attendance.sectionId, Tables.Attendance.periodId, Tables.Attendance.subjectId, Tables.Attendance.studentsAttendance,
        Tables.Attendance.attendanceTakenBy, Tables.Attendance.attendanceUpdatedBy, Tables.Attendance.noOfPresentStudents, Tables.Attendance.noOfAbsentStudents,
        Tables.Attendance.totalNoOfStudents, Tables.Attendance.periodId, Tables.Attendance.table, Tables.Attendance.isActive, Tables.Attendance.date,
        Tables.Attendance.instituteId, Tables.Attendance.classId, Tables.Attendance.sectionId, Tables.Attendance.periodId, Tables.Timetable.id,
        Tables.Timetable.table, Tables.Timetable.isActive, Tables.Timetable.id, Tables.Timetable.instituteId, Tables.Timetable.classId, Tables.Timetable.sectionId);

    Connection connection = null;
    PreparedStatement attendanceInsertPS = null;
    long totalNumberOfStudents = 0;
    long totalNumberOfAbsentStudents = 0;
    long totalNumberOfPresentStudents = 0;
    try {
      connection = db.getConnection();
      attendanceInsertPS = connection.prepareStatement(query);
      String attendance = convertToJsonObject(attendanceForm.getStudentsAttendance());
      totalNumberOfStudents = attendanceForm.getStudentsAttendance().size();
      totalNumberOfPresentStudents = numberOfPresentStudents(attendanceForm.getStudentsAttendance());
      totalNumberOfAbsentStudents = totalNumberOfStudents - totalNumberOfPresentStudents;
      attendanceInsertPS.setString(1, attendanceForm.getDate());
      attendanceInsertPS.setLong(2, attendanceForm.getInstituteId());
      attendanceInsertPS.setLong(3, attendanceForm.getClassId());
      attendanceInsertPS.setLong(4, attendanceForm.getSectionId());
      attendanceInsertPS.setLong(5, attendanceForm.getPeriodId());
      attendanceInsertPS.setLong(6, attendanceForm.getSubjectId());
      attendanceInsertPS.setString(7, attendance);
      attendanceInsertPS.setString(8, attendanceForm.getAttendanceTakenBy());
      attendanceInsertPS.setString(9, StringUtils.isBlank(attendanceForm.getAttendanceUpdatedBy()) ? attendanceForm.getAttendanceTakenBy():
          attendanceForm.getAttendanceUpdatedBy());
      attendanceInsertPS.setLong(10, totalNumberOfPresentStudents);
      attendanceInsertPS.setLong(11, totalNumberOfAbsentStudents);
      attendanceInsertPS.setLong(12, totalNumberOfStudents);
      attendanceInsertPS.setBoolean(13, true);
      attendanceInsertPS.setString(14, attendanceForm.getDate());
      attendanceInsertPS.setLong(15, attendanceForm.getInstituteId());
      attendanceInsertPS.setLong(16, attendanceForm.getClassId());
      attendanceInsertPS.setLong(17, attendanceForm.getSectionId());
      attendanceInsertPS.setLong(18, attendanceForm.getPeriodId());
      attendanceInsertPS.setBoolean(19, true);
      attendanceInsertPS.setLong(20, attendanceForm.getPeriodId());
      attendanceInsertPS.setLong(21, attendanceForm.getInstituteId());
      attendanceInsertPS.setLong(22, attendanceForm.getClassId());
      attendanceInsertPS.setLong(23, attendanceForm.getSectionId());
      if(attendanceInsertPS.executeUpdate() == 1) {
        return true;
      } else {
        return false;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } finally {
      if(attendanceInsertPS != null) {
        attendanceInsertPS.close();
      }
      if(connection != null) {
        connection.close();
      }
    }
  }

  private boolean addClassAttendance(AttendanceForm attendanceForm) throws SQLException {
    String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? as aDate, " +
            "? as insId, %s as cId, %s as pId, %s as suId, %s as stAtt, %s as atb, %s as aub, %s as nofps, %s as nofas, %s as tnos) " +
            "AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) AND EXISTS (SELECT %s FROM %s " +
            "WHERE %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1", Tables.Attendance.table, Tables.Attendance.date, Tables.Attendance.instituteId,
        Tables.Attendance.classId, Tables.Attendance.periodId, Tables.Attendance.subjectId, Tables.Attendance.studentsAttendance,
        Tables.Attendance.attendanceTakenBy, Tables.Attendance.attendanceUpdatedBy, Tables.Attendance.noOfPresentStudents, Tables.Attendance.noOfAbsentStudents,
        Tables.Attendance.totalNoOfStudents, Tables.Attendance.periodId, Tables.Attendance.table, Tables.Attendance.isActive, Tables.Attendance.date,
        Tables.Attendance.instituteId, Tables.Attendance.classId, Tables.Attendance.periodId, Tables.Timetable.id, Tables.Timetable.table,
        Tables.Timetable.isActive, Tables.Timetable.id, Tables.Timetable.instituteId, Tables.Timetable.classId);

    Connection connection = null;
    PreparedStatement attendanceInsertPS = null;
    long totalNumberOfStudents = 0;
    long totalNumberOfAbsentStudents = 0;
    long totalNumberOfPresentStudents = 0;
    try {
      connection = db.getConnection();
      attendanceInsertPS = connection.prepareStatement(query);
      String attendance = convertToJsonObject(attendanceForm.getStudentsAttendance());
      totalNumberOfStudents = attendanceForm.getStudentsAttendance().size();
      totalNumberOfPresentStudents = numberOfPresentStudents(attendanceForm.getStudentsAttendance());
      totalNumberOfAbsentStudents = totalNumberOfStudents - totalNumberOfPresentStudents;
      attendanceInsertPS.setString(1, attendanceForm.getDate());
      attendanceInsertPS.setLong(2, attendanceForm.getInstituteId());
      attendanceInsertPS.setLong(3, attendanceForm.getClassId());
      attendanceInsertPS.setLong(4, attendanceForm.getPeriodId());
      attendanceInsertPS.setLong(5, attendanceForm.getSubjectId());
      attendanceInsertPS.setString(6, attendance);
      attendanceInsertPS.setString(7, attendanceForm.getAttendanceTakenBy());
      attendanceInsertPS.setString(8, StringUtils.isBlank(attendanceForm.getAttendanceUpdatedBy()) ? attendanceForm.getAttendanceTakenBy():
          attendanceForm.getAttendanceUpdatedBy());
      attendanceInsertPS.setLong(9, totalNumberOfPresentStudents);
      attendanceInsertPS.setLong(10, totalNumberOfAbsentStudents);
      attendanceInsertPS.setLong(11, totalNumberOfStudents);
      attendanceInsertPS.setBoolean(12, true);
      attendanceInsertPS.setString(13, attendanceForm.getDate());
      attendanceInsertPS.setLong(14, attendanceForm.getInstituteId());
      attendanceInsertPS.setLong(15, attendanceForm.getClassId());
      attendanceInsertPS.setLong(16, attendanceForm.getPeriodId());
      attendanceInsertPS.setBoolean(17, true);
      attendanceInsertPS.setLong(18, attendanceForm.getPeriodId());
      attendanceInsertPS.setLong(19, attendanceForm.getInstituteId());
      attendanceInsertPS.setLong(20, attendanceForm.getClassId());
      if(attendanceInsertPS.executeUpdate() == 1) {
        return true;
      } else {
        return false;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } finally {
      if(attendanceInsertPS != null) {
        attendanceInsertPS.close();
      }
      if(connection != null) {
        connection.close();
      }
    }
  }

  private String convertToJsonObject(List<AttendanceForm.StudentsAttendance> value) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(value);
  }

  private long numberOfPresentStudents(List<AttendanceForm.StudentsAttendance> value) {
    return value.stream().filter(attendance -> isPresent(attendance.getPresent())).count();
  }

  private boolean isPresent(String value) {
    return StringUtils.isNotBlank(value) && value.equalsIgnoreCase("P");
  }
}
