package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.*;
import views.forms.LoginForm;
import views.forms.AddNewSchoolRequest;
import views.forms.SchoolFormData;
import views.html.registerIndex;
import views.html.*;

public class RegistrationRequest extends CustomController {

	public Result registrationRequest() {
//		Form<RegisterSchool> registerSchoolForm = Form.form(RegisterSchool.class);
		return redirect(routes.RegistrationRequest.newUser());
	}

	public Result postRegistrationRequest() {
		return ok("done");
	}
	
	public Result newUser() {
		Form<AddNewSchoolRequest> registerSchoolForm = Form.form(AddNewSchoolRequest.class);
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class);
		return ok(newuser.render(schoolForm, registerSchoolForm));
	}

	public Result preAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class);
//		return ok(newuser.render(schoolForm, registerSchoolForm));
		return ok("");
	}

	public Result postAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class).bindFromRequest();
		return ok("");
	}
}
