package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.SchoolFormData;

public class StudentController extends CustomController {
	public Result preStudentRegistrationRequest() {
		SchoolFormData studentData = new SchoolFormData();
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).fill(studentData);
		return ok("");
//		return ok(schoolFieldSetIndex.render(schoolForm,
//				State.makeStateMap(studentData),
//				SchoolBoard.makeSchoolBoardMap(studentData),
//				SchoolType.makeSchoolTypeMap(studentData)
//				));
	}

	public Result postStudentRegistrationRequest() {
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).bindFromRequest();
		return ok("school Registration completed");
	}
}
