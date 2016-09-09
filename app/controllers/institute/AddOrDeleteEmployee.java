package controllers.institute;

import javax.inject.Inject;

import controllers.CustomController;
import enum_package.SessionKey;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;
import security.institute.HeadInstituteBasicAuthCheck;
import views.forms.institute.AddEmployeeForm;

public class AddOrDeleteEmployee extends CustomController {
	@Inject
	private FormFactory formFactory;

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result preAddEmployeeRequest() {
		Form<AddEmployeeForm> addEmployee = formFactory.form(AddEmployeeForm.class);
		return ok("addEmployee " + addEmployee);
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result postAddEmployeeRequest() {
		Form<AddEmployeeForm> addEmployeeForm = formFactory.form(AddEmployeeForm.class).bindFromRequest();
		if(addEmployeeForm == null || addEmployeeForm.hasErrors()) {
			flash("error", "Some errors occur either of some fileds are missing or contains invalid value.");
			return redirect(controllers.institute.routes.AddOrDeleteEmployee.preAddEmployeeRequest());
		}

		AddEmployeeForm addEmployee = addEmployeeForm.get();
		if(addEmployee == null) {
			flash("error", "Some errors occur either of some fileds are missing or contains invalid value.");
			return redirect(controllers.institute.routes.AddOrDeleteEmployee.preAddEmployeeRequest());
		}

		String userName = session().get(SessionKey.of(SessionKey.username));
		String schoolId = session().get(SessionKey.of(SessionKey.instituteid));
		
		return ok("");
	}
}
