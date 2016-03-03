package validation;

public class StringValidation {
	private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	private final String NAME_REGEX = "[a-zA-Z]+";
	private final String PHONE_NUMBER_REGEX = "\\d{10}";

	public String validateFirstName(String name) {
		if(name == null) {
			return "first name should not be null";
		}

		if(name.isEmpty()) {
			return "first name should not be empty";
		}
		if(!name.matches(NAME_REGEX)) {
			return "first name should contains only letter";
		}

		return "";
	}

	public void validateMiddleLastName(String name) throws Exception {
		
		if(name != null && !name.isEmpty() && !name.matches(NAME_REGEX)) {
			throw new Exception("name should contains only letter");
		}
	}

	public void validateEmail(String email) throws Exception {
		if(email == null) {
			throw new Exception("email should not be null");
		}

		if(email.isEmpty()) {
			throw new Exception("email should not be empty");
		}

		if(!email.matches(EMAIL_REGEX)) {
			throw new Exception("email address format should be abc@domain.com");
		}

	}

	public void phoneNumber(String phoneNumber) throws Exception {
		if(phoneNumber == null) {
			throw new Exception("phonenumber should not be null");
		}

		if(phoneNumber.isEmpty()) {
			throw new Exception("phonenumber should not be empty");
		}

		if(!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
			throw new Exception("phonenumber should be in 1234567890 format");
		}

	}

	public void alternativePhoneNumber(String phoneNumber) throws Exception {
		if(phoneNumber != null && !phoneNumber.isEmpty() && !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
			throw new Exception("phonenumber should be in 1234567890 format");
		}
	}
}
