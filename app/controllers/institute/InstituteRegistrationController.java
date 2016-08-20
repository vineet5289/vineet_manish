package controllers.institute;

import java.util.Map;

import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.InstituteRegisterRequestAuthenticator;
import views.forms.OTPField;
import views.forms.institute.InstituteFormData;
import views.html.viewClass.SchoolRegistration;
import controllers.CustomController;
import dao.school.AddNewSchoolRequestDAO;
import dao.school.SchoolRegistrationDAO;
import enum_package.InstituteDaoProcessStatus;
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
			if(schoolData != null && schoolData.getProcessingStatus() == InstituteDaoProcessStatus.validschool) {
				session().clear();
				session(SessionKey.regschoolrequestnumber.name(), referenceKey);
				session(SessionKey.otpkey.name(), otp);
				session(SessionKey.reginstituterequestid.name(), Long.toString(schoolData.getRegisterInstituteRequestId()));

				Form<InstituteFormData> schoolFormData = Form.form(InstituteFormData.class).fill(schoolData);

				return ok(SchoolRegistration.render(schoolFormData));
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
		
		String referenceNumber = session().get(SessionKey.of(SessionKey.regschoolrequestnumber));
		session().remove(SessionKey.of(SessionKey.regschoolrequestnumber));

		String authToken = session().get(SessionKey.of(SessionKey.otpkey));
		session().remove(SessionKey.of(SessionKey.otpkey));

		String regInstituteRequestId = session().get(SessionKey.of(SessionKey.reginstituterequestid));
		session().remove(SessionKey.of(SessionKey.reginstituterequestid));

		SchoolRegistrationDAO schoolRegistrationDAO = new SchoolRegistrationDAO();
		InstituteDaoProcessStatus instituteDaoProcessStatus;
		try {
			instituteDaoProcessStatus = schoolRegistrationDAO.registerInstitute(schoolFormDetails, referenceNumber, authToken, Long.valueOf(regInstituteRequestId));
		} catch(Exception exception) {
			exception.printStackTrace();
			instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
		}

		session().clear();
		if(instituteDaoProcessStatus == InstituteDaoProcessStatus.validschool) {
			flash("success", "School has been successfully registered.");			
		} else {
			flash("error", instituteDaoProcessStatus.name());
		}
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}
}
