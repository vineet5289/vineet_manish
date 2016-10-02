package views.forms.institute;

import java.util.List;

import lombok.Data;

@Data
public class PermissionForm {
	public Long roleId;
	public List<Long> permissions;
}
