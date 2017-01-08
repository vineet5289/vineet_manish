package controllers.institute;

import java.sql.SQLException;

import javax.inject.Inject;

import controllers.CustomController;
import dao.school.TimeTableDao;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.timetable.TimeTableForm;

public class TimeTableController extends CustomController {
  @Inject
  private FormFactory formFactory;

  @Inject
  private TimeTableDao timeTableDao;

  public Result preCreateTimeTable() {
    long instituteId = 1l;
    long classId = 1l;
    long sectionId = 1l;
    String classStartTime = "9:20 AM";
    String classEndTime = "03:00 PM";
    int duration = 40;
    return ok("");
  }

  public Result postCreateTimeTable(String section) {
    Form<TimeTableForm> timeTableFormForm = formFactory.form(TimeTableForm.class).bindFromRequest();
    boolean isCreated = false;
    try {
      isCreated = timeTableDao.add(timeTableFormForm.get(), section);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ok("timetable created" + isCreated);
  }

  public Result editTimeTable() {
    return ok("");
  }

  public Result viewTimeTable(long classId, long secId, String sec) {
//    TimeTableForm timeTableData = timeTableDao.view(classId, secId, sec);
    return ok("");
  }
}
