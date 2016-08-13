package enum_package;

public enum InstituteDaoProcessStatus {
	validschool(""),
	invalidschool(""),
	servererror("Sorry!! something wrong happened processing of your request. Please try after sometime."),
	invalidreferencenumber("Your entered key is invalid. Please check and try again");

	private String value;
	private InstituteDaoProcessStatus(String value) {
		this.value = value;
	}
}
