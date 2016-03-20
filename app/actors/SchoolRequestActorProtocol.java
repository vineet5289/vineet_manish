package actors;

import lombok.Data;

public class SchoolRequestActorProtocol {

	@Data
	public static class NewSchoolRequest {
		private String receiverEmailId;
		private String receiverName;
		private String receiverPhoneNumber;
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
}
