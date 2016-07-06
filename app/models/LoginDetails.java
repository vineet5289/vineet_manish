package models;

import lombok.Data;
import enum_package.Role;

@Data
public class LoginDetails {
	private String userName;
	private String emailId;
	private Role role;
	private String authToken;
	private Long schoolId;
	private String accessRight;
	private String name;
	private String error = "";
}