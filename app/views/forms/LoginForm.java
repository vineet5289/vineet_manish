package views.forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import utils.ValidateFields;
import lombok.Data;

@Data
public class LoginForm {
	private String userName;
	private String password;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!ValidateFields.isValidUserName(userName)) {
			errors.add(new ValidationError("user", "user name format is invalid"));
		}

		if (!ValidateFields.isValidPassword(password)) {
			errors.add(new ValidationError("password", "password format is invalid"));     
		}

		return (errors.size() > 0) ? errors : null;
	}
}
