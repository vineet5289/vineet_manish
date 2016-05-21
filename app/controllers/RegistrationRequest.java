package controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.Country;
import models.SchoolBoard;
import models.SchoolCategory;
import models.SchoolType;
import models.State;
import play.data.Form;
import play.libs.mailer.MailerClient;
import play.mvc.Result;
import play.mvc.Security;
import security.SchoolRegisterRequestAuthenticator;
import views.forms.AddNewSchoolRequest;
import views.forms.NewSchoolApprovedRequest;
import views.forms.OTPField;
import views.forms.SchoolFormData;
import views.html.newSchoolApproved;
import views.html.schoolFieldSetIndex;
import views.html.addNewSchoolRequestIndex;
import views.html.viewClass.newSchoolRequest;
import views.html.viewClass.SchoolRegistration;
import actors.MailerActor;
import actors.MessageActor;
import actors.SchoolRequestActorProtocol.ApprovedSchool;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import dao.SchoolRegistrationDAO;
import dao.SchoolRegistrationRequestDAO;
import enum_package.SessionKey;


public class RegistrationRequest extends CustomController {

	
//	@Inject MailerClient mailerClient;
	final ActorSystem actorSystem = ActorSystem.create("srp");
//	final ActorRef mailerActor = actorSystem.actorOf(MailerActor.props(mailerClient));
	final ActorRef messageActor = actorSystem.actorOf(MessageActor.props());
	
	public Result preAddNewSchoolRequest() {
		Form<AddNewSchoolRequest> addNewSchoolRequest = Form.form(AddNewSchoolRequest.class);
		return ok(newSchoolRequest.render(addNewSchoolRequest));
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
		//send mail
		String receiverEmailId = addNewSchoolRequestDetails.get("principalEmail");
		String receiverPhoneNumber = addNewSchoolRequestDetails.get("contact");
		String receiverName = addNewSchoolRequestDetails.get("principalName");
		NewSchoolRequest newSchoolRequest = new NewSchoolRequest();
		newSchoolRequest.setReceiverEmailId(receiverEmailId);
		newSchoolRequest.setReceiverName(receiverName);
		newSchoolRequest.setReceiverPhoneNumber(receiverPhoneNumber);
		newSchoolRequest.setReferenceNumber(requestRefNumber);
		System.out.println("********postAddNewSchoolRequest 3" );
//		mailerActor.tell(newSchoolRequest, mailerActor);
		System.out.println("********postAddNewSchoolRequest 6" );
		messageActor.tell(newSchoolRequest, messageActor);
		
		System.out.println("********postAddNewSchoolRequest 7" );
		return ok(requestRefNumber); // generate proper html page and show reference number and message
	}

	public Result submitOTP() {
		session().clear();
		Form<OTPField> otp = Form.form(OTPField.class).bindFromRequest();
		if(otp == null || otp.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in request. Please check and enter valid value");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}
		Map<String, String> otpValues = otp.data();
		if(otpValues == null || otpValues.size() == 0) {
			flash("error", "Something parameter is missing or invalid in request. Please check and enter valid value");
			return redirect(controllers.login_logout.routes.LoginController.preLogin());
		}

		String referenceKey = otpValues.get("referenceKey");
		String otpValue = otpValues.get("otp");
		SchoolRegistrationRequestDAO schoolRegistrationRequestDAO = new SchoolRegistrationRequestDAO();
		try {
			boolean isValidUser = schoolRegistrationRequestDAO.isValidUserByOtpAndReferenceKey(referenceKey, otpValue);
			System.out.println("isValidUser" + isValidUser);
			if(isValidUser) {
				session("REFERENCE-NUMBER", referenceKey);
				session("AUTH-TOKEN", otpValue);
				Form<SchoolFormData> schoolFormData = Form.form(SchoolFormData.class);
				List<String> schoolBoards = SchoolBoard.getSchoolboardList();
				List<String> schoolCategory = SchoolCategory.getSchoolCategoryList();
				List<String> schoolType = SchoolType.getSchoolTypeList();
				List<String> countries = Country.getCountries();
				List<String> states = State.getStates();
				
				return ok(SchoolRegistration.render(schoolFormData, schoolBoards, schoolCategory, 
						schoolType, countries, states));
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		flash("error", "Your reference number or otp is invalid. Please check and enter again.");
		return redirect(controllers.login_logout.routes.LoginController.preLogin());
	}

	@Security.Authenticated(SchoolRegisterRequestAuthenticator.class)
	public Result postSchoolRegistrationRequest() {
		Form<SchoolFormData> schoolForm = Form.form(SchoolFormData.class).bindFromRequest();
		if(schoolForm == null || schoolForm.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}

		SchoolFormData schoolFormDetails = schoolForm.get();
		if(schoolFormDetails == null) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(routes.RegistrationRequest.preAddNewSchoolRequest());
		}
		
		String referenceNumber = session().get("REFERENCE-NUMBER");
		String authToken = session().get("AUTH-TOKEN");
		SchoolRegistrationDAO schoolRegistrationDAO = new SchoolRegistrationDAO();
		try {
			boolean isSuccessfull = schoolRegistrationDAO.registerSchool(schoolFormDetails);
		} catch(Exception exception) {
			System.out.println("exception &&&&&&&&&");
		}

		return ok("school Registration completed. please login using your user name and password");

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

	public Result preApprovedNewSchoolRequest() {
		SchoolRegistrationRequestDAO schoolRegistrationRequestDAO = new SchoolRegistrationRequestDAO();
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

		SchoolRegistrationRequestDAO schoolRegistrationRequestDAO = new SchoolRegistrationRequestDAO();
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
