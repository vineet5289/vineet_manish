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
import dao.dao_operation_status.SubjectDaoActionStatus;
import enum_package.SessionKey;

public class SubjectController extends CustomController {

  @Inject
  private FormFactory formFactory;
  @Inject
  private SubjectDAO subjectDAO;

  public Result preAddSubjects(Long classId, String sec) {
    Form<SubjectForm> subjectForm = formFactory.form(SubjectForm.class);
    // pass class ID and sec information from here
    return ok(test.render(subjectForm));
  }

  public Result postAddSubjects(Long classId, String sec) {
    Form<SubjectForm> subjectForm = formFactory.form(SubjectForm.class).bindFromRequest();
    if (subjectForm == null || subjectForm.hasErrors()) {
      flash("error", "Please check field details");
   // TODO: pass is to view class dashboard
      return redirect(controllers.institute.routes.SubjectController.preAddSubjects()); 
    }

    SubjectForm subjectsDetails = subjectForm.get();
    if (subjectsDetails == null || classId <= 0) {
      // TODO: pass is to view class dashboard
      flash("error", "Please check field details");
      return redirect(controllers.institute.routes.SubjectController.preAddSubjects()); 
    }

    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    String userName = session().get(SessionKey.username.name());
    String instituteIdFromSession = "1";// session().get(SessionKey.SCHOOL_ID.name());
    try {
      long instituteId = Long.parseLong(instituteIdFromSession);
      subjectDaoActionStatus = subjectDAO.addSubjects(subjectsDetails, classId, userName, instituteId);
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return ok("true");
  }

  public Result viewsSubjects() {
    return ok("");
  }

  public Result editSubject(Long subjectId, String action) {
    return ok("");
  }

  public Result deleteSubjects(Long subjectId) {
    return ok("");
  }
}
