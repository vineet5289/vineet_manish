package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum LoginStatus {
	invalidusername("UserName/Password is invalid"),
	usernameformatinvalid("UserName/Password is invalid"),
	invalidpassword("UserName/Password is invalid"),
	passwordformatinvalid("UserName/Password is invalid"),
	invaliduser("UserName/Password is invalid"),
	validuser(""),
	servererror("We are very sorry. Some server exception happen. We are working on it. Please try after some time."),
	overloginsessioncount("Sorry you are already active/login at more then 3 places.");

	private String message;
	private LoginStatus(String message) {
		this.message = message;
	}
	private static final Map<LoginStatus, String> loginStatusToMessage = new HashMap<LoginStatus, String>();
	static {
		for(LoginStatus ls : LoginStatus.values()) {
			loginStatusToMessage.put(ls, ls.message);
		}
	}

	public static String of(LoginStatus loginStatusMessage) {
		String result = loginStatusToMessage.get(loginStatusMessage);
		if(result == null) {
			throw new IllegalArgumentException("No Week day exits for given int value = " + loginStatusMessage);
		}
		return result;
	}
}
