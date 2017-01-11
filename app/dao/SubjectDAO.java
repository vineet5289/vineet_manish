package dao;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dao.dao_operation_status.SubjectDaoActionStatus;
import models.SubjectModels;
import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.SubjectForm;

import static dao.Tables.Class.parentClassId;

public class SubjectDAO {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public List<SubjectModels> getSubject(long instituteId, long classId, long sectionId, String sec) throws SQLException {
    List<SubjectModels> subjects = new ArrayList<SubjectModels>();
    Connection connection = null;
    try {
      connection = db.getConnection();
      if(StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) {
        subjects = getSubjectClassSubject(instituteId, classId, connection);
      } else {

      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if(connection != null) {
        connection.close();
      }
    }
    return subjects;
  }

  public List<SubjectModels> getSubjectClassSubject(long instituteId, long classId, Connection connection) throws SQLException {
    String subQuery = String.format("SELECT %s, %s FROM %s WHERE %s=? AND %s=? AND %s=?;", Tables.Subject.id, Tables.Subject.subjectName,
        Tables.Subject.table, Tables.Subject.isActive, Tables.Subject.classId, Tables.Subject.instituteId);
    List<SubjectModels> subjects = new ArrayList<SubjectModels>();
    PreparedStatement selectPS = null;
    ResultSet selectRS = null;
    try {
      selectPS = connection.prepareStatement(subQuery, ResultSet.TYPE_FORWARD_ONLY);
      selectPS.setBoolean(1, true);
      selectPS.setLong(2, classId);
      selectPS.setLong(3, instituteId);
      selectRS = selectPS.executeQuery();
      while (selectRS.next()) {
        SubjectModels subject = new SubjectModels();
        subject.setSId(selectRS.getLong(Tables.Subject.id));
        subject.setSName(selectRS.getString(Tables.Subject.subjectName));
        subjects.add(subject);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if(selectRS != null) {
        selectRS.close();
      }
      if(selectPS != null) {
        selectPS.close();
      }
    }
    return subjects;
  }

  public SubjectDaoActionStatus add(SubjectForm subjectDetails, long classId, long sectionId,
                                    long instituteId, String userName, String sec) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus =
        SubjectDaoActionStatus.norecordfoundforgivenclass;

    if (StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) {
      subjectDaoActionStatus = addSubjectIntoClassAndSection(subjectDetails, classId, userName, instituteId);
    } else if (sec.equalsIgnoreCase("yes")) {
      subjectDaoActionStatus = addSubjectIntoSection(subjectDetails, classId, sectionId, userName, instituteId);
    }
    return subjectDaoActionStatus;
  }

  public SubjectDaoActionStatus delete(long classId, long sectionId, long instituteId, long subjectId, String userName, String sec) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.norecordfoundforgivenclass;
    if (StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) {
      subjectDaoActionStatus = deleteFromClass(classId, userName, instituteId, subjectId);
    } else if (sec.equalsIgnoreCase("yes")) {
      subjectDaoActionStatus = deleteFromSection(classId, sectionId, userName, instituteId, subjectId);
    }
    return subjectDaoActionStatus;
  }

