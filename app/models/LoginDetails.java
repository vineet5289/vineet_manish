package models;

import lombok.Data;
import enum_package.Role;

@Data
public class LoginDetails {
	private String userName;
	private String emailId;
	private Role role;
	private String authToken;
	private String schoolIdList;
	private String accessRightList;
	private String error = "";
}