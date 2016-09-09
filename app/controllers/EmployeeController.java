package controllers;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.employee.AddEmployeeForm;
import views.forms.institute.InstituteFormData;
import dao.employee.EmployesDAO;
import enum_package.SessionKey;

public class EmployeeController extends CustomController {

	@Inject
	private FormFactory formFactory;

	/*
	 * validate valid user
	 * validate user has access right or not
	 * */
	public Result preAddEmployeeRequest() {
		String userName = session().get(SessionKey.username.name());
		String userAuthKey = session().get(SessionKey.authtoken.name());
		String schoolId = session().get(SessionKey.instituteid.name());
		Form<AddEmployeeForm> addEmployeeForm = formFactory.form(AddEmployeeForm.class);
		return ok("addEmployeeForm"); // may return all requested employee, not yet register
	}

	/*
	 * validate valid user
	 * validate user has access right or not
	 * */
	public Result postAddEmployeeRequest() {
		Form<AddEmployeeForm> addEmployeeForm = formFactory.form(AddEmployeeForm.class).bindFromRequest();
		if(addEmployeeForm == null || addEmployeeForm.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in add employee request.");
			return redirect(""); // redirect to add employee page
		}
		AddEmployeeForm addEmployeeDetails= addEmployeeForm.get();
		EmployesDAO employesDAO = new EmployesDAO();
		boolean isEmpAdded = false;
		try{
			String reuestedPersonUserName = session().get(SessionKey.username.name());
			String schoolId = session().get(SessionKey.instituteid.name());
			isEmpAdded = employesDAO.addNewEmpRequest(addEmployeeDetails, reuestedPersonUserName, schoolId);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		if(isEmpAdded) {
			flash("success", "Employee added successfully");
		} else {
			flash("error", "Sorry! something going wrong during add employee request. Please try after sometime.");
		}

		return ok(""); // redirect to add employee page, and decide what should be added
	}

	public Result preEmployeeRegistrationRequest() {
		InstituteFormData studentData = new InstituteFormData();
		Form<InstituteFormData> schoolForm = formFactory.form(InstituteFormData.class).fill(studentData);
		return ok("");
//		return ok(schoolFieldSetIndex.render(schoolForm,
//				State.makeStateMap(studentData),
//				SchoolBoard.makeSchoolBoardMap(studentData),
//				SchoolType.makeSchoolTypeMap(studentData)
//				));
	}

	public Result postEmployeeRegistrationRequest() {
		Form<InstituteFormData> schoolForm = formFactory.form(InstituteFormData.class).bindFromRequest();
		return ok("school Registration completed");

	}
}
