package controllers.institute;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import models.Country;
import models.State;
import play.data.Form;
import play.mvc.Result;
import views.forms.institute.AddNewInstituteRequest;
import views.html.viewClass.newSchoolRequest;
import views.html.viewClass.thanku;
import actors.MessageActor;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import controllers.CustomController;
import dao.school.AddNewSchoolRequestDAO;


public class NewInstituteRequestController extends CustomController {

	final ActorSystem actorSystem = ActorSystem.create("srp");
	final ActorRef mailerActor;
	final ActorRef messageActor = actorSystem.actorOf(MessageActor.props());
	@Inject
	public NewInstituteRequestController(@Named("srp") ActorRef mailerActor) {
		this.mailerActor = mailerActor;
	}

	public Result preAddNewInstituteRequest() {
		Form<AddNewInstituteRequest> addNewSchoolRequest = Form.form(AddNewInstituteRequest.class);
		List<String> countries = Country.getCountries();
		return ok(newSchoolRequest.render(addNewSchoolRequest, countries, State.states));
	}

	public Result postAddNewInstituteRequest() {
		Form<AddNewInstituteRequest> addNewSchoolRequestFrom = Form.form(AddNewInstituteRequest.class).bindFromRequest();
		System.out.println(addNewSchoolRequestFrom);
		if(addNewSchoolRequestFrom == null || addNewSchoolRequestFrom.hasErrors()) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(controllers.institute.routes.NewInstituteRequestController.preAddNewInstituteRequest());
		}

		AddNewInstituteRequest addNewSchoolRequest = addNewSchoolRequestFrom.get();
		if(addNewSchoolRequest == null) {
			flash("error", "Something parameter is missing or invalid in your registration request.");
			return redirect(controllers.institute.routes.NewInstituteRequestController.preAddNewInstituteRequest());
		}

		String requestRefNumber = "";
		try {
			AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
			requestRefNumber = schoolRegistrationRequestDAO.generateRequest(addNewSchoolRequest);
		} catch (Exception exception) {
			flash("error", "Something wrong happen with our server. Please try again.");
			return redirect(controllers.institute.routes.NewInstituteRequestController.preAddNewInstituteRequest());
		}

		if(requestRefNumber == null || requestRefNumber.isEmpty()) {
			flash("error", "Something wrong happen with our server. Please try again.");
			return redirect(controllers.institute.routes.NewInstituteRequestController.preAddNewInstituteRequest());
		}

		String schoolEmailId = addNewSchoolRequest.getEmail();
		String receiverPhoneNumber = addNewSchoolRequest.getPhoneNumber();
		String receiverName = addNewSchoolRequest.getName();

		NewSchoolRequest newSchoolRequest = new NewSchoolRequest();
		newSchoolRequest.setSchoolEmailId(schoolEmailId);
		newSchoolRequest.setSchoolName(receiverName);
		newSchoolRequest.setSchoolPhoneNumber(receiverPhoneNumber);
		newSchoolRequest.setReferenceNumber(requestRefNumber);
		mailerActor.tell(newSchoolRequest, mailerActor);
		messageActor.tell(newSchoolRequest, messageActor);
		return ok(thanku.render(requestRefNumber)); // generate proper html page and show reference number and message
	}

//	public Result preApprovedNewSchoolRequest() {
//		AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
//		try {
//			List<NewSchoolApprovedRequest> schools = schoolRegistrationRequestDAO.getAllSchoolNeedToBeApproved();
//			return ok(newSchoolApproved.render(schools));
//		} catch (Exception exception) {
//			flash("error", "Something wrong happen with our server. Please try again.");
//			exception.printStackTrace();
//			return badRequest(); // need to be decide
//		}
//	}

//	public Result postApproveNewSchooldRequest() {
//		Form<NewSchoolApprovedRequest> newSchoolApprovedRequest = Form.form(NewSchoolApprovedRequest.class).bindFromRequest();
//		if(newSchoolApprovedRequest == null || newSchoolApprovedRequest.hasErrors()) {
//			flash("error", "Referess page and try it again.");
//			return redirect(routes.RegistrationRequest.preApprovedNewSchoolRequest());
//		}
//
//		Map<String, String> newSchoolApprovedRequestDetails = newSchoolApprovedRequest.data();
//		if(newSchoolApprovedRequestDetails == null || newSchoolApprovedRequestDetails.isEmpty()) {
//			flash("error", "Referess page and try it again.");
//			return redirect(routes.RegistrationRequest.preApprovedNewSchoolRequest()); // same page
//		}
//
//		AddNewSchoolRequestDAO schoolRegistrationRequestDAO = new AddNewSchoolRequestDAO();
//		long id = newSchoolApprovedRequestDetails.get("id");
//		String referenceNumber = newSchoolApprovedRequestDetails.get("requestNumber");
//		try {
//			ApprovedSchool approvedSchool = schoolRegistrationRequestDAO.approved(referenceNumber, id);
//			mailerActor.tell(newSchoolRequest, mailerActor);
//			messageActor.tell(approvedSchool, messageActor);
//		} catch (Exception exception) {
//			flash("error", "Referess page and try it again.");
//			exception.printStackTrace();
//			return redirect(routes.RegistrationRequest.preApprovedNewSchoolRequest());
//		}
//		
//		return ok("approved");
//	}
}
