package controllers;

import java.util.Map;

import dao.employee.EmployesDAO;
import enum_package.SessionKey;
import play.data.Form;
import play.mvc.Result;
import views.forms.employee.AddEmployeeForm;
import views.forms.school.SchoolFormData;

public class EmployeeController extends CustomController {

	/*
	 * validate valid user
	 * validate user has access right or not
	 * */
	public Result preAddEmployeeRequest() {
		String superUserName = session().get(SessionKey.SUPER_USER_NAME.name());
		String superUserAuthKey = session().get(SessionKey.SUPER_AUTH_TOKEN.name());
		String superUserSchoolId = session().get(SessionKey.SUPER_SCHOOL_ID.name());
		Form<AddEmployeeForm> addEmployeeForm = Form.form(AddEmployeeForm.class);
		return ok("addEmployeeForm"); // may return all requested employee, not yet register
	}

	/*
	 * validate valid user
	 * validate user has access right or not
	 * */
	public Result postAddEmployeeRequest() {
		Form<AddEmployeeForm> addEmployeeForm = Form.form(AddEmployeeForm.class).bindFromRequest();
		if(addEmployeeForm == null || addEmployeeForm.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in add employee request.");
			return redirect(routes.SchoolController.preAddNewSchoolRequest()); // redirect to add employee page
		}
		AddEmployeeForm addEmployeeDetails= addEmployeeForm.get();
		EmployesDAO employesDAO = new EmployesDAO();
		boolean isEmpAdded = false;
		try{
			String reuestedPersonUserName = session().get(SessionKey.SUPER_USER_NAME.name());
			String schoolId = session().get(SessionKey.SUPER_SCHOOL_ID.name());
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
		SchoolFormData studentData = new SchoolFormData();
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).fill(studentData);
		return ok("");
//		return ok(schoolFieldSetIndex.render(schoolForm,
//				State.makeStateMap(studentData),
//				SchoolBoard.makeSchoolBoardMap(studentData),
//				SchoolType.makeSchoolTypeMap(studentData)
//				));
	}

	public Result postEmployeeRegistrationRequest() {
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).bindFromRequest();
		return ok("school Registration completed");

	}
}
