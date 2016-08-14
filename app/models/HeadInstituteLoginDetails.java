package models;

import java.util.List;

import enum_package.LoginStatus;
import lombok.Data;

@Data
public class HeadInstituteLoginDetails {
	private String headInstituteName;
	private String headInstituteUserName;
	private String headInstituteLoginType;
	private String headInstituteLoginState;
	private String headInstituteAccessRight;
	private String headInstituteUserRole;
	private Long headInstituteId;
	private int numberOfInstitute;
	private String gropuOfInstitute;
	private String headInstitutePrefered;
	private List<BranchDetails> branches;
	private LoginStatus loginStatus;

	@Data
	public static class BranchDetails {
		private Long instituteId;
		private Long headInstituteId;
		private String headInstituteUserName;
		public String instituteUserName;
		public String instituteName;
		private String institutePrefered;
	}
}
