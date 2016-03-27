package controllers.school;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.mvc.Result;
import views.forms.school.ClassForm;
import views.forms.school.DisplayClassForm;
import controllers.CustomController;
import dao.ClassDAO;
import enum_package.SessionKey;
import views.html.addClass;

public class ClassController extends CustomController {
	public Result preAddClass() {
		Form<ClassForm> classForm = Form.form(ClassForm.class);
		return ok(addClass.render(classForm));
	}

	public Result postAddClass() {
		Form<ClassForm> classForm = Form.form(ClassForm.class).bindFromRequest();
		if (classForm == null || classForm.hasErrors()) {
			flash("error", "Some server exception happen");
			return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
		}
		else {
			String userName = session().get(SessionKey.USER_NAME.name());
			String schoolIdFromSession = session().get(SessionKey.SCHOOL_ID.name());
			ClassForm classFormDetails = classForm.get();
			long schoolId = -1l;
			try {
				schoolId = Long.parseLong(schoolIdFromSession);
			} catch(Exception exception) {
				flash("error", "Some server exception happen");
				return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
			}
			List<ClassForm.AddClass> classes= classFormDetails.getClasses();
			ClassDAO classDAO = new ClassDAO();
			try {
				classDAO.addClass(classes, schoolId, userName);
			} catch (SQLException exception) {
				return redirect(controllers.school.routes.ClassController.preAddClass());
			}
		}
		return ok("class added"); // return to profile page
	}

	public Result viewAllClass() {
		ClassDAO classDAO = new ClassDAO();
		try {
			String schoolIdFromSession = session().get(SessionKey.SCHOOL_ID.name());
			long schoolId = -1l;
			try {
				schoolId = Long.parseLong(schoolIdFromSession);
			} catch(Exception exception) {
				flash("error", "Some server exception happen");
				return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
			}
			Map<String, List<DisplayClassForm>> classes = classDAO.getClass(schoolId);
			if(classes == null || classes.size() == 0) {
				flash("warn", "There are no classes to display.");
			}

			return ok(classes+"");
		} catch (SQLException exception) {
			redirect(routes.school.ClassController.preAddClass());
		}
	}

	public Result editClass() {
//		ClassDAO classDAO = new ClassDAO();
//		try {
//			classDAO.addClass(classes, schoolId, userName);
//		} catch (SQLException exception) {
//			redirect(routes.school.ClassController.preAddClass());
//		}
		return ok("");
	}

	public Result deleteClass() {
//		ClassDAO classDAO = new ClassDAO();
//		try {
//			classDAO.addClass(classes, schoolId, userName);
//		} catch (SQLException exception) {
//			redirect(routes.school.ClassController.preAddClass());
//		}
		return ok("");
	}
}
