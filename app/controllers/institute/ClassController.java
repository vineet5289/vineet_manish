package controllers.institute;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.typesafe.config.ConfigException.Parse;

import controllers.CustomController;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.ClassForm;
import dao.ClassDAO;
import enum_package.SessionKey;
import views.html.viewClass.viewclasses;
import views.html.viewClass.addClasses;


public class ClassController extends CustomController {
  @Inject
  private FormFactory formFactory;
  @Inject
  private ClassDAO classDAO;

  public Result preAddClass(String section) {
    String schoolIdFromSession = "1";// session().get(SessionKey.instituteid.name());
    Form<ClassForm> classForm = formFactory.form(ClassForm.class);
    Map<String, List<ClassForm>> classes = null;
    try {
      long instituteId = Long.parseLong(schoolIdFromSession);
      classes = classDAO.getClasses(instituteId);
    } catch (Exception exception) {
      classes = new HashMap<String, List<ClassForm>>();
    }

    // return classes, classForm
    return ok(addClasses.render(classForm));
  }

  public Result postAddClass(long classId, String section) {
    Form<ClassForm> classForm = formFactory.form(ClassForm.class).bindFromRequest();
    if (classForm == null || classForm.hasErrors()) {
      flash("error", "Some field are missing happen. Please check and submit again.");
      return redirect(controllers.institute.routes.ClassController.preAddClass());
    }

    String userName = session().get(SessionKey.username.name());
    String instituteIdFromSession = "1"; // session().get(SessionKey.instituteid.name());
    ClassForm classFormDetails = classForm.get();
    try {
      long instituteId = Long.parseLong(instituteIdFromSession);
      classDAO.add(classFormDetails, instituteId, userName, section, classId);
    } catch (SQLException exception) {
      flash("error", "Some server exception happen");
      exception.printStackTrace();
    }
    return redirect(controllers.institute.routes.ClassController.preAddClass());
  }

  public Result viewAllClass(String action) {
    Map<String, List<ClassForm>> classes = null;
    String instituteIdFromSession = "1";// session().get(SessionKey.SCHOOL_ID.name());
    try {
      long instituteId = Long.parseLong(instituteIdFromSession);
      classes = classDAO.getClasses(instituteId);
    } catch (Exception exception) {
      classes = new HashMap<String, List<ClassForm>>();
    }
    return ok(viewclasses.render(classes));
  }

  public Result editClass(long classId, String sec, String action) {
    Form<ClassForm> editClassForm = formFactory.form(ClassForm.class).bindFromRequest();
    if (editClassForm == null || editClassForm.hasErrors()) {
      flash("error", "Some server exception happen");
      return redirect(controllers.institute.routes.ClassController.preAddClass());
    }

    ClassForm editClass = editClassForm.get();
    if (editClass == null) {
      flash("error", "Some server exception happen");
      return redirect(controllers.institute.routes.ClassController.preAddClass());
    }

    String instituteIdFromSession = "1";// session().get(SessionKey.SCHOOL_ID.name());
    String userName = session().get(SessionKey.username.name());
    boolean isSuccessful = false;
    try {
      long instituteId = Long.parseLong(instituteIdFromSession);
      isSuccessful = classDAO.editClass(instituteId, classId, userName, editClass, sec, action);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    if (!isSuccessful) {
      flash("warn", "Some server exception happen during deletion. Please try after some time.");
    }
    return redirect(controllers.institute.routes.ClassController.preAddClass());
  }
}
