package models;

import lombok.Data;
//import views.formdata.SchoolFormData;

@Data
public class Login {
	private String userName;
	private String password;

	public Login() {
	}

	public Login(String userName, String password) {
		this.userName=userName;
		this.password=password;
	}
}