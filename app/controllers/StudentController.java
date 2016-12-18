package controllers;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.InstituteFormData;

public class StudentController extends CustomController {
	@Inject
	private FormFactory formFactory;

	public Result preStudentRegistrationRequest() {
		InstituteFormData studentData = new InstituteFormData();
		Form<InstituteFormData> schoolForm = formFactory.form(InstituteFormData.class).fill(studentData);
		return ok("");
//		return ok(schoolFieldSetIndex.render(schoolForm,
//				State.makeStateMap(studentData),
//				SchoolBoard.makeSchoolBoardMap(studentData),
//				SchoolType.makeSchoolTypeMap(studentData)
//				));
	}

	public Result postStudentRegistrationRequest() {
		Form<InstituteFormData> schoolForm = formFactory.form(InstituteFormData.class).bindFromRequest();
		return ok("school Registration completed");
	}
}
