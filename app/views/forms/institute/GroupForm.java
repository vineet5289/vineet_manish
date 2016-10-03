package views.forms.institute;

import lombok.Data;

@Data
public class GroupForm {
	public long id;
	public String groupName;
	public String groupDescription;
	public String groupCreatedAt;
	public String groupCreatedBy;
}
