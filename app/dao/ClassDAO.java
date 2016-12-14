package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.ClassForm;

public class ClassDAO {
  @Inject
  @NamedDatabase("srp")
  private Database db;

  public boolean add(ClassForm classe, long instituteId, String userName, String section, long classId)
      throws SQLException {
    boolean isSuccessfull = false;
    if(StringUtils.isBlank(section) || section.equalsIgnoreCase("no")) {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&  ere");
      isSuccessfull = addClass(instituteId, classe, userName);
    } else if(section.equalsIgnoreCase("yes")) {
      System.out.println("%%%%%%%% else part");
      isSuccessfull = addSection(instituteId, classe, userName, classId);
    }
    System.out.println("claases success "+isSuccessfull);
    return isSuccessfull;
  }

  private boolean addClass(long instituteId, ClassForm classDetails, String userName)
      throws SQLException {
    boolean isSuccessful = false;
    Connection connection = null;
    PreparedStatement insertClassPS = null;
    PreparedStatement insertSectionPS  = null;
    ResultSet insertClassRs = null;
    String insertClassQ =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ? as className, ? as instituteId, ? as classStartTime, ? as classEndTime,"
                    + "? as noOfPeriod, ? as pclassName, ? as userName) AS tmp WHERE NOT EXISTS (SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=? ) limit 1;",
                Tables.Class.table, Tables.Class.className, Tables.Class.instituteId,Tables.Class.classStartTime,
                Tables.Class.classEndTime, Tables.Class.noOfPeriod, Tables.Class.parentClassName, Tables.Class.userName,
                Tables.Class.className, Tables.Class.table, Tables.Class.instituteId, Tables.Class.isActive, Tables.Class.className);

    String insertSectionQ =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                Tables.Class.table, Tables.Class.className, Tables.Class.instituteId,
                Tables.Class.classStartTime, Tables.Class.classEndTime, Tables.Class.noOfPeriod,
                Tables.Class.parentClassId, Tables.Class.parentClassName, Tables.Class.userName);

    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      insertClassPS = connection.prepareStatement(insertClassQ, Statement.RETURN_GENERATED_KEYS);
      insertClassPS.setString(1, classDetails.getClassName());
      insertClassPS.setLong(2, instituteId);
      insertClassPS.setString(3, classDetails.getClassStartTime());
      insertClassPS.setString(4, classDetails.getClassEndTime());
      insertClassPS.setInt(5, classDetails.getNumberOfPeriod());
      insertClassPS.setString(6, classDetails.getClassName());
      insertClassPS.setString(7, userName);
      insertClassPS.setLong(8, instituteId);
      insertClassPS.setBoolean(9, true);
      insertClassPS.setString(10, classDetails.getClassName());

      insertClassPS.executeUpdate();
      insertClassRs = insertClassPS.getGeneratedKeys();
      Long generatedClassId = -1L;
      if(insertClassRs.next()) {
        generatedClassId = insertClassRs.getLong(1);
      }

      List<String> sectionNames = classDetails.getSectionNames();
