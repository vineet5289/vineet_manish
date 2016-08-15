package actors;

import enum_package.InstituteDaoProcessStatus;
import lombok.Data;

public class SchoolRequestActorProtocol {

	@Data
	public static class NewSchoolRequest {
		private String schoolEmailId;
		private String schoolName;
		private String schoolPhoneNumber;
		private String referenceNumber;
	}

	@Data
	public static class ApprovedSchool {
		private Long id;
		private String principleName;
		private String principleEmail;
		private String contract;
		private String authToke;
		private String referenceNumber;
	}

	@Data
	public static class AddInstituteBranch {
		private String instituteBranchName;
		private String instituteEmail;
		private String instituteContract;
		private String institutePassword;
		private String instituteUserName;
		private InstituteDaoProcessStatus processStatus;
	}
}
