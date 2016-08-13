package views.forms.institute;

import java.util.Date;

import lombok.Data;

@Data
public class NewSchoolApprovedRequest {
	private Long id;
	private String referenceNumber = "";
	private String schoolName = "";
	private String schoolEmail = "";
	private String schoolMobileNumber = "";
	private String schoolAddressLine1 = "";
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String contractPersonName;
	private String schoolAlternativeNumber = "";
	private String schoolAddressLine2 = "";
	private String schoolRegistrationId = "";
	private String query = "";
	private String status;
	private Date requestedAt;
	private Date statusUpdatedAt;
}
