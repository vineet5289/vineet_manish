package security;

import java.util.Map;

public class SessionKeyClass {
	private String userName;
	private String loginType;
	private String schoolId;
	private String userRole;
	private String shiftId;
	private String childId;
	
	private Map<String, String> authTokens;
	private Map<String, String> userAccessRights;
}
