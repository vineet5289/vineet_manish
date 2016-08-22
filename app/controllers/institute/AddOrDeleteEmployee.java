package controllers.institute;

import controllers.CustomController;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.institute.HeadInstituteBasicAuthCheck;
import views.forms.institute.AddEmployee;

public class AddOrDeleteEmployee extends CustomController {
	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result preAddEmployeeRequest() {
		Form<AddEmployee> addEmployee = Form.form(AddEmployee.class);
		return ok("addEmployee");
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result postAddEmployeeRequest() {
		
		return ok("");
	}
}
