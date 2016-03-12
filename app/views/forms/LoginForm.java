package views.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.data.validation.ValidationError;
import lombok.Data;

@Data
public class LoginForm {
	private String userName;
	private String password;

	Pattern pattern = Pattern.compile("[^a-z0-9@_]", Pattern.CASE_INSENSITIVE);

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!isValidString(userName)) {
			System.out.println("=====" + userName);
			errors.add(new ValidationError("user", "user name format is invalid"));
		}

		if (!isValidString(password)) {
			errors.add(new ValidationError("password", "password format is invalid"));     
		}
		return (errors.size() > 0) ? errors : null;
	}

	private boolean isValidString(String str) {
		if(str == null || str.isEmpty())
			return false;
		Matcher matcher = pattern.matcher(str);
		if(matcher.find())
			return false;
		return true;
	}
}
