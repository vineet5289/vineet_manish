package views.forms.institute.timetable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;

@Data
public class TimeTableForm {
  public long id;
  public long classId;
  public long sectionId;
  public long instituteId;
  public String timeTableEditedBy;
  public int duration;
  public List<Periods> periods;
  public int numberOfDays = 6;

  @Data
  public static class Periods {
    public int periodNo;
    public String periodName;
    public String periodStartTime;
    public String periodEndTime;
    public boolean isPeriodActive;
    public List<DayWise> dayWiseSchd;

    @Data
    public static class DayWise {
      public String day;
      private int dayNumber;
      public long professorId;
      public String professorName;
      public long subjectId;
      public String subjectName;
      public String sameAsPreviousPeriod;
      public String profCat;
      public boolean isPreviousPeriod = false;
    }
  }

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

//    if (StringUtils.isBlank(day)) {
//      errors.add(new ValidationError("day", "Please select valid day."));
//    }
//
//    boolean isValidTimeFormat = true;
//    if (!TimeUtils.isValidTime(periodStartTime)) {
//      isValidTimeFormat = false;
//      errors.add(new ValidationError("periodStartTime", "Please enter valid start time."));
//    }
//
//    if (!TimeUtils.isValidTime(periodEndTime)) {
//      isValidTimeFormat = false;
//      errors.add(new ValidationError("periodEndTime", "Please enter valid start time."));
//    }
//
//    if(isValidTimeFormat && !TimeUtils.isValidTimeRange(periodStartTime, periodEndTime)) {
//      errors.add(new ValidationError("error", "You have entered wrong period time information."));
//    }
//
//    if(professorId <= 0 || StringUtils.isBlank(professorName)){
//      errors.add(new ValidationError("professorName", "Please select valid professor."));
//    }
//
//    if(subjectId <= 0 || StringUtils.isBlank(subjectName)){
//      errors.add(new ValidationError("subjectName", "Please select valid subject."));
//    }
//
//    if(periodNo <= 0){
//      errors.add(new ValidationError("periodNo", "Period Name should start from 1."));
//    }

    if (errors.size() > 0)
      return errors;
    return null;
  }
}
