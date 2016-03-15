package controllers;

import java.util.List;
import java.util.Map;

import models.SchoolBoard;
import models.SchoolCategory;
import models.SchoolType;
import models.State;
import play.data.Form;
import play.mvc.Result;
import views.forms.AddNewSchoolRequest;
import views.forms.SchoolFormData;
import dao.SchoolRegistrationRequestDAO;
import views.html.addNewSchoolRequestIndex;
import views.html.schoolFieldSetIndex;
import enum_package.SchoolCateroryEnum;
import enum_package.SchoolTypeEnum;

public class RegistrationRequest extends CustomController {

	public Result preAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class);
		return ok(addNewSchoolRequestIndex.render(addNewSchoolRequest));
	}

	public Result postAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class).bindFromRequest();
		if(addNewSchoolRequest == null || addNewSchoolRequest.hasErrors()) {
			flash("error", "Something wrong with your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

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

	public Result preSchoolRegistrationRequest() {

		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class);
		
		Map<Long, String> states = State.getStateList();
//		Map<Long, String> schoolBoards = SchoolBoard.getSchoolboardList();
//		Map<Long, String> schoolTypes = SchoolType.getSchoolTypeList();
//		Map<Long, String> schoolCategories = SchoolCategory.getSchoolCategoryList();

		return ok(schoolFieldSetIndex.render(schoolForm,
				states));
//				schoolBoards,
//				schoolTypes,
//				schoolCategories
//				));
	}

	public Result postSchoolRegistrationRequest() {
		System.out.println("************* 1");
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).bindFromRequest();
		System.out.println("************* 2");
		SchoolFormData schoolCategory = schoolForm.get();
		System.out.println("************* 3");
		System.out.println(schoolCategory.getSchoolCategory());
		return ok("school Registration completed");

	}

	public Result preEmployeeRegistrationRequest() {
		SchoolFormData studentData = new SchoolFormData();
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).fill(studentData);
		ok("");
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

	public Result preStudentRegistrationRequest() {
		SchoolFormData studentData = new SchoolFormData();
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).fill(studentData);
		ok("");
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
