package controllers.employee;

import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.forms.employee.EmployeeAddRequest;
import controllers.CustomController;

public class EmployeeRegistrationController  extends CustomController {
	@Security.Authenticated(ActionAuthenticator.class)
	public Result preEmployeeAddRequest() {
		Form<EmployeeAddRequest> empAddRequest = Form.form(EmployeeAddRequest.class);
		return ok("");
	}

	public Result postEmployeeAddRequest() {
		return ok("");
	}
}
