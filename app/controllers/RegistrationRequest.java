package controllers;

import models.SchoolBoard;
import models.SchoolType;
import models.State;
import play.data.Form;
import play.mvc.Result;
import views.forms.*;
import views.html.*;

public class RegistrationRequest extends CustomController {

	public Result otpLoginRequest() {
		Form<OtpFormData> otpform = Form.form(OtpFormData.class);
		return ok(otpEnter.render(otpform));
	}

	public Result preRegistrationRequest() {
		SchoolFormData studentData = new SchoolFormData();
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).fill(studentData);
		return ok(schoolFieldSetIndex.render(schoolForm,
				State.makeStateMap(studentData),
			      SchoolBoard.makeSchoolBoardMap(studentData),
			      SchoolType.makeSchoolTypeMap(studentData)
				));
		
	}
	
	public Result postRegistrationRequest() {
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).bindFromRequest();
		return ok("school Registration completed");
		
	}
	
	public Result registerPage() {
		return ok(newUser.render());
	}

	public Result preAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class);
		return ok(addNewSchoolRequestIndex.render(addNewSchoolRequest));
	}

	public Result postAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class).bindFromRequest();
		return ok("");
	}
}
