package views.forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import utils.OTPReferenceKeyValidationUtils;
import utils.ValidateFields;
import lombok.Data;

@Data
public class OTPField {
	private String referenceKey;
	private String otp;
	private String otpCategory;
	private String emailId;
	
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!OTPReferenceKeyValidationUtils.isValidReferenceKey(referenceKey)) {
			errors.add(new ValidationError("referenceKey", "Please enter valid reference key send through mail and message"));
		}

		if (!OTPReferenceKeyValidationUtils.isValidOTP(otp)) {
			errors.add(new ValidationError("otp", "Invalid otp."));
		}

		if(!ValidateFields.isValidEmailId(emailId)) {
			errors.add(new ValidationError("emailId","Enter valid email id provided during registration request."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
