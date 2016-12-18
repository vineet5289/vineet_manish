package views.forms.institute;

import lombok.Data;

@Data
public class RoleForm {
	public long id;
	public String roleName;
	public String roleDescription;
	public String roleCreatedAt;
	public String roleCreatedBy;
	public String permission;
	public boolean isEditable;
}
