package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum InstituteDaoProcessStatus {
	validschool(""),
	invalidschool(""),
	servererror("Sorry!! something wrong happened processing of your request. Please try after sometime."),
	invalidreferencenumber("Your entered key is invalid. Please check and try again"),
	invalidvalue("Please check your input value and fill it again"),
	invalidshift("Please enter all class/shift related information i.e attendance type, timining etc"),
	branchsuccessfullyadded("Congratulations your requested branch has been add successfully. You can see newly branch on profile page."),
	requestedinstitutednotfound("Error occur during request process. Please refress or try after some time.");

	private String value;
	private final static Map<InstituteDaoProcessStatus, String> statusToValue = new HashMap<InstituteDaoProcessStatus, String>(InstituteDaoProcessStatus.values().length);
	private InstituteDaoProcessStatus(String value) {
		this.value = value;
	}

	static {
		for(InstituteDaoProcessStatus idps : InstituteDaoProcessStatus.values()) {
			statusToValue.put(idps, idps.value);
		}
	}

	public static String of(InstituteDaoProcessStatus key) {
		String statusMessage = statusToValue.get(key);
		if(statusMessage == null)
			return "";
		return statusMessage;
	}
}
