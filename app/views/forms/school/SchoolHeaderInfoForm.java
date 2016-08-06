package views.forms.school;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import utils.SchoolSpecificFiledValidation;
import utils.ValidateFields;
import lombok.Data;

@Data
public class SchoolHeaderInfoForm {
	private String schoolUserName;
	private String schoolName;
	private String schoolEmail;
	private String schoolMobileNumber;
	private String schoolPreferedName;
	private String schoolWebsiteUrl; // need to write validation
	private String schoolLogoUrl;  // need to write validation

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();

		if (!ValidateFields.isValidUserName(schoolUserName)) {
			errors.add(new ValidationError("schoolUserName", "Enter valid school user name."));
		}
		
		if (!SchoolSpecificFiledValidation.isValidSchoolName(schoolName)) {
			errors.add(new ValidationError("schoolName", "School name should not be empty. And should not contains special characters like ;@[]"));
		}

		if (!ValidateFields.isValidEmailId(schoolEmail)) {
			errors.add(new ValidationError("schoolEmail", "Enter valid school user name."));
		}

		if (!ValidateFields.isValidMobileNumber(schoolMobileNumber)) {
			errors.add(new ValidationError("schoolMobileNumber", "Enter valid contract number."));
		}

		if (schoolPreferedName != null && !SchoolSpecificFiledValidation.isValidSchoolName(schoolPreferedName)) {
			errors.add(new ValidationError("schoolPreferedName", "School name either empty or should not contains special characters like ;@[]"));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
