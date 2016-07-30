package views.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.SchoolSpecificFiledValidation;
import utils.ValidateFields;

@Data
public class AddNewSchoolRequest {
	//only for internal use
	private Long id;
	private String referenceNumber = "";

	//compulsory field
	private String schoolName = "";
	private String schoolEmail = "";
	private String schoolMobileNumber = "";
	private String schoolAddressLine1 = "";
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String contractPersonName;

	// optional field
	private String schoolAlternativeNumber = "";
	private String schoolAddressLine2 = "";
	private String schoolRegistrationId = "";
	private String query = "";

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!SchoolSpecificFiledValidation.isValidSchoolName(schoolName)) {
			errors.add(new ValidationError("schoolName", "School name should not be empty. And should not contains special characters like ;@[]"));
		}

		if (contractPersonName == null || contractPersonName.trim().isEmpty()) {
			errors.add(new ValidationError("contractPersonName", "contract person name should not be empty. And should not contains special characters like ;@[]"));
		}

		if(!ValidateFields.isValidEmailId(schoolEmail)) {
			errors.add(new ValidationError("schoolEmail","Enter valid email id like abcd@xyz.com"));
		}

		if (!ValidateFields.isValidMobileNumber(schoolMobileNumber)) {
			errors.add(new ValidationError("schoolMobileNumber", "Enter valid contract number."));
		}

		if (schoolAlternativeNumber != null && !schoolAlternativeNumber.trim().isEmpty()
				&& (ValidateFields.isValidMobileNumber(schoolAlternativeNumber) || ValidateFields.isValidAlternativeNumber(schoolAlternativeNumber))) {
			errors.add(new ValidationError("schoolAlternativeNumber", "Alternative number should be valid."));
		}

		if(schoolAddressLine1 == null || schoolAddressLine1.trim().isEmpty()) {
			errors.add(new ValidationError("schoolAddressLine1", "Address should not be empty."));
		}

		if(!AddressFieldValidationUtils.isValidCity(city)) {
			errors.add(new ValidationError("city", "City should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidState(state)) {
			errors.add(new ValidationError("state", "State should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidCountry(country)) {
			errors.add(new ValidationError("country", "Country should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidPincode(pincode)) {
			errors.add(new ValidationError("pincode", "Pincode should not be empty. And should not contains any special characters."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
