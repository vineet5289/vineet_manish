package controllers.employee;

import javax.inject.Inject;

import controllers.CustomController;
import controllers.institute.routes;
import dao.employee.EmployesDAO;
import enum_package.SessionKey;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;
import security.institute.HeadInstituteBasicAuthCheck;
import views.forms.employee.AddEmployeeForm;
import views.forms.employee.EmployeeDetailsForm;

public class EmployeeController extends CustomController {
	@Inject
	private FormFactory formFactory;
	@Inject EmployesDAO employesDAO;
//	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result preAddEmployeeRequest() {
		Form<AddEmployeeForm> addEmployee = formFactory.form(AddEmployeeForm.class);
		return ok("addEmployee " + addEmployee);
	}

//	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
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

		boolean isEmpAdded = false;
		try {
			String userName = session().get(SessionKey.of(SessionKey.username));
			String instituteId = session().get(SessionKey.of(SessionKey.instituteid));
			isEmpAdded = employesDAO.addNewEmpRequest(addEmployee, userName, instituteId);
			
		} catch(Exception exception) {
			exception.printStackTrace();
			isEmpAdded = false;
		}

		return ok("");
	}

//	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result viewAllEmployee() {
		Form<EmployeeDetailsForm> addEmployeeForm = formFactory.form(EmployeeDetailsForm.class).bindFromRequest();
		if(addEmployeeForm == null || addEmployeeForm.hasErrors()) {
			flash("error", "Some errors occur either of some fileds are missing or contains invalid value.");
			return redirect(controllers.institute.routes.AddOrDeleteEmployee.preAddEmployeeRequest());
		}

		AddEmployeeForm addEmployee = addEmployeeForm.get();
		if(addEmployee == null) {
			flash("error", "Some errors occur either of some fileds are missing or contains invalid value.");
			return redirect(controllers.institute.routes.AddOrDeleteEmployee.preAddEmployeeRequest());
		}

		boolean isEmpAdded = false;
		try {
			String userName = session().get(SessionKey.of(SessionKey.username));
			String instituteId = session().get(SessionKey.of(SessionKey.instituteid));
			isEmpAdded = employesDAO.addNewEmpRequest(addEmployee, userName, instituteId);
			
		} catch(Exception exception) {
			exception.printStackTrace();
			isEmpAdded = false;
		}

		return ok("");
	}
}
