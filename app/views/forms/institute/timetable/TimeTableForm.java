package views.forms.institute.timetable;

import java.util.ArrayList;
import java.util.List;

import enum_package.PeriodCategories;
import enum_package.TeacherAppointmentCategories;
import enum_package.WeekDayEnum;
import lombok.Data;
import play.data.validation.ValidationError;
import utils.StringUtils;

import static utils.TimeUtils.calculateDuration;

@Data
public class TimeTableForm {
  public long id;
  public long classId; // internally set during form submission
  public long sectionId; // internally set during form submission, in case of section
  public long instituteId;// internally set during form submission
  public String timeTableEditedBy; // internally set during form submission
  public List<DayWise> dayWiseSchd;// set based on user input, should not null

//  private Map<String, String>
  @Data
  public static class DayWise {
    public String day; // selected by user
    private int dayNumber;// internal uses
    public int numberOfPeriod;// 1st time enter by user then everytime just set internally
    public List<Periods> periods;// size should be equal to numberOfPeriod
    @Data
    public static class Periods {
      public String periodType;//show in drop down list
      public String periodDescription;//for some periodType there should be some description
      private int duration;// internally calculated
      public String periodStartTime;//enter by user
      public String periodEndTime;//enter by user
      public String sameAsPreviousPeriod; //checkbox selected by user
      public long professorId;//enter by user
      public long subjectId;//enter by user
      public String profCat;//enter by user, default is permanent
      private boolean isPreviousPeriod = false;// internal uses
      private boolean isSpecialPeriod = false;
    }
  }

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if(classId <= 0) {
      errors.add(new ValidationError("classId", "Class Validation Error Occur."));
    }

    if(instituteId <= 0) {
      errors.add(new ValidationError("instituteId", "Institute Validation Error Occur."));
    }

    if (StringUtils.isBlank(timeTableEditedBy)) {
      errors.add(new ValidationError("timeTableEditedBy", "Requested User is not valid"));
    }

    if (dayWiseSchd == null || dayWiseSchd.isEmpty()) {
      errors.add(new ValidationError("periods", "Please Add Atleast One Period."));
    } else {
      for(DayWise dayWise : dayWiseSchd) {
        if (WeekDayEnum.contains(dayWise.getDay())) {
          errors.add(new ValidationError("day", "Please Select Valid Week Day from drop down."));
          break;
        } else {
          dayWise.setDayNumber(WeekDayEnum.of(dayWise.getDay()));
        }

        if(dayWise.getNumberOfPeriod() == 0 || dayWise.getPeriods() == null
            || dayWise.getNumberOfPeriod() != dayWise.getPeriods().size()) {
          errors.add(new ValidationError("day", "You have entered either zero number of periods or some period details are missing."));
          break;
        }

        for(DayWise.Periods period : dayWise.getPeriods()) {
          if (!PeriodCategories.contains(period.getPeriodType())) {
            period.setPeriodType(PeriodCategories.get(PeriodCategories.LECTURE.name()));
          }

          if(PeriodCategories.isProfSubjRequired(period.getPeriodType())) {
            period.setSpecialPeriod(false);
          } else {
            period.setSpecialPeriod(true);
          }

          if(period.isSpecialPeriod() && StringUtils.isBlank(period.getPeriodDescription())) {
            errors.add(new ValidationError("periodDescription", "Please Enter Single Line Description for given Period."));
          }
          if (!period.isSpecialPeriod() && (period.getProfessorId() <= 0 || period.getSubjectId() <= 0)) {
            errors.add(new ValidationError("period", "Please Select Atleas One Period."));
          }

          int calculatedDuration = calculateDuration(period.getPeriodStartTime(), period.getPeriodEndTime());
          if (calculatedDuration <= 0) {
            errors.add(new ValidationError("periodTiming", "Periods timing is incorrect."));
          }

          if(StringUtils.isBlank(period.getSameAsPreviousPeriod()) || period.getSameAsPreviousPeriod().equalsIgnoreCase("false")) {
            period.setPreviousPeriod(false);
          } else {
            period.setPreviousPeriod(true);
          }

          if(!TeacherAppointmentCategories.contains(period.getProfCat())) {
            period.setProfCat(TeacherAppointmentCategories.PERMANENT.name());
          }
        }
      }
    }

    if (errors.size() > 0)
      return errors;
    return null;
  }
}
