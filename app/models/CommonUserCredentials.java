package models;

import enum_package.LoginStatus;
import lombok.Data;

@Data
public class CommonUserCredentials {
	private String userName;
	private String authToken;
	private String type;
	private String role;
	private String email;
	private String loginstate;
	private String name;
	private LoginStatus loginStatus;
}
