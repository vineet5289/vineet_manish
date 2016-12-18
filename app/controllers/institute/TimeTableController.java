package controllers.institute;

import controllers.CustomController;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.TimeTableForm;

import javax.inject.Inject;

public class TimeTableController extends CustomController {
  @Inject
  private FormFactory formFactory;

  public Result preCreateTimeTable() {
    long instituteId = 1l;
    long classId = 1l;
    long sectionId = 1l;
    String classStartTime = "9:20 AM";
    String classEndTime = "03:00 PM";
    int duration = 40;
    return ok("");
  }

  public Result postCreateTimeTable() {
    Form<TimeTableForm> timeTableFormForm = formFactory.form(TimeTableForm.class).bindFromRequest();
    System.out.println("timeTableFormForm:" + timeTableFormForm);
    return ok("timetable created");
  }

  public Result editTimeTable() {
    return ok("");
  }

  public Result viewTimeTable() {
    return ok("");
  }
}
