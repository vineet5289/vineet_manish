package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.Tables;

public class ClassProfessorDao {
  // this method insert record if record is not present and return primary key
  public long getId(Connection connection, long instituteid, long profId, long classId, String addedBy, String profCatg) throws SQLException {
    String selectClassProfessorQ = String.format("SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=? LIMIT 1;", Tables.ClassProfessor.id,
        Tables.ClassProfessor.table, Tables.ClassProfessor.isActive, Tables.ClassProfessor.instituteId, Tables.ClassProfessor.professorId,
        Tables.ClassProfessor.classId);

    String insertClassProfessorQ = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?);", Tables.ClassProfessor.table,
        Tables.ClassProfessor.instituteId, Tables.ClassProfessor.classId, Tables.ClassProfessor.professorId, Tables.ClassProfessor.professorCategory,
        Tables.ClassProfessor.addedBy);

    ResultSet selectRS = null;
    ResultSet insertRS = null;
    PreparedStatement selectPS = null;
    PreparedStatement insertPS = null;
    long id = -1;
    try {
      selectPS = connection.prepareStatement(selectClassProfessorQ, ResultSet.TYPE_FORWARD_ONLY);
      insertPS = connection.prepareStatement(insertClassProfessorQ, Statement.RETURN_GENERATED_KEYS);
      selectPS.setBoolean(1, true);
      selectPS.setLong(2, instituteid);
      selectPS.setLong(3, profId);
      selectPS.setLong(4, classId);
      selectRS = selectPS.executeQuery();
      if(selectRS.next()) {
       id = selectRS.getLong(Tables.ClassProfessor.id);
      } else {
        insertPS.setLong(1, instituteid);
        insertPS.setLong(2, classId);
        insertPS.setLong(3, profId);
        insertPS.setString(4, "permanent");//TODO: change and use profCatg
        insertPS.setString(5, addedBy);
        insertPS.executeUpdate();
        insertRS = insertPS.getGeneratedKeys();
        if (insertRS.next()) {
          id = insertRS.getLong(1);
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      id = -1;
    } finally {
      if (selectRS != null) {
        selectRS.close();
      }
      if (insertRS != null) {
        insertRS.close();
      }
      if (selectPS != null) {
        selectPS.close();
      }
      if (insertPS != null) {
        insertPS.close();
      }
    }
    return id;
  }
}
