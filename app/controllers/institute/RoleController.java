package controllers.institute;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.RoleForm;
import controllers.CustomController;

public class RoleController extends CustomController {

	@Inject
	private FormFactory formFactory;

	public Result preAddRole() {
		Form<RoleForm> addEmployee = formFactory.form(RoleForm.class);
		return ok("");
	}
}
