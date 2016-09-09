package controllers;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import controllers.routes;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.forms.AccessRightsForm;
import dao.UserLoginDAO;

public class AccessRightsController extends Controller {

	
	@Inject
	private FormFactory formFactory;
	@Inject
	private UserLoginDAO userLoginDAO;

	@Security.Authenticated(ActionAuthenticator.class)
	public Result addAccessRight() {
		Form<AccessRightsForm> accessRightsForm = formFactory.form(AccessRightsForm.class).bindFromRequest();
		if(accessRightsForm == null || accessRightsForm.hasErrors()) {
			flash("error", "please check whether correct user or access right is selected");
			return redirect(controllers.institute.routes.SubjectController.preAddSubjects()); // check for correct redirection
		}

		List<AccessRightsForm.UserAccessRights> userAccessRights = accessRightsForm.get().getAccessRights();
		if(userAccessRights == null || userAccessRights.size() == 0) {
			flash("error", "please check whether correct user or access right is selected");
			return redirect(controllers.institute.routes.SubjectController.preAddSubjects()); // check for correct redirection
		}

		boolean isSuccessful = true;
		try {
			isSuccessful = userLoginDAO.updateUserAccessRight(userAccessRights);
		} catch (SQLException exception) {
			exception.printStackTrace();
			isSuccessful = false;
		}

		if(!isSuccessful) {
			flash("error", "please check whether correct user or access right is selected");
			return redirect(controllers.institute.routes.SubjectController.preAddSubjects()); // check for correct redirection
		}

		return redirect(routes.SRPController.index()); // check for correct redirection
	}
}
