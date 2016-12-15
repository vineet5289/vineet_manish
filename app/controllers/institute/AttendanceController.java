package controllers.institute;

import play.mvc.Result;
import controllers.CustomController;

public class AttendanceController extends CustomController {

  public Result addAttendance(long classId, long sectionId, String sec) {
    return ok("");
  }

  public Result editAttendance(long classId, long sectionId, String sec) {
    return ok("");
  }

  public Result viewAttendance(long classId, long sectionId, String sec, String viewBy) {
    return ok("");
  }
}
