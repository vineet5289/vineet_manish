package views.forms.employee;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.SchoolSpecificFiledValidation;
import utils.ValidateFields;
import lombok.Data;

@Data
public class AddEmployeeForm {

	//compulsory field
	private String empName;
	private String empPhoneNumber;

	//optional field
	private String empCode;
	private String empEmail;
	private String department;
	private String jobTitle;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (empName == null || empName.trim().isEmpty()) {
			errors.add(new ValidationError("empName", "Person name should not be empty. And should not contains special characters like ;@[]"));
		}

		if (!ValidateFields.isValidMobileNumber(empPhoneNumber)) {
			errors.add(new ValidationError("empName", "Enter valid contract number."));
		}

		if(empEmail != null && !ValidateFields.isValidEmailId(empEmail)) {
			errors.add(new ValidationError("schoolEmail","Enter valid email id like abcd@xyz.com"));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
