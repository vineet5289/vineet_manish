package views.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import models.SchoolBoard;
import models.State;
import play.data.validation.ValidationError;
import enum_package.SchoolCateroryEnum;
import enum_package.SchoolTypeEnum;

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

	private String principleName;
	private String principleEmail;
	private String principleMobileNumber;
	private String principleUserName;
	private String principlePassword;
	private String principleConfirmPassword;

	public List<ValidationError> validate() {

		List<ValidationError> errors = new ArrayList<>();
//
//		if (schoolName == null || schoolName.isEmpty()) {
//			errors.add(new ValidationError("schoolname", "No schoolName was given."));
//		}
//
//		if (principleName == null || principleName.isEmpty()) {
//			errors.add(new ValidationError("principalname", "No principleName was given."));
//		}

		if(errors.size() > 0)
			return errors;

		return null;
	}
}

