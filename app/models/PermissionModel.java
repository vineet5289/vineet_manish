package models;

import lombok.Data;

@Data
public class PermissionModel {
	public long id;
	public String permissionName;
	public String permissionDescription;
}
