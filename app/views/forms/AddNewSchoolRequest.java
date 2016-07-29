package views.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;
import utils.ValidateFields;

@Data
public class AddNewSchoolRequest {
	private String id;
	private String schoolName = "";
	private String principalName = "";
	private String principalEmail = "";
	private String schoolAddress = "";
	private String schoolEmail = "";
	private String contact = "";
	private String schoolRegistrationId = "";
	private String query = "";
	private String referenceNumber = "";

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (schoolName == null || schoolName.isEmpty()) {
			errors.add(new ValidationError("schoolName", "No School Name Was Given."));
		}

		if (principalName == null || principalName.isEmpty()) {
			errors.add(new ValidationError("principalname", "No Principla Name Was Given."));
		}

		if (schoolAddress == null || schoolAddress.isEmpty()) {
			errors.add(new ValidationError("address", "No School Address was given."));
		}

		if (contact == null ||  contact.isEmpty()) {
			errors.add(new ValidationError("mobile", "No contact was given."));
		}

		if(!(ValidateFields.isValidEmailId(principalEmail) || ValidateFields.isValidEmailId(schoolEmail))) {
			errors.add(new ValidationError("email","enter atleast one valid email id"));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
