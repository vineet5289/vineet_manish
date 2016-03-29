package models;

import enum_package.Role;
import lombok.Data;

@Data
public class LoginDetails {
	private String userName;
	private Role role;
	private String authToken;
	private String error = "";
	private Long schoolId;
}