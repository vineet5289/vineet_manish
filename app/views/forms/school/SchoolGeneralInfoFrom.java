package views.forms.school;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import models.SchoolType;
import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.ValidateFields;

@Data
public class SchoolGeneralInfoFrom {
	//non-editable
		private String state;
		private String country;

		private String schoolBoardName; // set by board id
		private String schoolType;
		private String schoolCurrentFinancialYear;
		private int noOfShift;

		//editable
		private String schoolRegistrationId;
		private String schoolAlternativeEmail;
		private String schoolAlternativeNumber;
		private String schoolAddressLine1;
		private String schoolAddressLine2;
		private String city;
		private String pincode;

		private boolean isHostelFacilitiesAvailable;
		private boolean isHostelCompulsory;
		private String schoolClassFrom;
		private String schoolClassTo;
		private String schoolOfficeStartTime;
		private String schoolOfficeEndTime;
		private String schoolOfficeWeekStartDay;
		private String schoolOfficeWeekEndDay;
		private Date schoolFinancialStartDate;
		private Date schoolFinancialEndDate;

		public List<ValidationError> validate() {
			List<ValidationError> errors = new ArrayList<>();
			if(schoolAlternativeEmail != null && !schoolAlternativeEmail.isEmpty() && !ValidateFields.isValidEmailId(schoolAlternativeEmail)) {
				errors.add(new ValidationError("schoolAlternativeEmail","Enter valid alternatice email id like abcd@xyz.com"));
			}

			if (schoolAlternativeNumber != null && !schoolAlternativeNumber.trim().isEmpty()
					&& (ValidateFields.isValidMobileNumber(schoolAlternativeNumber) || ValidateFields.isValidAlternativeNumber(schoolAlternativeNumber))) {
				errors.add(new ValidationError("schoolAlternativeNumber", "Alternative number should be valid."));
			}

			if(!AddressFieldValidationUtils.isValidCity(city)) {
				errors.add(new ValidationError("city", "City should not be empty. And should not contains any special characters except space."));
			}

			if(!AddressFieldValidationUtils.isValidPincode(pincode)) {
				errors.add(new ValidationError("pincode", "Pincode should not be empty. And should not contains any special characters."));
			}

			if(schoolAddressLine1 == null || schoolAddressLine1.trim().isEmpty()) {
				errors.add(new ValidationError("schoolAddressLine1", "Address should not be empty."));
			}

			// validation of non editable feild
			if(!AddressFieldValidationUtils.isValidState(state)) {
				errors.add(new ValidationError("state", "State should not be empty. And should not contains any special characters except space."));
			}

			if(!AddressFieldValidationUtils.isValidCountry(country)) {
				errors.add(new ValidationError("country", "Country should not be empty. And should not contains any special characters except space."));
			}

			if(schoolType == null || SchoolType.schoolTypeToValue.get(schoolType.trim().toUpperCase())  == null) {
				errors.add(new ValidationError("schoolType", "Please enter valid school type without any special characters like @;$."));
			}

			if(errors.size() > 0)
				return errors;
			return null;
		}
}
