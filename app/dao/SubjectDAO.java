package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import play.db.DB;
import views.forms.institute.ClassForm;
import views.forms.institute.SubjectForm;

public class SubjectDAO {

	private String tableName = "subject";
	private String idField = "id";
	private String subjectNameField = "subject_name";
	private String classIdField = "class_id";
	private String schoolIdField = "school_id";
	private String subjectCodeField = "subject_code";
	private String createdAtField = "created_at";
	private String updatedAtField = "updated_at";;
	private String isActiveField = "isActive";
	private String userNameField = "user_name";

	public boolean addSubjects(Long classId, long schoolId, List<SubjectForm.SubjectInfo> subjects, String userName) throws Exception {
		boolean isSuccessfull = false;

		Connection connection = null;
		PreparedStatement insertStatement = null;
		String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?);", 
				tableName, subjectNameField, classIdField, schoolIdField, subjectCodeField, userNameField);
		try {
			connection = DB.getDataSource("srp").getConnection();
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertQuery);
			for(SubjectForm.SubjectInfo s : subjects) {
				insertStatement.setString(1, s.getSubjectName());
				insertStatement.setLong(2, classId);
				insertStatement.setLong(3, schoolId);
				insertStatement.setString(4, s.getSubjectCode());
				insertStatement.setString(5, userName);
				insertStatement.addBatch();
			}

			int[] results = insertStatement.executeBatch();
			for(int result : results) {
				if(result < 0)
					throw new Exception("Batch update mysql server error");
			}
			connection.commit();
			isSuccessfull = true;
		} catch(Exception exception) {
			isSuccessfull = false;
			System.out.println("Exception: server problem occur");
			exception.printStackTrace();
			if(connection != null)
				connection.rollback();
			throw new Exception(exception);
			
		} finally {
			if(insertStatement != null)
				insertStatement.close();
			if(connection != null)
				connection.close();
		}
		return isSuccessfull;
	}

}
