package controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import controllers.routes;
import models.Country;
import models.SchoolBoard;
import models.SchoolCategory;
import models.SchoolType;
import models.State;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import security.SchoolRegisterRequestAuthenticator;
import views.forms.AddNewSchoolRequest;
import views.forms.OTPField;
import views.forms.SchoolFormData;
import views.html.newSchoolApproved;
import views.html.viewClass.SchoolRegistration;
import views.html.viewClass.newSchoolRequest;
import views.html.viewClass.thanku;
import actors.MessageActor;
import actors.SchoolRequestActorProtocol.ApprovedSchool;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import dao.SchoolRegistrationDAO;
import dao.AddNewSchoolRequestDAO;
import enum_package.SessionKey;


public class SchoolController extends CustomController {

	final ActorSystem actorSystem = ActorSystem.create("srp");
	final ActorRef mailerActor;
	final ActorRef messageActor = actorSystem.actorOf(MessageActor.props());
	@Inject
	public SchoolController(@Named("srp") ActorRef mailerActor) {
		this.mailerActor = mailerActor;
	}

	public Result preAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class);
		List<String> countries = Country.getCountries();
		List<String> states = State.getStates();
		return ok(newSchoolRequest.render(addNewSchoolRequest, states, countries));
	}

	public Result postAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class).bindFromRequest();
		if(addNewSchoolRequest == null || addNewSchoolRequest.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		Map<String, String> addNewSchoolRequestDetails = addNewSchoolRequest.data();
		if(addNewSchoolRequestDetails == null || addNewSchoolRequestDetails.isEmpty()) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
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

		String schoolEmailId = addNewSchoolRequestDetails.get("schoolEmail");
		String receiverPhoneNumber = addNewSchoolRequestDetails.get("schoolMobileNumber");
		String receiverName = addNewSchoolRequestDetails.get("schoolName");

		NewSchoolRequest newSchoolRequest = new NewSchoolRequest();
		newSchoolRequest.setSchoolEmailId(schoolEmailId);
		newSchoolRequest.setSchoolName(receiverName);
		newSchoolRequest.setSchoolPhoneNumber(receiverPhoneNumber);
		newSchoolRequest.setReferenceNumber(requestRefNumber);
		mailerActor.tell(newSchoolRequest, mailerActor);
		messageActor.tell(newSchoolRequest, messageActor);
		return ok(thanku.render(requestRefNumber)); // generate proper html page and show reference number and message
	}

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
			SchoolFormData schoolData = schoolRegistrationRequestDAO.isValidSchoolRegistrationRequest(referenceKey, otp, emailId);
			if(schoolData != null && schoolData.isValidSchool()) {
				session(SessionKey.REG_SCHOOL_REQUEST_NUMBER.name(), referenceKey);
				session(SessionKey.OTP_KEY.name(), otp);

				Form<SchoolFormData> schoolFormData = Form.form(SchoolFormData.class);
				schoolFormData.fill(schoolData);
				List<String> schoolBoards = SchoolBoard.getSchoolboardList();
				List<String> schoolCategory = SchoolCategory.getSchoolCategoryList();
				List<String> schoolType = SchoolType.getSchoolTypeList();				
				return ok(SchoolRegistration.render(schoolFormData, schoolBoards, schoolCategory, schoolType));
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

	@Security.Authenticated(SchoolRegisterRequestAuthenticator.class)
	public Result postSchoolRegistrationRequest() {
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).bindFromRequest();
		if(schoolForm == null || schoolForm.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		SchoolFormData schoolFormDetails = schoolForm.get();
		if(schoolFormDetails == null) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}
		
		String referenceNumber = session().get(SessionKey.REG_SCHOOL_REQUEST_NUMBER.name());
		String authToken = session().get(SessionKey.OTP_KEY.name());
		SchoolRegistrationDAO schoolRegistrationDAO = new SchoolRegistrationDAO();
		try {
			boolean isSuccessfull = schoolRegistrationDAO.registerSchool(schoolFormDetails, referenceNumber, authToken);
		} catch(Exception exception) {
			System.out.println("exception &&&&&&&&&");
			exception.printStackTrace();
		}
		session().clear();
		return redirect(controllers.login_logout.routes.LoginController.preLogin());

	}

	public Result preApprovedNewSchoolRequest() {
		AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
		try {
			List<models.NewSchoolApprovedRequest> schools = schoolRegistrationRequestDAO.getAllSchoolNeedToBeApproved();
			return ok(newSchoolApproved.render(schools));
		} catch (Exception exception) {
			flash("error", "Something wrong happen with our server. Please try again.");
			exception.printStackTrace();
			return badRequest(); // need to be decide
		}
	}

	public Result postApproveNewSchooldRequest() {
		Form<views.forms.NewSchoolApprovedRequest> newSchoolApprovedRequest = Form.form(views.forms.NewSchoolApprovedRequest.class).bindFromRequest();
		if(newSchoolApprovedRequest == null || newSchoolApprovedRequest.hasErrors()) {
			flash("error", "Referess page and try it again.");
			return redirect(routes.RegistrationRequest.preApprovedNewSchoolRequest());
		}

		Map<String, String> newSchoolApprovedRequestDetails = newSchoolApprovedRequest.data();
		if(newSchoolApprovedRequestDetails == null || newSchoolApprovedRequestDetails.isEmpty()) {
			flash("error", "Referess page and try it again.");
			return redirect(routes.RegistrationRequest.preApprovedNewSchoolRequest()); // same page
		}

		AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
		long id = Long.parseLong(newSchoolApprovedRequestDetails.get("id"));
		String referenceNumber = newSchoolApprovedRequestDetails.get("requestNumber");
		try {

			ApprovedSchool approvedSchool = schoolRegistrationRequestDAO.approved(referenceNumber, id);
			messageActor.tell(approvedSchool, messageActor);
		} catch (Exception exception) {
			flash("error", "Referess page and try it again.");
			exception.printStackTrace();
			return redirect(routes.RegistrationRequest.preApprovedNewSchoolRequest());
		}
		
		return ok("approved");
	}
}
