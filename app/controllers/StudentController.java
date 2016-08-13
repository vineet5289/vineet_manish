package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.institute.InstituteFormData;

public class StudentController extends CustomController {
	public Result preStudentRegistrationRequest() {
		InstituteFormData studentData = new InstituteFormData();
		Form<InstituteFormData> schoolForm = Form.form(InstituteFormData.class).fill(studentData);
		return ok("");
//		return ok(schoolFieldSetIndex.render(schoolForm,
//				State.makeStateMap(studentData),
//				SchoolBoard.makeSchoolBoardMap(studentData),
//				SchoolType.makeSchoolTypeMap(studentData)
//				));
	}

	public Result postStudentRegistrationRequest() {
		Form<InstituteFormData> schoolForm = Form.form(InstituteFormData.class).bindFromRequest();
		return ok("school Registration completed");
	}
}
