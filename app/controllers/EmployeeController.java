package controllers;

import enum_package.SessionKey;
import play.data.Form;
import play.mvc.Result;
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

		return ok("");
	}

	/*
	 * validate valid user
	 * validate user has access right or not
	 * */
	public Result postAddEmployeeRequest() {
		String superUserName = session().get(SessionKey.SUPER_USER_NAME.name());
		String superUserAuthKey = session().get(SessionKey.SUPER_AUTH_TOKEN.name());
		String superUserSchoolId = session().get(SessionKey.SUPER_SCHOOL_ID.name());
		return ok("");
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
