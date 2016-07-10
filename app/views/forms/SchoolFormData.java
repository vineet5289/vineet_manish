package views.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;
import utils.ValidateFields;

@Data
public class SchoolFormData {
	private String schoolName = "";
	private String schoolRegistration;
	private String schooleEmail;
	private String schoolMobileNumber;
	private String schoolOfficeNumber;
	private String schoolUserName;
	private String schoolPassword;
	private String schoolConfirmPassword;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String schoolCountry;
	private String pincode;
	private int noOfShift = 1;
	private String schoolBoard;
	private String schoolCategory;
	private String schoolType;

	public List<ValidationError> validate() {

		List<ValidationError> errors = new ArrayList<>();

		if (schoolName == null || schoolName.isEmpty()) {
			errors.add(new ValidationError("schoolname", "No schoolName was given."));
		}

//		if (schoolRegistration != null || schoolName.isEmpty()) {
//			errors.add(new ValidationError("schoolname", "No schoolName was given."));
//		}

		if (schooleEmail != null && !ValidateFields.isValidEmailId(schooleEmail)) {
			errors.add(new ValidationError("schooleEmail", "School email is empty"));
		}

//		if () { phone number validation
//			errors.add(new ValidationError("schoolname", "No schoolName was given."));
//		}

		if (!ValidateFields.isValidPassword(schoolPassword)) {
			errors.add(new ValidationError("schoolPassword", "School password is invalid"));
		}

		if (schoolConfirmPassword == null || schoolPassword == null || !schoolConfirmPassword.equals(schoolPassword)) {
			errors.add(new ValidationError("schoolPassword", "School password is invalid"));
		}

		if(errors.size() > 0)
			return errors;

		return null;
	}
}

