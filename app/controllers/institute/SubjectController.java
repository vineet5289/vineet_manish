package controllers.institute;

import java.util.List;

import javax.inject.Inject;

import controllers.CustomController;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.SubjectForm;
import views.html.test;
import dao.SubjectDAO;
import enum_package.SessionKey;

public class SubjectController extends CustomController {

  @Inject
  private FormFactory formFactory;
  @Inject
  private SubjectDAO subjectDAO;

  public Result preAddSubjects() {
    Form<SubjectForm> subjectForm = formFactory.form(SubjectForm.class);
    return ok(test.render(subjectForm));
  }

  public Result postAddSubjects() {
    Form<SubjectForm> subjectForm = formFactory.form(SubjectForm.class).bindFromRequest();
    if (subjectForm == null || subjectForm.hasErrors()) {
      flash("error", "Please check field details");
      return redirect(controllers.institute.routes.SubjectController.preAddSubjects()); 
    }

    String userName = session().get(SessionKey.username.name());
    String schoolIdFromSession = "1";// session().get(SessionKey.SCHOOL_ID.name());
    SubjectForm subjectsDetails = subjectForm.get();
    long schoolId = -1l;
    try {
      schoolId = Long.parseLong(schoolIdFromSession);
    } catch (Exception exception) {
      flash("error", "Some server exception happen");
      return redirect(controllers.login_logout.routes.LoginController.preLogin()); // check for
                                                                                   // correct
                                                                                   // redirection
    }

    Long classId = 1L;// subjectsDetails.getClassId();
    boolean isSuccessful = false;
    try {
//      isSuccessful = subjectDAO.addSubjects(classId, schoolId, subjects, userName);
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return ok("true");
  }

  public Result viewsSubjects() {
    return ok("");
  }

  public Result editSubject(Long subjectId) {
    return ok("");
  }

  public Result deleteSubjects(Long subjectId) {
    return ok("");
  }
}
