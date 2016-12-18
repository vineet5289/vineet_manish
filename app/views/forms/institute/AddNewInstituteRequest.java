package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import models.State;
import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.InstituteSpecificFiledValidation;
import utils.ValidateFields;

@Data
public class AddNewInstituteRequest {
	//compulsory field
	private String instituteName;
	private String instituteEmail;
	private String institutePhoneNumber;
	private String instituteAddressLine1;
	private String instituteCity;
	private String instituteState;
	private String instituteCountry;
	private String institutePinCode;
	private String contactPersonName;
	private String groupOfInstitute;
	
	// optional field
	private String instituteOfficeNumber;
	private String instituteAddressLine2 = "";
	private String instituteRegistrationId = "";
	private String query = "";

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!InstituteSpecificFiledValidation.isValidInstituteName(instituteName)) {
			errors.add(new ValidationError("instituteName", "Institute name should not be empty. And should not contains special characters like ;@[]"));
		} else {
//			instituteName
		}

		if (contactPersonName == null || contactPersonName.trim().isEmpty()) {
			errors.add(new ValidationError("contractPersonName", "contract person name should not be empty. And should not contains special characters like ;@[]"));
		}

		if(!ValidateFields.isValidEmailId(instituteEmail)) {
			errors.add(new ValidationError("schoolEmail","Enter valid email id like abcd@xyz.com"));
		}

		if (!ValidateFields.isValidMobileNumber(institutePhoneNumber)) {
			errors.add(new ValidationError("schoolMobileNumber", "Enter valid contract number."));
		}

		if (instituteOfficeNumber != null && !instituteOfficeNumber.trim().isEmpty()
				&& !(ValidateFields.isValidMobileNumber(instituteOfficeNumber) || ValidateFields.isValidAlternativeNumber(instituteOfficeNumber))) {
			errors.add(new ValidationError("schoolAlternativeNumber", "Alternative number should be valid."));
		}

		if(instituteAddressLine1 == null || instituteAddressLine1.trim().isEmpty()) {
			errors.add(new ValidationError("schoolAddressLine1", "Address should not be empty."));
		}

		if(!AddressFieldValidationUtils.isValidCity(instituteCity)) {
			errors.add(new ValidationError("city", "City should not be empty. And should not contains any special characters except space."));
		}

		if(instituteState == null || State.states.get(instituteState.trim().toUpperCase()) == null) {
			errors.add(new ValidationError("state", "State should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidCountry(instituteCountry)) {
			errors.add(new ValidationError("country", "Country should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidPincode(institutePinCode)) {
			errors.add(new ValidationError("pincode", "Pincode should not be empty. And should not contains any special characters."));
		}

		if(groupOfInstitute == null || !(groupOfInstitute.equalsIgnoreCase("single") || groupOfInstitute.equalsIgnoreCase("group"))) {
			errors.add(new ValidationError("groupOfInstitute", "Please tell us whether registration is for single or group of institute."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
