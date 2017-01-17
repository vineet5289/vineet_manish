package controllers.institute;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import controllers.CustomController;
import dao.ClassDAO;
import dao.SubjectDAO;
import dao.employee.EmployesDAO;
import dao.school.TimeTableDao;
import models.ClassModels;
import models.EmployeeModels;
import models.SubjectModels;
import models.TimetableModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.timetable.TimeTableCreateForm;
import views.forms.institute.timetable.TimeTableForm;

public class TimeTableController extends CustomController {
  @Inject
  private FormFactory formFactory;

  @Inject
  private TimeTableDao timeTableDao;
  @Inject
  private EmployesDAO employesDAO;
  @Inject
  private ClassDAO classDAO;
  @Inject
  private SubjectDAO subjectDAO;

  public Result preCreateTimeTable(long instituteId, long classId, long secId, String sec) {
    Form<TimeTableForm> timeTableFormForm = formFactory.form(TimeTableForm.class);
    TimeTableCreateForm timeTableCreateForm = new TimeTableCreateForm();
    try {
      List<EmployeeModels> employees = employesDAO.getAllTeachers(instituteId, "");
      List<SubjectModels> subject = subjectDAO.getSubject(instituteId, classId, secId, sec);
      ClassModels classDetails = classDAO.getActiveClassDetails(instituteId, classId, secId, sec);
      timeTableCreateForm = TimeTableCreateForm.bindData(employees, subject, classDetails);
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return ok(timeTableCreateForm.toString());
  }

  public Result postCreateTimeTable(String section) {
    Form<TimeTableForm> timeTableFormForm = formFactory.form(TimeTableForm.class).bindFromRequest();

    boolean isCreated = false;
    try {
      System.out.println("====> " + timeTableFormForm.get());
      isCreated = timeTableDao.add(timeTableFormForm.get(), section);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ok("timetable created" + isCreated);
  }

  public Result editTimeTable() {
    return ok("");
  }

  public Result viewTimeTable(long classId, long instituteId, long secId, String sec) {
    List<TimetableModel> timetableModel = new ArrayList<TimetableModel>();
    try {
      timetableModel = timeTableDao.get(instituteId, classId, secId, sec);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ok(timetableModel.toString());
  }
}
