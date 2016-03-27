package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import play.db.DB;
import views.forms.school.ClassForm;
import views.forms.school.DisplayClassForm;

public class ClassDAO {
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
			connection = DB.getDataSource("srp").getConnection();
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

	public boolean addEditClass(List<ClassForm> classes, long schoolId, String userName) throws SQLException {
		boolean isSuccessfull = false;
		Connection connection = null;
		PreparedStatement insertStatement = null;
		String insertQuery = String.format("INSERT INTO class (%s, %s, %s, %s, %s, %s, %, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?);", 
				className, schoolIdField, classStartTime, classEndTime, noOfPeriod, parentClass, isActive, userNameField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);


		} catch(Exception exception) {
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

	public boolean addEditClassTime(List<ClassForm> classes, long schoolId, String userName) throws SQLException {
		boolean isSuccessfull = false;
		Connection connection = null;
		PreparedStatement insertStatement = null;
		String insertQuery = String.format("INSERT INTO class (%s, %s, %s, %s, %s, %s, %, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?);", 
				className, schoolIdField, classStartTime, classEndTime, noOfPeriod, parentClass, isActive, userNameField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);


		} catch(Exception exception) {
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

	public Map<String, List<DisplayClassForm>> getClass(long schoolId) throws SQLException {
		Map<String, List<DisplayClassForm>> sortedClasses = new HashMap<String, List<DisplayClassForm>>();
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		String selectQuery = String.format("Select %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %=?;", 
				className, schoolIdField, classStartTime, classEndTime, noOfPeriod, parentClass, userNameField, updatedAt,
				tableName, schoolIdField);
		try {
			Map<String, List<DisplayClassForm>> classes = new HashMap<String, List<DisplayClassForm>>();
			connection = DB.getDataSource("srp").getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setLong(1, schoolId);
			resultSet = selectStatement.executeQuery();
			while(resultSet.next()) {
				DisplayClassForm addClass = new DisplayClassForm();
				addClass.setClassName(resultSet.getString(className));
				addClass.setClassStartTime(resultSet.getString(classStartTime));
				addClass.setClassEndTime(resultSet.getString(classEndTime));
				String pClass = resultSet.getString(parentClass);
				addClass.setParentClassName(pClass);
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
}
