package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.PermissionModel;
import play.db.Database;
import play.db.NamedDatabase;

public class PermissionDAO {
	@Inject @NamedDatabase("srp") private Database db;
	public List<PermissionModel> getAllPermissionInSystem() throws SQLException {
		List<PermissionModel> permissions = new ArrayList<PermissionModel>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = String.format("SELECT %s, %s, %s FROM %s WHERE %s = ?;", Tables.Permissions.id, Tables.Permissions.permissionName,
				Tables.Permissions.permissionDescription, Tables.Permissions.table, Tables.Permissions.isActive);
		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
			preparedStatement.setBoolean(1, true);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				PermissionModel permission = new PermissionModel();
				permission.setId(resultSet.getLong(Tables.Permissions.id));
				permission.setPermissionName(resultSet.getString(Tables.Permissions.permissionName));
				permission.setPermissionDescription(resultSet.getString(Tables.Permissions.permissionDescription));
				permissions.add(permission);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			permissions = new ArrayList<PermissionModel>();
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
		return permissions;
	}
}
