package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import models.State;
import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.InstituteSpecificFiledValidation;
import utils.StringUtils;
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
			instituteName = instituteName.trim();
		}

		if (StringUtils.isBlank(contactPersonName)) {
			errors.add(new ValidationError("contactPersonName", "contact person name should not be empty. And should not contains special characters like ;@[]"));
		} else {
			contactPersonName = contactPersonName.trim();
		}

		if(!ValidateFields.isValidEmailId(instituteEmail)) {
			errors.add(new ValidationError("instituteEmail","Enter valid email id like abcd@xyz.com"));
		} else {
			instituteEmail = instituteEmail;
		}

		if (!ValidateFields.isValidMobileNumber(institutePhoneNumber)) {
			errors.add(new ValidationError("institutePhoneNumber", "Enter valid contract number."));
		} else {
			institutePhoneNumber = institutePhoneNumber.trim();
		}

		if (instituteOfficeNumber != null && !instituteOfficeNumber.trim().isEmpty()
				&& !(ValidateFields.isValidMobileNumber(instituteOfficeNumber) || ValidateFields.isValidAlternativeNumber(instituteOfficeNumber))) {
			errors.add(new ValidationError("instituteOfficeNumber", "Alternative number should be valid."));
		}

		if(StringUtils.isBlank(instituteAddressLine1)) {
			errors.add(new ValidationError("instituteAddressLine1", "Address should not be empty."));
		} else {
			instituteAddressLine1 = instituteAddressLine1.trim();
		}

		if(!AddressFieldValidationUtils.isValidCity(instituteCity)) {
			errors.add(new ValidationError("instituteCity", "City should not be empty. And should not contains any special characters except space."));
		} else {
			instituteCity = instituteCity.trim();
		}

		if(instituteState == null || State.states.get(instituteState.trim().toUpperCase()) == null) {
			errors.add(new ValidationError("instituteState", "State should not be empty. And should not contains any special characters except space."));
		} else {
			instituteState = State.states.get(instituteState.trim().toUpperCase());
		}

		if(!AddressFieldValidationUtils.isValidCountry(instituteCountry)) {
			errors.add(new ValidationError("instituteCountry", "Country should not be empty. And should not contains any special characters except space."));
		} else {
			instituteCountry = instituteCountry.trim();
		}

		if(!AddressFieldValidationUtils.isValidPincode(institutePinCode)) {
			errors.add(new ValidationError("institutePinCode", "Pincode should not be empty. And should not contains any special characters."));
		} else {
			institutePinCode = institutePinCode.trim();
		}

		if(groupOfInstitute == null || !(groupOfInstitute.equalsIgnoreCase("single") || groupOfInstitute.equalsIgnoreCase("group"))) {
			errors.add(new ValidationError("groupOfInstitute", "Please tell us whether registration is for single or group of institute."));
		}

		if(StringUtils.isNotBlank(query)) {
			query = StringUtils.getValidStringValue(query);
		}
		if(errors.size() > 0)
			return errors;
		return null;
	}
}
