package dao.school;

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
import views.forms.institute.GroupForm;
import dao.Tables;

public class GroupDao {
	@Inject @NamedDatabase("srp") private Database db;
	public Map<Boolean, List<GroupForm>> getAllGroup(Long instituteId) throws SQLException {
		Map<Boolean, List<GroupForm>> instituteGroup = new HashMap<Boolean, List<GroupForm>>();
		List<GroupForm> activeGroup = new ArrayList<GroupForm>();
		List<GroupForm> inactiveGroup = new ArrayList<GroupForm>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s = ?;", Tables.Group.id, Tables.Group.groupName,
				Tables.Group.groupDescription, Tables.Group.groupAddedBy, Tables.Group.createdAt, Tables.Group.table,
				Tables.Group.instituteId);
		
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, instituteId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				GroupForm groupForm = new GroupForm();
				groupForm.setGroupName(resultSet.getString(Tables.Group.groupName));
				groupForm.setGroupDescription(resultSet.getString(Tables.Group.groupDescription));
				groupForm.setId(resultSet.getLong(Tables.Group.id));
				groupForm.setGroupCreatedBy(resultSet.getString(Tables.Group.groupAddedBy));
				String groupCreatedAt = resultSet.getDate(Tables.Group.createdAt).toString();
				groupForm.setGroupCreatedAt(groupCreatedAt);
				if(resultSet.getBoolean(Tables.Group.isActive)) {
					activeGroup.add(groupForm);					
				} else {
					inactiveGroup.add(groupForm);
				}
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			if(resultSet != null) {
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}

		instituteGroup.put(true, activeGroup);
		instituteGroup.put(false, inactiveGroup);
		return instituteGroup;
	}

	public boolean addGroup(Long instituteId, String groupName, String groupDescription, String userName) throws SQLException {
		boolean isGroupAdded = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", Tables.Group.table,
				Tables.Group.groupName, Tables.Group.groupDescription, Tables.Group.groupAddedBy, Tables.Group.instituteId);;
		
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, groupName);
			preparedStatement.setString(2, groupDescription);
			preparedStatement.setString(3, userName);
			preparedStatement.setLong(4, instituteId);
			isGroupAdded = preparedStatement.execute();
		} catch(Exception exception) {
			exception.printStackTrace();
			isGroupAdded = false;
		} finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return isGroupAdded;
	}

	public boolean addDisabled(Long instituteId, Long groupId, String userName) throws SQLException {
		boolean isGroupDisabled = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? LIMIT 1;", Tables.Group.table,
				Tables.Group.isActive, Tables.Group.groupAddedBy, Tables.Group.instituteId, Tables.Group.id);
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setString(2, userName);
			preparedStatement.setLong(3, instituteId);
			preparedStatement.setLong(4, groupId);
			int noOfRowUpdated = preparedStatement.executeUpdate();
			if(noOfRowUpdated == 1) {
				isGroupDisabled = true;
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			isGroupDisabled = false;
		} finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return isGroupDisabled;
	}
}
