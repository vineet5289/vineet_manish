package views.forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import utils.ValidateFields;

public class ChangePasswordForm {
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if(!ValidateFields.isValidPassword(oldPassword)) {
			errors.add(new ValidationError("oldPassword", "Invalid password format."));
		}

		if(!ValidateFields.isValidPassword(newPassword)) {
			errors.add(new ValidationError("newPassword", "Invalid password format."));
		}

		if(!ValidateFields.isValidPassword(confirmNewPassword) || (newPassword != null && !newPassword.equals(confirmNewPassword))) {
			errors.add(new ValidationError("confirmNewPassword", "Invalid confirom password format."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
