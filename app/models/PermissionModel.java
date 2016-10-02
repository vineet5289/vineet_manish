package models;

import lombok.Data;

@Data
public class PermissionModel {
	private long id;
	private String permissionName;
	private String permissionDescription;
}
