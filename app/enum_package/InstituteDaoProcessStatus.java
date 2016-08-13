package enum_package;

public enum InstituteDaoProcessStatus {
	validschool(""),
	invalidschool(""),
	servererror("Sorry!! something wrong happened processing of your request. Please try after sometime."),
	invalidreferencenumber("Your entered key is invalid. Please check and try again"),
	invalidvalue("Please check your input value and fill it again"),
	invalidshift("Please enter all class/shift related information i.e attendance type, timining etc");

	private String value;
	private InstituteDaoProcessStatus(String value) {
		this.value = value;
	}
}
