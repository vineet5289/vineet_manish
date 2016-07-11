package controllers;

import play.mvc.Result;

public class RegistrationController extends CustomController {
	public Result preRegistration(String userType) {
		System.out.println("user Type");
		return ok("pre registration");
	}

	public Result postRegistration() {
		return ok("post registration");
	}

}
