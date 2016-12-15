package views.forms.institute;

import enum_package.WeekDayEnum;
import lombok.Data;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

@Data
public class TimeTableForm {
  public long classId;
  public long sectionId;
  public long id;
  public long instituteId;
  public long timeTableEditedBy;
  public String classStartTime;
  public String classEndTime;
  public long duration;
  public List<Periods> periods;

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
      private WeekDayEnum dayEnum;
      public long professorId;
      public String professorName;
      public long subjectId;
      public String subjectName;
      public String sameAsPreviousPeriod;
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
//    if (!TimeUtiles.isValidTime(periodStartTime)) {
//      isValidTimeFormat = false;
//      errors.add(new ValidationError("periodStartTime", "Please enter valid start time."));
//    }
//
//    if (!TimeUtiles.isValidTime(periodEndTime)) {
//      isValidTimeFormat = false;
//      errors.add(new ValidationError("periodEndTime", "Please enter valid start time."));
//    }
//
//    if(isValidTimeFormat && !TimeUtiles.isValidTimeRange(periodStartTime, periodEndTime)) {
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
