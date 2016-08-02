package views.forms.school;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import models.SchoolBoard;
import models.SchoolType;
import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.SchoolSpecificFiledValidation;
import utils.ValidateFields;

@Data
public class SchoolFormData {

	//auto filed data
	private String schoolName;
	private String schoolEmail;
	private String schoolMobileNumber;
	private String schoolAlternativeNumber;
	private String schoolAddressLine1;
	private String schoolAddressLine2;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String schoolRegistrationId;
	private String schoolUserName; //set to email id

	//ask school to fill during registration 
	private String password;
	private String confirmPassword;
	private String schoolBoard;
	private String schoolType;

	/*
	 * not set or asked by user, for internal use
	 * */ 
	private boolean validSchool;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();

		if (!ValidateFields.isValidUserName(schoolUserName)) {
			errors.add(new ValidationError("schoolUserName", "School UserName is either emailId or combination alphanumeric character that can contains only @_-. special characters."));
		}

		if (!SchoolSpecificFiledValidation.isValidSchoolName(schoolName)) {
			errors.add(new ValidationError("schoolName", "School name should not be empty. And should not contains special characters like ;@[]"));
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

		if(!ValidateFields.isValidPassword(password)) {
			errors.add(new ValidationError("password", "Invalid password."));
		}

		if(!ValidateFields.isValidPassword(confirmPassword) || (password != null && !password.equals(confirmPassword))) {
			errors.add(new ValidationError("password", "Invalid confirom password."));
		}

		if(schoolBoard == null || SchoolBoard.getDisplayNameGivenAffiliatedTo(schoolBoard.trim().toUpperCase()) == null) {
			errors.add(new ValidationError("schoolBoard", "Please enter valid school board without any special characters like @;$."));
		}

		if(schoolType == null || SchoolType.schoolTypeToValue.get(schoolType.trim().toUpperCase())  == null) {
			errors.add(new ValidationError("schoolType", "Please enter valid school type without any special characters like @;$."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}

