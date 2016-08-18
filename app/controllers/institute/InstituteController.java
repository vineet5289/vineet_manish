package controllers.institute;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import actors.MessageActor;
import actors.SchoolRequestActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import models.Country;
import models.State;
import play.data.Form;
import play.mvc.Result;
import views.forms.institute.AddInstituteBranchForm;
import views.html.viewClass.School.addBranchHeadInstitute;
import controllers.CustomController;
import controllers.routes;
import dao.school.AddBranchDAO;
import enum_package.InstituteDaoProcessStatus;
import enum_package.SessionKey;

public class InstituteController extends CustomController {

	final ActorSystem actorSystem = ActorSystem.create("srp");
	final ActorRef mailerActor;
	final ActorRef messageActor = actorSystem.actorOf(MessageActor.props());
	@Inject
	public InstituteController(@Named("srp") ActorRef mailerActor) {
		this.mailerActor = mailerActor;
	}

	public Result switchBranch() {
		return ok("===> switch branch done");
	}

	public Result preAddInstituteBranch() {
		Form<AddInstituteBranchForm> addInstituteBranchForm = Form.form(AddInstituteBranchForm.class);
		List<String> countries = Country.getCountries();
		return ok(addBranchHeadInstitute.render(addInstituteBranchForm, countries, State.states));
	}

	public Result postAddInstituteBranch() {
		Form<AddInstituteBranchForm> addInstituteBranchForm = Form.form(AddInstituteBranchForm.class).bindFromRequest();
		if(addInstituteBranchForm == null || addInstituteBranchForm.hasErrors()) {
			flash("error", "Some field are missing or invalid. Please try again.");
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		AddInstituteBranchForm addInstituteBranch = addInstituteBranchForm.get();
		if(addInstituteBranch == null) {
			flash("error", "Some field are missing or invalid. Please try again.");
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		String instituteId = session().get(SessionKey.instituteid.name());
		SchoolRequestActorProtocol.AddInstituteBranch  addInstituteBranchProtocol = null;
		try {
			AddBranchDAO addBranchDAO = new AddBranchDAO();
			addInstituteBranchProtocol = addBranchDAO.addBranch(addInstituteBranch, Long.valueOf(instituteId));
		} catch (NumberFormatException | SQLException exception) {
			flash("error", "Some server exception happen during add branch request. Please try again.");
			exception.printStackTrace();
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		if(addInstituteBranchProtocol.getProcessStatus() != InstituteDaoProcessStatus.branchsuccessfullyadded) {
			flash("error", addInstituteBranchProtocol.getProcessStatus().name());
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		mailerActor.tell(addInstituteBranchProtocol, mailerActor);
		messageActor.tell(addInstituteBranchProtocol, messageActor);
		
		return redirect(routes.SRPController.index());
	}
}
