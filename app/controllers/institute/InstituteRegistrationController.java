package controllers.institute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.CustomController;
import models.SchoolBoard;
import models.SchoolCategory;
import models.SchoolType;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.InstituteRegisterRequestAuthenticator;
import views.forms.OTPField;
import views.forms.institute.InstituteFormData;
import views.html.viewClass.SchoolRegistration;
import dao.school.AddNewSchoolRequestDAO;
import dao.school.SchoolRegistrationDAO;
import enum_package.SessionKey;

public class InstituteRegistrationController extends CustomController {
	public Result submitOTP() {
		session().clear();
		Form<OTPField> otpForm = Form.form(OTPField.class).bindFromRequest();
		if(otpForm == null || otpForm.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in request. Please check and enter valid value");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());// same otp page call
		}
		Map<String, String> otpFieldsValues = otpForm.data();
		if(otpFieldsValues == null || otpFieldsValues.size() == 0) {
			flash("error", "Something parameter is missing or invalid in request. Please check and enter valid value");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());// same otp page call
		}

		String referenceKey = otpFieldsValues.get("referenceKey");
		String otp = otpFieldsValues.get("otp");
		String emailId = otpFieldsValues.get("emailId");

		AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
		try {
			InstituteFormData schoolData = schoolRegistrationRequestDAO.isValidSchoolRegistrationRequest(referenceKey, otp, emailId);
			if(schoolData != null) {
				session(SessionKey.REG_SCHOOL_REQUEST_NUMBER.name(), referenceKey);
				session(SessionKey.OTP_KEY.name(), otp);

				Form<InstituteFormData> schoolFormData = Form.form(InstituteFormData.class).fill(schoolData);
				Map<String, String> schoolBoards = new HashMap<String, String>();

				schoolBoards.put("CBSE", "CBSE");
				schoolBoards.put("ICSE", "ICSE");
				schoolBoards.put("IB", "International Baccalaureate");
				
				String affiliatedTo = schoolData.getInstituteState().trim().toUpperCase();
				String otherBoard = SchoolBoard.getDisplayNameGivenAffiliatedTo(affiliatedTo);
				schoolBoards.put(affiliatedTo, otherBoard);

				List<String> schoolCategory = SchoolCategory.getSchoolCategoryList();
				return ok(SchoolRegistration.render(schoolFormData, schoolBoards, schoolCategory, SchoolType.schoolTypeToValue));
			} else {
				flash("error", "Your reference number or otp or email id is invalid. Please check and try again.");
				return redirect(controllers.login_logout.routes.LoginController.preLogin()); // check here for proper redirection
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		flash("error", "We Are Sorry! Server exception occur. Please try after sometime.");
		return redirect(controllers.login_logout.routes.LoginController.preLogin()); // check here for proper redirection
	}

	@Security.Authenticated(InstituteRegisterRequestAuthenticator.class)
	public Result postInstituteRegistrationRequest() {
		Form<InstituteFormData> schoolForm = Form.form(InstituteFormData.class).bindFromRequest();
		if(schoolForm == null || schoolForm.hasErrors()) {
			System.out.println(schoolForm.errors());
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		InstituteFormData schoolFormDetails = schoolForm.get();
		if(schoolFormDetails == null) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}
		
		String referenceNumber = session().get(SessionKey.REG_SCHOOL_REQUEST_NUMBER.name());
		String authToken = session().get(SessionKey.OTP_KEY.name());
		SchoolRegistrationDAO schoolRegistrationDAO = new SchoolRegistrationDAO();
		boolean isSuccessfull = false;
		try {
			isSuccessfull = schoolRegistrationDAO.registerSchool(schoolFormDetails, referenceNumber, authToken);
		} catch(Exception exception) {
			exception.printStackTrace();
		}

		session().clear();
		if(isSuccessfull) {
			flash("success", "School has been successfully registered. Please use your username and password for login");			
		} else {
			flash("error", "Sorry!! something error occur registration request. Please try after sometime.");
		}
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