System.out.println("*************************inside class ection "+sectionNames + ", " + classDetails.numberOfsection + ", generatedClassId" + generatedClassId);
      if(generatedClassId > 0 && sectionNames != null && sectionNames.size() > 0 &&
          classDetails.numberOfsection > 0) {
           System.out.println(".....inside class ection "+sectionNames);
         insertSectionPS = connection.prepareStatement(insertSectionQ);
        for(String sectionName : sectionNames) {
          System.out.println(".....inside class ection "+sectionName);
          
          insertSectionPS.setString(1, sectionName);
          insertSectionPS.setLong(2, instituteId);
          insertSectionPS.setString(3, classDetails.getClassStartTime());
          insertSectionPS.setString(4, classDetails.getClassEndTime());
          insertSectionPS.setInt(5, classDetails.getNumberOfPeriod());
          insertSectionPS.setString(6, classDetails.getClassName());
          insertSectionPS.setLong(7, generatedClassId);
          insertSectionPS.setString(8, userName);
          insertSectionPS.addBatch();
        }

        int[] results = insertSectionPS.executeBatch();
        for(int result : results) {
          System.out.println(" results print ***"+result);
          if(result < 0)
            throw new Exception("Section insert error.");
          }
      }

      if(generatedClassId > 0) {
        connection.commit();
        isSuccessful = true;
      } else {
        throw new Exception("Section insert error.");
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      isSuccessful = false;
      connection.rollback();
    } finally {
      if (insertClassRs != null)
        insertClassPS.close();

      if (insertClassPS != null)
        insertClassPS.close();

      if (insertSectionPS != null)
        insertClassPS.close();

      if (connection != null)
        connection.close();
    }
    return isSuccessful;
  }

  private boolean addSection(long instituteId, ClassForm sectionDetails, String userName,
      long classId) throws SQLException {
   if(classId != sectionDetails.getClassId() || !sectionDetails.isSection()) {
     return false;
   }
    boolean isSuccessful = false;
    Connection connection = null;
    PreparedStatement insertPS = null;
    String insertQuery =
        String
            .format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) SELECT * FROM (SELECT ?, ?, ?, ?,"
                    + "?, ?, ?, ?) AS tmp WHERE %s=(SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=?) AND NOT EXITS "
                    + "(SELECT %s FROM %s WHERE %s=? AND %s=? AND %s=?);",
                Tables.Class.table, Tables.Class.className, Tables.Class.instituteId,
                Tables.Class.classStartTime, Tables.Class.classEndTime, Tables.Class.noOfPeriod,
                Tables.Class.parentClassId, Tables.Class.parentClassName, Tables.Class.userName,
                Tables.Class.id, Tables.Class.id, Tables.Class.table, Tables.Class.id,
                Tables.Class.instituteId, Tables.Class.isActive, Tables.Class.className,
                Tables.Class.table, Tables.Class.parentClassId, Tables.Class.instituteId,
                Tables.Class.isActive);
    try {
      connection = db.getConnection();
      insertPS = connection.prepareStatement(insertQuery);
      insertPS.setString(1, sectionDetails.getClassName());
      insertPS.setLong(2, instituteId);
      insertPS.setString(3, sectionDetails.getClassStartTime());
      insertPS.setString(4, sectionDetails.getClassEndTime());
      insertPS.setInt(5, sectionDetails.getNumberOfPeriod());
      insertPS.setLong(6, sectionDetails.getParentClassId());
      insertPS.setString(7, sectionDetails.getParentClassName());
      insertPS.setString(8, userName);
      insertPS.setLong(9, sectionDetails.getParentClassId());
      insertPS.setLong(10, instituteId);
      insertPS.setBoolean(11, true);
      insertPS.setLong(12, sectionDetails.getParentClassId());
      insertPS.setLong(10, instituteId);
      insertPS.setBoolean(11, true);
      if (insertPS.executeUpdate() == 1) {
        isSuccessful = true;
      } else {
        isSuccessful = false;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      isSuccessful = false;
    } finally {
      if (insertPS != null)
        insertPS.close();

      if (connection != null)
        connection.close();
    }
    return isSuccessful;
  }

  public Map<String, List<ClassForm>> getClasses(long instituteId) throws SQLException {
    Map<String, List<ClassForm>> sortedClasses = new HashMap<String, List<ClassForm>>();
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet resultSet = null;
    String selectQuery =
        String.format("Select %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;",
            Tables.Class.id, Tables.Class.className, Tables.Class.classStartTime,
            Tables.Class.classEndTime, Tables.Class.noOfPeriod, Tables.Class.parentClassId,
            Tables.Class.parentClassName, Tables.Class.table, Tables.Class.instituteId,
            Tables.Class.isActive);
    try {
      connection = db.getConnection();
      selectStatement = connection.prepareStatement(selectQuery);
      selectStatement.setLong(1, instituteId);
      selectStatement.setBoolean(2, true);
      resultSet = selectStatement.executeQuery();
      while (resultSet.next()) {
        ClassForm addClass = new ClassForm();
        addClass.setInstituteId(instituteId);
        addClass.setClassId(resultSet.getLong(Tables.Class.id));
        addClass.setClassName(resultSet.getString(Tables.Class.className));
        addClass.setClassStartTime(resultSet.getString(Tables.Class.classStartTime));
        addClass.setClassEndTime(resultSet.getString(Tables.Class.classEndTime));
        addClass.setNumberOfPeriod(resultSet.getInt(Tables.Class.noOfPeriod));
        if (resultSet.getLong(Tables.Class.parentClassId) != 0
            && resultSet.getLong(Tables.Class.parentClassId) != resultSet.getLong(Tables.Class.id)) {
          addClass.setParentClassName(resultSet.getString(Tables.Class.parentClassName));
          addClass.setParentClassId(resultSet.getLong(Tables.Class.parentClassId));
          addClass.setSection(true);
        }

        List<ClassForm> classList =
            sortedClasses.get(resultSet.getString(Tables.Class.parentClassName));
        if (classList == null || classList.size() == 0) {
          classList = new ArrayList<ClassForm>();
        }
        classList.add(addClass);
        sortedClasses.put(resultSet.getString(Tables.Class.parentClassName), classList);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (resultSet != null)
        resultSet.close();

      if (selectStatement != null)
        selectStatement.close();

      if (connection != null)
        connection.close();
    }
    return sortedClasses;
  }

  /*
   * assume: 1. ParentClassId is null for class and not null for section 2. if sec is null or empty
   * or no that means it's represent Class 3. if sec if not null and not empty and value is yes that
   * means request is for section TODO: we need to consider sectionId and put validation on this
   */

  public boolean editClass(long instituteId, long classId, String userName, ClassForm editClass,
      String section, String action) throws SQLException {
    
   if(StringUtils.isBlank(action) || action.equalsIgnoreCase("edit")) {
     edit(instituteId, classId, userName, editClass, section);
   } else if(action.equalsIgnoreCase("delete")) {
     delete(instituteId, classId, userName, editClass, section);
   } else {
     
   }
    return true;
  }

  private boolean edit(long instituteId, long classId, String username, ClassForm classDetails, String sec) throws SQLException {
    boolean isEdited = false;
    if ((StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) && classId > 0) {
      isEdited = editClass(instituteId, classId, username, classDetails);
    } else if (sec.equalsIgnoreCase("yes")) {
      isEdited = editSection(instituteId, classId, username, classDetails);
    }
    return isEdited;
  }
  
  private boolean editClass(long instituteId, long classId, String username, ClassForm classDetails) throws SQLException {
    if(classId != classDetails.getClassId() || classDetails.isSection()) {
      return false;
    }
    boolean isEdited = false;
    Connection connection = null;
    PreparedStatement updateClassPS = null;
    PreparedStatement updateSectionPS = null;
    PreparedStatement updateParentNamePS = null;
    String updateClassQ = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=? AND %s=? LIMIT 1",
        Tables.Class.table, Tables.Class.className, Tables.Class.classStartTime, Tables.Class.classEndTime, Tables.Class.noOfPeriod,
        Tables.Class.userName, Tables.Class.instituteId, Tables.Class.id, Tables.Class.isActive);
    String updateSectionQ = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s IN NOT NULL AND %s=? AND %s=? LIMIT 1",
        Tables.Class.table, Tables.Class.classStartTime, Tables.Class.classEndTime, Tables.Class.noOfPeriod, Tables.Class.parentClassName,
        Tables.Class.userName, Tables.Class.instituteId, Tables.Class.parentClassId, Tables.Class.parentClassId, Tables.Class.isActive);
    String updateParentNameQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s IN NOT NULL AND %s=? AND %s=?",
        Tables.Class.table, Tables.Class.parentClassName, Tables.Class.userName, Tables.Class.instituteId, Tables.Class.parentClassId,
        Tables.Class.parentClassId, Tables.Class.isActive);
    try {
      connection = db.getConnection();
      connection.setAutoCommit(false);
      updateClassPS = connection.prepareStatement(updateClassQ);
      updateClassPS.setString(1, classDetails.getClassName());
      updateClassPS.setString(2, classDetails.getClassStartTime());
      updateClassPS.setString(3, classDetails.getClassEndTime());
      updateClassPS.setInt(4, classDetails.getNumberOfPeriod());
      updateClassPS.setString(5, username);
      updateClassPS.setLong(6, instituteId);
      updateClassPS.setLong(7, classDetails.getClassId());
      updateClassPS.setBoolean(8, true);
      if(classDetails.isUpdateSection()) {
        updateSectionPS = connection.prepareStatement(updateSectionQ);
        updateSectionPS.setString(1, classDetails.getClassStartTime());
        updateSectionPS.setString(2, classDetails.getClassEndTime());
        updateSectionPS.setInt(3, classDetails.getNumberOfPeriod());
        updateSectionPS.setString(4, classDetails.getClassName());
        updateSectionPS.setString(5, username);
        updateSectionPS.setLong(6, instituteId);
        updateSectionPS.setLong(7, classDetails.getClassId());
        updateSectionPS.setBoolean(8, true);
        if(updateClassPS.executeUpdate() == 1) {
          updateSectionPS.executeUpdate();
          isEdited = true;
        }
      } else {
        updateParentNamePS = connection.prepareStatement(updateParentNameQ);
        updateParentNamePS.setString(1, classDetails.getClassName());
        updateParentNamePS.setString(2, username);
        updateParentNamePS.setLong(3, instituteId);
        updateParentNamePS.setLong(4, classDetails.getClassId());
        updateParentNamePS.setBoolean(4, true);
        if(updateClassPS.executeUpdate() == 1) {
          updateParentNamePS.executeUpdate();
          isEdited = true;
        }
      }
      if(isEdited) {
        connection.commit();
      } else {
        connection.rollback();
      }
    } catch(Exception exception) {
      connection.rollback();
      isEdited = false;
      exception.printStackTrace();
    } finally {
      if(updateClassPS != null)
        updateClassPS.close();
      if(updateParentNamePS != null)
        updateParentNamePS.close();
      if(updateSectionPS != null)
        updateSectionPS.close();
      if(connection != null)
        connection.close();
    }
    return isEdited;
  }

  private boolean editSection(long instituteId, long parentClassId, String username, ClassForm classDetails) throws SQLException {
    if(classDetails.getParentClassId() != parentClassId || classDetails.getClassId() <= 0 || !classDetails.isSection()) {
      return false;
    }
    boolean isEdited = false;
    Connection connection = null;
    PreparedStatement insertPS = null;
    String insertQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s IN NOT NULL AND %s=? AND %s=? LIMIT 1",
        Tables.Class.table, Tables.Class.className, Tables.Class.classStartTime, Tables.Class.classEndTime, Tables.Class.noOfPeriod,
        Tables.Class.userName, Tables.Class.instituteId, Tables.Class.parentClassId, Tables.Class.parentClassId, Tables.Class.id);
    try {
      connection = db.getConnection();
      insertPS = connection.prepareStatement(insertQuery);
      insertPS.setString(1, classDetails.getClassName());
      insertPS.setString(2, classDetails.getClassStartTime());
      insertPS.setString(3, classDetails.getClassEndTime());
      insertPS.setInt(4, classDetails.getNumberOfPeriod());
      insertPS.setString(5, username);
      insertPS.setLong(6, instituteId);
      insertPS.setLong(7, parentClassId);
      insertPS.setLong(8, classDetails.getClassId());
      if(insertPS.executeUpdate() == 1) {
        isEdited = true;
      }
    } catch(Exception exception) {
      isEdited = false;
      exception.printStackTrace();
    } finally {
      if(insertPS != null)
        insertPS.close();
      if(connection != null)
        connection.close();
    }

    return isEdited;
  }

  private boolean delete(long instituteId, long classId, String username, ClassForm classDetails, String sec)
      throws SQLException {
    boolean isDeleted = false;
    if ((StringUtils.isBlank(sec) || sec.equalsIgnoreCase("no")) && classId > 0) {
      isDeleted = deleteClass(instituteId, classId, username, classDetails);
    } else if (sec.equalsIgnoreCase("yes")) {
      isDeleted = deleteSection(instituteId, classId, username, classDetails);
    }
    return isDeleted;
  }

  /*
   * assume: ParentClassId is null for class and not null for section
   */
  private boolean deleteSection(long instituteId, long parentClassId, String username, ClassForm classDetails)
      throws SQLException {
    boolean isDeleted = false;
    Connection connection = null;
    PreparedStatement updateStatement = null;
    String updateQuery =
        String.format(
            "UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s IS NOT NULL AND %s=? AND %s=? LIMIT 1;",
            Tables.Class.table, Tables.Class.isActive, Tables.Class.instituteId,
            Tables.Class.parentClassId, Tables.Class.parentClassId, Tables.Class.id);
    try {
      long sectionId = classDetails.getClassId();
      if(sectionId <= 0 || parentClassId != classDetails.getParentClassId()) {
        return false;
      }
      connection = db.getConnection();
      updateStatement = connection.prepareStatement(updateQuery);
      updateStatement.setBoolean(1, false);
      updateStatement.setString(2, username);
      updateStatement.setLong(3, instituteId);
      updateStatement.setLong(4, parentClassId);
      updateStatement.setLong(5, sectionId);
      if (updateStatement.executeUpdate() == 1) {
        isDeleted = true;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (updateStatement != null)
        updateStatement.close();
      if (connection != null)
        connection.close();
    }
    return isDeleted;
  }

  /*
   * assume: ParentClassId is null for class and not null for section
   */
  private boolean deleteClass(long instituteId, long classId, String username, ClassForm classDetails) throws SQLException {
    if(classId != classDetails.getClassId() || classDetails.isSection()) {
      return false;
    }

    boolean isDeleted = false;
    Connection connection = null;
    PreparedStatement updateStatement = null;
    String updateQ =
        String
            .format(
                "UPDATE %s SET %s=?, %s=? WHERE %s=? AND ((%s IS NULL AND %s=?) OR (%s IS NOT NULL AND %s=?)) ;",
                Tables.Class.table, Tables.Class.isActive, Tables.Class.instituteId,
                Tables.Class.parentClassId, Tables.Class.id, Tables.Class.parentClassId,
                Tables.Class.parentClassId);
    try {
      connection = db.getConnection();
      updateStatement = connection.prepareStatement(updateQ);
      updateStatement.setBoolean(1, false);
      updateStatement.setString(2, username);
      updateStatement.setLong(3, instituteId);
      updateStatement.setLong(4, classId);
      updateStatement.setLong(5, classId);
      if (updateStatement.executeUpdate() >= 1) {
        isDeleted = true;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (updateStatement != null)
        updateStatement.close();
      if (connection != null)
        connection.close();
    }
    return isDeleted;
  }

}
