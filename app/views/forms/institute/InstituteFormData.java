package views.forms.institute;

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
public class InstituteFormData {

	//auto filed data
	private String name;
	private String email;
	private String phoneNumber;
	private String officeNumber;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private String registrationId;
	private String userName; //set to email id
	private String groupOfInstitute;
	private int noOfInstitute;

	//ask school to fill during registration 
	private String password;
	private String confirmPassword;
	private String board;
	private String type;

	/*
	 * not set or asked by user, for internal use
	 * */ 
	private boolean validSchool;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();

		if (!ValidateFields.isValidUserName(userName)) {
			errors.add(new ValidationError("schoolUserName", "School UserName is either emailId or combination alphanumeric character that can contains only @_-. special characters."));
		}

		if (!SchoolSpecificFiledValidation.isValidSchoolName(name)) {
			errors.add(new ValidationError("schoolName", "School name should not be empty. And should not contains special characters like ;@[]"));
		}

		if(!ValidateFields.isValidEmailId(email)) {
			errors.add(new ValidationError("schoolEmail","Enter valid email id like abcd@xyz.com"));
		}

		if (!ValidateFields.isValidMobileNumber(phoneNumber)) {
			errors.add(new ValidationError("schoolMobileNumber", "Enter valid contract number."));
		}

		if (officeNumber != null && !officeNumber.trim().isEmpty()
				&& !(ValidateFields.isValidMobileNumber(officeNumber) || ValidateFields.isValidAlternativeNumber(officeNumber))) {
			errors.add(new ValidationError("schoolAlternativeNumber", "Alternative number should be valid."));
		}

		if(addressLine1 == null || addressLine1.trim().isEmpty()) {
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

		if(!AddressFieldValidationUtils.isValidPincode(pinCode)) {
			errors.add(new ValidationError("pincode", "Pincode should not be empty. And should not contains any special characters."));
		}

		if(!ValidateFields.isValidPassword(password)) {
			errors.add(new ValidationError("password", "Invalid password."));
		}

		if(!ValidateFields.isValidPassword(confirmPassword) || (password != null && !password.equals(confirmPassword))) {
			errors.add(new ValidationError("password", "Invalid confirom password."));
		}

		if(board == null || SchoolBoard.getDisplayNameGivenAffiliatedTo(board.trim().toUpperCase()) == null) {
			errors.add(new ValidationError("schoolBoard", "Please enter valid school board without any special characters like @;$."));
		}

		if(type == null || SchoolType.schoolTypeToValue.get(type.trim().toUpperCase())  == null) {
			errors.add(new ValidationError("schoolType", "Please enter valid school type without any special characters like @;$."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}

