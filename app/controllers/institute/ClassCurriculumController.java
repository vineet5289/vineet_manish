package controllers.institute;

import javax.inject.Inject;

import controllers.CustomController;
import dao.school.ClassCurriculumDao;
import play.data.FormFactory;
import play.mvc.Result;

public class ClassCurriculumController extends CustomController {
  @Inject
  private FormFactory formFactory;
  @Inject
  private ClassCurriculumDao classCurriculumDao;

  public Result checkSlotAvailable(long profId, long instituteId, String startTime, String endTime) {
    boolean isValid = false;
    try {
      System.out.println("profId:" + profId + ", instituteId:" + instituteId + ", startTime:" + startTime + ", endTime:" + endTime);
      isValid = classCurriculumDao.isProfessorFreeGivenTimeRange(profId, startTime, endTime, instituteId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if(isValid)
      return ok("yes");
    else
      return ok("no");
  }
}
