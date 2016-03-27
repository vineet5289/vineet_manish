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
		Map<String, List<DisplayClassForm>> classes = null; 
		try {
			String schoolIdFromSession = session().get(SessionKey.SCHOOL_ID.name());
			long schoolId = -1l;
			try {
				schoolId = Long.parseLong(schoolIdFromSession);
			} catch(Exception exception) {
				flash("error", "Some server exception happen");
				return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
			}
			classes = classDAO.getClass(schoolId);
		} catch (SQLException exception) {
			redirect(controllers.school.routes.ClassController.preAddClass());
		}

		if(classes == null || classes.size() == 0) {
			flash("warn", "There are no classes to display.");
		}

		return ok(classes+"");
	}

	public Result editClass(String className) {
		Form<DisplayClassForm> editClassForm = Form.form(DisplayClassForm.class).bindFromRequest();
		String schoolIdFromSession = session().get(SessionKey.SCHOOL_ID.name());
		String userName = session().get(SessionKey.USER_NAME.name());

		if(editClassForm == null || editClassForm.hasErrors()
				|| className == null || className.isEmpty()
				|| schoolIdFromSession == null || schoolIdFromSession.isEmpty()) {
			flash("error", "Some server exception happen");
			return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
		}

		long schoolId = -1l;
		
		try {
			schoolId = Long.parseLong(schoolIdFromSession);
		} catch(Exception exception) {
			flash("error", "Some server exception happen");
			return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
		}
		ClassDAO classDAO = new ClassDAO();
		DisplayClassForm editClass = editClassForm.get();
		if(editClass == null) {
			flash("error", "Some server exception happen");
			return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
		}

		boolean isSuccessful = false;
		try {
			isSuccessful = classDAO.editClass(schoolId, userName, editClass);
		} catch (SQLException exception) {
			exception.printStackTrace();
			redirect(controllers.school.routes.ClassController.preAddClass());
		}
		if(!isSuccessful) {
			flash("warn", "Some server exception happen during deletion. Please try after some time.");
			//redirect to particular page
		}
		return ok("class updated successful"); //redirect to particular page
	}

	public Result deleteClass(String classsName) {
		String schoolIdFromSession = session().get(SessionKey.SCHOOL_ID.name());
		long schoolId = -1l;
		try {
			schoolId = Long.parseLong(schoolIdFromSession);
		} catch(Exception exception) {
			flash("error", "Some server exception happen");
			return redirect(controllers.routes.SRPController.preLogin()); // check for correct redirection
		}
		ClassDAO classDAO = new ClassDAO();
		Map<String, List<DisplayClassForm>> classes = null;

		boolean isSuccessfull = false;
		if(classsName == null || classsName.isEmpty()) {
			isSuccessfull = false;
		} else {
			try {
				isSuccessfull = classDAO.deleteClass(schoolId, classsName);
			} catch (SQLException exception) {
				exception.printStackTrace();
				redirect(controllers.school.routes.ClassController.preAddClass());
			}
		}
		try {
			classes = classDAO.getClass(schoolId);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		if(!isSuccessfull)
			flash("warn", "Some server exception happen during deletion. Please try after some time.");
		if(classes == null || classes.size() == 0) {
			flash("warn", "There are no classes to display.");
		}
		return ok(classes+"");
	}
}
