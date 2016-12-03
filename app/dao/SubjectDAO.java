package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.SubjectForm;

public class SubjectDAO {
	@Inject @NamedDatabase("srp") private Database db;

	public boolean addSubjects(Long classId, long schoolId, SubjectForm subjects, String userName) throws Exception {
		boolean isSuccessfull = false;

//		Connection connection = null;
//		PreparedStatement insertStatement = null;
//		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?);", 
//				tableName, subjectNameField, classIdField, schoolIdField, subjectCodeField, userNameField);
//		try {
//			connection = db.getConnection();
//			connection.setAutoCommit(false);
//			insertStatement = connection.prepareStatement(insertQuery);
//			for(SubjectForm.SubjectInfo s : subjects) {
//				insertStatement.setString(1, s.getSubjectName());
//				insertStatement.setLong(2, classId);
//				insertStatement.setLong(3, schoolId);
//				insertStatement.setString(4, s.getSubjectCode());
//				insertStatement.setString(5, userName);
//				insertStatement.addBatch();
//			}
//
//			connection.commit();
//			isSuccessfull = true;
//		} catch(Exception exception) {
//			isSuccessfull = false;
//			System.out.println("Exception: server problem occur");
//			exception.printStackTrace();
//			if(connection != null)
//				connection.rollback();
//			throw new Exception(exception);
//			
//		} finally {
//			if(insertStatement != null)
//				insertStatement.close();
//			if(connection != null)
//				connection.close();
//		}
		return isSuccessfull;
	}

}
