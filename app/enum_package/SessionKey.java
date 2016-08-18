package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum SessionKey {
	username("username"),
	authtoken("authtoken"),
	logintype("logintype"),
	loginstate("loginstate"),
	instituteid("instituteid"),
	userrole("userrole"),
	useraccessright("useraccessright"),
	shiftid("shiftid"),
	childid("childid"),
	reguserrequest("reguserrequest"),
	otpkey("otpkey"),
	regschoolrequestnumber("regschoolrequestnumber"),
	reginstituterequestid("reginstituterequestid"),
	numerofinstituteingroup("numerofinstituteingroup"),
	currentuserloginnumber("currentuserloginnumber"),
	sessiontrackfield("sessiontrackfield");

	private String value;
	private SessionKey(String value) {
		this.value = value;
	}

	private final static Map<SessionKey, String> nameToValue = new HashMap<SessionKey, String>(SessionKey.values().length);
	private final static Map<String, SessionKey> valueToName = new HashMap<String, SessionKey>(SessionKey.values().length);
	static {
		for(SessionKey sk : SessionKey.values()) {
			nameToValue.put(sk, sk.value);
			valueToName.put(sk.value, sk);
		}
	}

	public static boolean contain(SessionKey key) {
		String result = nameToValue.get(key);
		if(result == null)
			return false;
		return true;
	}

	public static boolean contain(String key) {
		SessionKey result = valueToName.get(key);
		if(result == null)
			return false;
		return true;
	}
}
