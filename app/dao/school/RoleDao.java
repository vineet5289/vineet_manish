package dao.school;

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

import play.db.Database;
import play.db.NamedDatabase;
import views.forms.institute.RoleForm;
import dao.Tables;

public class RoleDao {
	@Inject @NamedDatabase("srp") private Database db;

	public Map<Boolean, List<RoleForm>> getAllRole(Long instituteId) throws SQLException {
		Map<Boolean, List<RoleForm>> instituteRoles = new HashMap<Boolean, List<RoleForm>>();
		List<RoleForm> activeRoles = new ArrayList<RoleForm>();
		List<RoleForm> inactiveRoles = new ArrayList<RoleForm>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s = ?;", Tables.Role.id,
				Tables.Role.roleName, Tables.Role.roleDescription, Tables.Role.roleAddedBy, Tables.Role.createdAt,
				Tables.Role.permission, Tables.Role.isActive, Tables.Role.isEditable, Tables.Role.table,Tables.Role.instituteId);
		
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setLong(1, instituteId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RoleForm roleForm = new RoleForm();
				roleForm.setId(resultSet.getLong(Tables.Role.id));
				roleForm.setRoleName(resultSet.getString(Tables.Role.roleName));
				roleForm.setRoleDescription(resultSet.getString(Tables.Role.roleDescription));
				roleForm.setRoleCreatedBy(resultSet.getString(Tables.Role.roleAddedBy));
				String roleCreatedAt = resultSet.getDate(Tables.Role.createdAt).toString();
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

	public Long addNewRole(Long instituteId, String userName, String roleName, String roleDescription) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Long roleId = 0l;
		String query = String.format("INSERT INTO %s SET %s=?, %s=?, %s=?, %s=?;", Tables.Role.table, Tables.Role.roleName,
				Tables.Role.roleDescription, Tables.Role.roleAddedBy, Tables.Role.instituteId);
		try {
			connection = db.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, roleName);
			preparedStatement.setString(2, roleDescription);
			preparedStatement.setString(3, userName);
			preparedStatement.setLong(4, instituteId);
			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				roleId = resultSet.getLong(1);
			} else {
				System.out.println("==> role addition problem");
			}
			connection.commit();
		} catch(Exception exception) {
			connection.rollback();
			exception.printStackTrace();
		} finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return roleId;
	}

	public boolean disableEnableRole(Long roleId, Long instituteId, String userName, boolean isDisabled) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? limit 1;", Tables.Role.table,
				Tables.Role.isActive, Tables.Role.roleAddedBy, Tables.Role.id, Tables.Role.instituteId);
		boolean isDisabledSuccessfully = false;
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(updateQ);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setString(2, userName);
			preparedStatement.setLong(3, roleId);
			preparedStatement.setLong(4, instituteId);
			int numberOfRoleDesabled = preparedStatement.executeUpdate();
			while (numberOfRoleDesabled == 1) {
				isDisabledSuccessfully = true;
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return isDisabledSuccessfully;
	}

	public boolean assignPermission(Long instituteId, Long roleId, List<Long> permissionIds, String userName) throws SQLException {
		String permissions = getPermissionString(permissionIds);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateQ = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=? AND %s=? limit 1;", Tables.Role.table,
				Tables.Role.permission, Tables.Role.roleAddedBy, Tables.Role.id, Tables.Role.instituteId, Tables.Role.isActive);
		boolean isUpdated = false;
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(updateQ);
			preparedStatement.setString(1, permissions);
			preparedStatement.setString(2, userName);
			preparedStatement.setLong(3, roleId);
			preparedStatement.setLong(4, instituteId);
			preparedStatement.setBoolean(5, true);
			int numberOfRoleUpdated = preparedStatement.executeUpdate();
			if(numberOfRoleUpdated == 1) {
				isUpdated = true;
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		return isUpdated;
	}

	private String getPermissionString(List<Long> permissionIds) {
		if(permissionIds == null || permissionIds.size() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (Long s : permissionIds) {
			System.out.println("inside for loop");
		    sb.append(s);
		    sb.append(",");
		}
		return sb.toString();
	}
}
