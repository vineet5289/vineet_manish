package views.forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

@Data
public class AddNewSchoolRequest {
	private String schoolName="";
	private String principalName="";
	private String principalEmail="";
	private String schoolAddress="";
	private String contact="";
	private String schoolRegistrationId="";
	private String query="";

	private static Pattern emailNamePtrn = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (schoolName == null || schoolName.isEmpty()) {
			errors.add(new ValidationError("schoolName", "No School Name Was Given."));
		}

		if (principalName == null || principalName.isEmpty()) {
			errors.add(new ValidationError("principalname", "No Principla Name Was Given."));
		}

		if (schoolAddress == null || schoolAddress.isEmpty()) {
			errors.add(new ValidationError("address", "No School Address was given."));
		}

		if (contact == null || contact.isEmpty()) {
			errors.add(new ValidationError("mobile", "No contact was given."));
		}

		if(principalEmail == null || validateEmailAddress(principalEmail)==false) {
			System.out.println("*888 email");
			errors.add(new ValidationError("Email","check your email"));
		}
System.out.println(errors + "=-----");
		if(errors.size() > 0)
			return errors;

		return null;
	}

	public static boolean validateEmailAddress(String email) {
		Matcher mtch = emailNamePtrn.matcher(email);
		if(mtch.matches()){
			return true;
		}
		return false;
	}
}
