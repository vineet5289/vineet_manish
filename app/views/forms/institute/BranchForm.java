package views.forms.institute;

import lombok.Data;

@Data
public class BranchForm {
	private Long instituteId;
	private Long headInstituteId;
	private String headInstituteUserName;
	private String instituteUserName;
}
