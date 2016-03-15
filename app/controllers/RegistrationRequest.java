package controllers;

import java.util.Map;

import dao.SchoolRegistrationRequestDAO;
import play.data.Form;
import play.mvc.Result;
import views.forms.*;
import views.html.*;

public class RegistrationRequest extends CustomController {

	public Result registrationRequest() {
//		Form<RegisterSchool> registerSchoolForm = Form.form(RegisterSchool.class);
		return redirect(routes.RegistrationRequest.newUser());
	}

	public Result postRegistrationRequest() {
		return ok("done");
	}
	
	public Result newUser() {
		Form<AddNewSchoolRequest> registerSchoolForm = Form.form(AddNewSchoolRequest.class);
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class);
		return ok(newUser.render(schoolForm, registerSchoolForm));
	}

	public Result preAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class);
		return ok(addNewSchoolRequestIndex.render(addNewSchoolRequest));
	}

	public Result postAddNewSchoolRequest() {
		System.out.println("post********* 1");
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class).bindFromRequest();
		if(addNewSchoolRequest == null || addNewSchoolRequest.hasErrors()) {
			flash("error", "Something wrong with your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		System.out.println("post********* 2");
		Map<String, String> addNewSchoolRequestDetails = addNewSchoolRequest.data();
		if(addNewSchoolRequestDetails == null || addNewSchoolRequestDetails.isEmpty()) {
			flash("error", "Something wrong with your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		SchoolRegistrationRequestDAO schoolRegistrationRequestDAO = new SchoolRegistrationRequestDAO();
		String requestRefNumber = "";
		try {
			requestRefNumber = schoolRegistrationRequestDAO.generateRequest(addNewSchoolRequestDetails);
		} catch (Exception exception) {
			flash("error", "Something wrong happen with our server. Please try again.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		if(requestRefNumber == null || requestRefNumber.isEmpty()) {
			flash("error", "Something wrong happen with our server. Please try again.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		return ok(requestRefNumber);
	}
}
