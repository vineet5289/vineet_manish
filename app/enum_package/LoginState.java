package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum LoginState {
	firststate("firststate"),
	blockedstate("blockedstate"),
	redirectstate("redirectstate"),
	finalstate("finalstate");

	private String value;
	private LoginState(String value) {
		this.value = value;
	}

	private final static Map<LoginState, String> nameToValue = new HashMap<LoginState, String>(LoginState.values().length);
	private final static Map<String, LoginState> valueToName = new HashMap<String, LoginState>(LoginState.values().length);
	static {
		for(LoginState ls : LoginState.values()) {
			nameToValue.put(ls, ls.value);
			valueToName.put(ls.value, ls);
		}
	}

	public static boolean contain(LoginState key) {
		String result = nameToValue.get(key);
		if(result == null)
			return false;
		return true;
	}

	public static boolean contain(String key) {
		LoginState result = valueToName.get(key);
		if(result == null)
			return false;
		return true;
	}
}
