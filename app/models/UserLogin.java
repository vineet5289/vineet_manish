package models;

import enum_package.PasswordState;
import play.data.validation.Constraints.Required;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UserLogin {

	@Required @Setter @Getter
	private String userName;

	@Required
	private String password;
	
	@Required
	private PasswordState passwordState;

	private String authToken;
}
