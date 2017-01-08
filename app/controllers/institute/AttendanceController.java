package controllers.institute;

import javax.inject.Inject;

import controllers.CustomController;
import dao.school.AttendanceDao;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.AttendanceForm;

public class AttendanceController extends CustomController {

  @Inject
  private FormFactory formFactory;
  @Inject private AttendanceDao attendanceDao;

  public Result addAttendance(long classId, long sectionId, String sec) {
    Form<AttendanceForm> attendanceForm = formFactory.form(AttendanceForm.class).bindFromRequest();
    if (attendanceForm == null || attendanceForm.hasErrors()) {
      flash("error", "Some parameters are missing.");
      return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
    }

    AttendanceForm attendanceData = attendanceForm.get();
    boolean isAdded = attendanceDao.add(attendanceData, sec, true);

    return ok("successfully updated");
  }

  public Result editAttendance(long classId, long sectionId, String sec) {
    return ok("");
  }

  public Result viewAttendance(long classId, long sectionId, String sec, String viewBy) {
    return ok("");
  }
}
