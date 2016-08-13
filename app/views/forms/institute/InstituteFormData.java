package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import enum_package.InstituteDaoProcessStatus;
import lombok.Data;
import models.SchoolBoard;
import models.SchoolType;
import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.SchoolSpecificFiledValidation;
import utils.ValidateFields;

@Data
public class InstituteFormData {
	// for internal use
	InstituteDaoProcessStatus processingStatus;
	//auto filed data
	private String instituteName;
	private String instituteEmail;
	private String institutePhoneNumber;
	private String instituteOfficeNumber;
	private String instituteAddressLine1;
	private String instituteAddressLine2;
	private String instituteCity;
	private String instituteState;
	private String instituteCountry;
	private String institutePinCode;
	private String instituteRegistrationId;
	private String instituteUserName; //set to email id
	private String groupOfInstitute;
	private int noOfInstitute;

	//ask school to fill during registration 
	private String institutePassword;
	private String instituteConfirmPassword;
	private String instituteBoard;
	private String instituteType;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();

		if (!ValidateFields.isValidUserName(instituteUserName)) {
			errors.add(new ValidationError("schoolUserName", "School UserName is either emailId or combination alphanumeric character that can contains only @_-. special characters."));
		}

		if (!SchoolSpecificFiledValidation.isValidSchoolName(instituteName)) {
			errors.add(new ValidationError("schoolName", "School name should not be empty. And should not contains special characters like ;@[]"));
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

		if(!AddressFieldValidationUtils.isValidState(instituteState)) {
			errors.add(new ValidationError("state", "State should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidCountry(instituteCountry)) {
			errors.add(new ValidationError("country", "Country should not be empty. And should not contains any special characters except space."));
		}

		if(!AddressFieldValidationUtils.isValidPincode(institutePinCode)) {
			errors.add(new ValidationError("pincode", "Pincode should not be empty. And should not contains any special characters."));
		}

		if(!ValidateFields.isValidPassword(institutePassword)) {
			errors.add(new ValidationError("password", "Invalid password."));
		}

		if(!ValidateFields.isValidPassword(instituteConfirmPassword) || (institutePassword != null && !institutePassword.equals(instituteConfirmPassword))) {
			errors.add(new ValidationError("password", "Invalid confirom password."));
		}

		if(instituteBoard == null || SchoolBoard.getDisplayNameGivenAffiliatedTo(instituteBoard.trim().toUpperCase()) == null) {
			errors.add(new ValidationError("schoolBoard", "Please enter valid school board without any special characters like @;$."));
		}

		if(instituteType == null || SchoolType.schoolTypeToValue.get(instituteType.trim().toUpperCase())  == null) {
			errors.add(new ValidationError("schoolType", "Please enter valid school type without any special characters like @;$."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}

