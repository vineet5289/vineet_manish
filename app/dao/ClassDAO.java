package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.ClassForm;
import views.forms.institute.DisplayClassForm;

public class ClassDAO {
	@Inject @NamedDatabase("srp") private Database db;
	private String tableName = "class";
	private String className = "class_name";
	private String schoolIdField = "school_id";
	private String classStartTime = "class_start_time";
	private String classEndTime = "class_end_time";
	private String noOfPeriod = "no_of_period";
	private String parentClass = "parent_class";
	private String isActive = "is_active";
	private String userNameField = "user_name";
	private String updatedAt = "updated_at";

	public boolean addClass(List<ClassForm.AddClass> classes, long schoolId, String userName) throws SQLException {
		boolean isSuccessfull = false;

		Connection connection = null;
		PreparedStatement insertStatement = null;
		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?);", 
				tableName, className, schoolIdField, classStartTime, classEndTime, noOfPeriod, parentClass, userNameField);
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertQuery);
			for(ClassForm.AddClass c : classes) {
				List<String> sections = c.getSectionNames();
				if(sections == null || sections.size() == 0) {
					insertStatement.setString(1, c.getClassName());
					insertStatement.setLong(2, schoolId);
					insertStatement.setString(3, c.getClassStartTime());
					insertStatement.setString(4, c.getClassEndTime());
					insertStatement.setInt(5, c.getNoOfPeriod());
					insertStatement.setString(6, c.getClassName());
					insertStatement.setString(7, userName);
					insertStatement.addBatch();
				} else {
					for(String section : sections) {
						insertStatement.setString(1, section);
						insertStatement.setLong(2, schoolId);
						insertStatement.setString(3, c.getClassStartTime());
						insertStatement.setString(4, c.getClassEndTime());
						insertStatement.setInt(5, c.getNoOfPeriod());
						insertStatement.setString(6, c.getClassName());
						insertStatement.setString(7, userName);
						insertStatement.addBatch();
					}
				}
			}

			int[] results = insertStatement.executeBatch();
			for(int result : results) {
				if(result < 0)
					throw new Exception("Server error");
			}
			connection.commit();
		} catch(Exception exception) {
			System.out.println("Exception: server problem occur");
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
		} finally {
			if(insertStatement != null)
				insertStatement.close();
			if(connection != null)
				connection.close();
		}
		return true;
	}

	public boolean editClass(long schoolId, String userName, DisplayClassForm editClass) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		int result = 0;
		String updateQuery = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?;", 
				tableName, classStartTime, classEndTime, noOfPeriod, userNameField, schoolId, className);
		try {
			connection = db.getConnection();
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.setString(1, editClass.getClassStartTime());
			updateStatement.setString(2, editClass.getClassEndTime());
			updateStatement.setInt(3, editClass.getNoOfPeriod());
			updateStatement.setString(4, userName);
			updateStatement.setLong(5, schoolId);
			updateStatement.setString(6, className);
			result = updateStatement.executeUpdate();
		} catch(Exception exception) {
			result = 0;
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			throw new SQLException(exception);
		} finally {
			if(updateStatement != null)
				updateStatement.close();
			if(connection != null)
				connection.close();
		}
		return (result == 1);
	}

	public Map<String, List<DisplayClassForm>> getClass(long schoolId) throws SQLException {
		Map<String, List<DisplayClassForm>> sortedClasses = new HashMap<String, List<DisplayClassForm>>();
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("Select %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?;", 
				className, schoolIdField, classStartTime, classEndTime, noOfPeriod, parentClass, userNameField, updatedAt,
				tableName, schoolIdField, isActive);
		try {
			Map<String, List<DisplayClassForm>> classes = new HashMap<String, List<DisplayClassForm>>();
			connection = db.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setLong(1, schoolId);
			selectStatement.setBoolean(2, true);
			resultSet = selectStatement.executeQuery();
			while(resultSet.next()) {
				DisplayClassForm addClass = new DisplayClassForm();
				addClass.setClassName(resultSet.getString(className));
				addClass.setClassStartTime(resultSet.getString(classStartTime));
				addClass.setClassEndTime(resultSet.getString(classEndTime));
				String pClass = resultSet.getString(parentClass);
				addClass.setParentClassName(pClass);
				addClass.setNoOfPeriod(resultSet.getInt(noOfPeriod));
				List<DisplayClassForm> classList = classes.get(pClass);
				if(classList == null || classList.size() == 0)
					classList = new ArrayList<DisplayClassForm>();
				classList.add(addClass);
				classes.put(pClass, classList);
			}

			for(Map.Entry<String, List<DisplayClassForm>> entry : classes.entrySet()) {
				String pClass = entry.getKey();
				List<DisplayClassForm> cClass = entry.getValue();
				cClass.sort((c1, c2) -> c1.className.compareTo(c2.className));
				sortedClasses.put(pClass, cClass);
			}
		} catch(Exception exception) {
			sortedClasses = null;
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			throw new SQLException(exception);
		} finally {
			if(resultSet != null)
				resultSet.close();

			if(selectStatement != null)
				selectStatement.close();

			if(connection != null)
				connection.close();
		}
		return sortedClasses;
	}

	public boolean deleteClass(long schoolId, String classNameValue) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		int result = 0;
		String updateQuery = String.format("UPDATE %s SET %s=? WHERE %s=? AND %s=?;", tableName, isActive, schoolId, className);
		try {
			connection = db.getConnection();
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.setBoolean(1, false);
			updateStatement.setLong(2, schoolId);
			updateStatement.setString(3, classNameValue);
			result = updateStatement.executeUpdate();
		} catch(Exception exception) {
			result = 0;
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			throw new SQLException(exception);
		} finally {
			if(updateStatement != null)
				updateStatement.close();

			if(connection != null)
				connection.close();
		}
		return (result == 1);
	}

	public boolean addSection(long schoolId, String parentClassValue, DisplayClassForm section, String userNameValue) throws SQLException {
		boolean isSuccessful = false;
		Connection connection = null;
		PreparedStatement insertStatement = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?);", 
				tableName, className, schoolIdField, classStartTime, classEndTime, noOfPeriod, parentClass, userNameField);
		String selectQuery = String.format("Select %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s=? AND %s=? AND %s=? AND %s=?;", 
				className, classStartTime, classEndTime, noOfPeriod, parentClass, userNameField, isActive, tableName,
				schoolIdField, parentClass, className, isActive);
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setLong(1, schoolId);
			selectStatement.setString(2, parentClassValue);
			selectStatement.setString(3, parentClassValue);
			selectStatement.setBoolean(4, true);

			resultSet = selectStatement.executeQuery();
			if(resultSet != null && resultSet.next()) {
				resultSet.updateString(className, section.getClassName());
				resultSet.updateString(classStartTime, section.getClassStartTime());
				resultSet.updateString(classEndTime, section.getClassEndTime());
				resultSet.updateString(userNameField, userNameValue);
				resultSet.updateInt(noOfPeriod, section.getNoOfPeriod());
				resultSet.updateRow();
			} else {
				insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setString(1, section.getClassName());
				insertStatement.setLong(2, schoolId);
				insertStatement.setString(3, section.getClassStartTime());
				insertStatement.setString(4, section.getClassEndTime());
				insertStatement.setInt(5, section.getNoOfPeriod());
				insertStatement.setString(6, section.getParentClassName());
				insertStatement.setString(7, userNameValue);
				insertStatement.execute();
			}

			connection.commit();
			isSuccessful = true;
		} catch(Exception exception) {
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			throw new SQLException(exception);
		} finally {
			if(resultSet != null)
				resultSet.close();

			if(insertStatement != null)
				insertStatement.close();

			if(selectStatement != null)
				selectStatement.close();

			if(connection != null)
				connection.close();
		}
		return isSuccessful;
	}
}
