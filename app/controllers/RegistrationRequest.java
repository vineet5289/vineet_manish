package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.LoginForm;
import views.forms.RegisterSchool;
import views.html.registerIndex;

public class RegistrationRequest extends CustomController {

	public Result registrationRequest() {
		Form<RegisterSchool> registerSchoolForm = Form.form(RegisterSchool.class);
		return ok(registerIndex.render(registerSchoolForm));
	}

	public Result postRegistrationRequest() {
		return ok("done");
	}
}
