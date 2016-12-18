package controllers.institute;

import javax.inject.Inject;

import controllers.CustomController;
import dao.SubjectDAO;
import dao.dao_operation_status.SubjectDaoActionStatus;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.SubjectForm;

public class SubjectController extends CustomController {

  @Inject
  private FormFactory formFactory;
  @Inject
  private SubjectDAO subjectDAO;

  public Result preAddSubjects(long classId, long sectionId, String sec) {
    Form<SubjectForm> subjectForm = formFactory.form(SubjectForm.class);

    return ok("");
  }

  public Result postAddSubjects(long classId, long sectionId, String sec) {
    Form<SubjectForm> subjectForm = formFactory.form(SubjectForm.class).bindFromRequest();
    if (subjectForm == null || subjectForm.hasErrors()) {
      flash("error", "Please check field details");
      return redirect(controllers.institute.routes.ClassController.preAddClass(sec)); 
    }

    SubjectForm subjectsDetails = subjectForm.get();
    if (subjectsDetails == null || classId <= 0) {
      flash("error", "Please check field details");
      return redirect(controllers.institute.routes.ClassController.preAddClass(sec)); 
    }

    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    String userName = "vineet"; //session().get(SessionKey.username.name());
    String instituteIdFromSession = "1";// session().get(SessionKey.SCHOOL_ID.name());
    try {
      long instituteId = Long.parseLong(instituteIdFromSession);
      subjectDaoActionStatus = subjectDAO.add(subjectsDetails, classId, sectionId, instituteId, userName, sec);
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return ok(subjectDaoActionStatus.getValue());
  }

  public Result viewsSubjects(long subjectId) {
    return ok("");
  }

  public Result editSubject(Long subjectId, String action) {
    return ok("");
  }

  public Result deleteSubjects(Long subjectId, long classId, long sectionId, String sec) {
    if(subjectId <= 0 || classId <= 0) {
      flash("error", "Bad request");
    }
    SubjectDaoActionStatus subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    String userName = "vineet"; //session().get(SessionKey.username.name());
    String instituteIdFromSession = "1";// session().get(SessionKey.SCHOOL_ID.name());
    try {
      long instituteId = Long.parseLong(instituteIdFromSession);
      subjectDaoActionStatus = subjectDAO.delete(classId, sectionId, instituteId, subjectId, userName, sec);
    } catch (Exception exception) {
      exception.printStackTrace();
      subjectDaoActionStatus = SubjectDaoActionStatus.serverexception;
    }
    return ok(subjectDaoActionStatus.getValue());
  }
}