  private SubjectDaoActionStatus deleteFromClass(long classId, String userName, long instituteId, long subjectId) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.norecordfoundforgivenclass;
    Connection connection = null;
    PreparedStatement insertSubjectPS = null;
    String deleteQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? AND %s=? AND (%s=? OR ((%s IS NULL OR %s=0) AND %s=?));", Tables.Subject.table,
        Tables.Subject.isActive, Tables.Subject.createdBy, Tables.Subject.isActive, Tables.Subject.instituteId,
        Tables.Subject.classId, Tables.Subject.subjectId, Tables.Subject.subjectId, Tables.Subject.subjectId, Tables.Subject.id);
    try {
      connection = db.getConnection();
      insertSubjectPS = connection.prepareStatement(deleteQ);
      insertSubjectPS.setBoolean(1, false);
      insertSubjectPS.setString(2, userName);
      insertSubjectPS.setBoolean(3, true);
      insertSubjectPS.setLong(4, instituteId);
      insertSubjectPS.setLong(5, classId);
      insertSubjectPS.setLong(6, subjectId);
      insertSubjectPS.setLong(7, subjectId);
      insertSubjectPS.executeUpdate();
      subjectDaoActionStatus = SubjectDaoActionStatus.successfullyDeletedFromClass;
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    } finally {
      if(insertSubjectPS != null) {
        insertSubjectPS.close();
      }
      if(connection != null) {
        connection.close();
      }
    }
    return subjectDaoActionStatus;
  }

  private SubjectDaoActionStatus deleteFromSection(long classId, long sectionId, String userName, long instituteId, long subjectId) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.norecordfoundforgivenclass;
    Connection connection = null;
    PreparedStatement insertSubjectPS = null;
    String deleteQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=? LIMIT 1;", Tables.Subject.table,
        Tables.Subject.isActive, Tables.Subject.createdBy, Tables.Subject.isActive, Tables.Subject.instituteId, Tables.Subject.classId,
        Tables.Subject.sectionId, Tables.Subject.subjectId);
    try {
      connection = db.getConnection();
      insertSubjectPS = connection.prepareStatement(deleteQ);
      insertSubjectPS.setBoolean(1, false);
      insertSubjectPS.setString(2, userName);
      insertSubjectPS.setBoolean(3, true);
      insertSubjectPS.setLong(4, instituteId);
      insertSubjectPS.setLong(5, classId);
      insertSubjectPS.setLong(6, sectionId);
      insertSubjectPS.setLong(7, subjectId);
      insertSubjectPS.executeUpdate();
      subjectDaoActionStatus = SubjectDaoActionStatus.successfullyDeletedFromClass;
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    } finally {
      if(insertSubjectPS != null) {
        insertSubjectPS.close();
      }
      if(connection != null) {
        connection.close();
      }
    }
    return subjectDaoActionStatus;
  }

  private SubjectDaoActionStatus addSubjectIntoSection(SubjectForm subjectDetails, long classId, long sectionId,
                                                       String userName, long instituteId) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.norecordfoundforgivenclass;
    if (classId <= 0 || classId != subjectDetails.getClassId() || sectionId <= 0 || sectionId != subjectDetails.getSectionId()) {
      return subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
    }

    ResultSet selectClassRS = null;
    ResultSet insertClassRS = null;
    Connection connection = null;
    PreparedStatement insertSubjectPS = null;
    PreparedStatement insertClassPS = null;
    PreparedStatement selectClassPS = null;

    String selectClassQ = String.format("SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?;", Tables.Subject.id,
        Tables.Subject.table, Tables.Subject.isActive, Tables.Subject.instituteId, Tables.Subject.classId, Tables.Subject.sectionId,
        Tables.Subject.subjectName);

    String insertClassQ =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? as subN, ? as cId, ? as sId, ? as iId, ? as subCode, " +
                    "? as createdBy, ? as des, ? as recBook) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1;",
                Tables.Subject.table,
                Tables.Subject.subjectName, Tables.Subject.classId, Tables.Subject.sectionId, Tables.Subject.instituteId, Tables.Subject.subjectCode,
                Tables.Subject.createdBy, Tables.Subject.description, Tables.Subject.recommendedBook, Tables.Subject.subjectName, Tables.Subject.table,
                Tables.Subject.isActive, Tables.Subject.instituteId, Tables.Subject.classId, Tables.Subject.sectionId, Tables.Subject.subjectName);

    String insertSectionQ =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? as subN, ? as cId, ? as sId, ? as iId, ? as subCode, " +
                    "? as createdBy, ? as des, ? as recBook, ? as subId) AS tmp WHERE ?=(SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=?) AND " +
                    "NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?) LIMIT 1;", Tables.Subject.table,
                Tables.Subject.subjectName, Tables.Subject.classId, Tables.Subject.sectionId, Tables.Subject.instituteId, Tables.Subject.subjectCode,
                Tables.Subject.createdBy, Tables.Subject.description, Tables.Subject.recommendedBook, Tables.Subject.subjectId, Tables.Class.id, Tables.Class.table,
                Tables.Class.isActive, Tables.Class.instituteId, Tables.Class.parentClassId, Tables.Class.id, Tables.Subject.subjectName,
                Tables.Subject.table, Tables.Subject.isActive, Tables.Subject.instituteId, Tables.Subject.classId, Tables.Subject.sectionId,
                Tables.Subject.subjectName);

    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      long subjectId = -1l;
      selectClassPS = connection.prepareStatement(selectClassQ, ResultSet.TYPE_FORWARD_ONLY);
      selectClassPS.setBoolean(1, true);
      selectClassPS.setLong(2, instituteId);
      selectClassPS.setLong(3, classId);
      selectClassPS.setLong(4, classId);
      selectClassPS.setString(5, subjectDetails.getSubjectName());
      selectClassRS = selectClassPS.executeQuery();
      if(selectClassRS.next()) {
        subjectId = selectClassRS.getLong(Tables.Subject.id);
      } else {
        insertClassPS = connection.prepareStatement(insertClassQ, Statement.RETURN_GENERATED_KEYS);
        insertClassPS.setString(1, subjectDetails.getSubjectName());
        insertClassPS.setLong(2, classId);
        insertClassPS.setLong(3, classId);
        insertClassPS.setLong(4, instituteId);
        insertClassPS.setString(5, subjectDetails.getSubjectCode());
        insertClassPS.setString(6, userName);
        insertClassPS.setString(7, subjectDetails.getDescription());
        insertClassPS.setString(8, subjectDetails.getRecommendedBook());
        insertClassPS.setBoolean(9, true);
        insertClassPS.setLong(10, instituteId);
        insertClassPS.setLong(11, classId);
        insertClassPS.setLong(12, classId);
        insertClassPS.setString(13, subjectDetails.getSubjectName());
        insertClassPS.executeUpdate();
        insertClassRS = insertClassPS.getGeneratedKeys();
        if(insertClassRS.next()) {
          subjectId = insertClassRS.getLong(1);
        }
      }

      insertSubjectPS = connection.prepareStatement(insertSectionQ);
      insertSubjectPS.setString(1, subjectDetails.getSubjectName());
      insertSubjectPS.setLong(2, classId);
      insertSubjectPS.setLong(3, sectionId);
      insertSubjectPS.setLong(4, instituteId);
      insertSubjectPS.setString(5, subjectDetails.getSubjectCode());
      insertSubjectPS.setString(6, userName);
      insertSubjectPS.setString(7, subjectDetails.getDescription());
      insertSubjectPS.setString(8, subjectDetails.getRecommendedBook());
      insertSubjectPS.setLong(9, subjectId);
      insertSubjectPS.setLong(10, sectionId);
      insertSubjectPS.setBoolean(11, true);
      insertSubjectPS.setLong(12, instituteId);
      insertSubjectPS.setLong(13, classId);
      insertSubjectPS.setLong(14, sectionId);
      insertSubjectPS.setBoolean(15, true);
      insertSubjectPS.setLong(16, instituteId);
      insertSubjectPS.setLong(17, classId);
      insertSubjectPS.setLong(18, sectionId);
      insertSubjectPS.setString(19, subjectDetails.getSubjectName());

      if(subjectId > 0 && insertSubjectPS.executeUpdate() == 1) {
        subjectDaoActionStatus = SubjectDaoActionStatus.successfullyAddedInSection;
        connection.commit();
      } else {
        subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
        connection.rollback();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
      connection.rollback();
    } finally {
      if(selectClassRS != null) {
        selectClassRS.close();
      }
      if(insertClassRS != null) {
        insertClassRS.close();
      }
      if(insertSubjectPS != null) {
        insertSubjectPS.close();
      }
      if(insertClassPS != null) {
        insertClassPS.close();
      }
      if(selectClassPS != null) {
        selectClassPS.close();
      }
      if(connection != null) {
        connection.close();
      }
    }
    return subjectDaoActionStatus;
  }

  private SubjectDaoActionStatus addSubjectIntoClassAndSection(SubjectForm subjectDetails,
                                                               long classId, String userName, long instituteId) throws SQLException {
    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.norecordfoundforgivenclass;
    if (classId <= 0 || classId != subjectDetails.getClassId()) {
      return subjectDaoActionStatus = SubjectDaoActionStatus.invalidRequest;
    }
    ResultSet selectClassRS = null;
    ResultSet insertClassRS = null;
    Connection connection = null;
    PreparedStatement insertSubjectClassPS = null;
    PreparedStatement insertSubjectSectionPS = null;
    PreparedStatement selectClassPS = null;

        String insertQWhenSectionPresent = String.format(
        "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? as subName, ? as cId, ? as secId, ? as insId, " +
            "? as subCode, ? as createdBy, ? as des, ? as recBook, ? as subId) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND " +
            "%s=? AND %s=? AND %s=? AND %s=?);", Tables.Subject.table, Tables.Subject.subjectName, Tables.Subject.classId,
        Tables.Subject.sectionId, Tables.Subject.instituteId, Tables.Subject.subjectCode, Tables.Subject.createdBy,
        Tables.Subject.description, Tables.Subject.recommendedBook, Tables.Subject.subjectId, Tables.Subject.subjectName, Tables.Subject.table,
        Tables.Subject.isActive, Tables.Subject.instituteId, Tables.Subject.classId, Tables.Subject.sectionId, Tables.Subject.subjectName);

    String selectClassQ = String.format("SELECT %s, %s FROM %s WHERE %s=? AND %s=? AND (%s=? OR %s=?);", Tables.Class.id, parentClassId,
        Tables.Class.table, Tables.Class.isActive, Tables.Class.instituteId, Tables.Class.id, parentClassId);

    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);

      selectClassPS = connection.prepareStatement(selectClassQ, ResultSet.TYPE_FORWARD_ONLY);
      selectClassPS.setBoolean(1, true);
      selectClassPS.setLong(2, instituteId);
      selectClassPS.setLong(3, classId);
      selectClassPS.setLong(4, classId);
      selectClassRS = selectClassPS.executeQuery();
      long pClassId = -1;
      List<Long> listOfIds = new ArrayList<Long>();
      while (selectClassRS.next()) {
        long idFromQ = selectClassRS.getLong(Tables.Class.id);
        listOfIds.add(idFromQ);
        pClassId = selectClassRS.getLong(parentClassId) > 0 ? selectClassRS.getLong(parentClassId) : classId;
      }
      boolean shouldCommitTrans = true;
      long generatedSubjectId = -1L;
      if (listOfIds != null && listOfIds.size() >= 1) {
        insertSubjectClassPS = connection.prepareStatement(insertQWhenSectionPresent, Statement.RETURN_GENERATED_KEYS);
        insertSubjectClassPS.setString(1, subjectDetails.getSubjectName());
        insertSubjectClassPS.setLong(2, pClassId);
        insertSubjectClassPS.setLong(3, pClassId);
        insertSubjectClassPS.setLong(4, instituteId);
        insertSubjectClassPS.setString(5, subjectDetails.getSubjectCode());
        insertSubjectClassPS.setString(6, userName);
        insertSubjectClassPS.setString(7, subjectDetails.getDescription());
        insertSubjectClassPS.setString(8, subjectDetails.getRecommendedBook());
        insertSubjectClassPS.setLong(9, 0); // for class there will be no subject_id
        insertSubjectClassPS.setBoolean(10, true);
        insertSubjectClassPS.setLong(11, instituteId);
        insertSubjectClassPS.setLong(12, pClassId);
        insertSubjectClassPS.setLong(13, pClassId);
        insertSubjectClassPS.setString(14, subjectDetails.getSubjectName());
        insertSubjectClassPS.executeUpdate();
        insertClassRS = insertSubjectClassPS.getGeneratedKeys();

        if (insertClassRS.next()) {
          generatedSubjectId = insertClassRS.getLong(1);
          insertSubjectSectionPS = connection.prepareStatement(insertQWhenSectionPresent);
          for (long id : listOfIds) {
            if (id == pClassId) {
              continue;
            }
            insertSubjectSectionPS.setString(1, subjectDetails.getSubjectName());
            insertSubjectSectionPS.setLong(2, pClassId);
            insertSubjectSectionPS.setLong(3, id);
            insertSubjectSectionPS.setLong(4, instituteId);
            insertSubjectSectionPS.setString(5, subjectDetails.getSubjectCode());
            insertSubjectSectionPS.setString(6, userName);
            insertSubjectSectionPS.setString(7, subjectDetails.getDescription());
            insertSubjectSectionPS.setString(8, subjectDetails.getRecommendedBook());
            insertSubjectSectionPS.setLong(9, generatedSubjectId); // subjectId is wqual to generated id when insert subject for class
            insertSubjectSectionPS.setBoolean(10, true);
            insertSubjectSectionPS.setLong(11, instituteId);
            insertSubjectSectionPS.setLong(12, pClassId);
            insertSubjectSectionPS.setLong(13, id);
            insertSubjectSectionPS.setString(14, subjectDetails.getSubjectName());
            insertSubjectSectionPS.addBatch();
          }
          int[] results = insertSubjectSectionPS.executeBatch();
          for (int result : results) {
            if (result < 1) {
              shouldCommitTrans = false;
              subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
            }
          }
        } else {
          shouldCommitTrans = false;
        }
      } else {
        subjectDaoActionStatus = SubjectDaoActionStatus.norecordfoundforgivenclass;
        shouldCommitTrans = false;
      }

      if (shouldCommitTrans) {
        connection.commit();
        subjectDaoActionStatus = SubjectDaoActionStatus.successfullyAddedInClass;
      } else {
        connection.rollback();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    } finally {
      if (selectClassRS != null) {
        selectClassRS.close();
      }
      if (insertClassRS != null) {
        insertClassRS.close();
      }
      if (selectClassPS != null) {
        selectClassPS.close();
      }
      if (insertSubjectClassPS != null) {
        insertSubjectClassPS.close();
      }
      if (insertSubjectSectionPS != null) {
        insertSubjectSectionPS.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return subjectDaoActionStatus;
  }
}
