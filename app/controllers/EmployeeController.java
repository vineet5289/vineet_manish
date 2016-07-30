package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.SchoolFormData;

public class EmployeeController extends CustomController {
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
