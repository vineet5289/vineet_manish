package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;
import utils.ValidateFields;

@Data
public class AddEmployee {
	private String empName;
	private String empEmail;// optional
	private  String empPhoneNumber;
	private String empJobTitles; // optional
	private String empCode;// optional

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!ValidateFields.isValidStringField(empName)) {
			errors.add(new ValidationError("empName", "Employee name should not be empty. And should not contains special characters like ;@[]"));
		}

		if(empEmail != null && !empEmail.isEmpty() && !ValidateFields.isValidEmailId(empEmail)) {
			errors.add(new ValidationError("empEmail","Enter valid email id like abcdXXXXX@xyz.com"));
		}

		if (!ValidateFields.isValidMobileNumber(empPhoneNumber)) {
			errors.add(new ValidationError("empPhoneNumber", "Enter valid contract number."));
		}

		if (!ValidateFields.isValidStringField(empJobTitles)) {
			errors.add(new ValidationError("empJobTitles", "Enter valid contract number."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}

}
