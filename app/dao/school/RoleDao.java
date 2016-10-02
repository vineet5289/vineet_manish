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

import dao.Tables;
import enum_package.InstituteUserRole;
import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.RoleForm;

public class RoleDao {
	@Inject @NamedDatabase("srp") private Database db;

	public Map<Boolean, List<RoleForm>> getAllRole(Long instituteId) throws SQLException {
		Map<Boolean, List<RoleForm>> instituteRoles = new HashMap<Boolean, List<RoleForm>>();
		List<RoleForm> activeRoles = new ArrayList<RoleForm>();
		List<RoleForm> inactiveRoles = new ArrayList<RoleForm>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s = ? AND %s = ?;",
				Tables.Role.roleName, Tables.Role.roleDescription, Tables.Role.roleAddedBy, Tables.Role.createdAt,
				Tables.Role.permission, Tables.Role.isActive, Tables.Role.isEditable, Tables.Role.instituteId);
		
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, instituteId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RoleForm roleForm = new RoleForm();
				roleForm.setRoleName(resultSet.getString(Tables.Role.roleName));
				roleForm.setRoleDescription(resultSet.getString(Tables.Role.roleDescription));
				roleForm.setRoleCreatedBy(resultSet.getString(Tables.Role.roleAddedBy));
				String roleCreatedAt = resultSet.getDate(Tables.Role.createdAt) != null ? resultSet.getDate(Tables.Role.createdAt).toString() : "";
				roleForm.setRoleCreatedAt(roleCreatedAt);
				roleForm.setEditable(resultSet.getBoolean(Tables.Role.isEditable));
				roleForm.setPermission(resultSet.getString(Tables.Role.permission));

				if(resultSet.getBoolean(Tables.Role.isActive)) {
					activeRoles.add(roleForm);					
				} else {
					inactiveRoles.add(roleForm);
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

		instituteRoles.put(true, activeRoles);
		instituteRoles.put(false, inactiveRoles);
		return instituteRoles;
	}

	public boolean addNewRole(Long instituteId, Long headInstituteId, String userName) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = String.format("INSERT INTO %s SET %s=?, %s=?, %s=?;", Tables.Role.roleName,
				Tables.Role.roleDescription, Tables.Role.roleAddedBy, Tables.Role.createdAt, Tables.Role.instituteId,
				Tables.Role.isActive);
		List<RoleForm> roles = new ArrayList<RoleForm>();
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, instituteId);
			preparedStatement.setBoolean(2, true);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RoleForm roleForm = new RoleForm();
				roleForm.setRoleName(resultSet.getString(Tables.Role.roleName));
				roleForm.setRoleDescription(resultSet.getString(Tables.Role.roleDescription));
				roleForm.setRoleCreatedBy(resultSet.getString(Tables.Role.roleAddedBy));
				String roleCreatedAt = resultSet.getDate(Tables.Role.createdAt) != null ? resultSet.getDate(Tables.Role.createdAt).toString() : "";
				roleForm.setRoleCreatedAt(roleCreatedAt);
				roles.add(roleForm);
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
		return true;
	}

	public boolean removeRole(Long instituteId, Long headInstituteId, String userName) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = String.format("INSERT INTO %s SET %s=?, %s=?, %s=?;", Tables.Role.roleName,
				Tables.Role.roleDescription, Tables.Role.roleAddedBy, Tables.Role.createdAt, Tables.Role.instituteId,
				Tables.Role.isActive);
		List<RoleForm> roles = new ArrayList<RoleForm>();
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, instituteId);
			preparedStatement.setBoolean(2, true);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RoleForm roleForm = new RoleForm();
				roleForm.setRoleName(resultSet.getString(Tables.Role.roleName));
				roleForm.setRoleDescription(resultSet.getString(Tables.Role.roleDescription));
				roleForm.setRoleCreatedBy(resultSet.getString(Tables.Role.roleAddedBy));
				String roleCreatedAt = resultSet.getDate(Tables.Role.createdAt) != null ? resultSet.getDate(Tables.Role.createdAt).toString() : "";
				roleForm.setRoleCreatedAt(roleCreatedAt);
				roles.add(roleForm);
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
		return true;
	}
}
