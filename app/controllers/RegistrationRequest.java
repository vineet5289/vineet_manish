package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.*;
import views.forms.LoginForm;
import views.forms.RegisterSchool;
import views.forms.SchoolFormData;
import views.html.registerIndex;
import views.html.newuser;

public class RegistrationRequest extends CustomController {

	public Result registrationRequest() {
		System.out.println("registrationRequest************");
//		Form<RegisterSchool> registerSchoolForm = Form.form(RegisterSchool.class);
		return redirect(routes.RegistrationRequest.newUser());
	}

	public Result postRegistrationRequest() {
		return ok("done");
	}
	
	public Result newUser() {
		System.out.println("*********newUser ");
		Form<RegisterSchool> registerSchoolForm = Form.form(RegisterSchool.class);
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class);
		return ok(newUser.render(schoolForm, registerSchoolForm));
	}
}
