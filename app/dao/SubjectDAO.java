package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import dao.dao_operation_status.SubjectDaoActionStatus;
import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.SubjectForm;

public class SubjectDAO {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public SubjectDaoActionStatus add(SubjectForm subjectDetails, long classId,
      String userName, long institute, String sec) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus =
        SubjectDaoActionStatus.norecordfoundforgivenclass;

    if (StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) {
      subjectDaoActionStatus =
          addSubjectIntoClassAndSection(subjectDetails, classId, userName, institute);
    } else if (sec.equalsIgnoreCase("yes")) {
      subjectDaoActionStatus = addSubjectIntoSection(subjectDetails, classId, userName, institute);
    }
    return subjectDaoActionStatus;
  }

  private SubjectDaoActionStatus addSubjectIntoSection(SubjectForm subjectDetails, long classId,
      String userName, long instituteId) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus =
        SubjectDaoActionStatus.norecordfoundforgivenclass;
    long sectionId = subjectDetails.getSectionId();
    if (classId <= 0 || classId != subjectDetails.getClassId() || sectionId <= 0
        || classId < sectionId) {
      return subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
    }

    Connection connection = null;
    PreparedStatement insertSubjectPS = null;
    String insertQuery =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM "
                    + "(SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?) AS tmp WHERE %s=(SELECT %s FROM %s "
                    + "WHERE %s=? AND %s=? AND %s=? AND %s=?) AND (%s IS NULL OR NOT EXISTS (SELECT %s FROM %s "
                    + "WHERE %s=? AND %s=? AND %s=? AND %s=?)) LIMIT 1;", Tables.Subject.table,
                Tables.Subject.subjectName, Tables.Subject.classId, Tables.Subject.sectionId,
                Tables.Subject.instituteId, Tables.Subject.subjectCode, Tables.Subject.isActive,
                Tables.Subject.createdBy, Tables.Subject.description,
                Tables.Subject.recommendedBook, Tables.Subject.sectionId, Tables.Class.id,
                Tables.Class.table, Tables.Class.isActive, Tables.Class.instituteId,
                Tables.Class.parentClassId, Tables.Class.id, Tables.Subject.subjectCode,
                Tables.Subject.subjectCode, Tables.Subject.table, Tables.Subject.isActive,
                Tables.Subject.instituteId, Tables.Subject.classId, Tables.Subject.sectionId);
    try {
      connection = db.getConnection();
      insertSubjectPS = connection.prepareStatement(insertQuery);
      insertSubjectPS.setString(1, subjectDetails.getSubjectName());
      insertSubjectPS.setLong(2, classId);
      insertSubjectPS.setLong(3, sectionId);
      insertSubjectPS.setLong(4, instituteId);
      insertSubjectPS.setString(5, subjectDetails.getSubjectCode());
      insertSubjectPS.setBoolean(6, true);
      insertSubjectPS.setString(7, userName);
      insertSubjectPS.setString(8, subjectDetails.getDescription());
      insertSubjectPS.setString(9, subjectDetails.getRecommendedBook());
      insertSubjectPS.setBoolean(10, true);
      insertSubjectPS.setLong(11, instituteId);
      insertSubjectPS.setLong(12, classId);
      insertSubjectPS.setLong(13, sectionId);
      insertSubjectPS.setBoolean(14, true);
      insertSubjectPS.setLong(15, instituteId);
      insertSubjectPS.setLong(16, classId);
      insertSubjectPS.setLong(17, sectionId);
      if (insertSubjectPS.executeUpdate() == 1) {
        subjectDaoActionStatus = SubjectDaoActionStatus.successfullyAddedInSection;
      } else {
        subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    } finally {
      if (insertSubjectPS != null) {
        insertSubjectPS.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return subjectDaoActionStatus;
  }

  private SubjectDaoActionStatus addSubjectIntoClassAndSection(SubjectForm subjectDetails,
      long classId, String userName, long instituteId) {
    SubjectDaoActionStatus subjectDaoActionStatus =
        SubjectDaoActionStatus.norecordfoundforgivenclass;
    if (classId <= 0 || classId != subjectDetails.getClassId()) {
      return subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
    }

    Connection connection = null;
    PreparedStatement insertSubjectPS = null;
    String insertQuery =String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM "
                    + "(SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?) AS tmp WHERE %s=(SELECT %s FROM %s "
                    + "WHERE %s=? AND %s=? AND %s=? AND %s=?) AND (%s IS NULL OR NOT EXISTS (SELECT %s FROM %s "
                    + "WHERE %s=? AND %s=? AND %s=? AND %s=?)) LIMIT 1;", Tables.Subject.table,
                Tables.Subject.subjectName, Tables.Subject.classId, Tables.Subject.sectionId,
                Tables.Subject.instituteId, Tables.Subject.subjectCode, Tables.Subject.isActive,
                Tables.Subject.createdBy, Tables.Subject.description,
                Tables.Subject.recommendedBook, Tables.Subject.sectionId, Tables.Class.id,
                Tables.Class.table, Tables.Class.isActive, Tables.Class.instituteId,
                Tables.Class.parentClassId, Tables.Class.id, Tables.Subject.subjectCode,
                Tables.Subject.subjectCode, Tables.Subject.table, Tables.Subject.isActive,
                Tables.Subject.instituteId, Tables.Subject.classId, Tables.Subject.sectionId);
    try {
      connection = db.getConnection();
      insertSubjectPS = connection.prepareStatement(insertQuery);
      insertSubjectPS.setString(1, subjectDetails.getSubjectName());
      insertSubjectPS.setLong(2, classId);
      insertSubjectPS.setLong(3, sectionId);
      insertSubjectPS.setLong(4, instituteId);
      insertSubjectPS.setString(5, subjectDetails.getSubjectCode());
      insertSubjectPS.setBoolean(6, true);
      insertSubjectPS.setString(7, userName);
      insertSubjectPS.setString(8, subjectDetails.getDescription());
      insertSubjectPS.setString(9, subjectDetails.getRecommendedBook());
      insertSubjectPS.setBoolean(10, true);
      insertSubjectPS.setLong(11, instituteId);
      insertSubjectPS.setLong(12, classId);
      insertSubjectPS.setLong(13, sectionId);
      insertSubjectPS.setBoolean(14, true);
      insertSubjectPS.setLong(15, instituteId);
      insertSubjectPS.setLong(16, classId);
      insertSubjectPS.setLong(17, sectionId);
      if (insertSubjectPS.executeUpdate() == 1) {
        subjectDaoActionStatus = SubjectDaoActionStatus.successfullyAddedInSection;
      } else {
        subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    } finally {
      if (insertSubjectPS != null) {
        insertSubjectPS.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return subjectDaoActionStatus;
  }
}
