package models;

import java.util.Map;

import enum_package.LoginStatus;
import lombok.Data;

@Data
public class LoginDetails {
	private String userName;
	private String authToken;
	private String type;
	private Long schoolId;
	private String shiftId;
	private String childId;	
	private Map<String, String> childIds;
	private String role;
	private String accessRight;
	private String passwordState;
	private LoginStatus loginStatus;
}