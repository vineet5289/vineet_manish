package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import enum_package.AttendenceTypeEnum;
import enum_package.SchoolClassEnum;
import enum_package.WeekDayEnum;
import lombok.Data;
import models.SchoolType;
import play.data.validation.ValidationError;
import utils.StringUtils;
import utils.TimeUtils;

@Data
public class FirstTimeInstituteUpdateForm {
  private int numberOfShift = 1;
  private String hostelFacilitiesIsAvailable;
  private String hostelIsCompulsory;
  private String instituteOfficeWeekStartDay;
  private WeekDayEnum instituteOfficeWeekStartDayEnum;
  private String instituteOfficeWeekEndDay;
  private WeekDayEnum instituteOfficeWeekEndDayEnum;
  private int startClassIndex;
  private String instituteClassFrom;
  private int endClassIndex;
  private String instituteClassTo;
  private String instituteOfficeStartTime;
  private String instituteOfficeEndTime;
  private String instituteFinancialStartDate;
  private String instituteFinancialEndDate;
  private boolean isHostelFacilitiesAvailable = false;
  private boolean isHostelCompulsory = false;
  private String instituteBoard;
  private String instituteType;
  private List<InstituteShiftAndClassTimingInfoForm.Shift> shifts;

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if (numberOfShift < 1) {
      errors.add(new ValidationError("numberOfShift", "Number Of Shift should be greater then one."));
    }

    if (hostelFacilitiesIsAvailable == null || !(hostelFacilitiesIsAvailable.trim().equalsIgnoreCase("true")
        || hostelFacilitiesIsAvailable.trim().equalsIgnoreCase("false"))) {
      errors.add(new ValidationError("hostelFacilitiesAvailable", "Please select hostel facilities is available or not."));
    } else {
      isHostelFacilitiesAvailable = hostelFacilitiesIsAvailable.trim().equalsIgnoreCase("true");
    }

    if (isHostelFacilitiesAvailable && (hostelIsCompulsory == null || !(hostelIsCompulsory.trim().equalsIgnoreCase("true")
        || hostelIsCompulsory.trim().equalsIgnoreCase("false")))) {
      errors.add(new ValidationError("isHostelCompulsory", "Please select hostel is compulsory or optional."));
    } else {
      isHostelCompulsory = (isHostelFacilitiesAvailable && hostelIsCompulsory.trim().equalsIgnoreCase("true"));
    }

    if (instituteOfficeWeekStartDay == null || !WeekDayEnum.contains(instituteOfficeWeekStartDay)) {
      errors.add(new ValidationError("instituteOfficeWeekStartDay", "Please select one of the week day from drop down"));
    } else {
      instituteOfficeWeekStartDayEnum = WeekDayEnum.of(instituteOfficeWeekStartDay);
    }

    if (instituteOfficeWeekEndDay == null || !WeekDayEnum.contains(instituteOfficeWeekEndDay)) {
      errors.add(new ValidationError("instituteOfficeWeekEndDay", "Please select one of the week day from drop down"));
    } else {
      instituteOfficeWeekEndDayEnum = WeekDayEnum.of(instituteOfficeWeekEndDay);
    }

    if (startClassIndex < 1 || endClassIndex < 1 || startClassIndex > endClassIndex
        || !SchoolClassEnum.contains(startClassIndex) || !SchoolClassEnum.contains(endClassIndex)) {
      errors.add(new ValidationError("instituteClass", "Please select class from drop down"));
    } else {
      instituteClassFrom = SchoolClassEnum.of(startClassIndex);
      instituteClassTo = SchoolClassEnum.of(endClassIndex);
    }

    boolean isOfficeTimingValid = true;
    if (!(TimeUtils.isValidTime(instituteOfficeStartTime))) {
      errors.add(new ValidationError("instituteOfficeStartTime", "Please select office open time."));
      isOfficeTimingValid = false;
    }

    if (!(TimeUtils.isValidTime(instituteOfficeEndTime))) {
      errors.add(new ValidationError("instituteOfficeEndTime", "Please select closing time."));
      isOfficeTimingValid = false;
    }

    if (isOfficeTimingValid && !TimeUtils.isValidTimeRange(instituteOfficeStartTime, instituteOfficeEndTime)) {
      errors.add(new ValidationError("instituteTimeRange", "Please Select valid office start and end time."));
    } else {
      instituteOfficeStartTime = instituteOfficeStartTime.trim();
      instituteOfficeEndTime = instituteOfficeEndTime.trim();
    }

    instituteFinancialStartDate = TimeUtils.validDate(instituteFinancialStartDate);
    instituteFinancialEndDate = TimeUtils.validDate(instituteFinancialEndDate);
    if (instituteFinancialStartDate.isEmpty()) {
      errors.add(new ValidationError("instituteFinancialStartDate", "Please select financial year start date."));
    }

    if (instituteFinancialEndDate.isEmpty()) {
      errors.add(new ValidationError("instituteFinancialEndDate", "Please select financial year end date."));
    }

    if(!TimeUtils.validDateRange(instituteFinancialStartDate, instituteFinancialEndDate)) {
      errors.add(new ValidationError("instituteFinancialDateError", "Please select financial year."));
    }

    if (StringUtils.isBlank(instituteBoard)) {
      errors.add(new ValidationError("instituteBoard", "Please select valid institute board."));
    } else {
      instituteBoard = instituteBoard.trim();
    }

    if (instituteType == null || SchoolType.schoolTypeToValue.get(instituteType.trim().toLowerCase()) == null) {
      errors.add(new ValidationError("instituteType", "Please enter valid school type without any special characters like @;$."));
    } else {
      instituteType = instituteType.trim().toLowerCase();
    }

    if (numberOfShift > 1 && (shifts == null || shifts.size() != numberOfShift || !isValidShiftInfo())) {
      errors.add(new ValidationError("shifts", "Please enter shift info."));
    }

    if (errors.size() > 0)
      return errors;
    return null;
  }

  private boolean isValidShiftInfo() {
    for (InstituteShiftAndClassTimingInfoForm.Shift shiftInfo : shifts) {
      if (!TimeUtils.isValidTime(shiftInfo.getShiftClassStartTime()) || !TimeUtils.isValidTime(shiftInfo.getShiftClassEndTime())
          || !TimeUtils.isValidTimeRange(shiftInfo.getShiftClassStartTime(), shiftInfo.getShiftClassEndTime())) {
        return false;
      } else {
        shiftInfo.setShiftClassStartTime(shiftInfo.getShiftClassStartTime().trim());
        shiftInfo.setShiftClassEndTime(shiftInfo.getShiftClassEndTime().trim());
      }

      if (shiftInfo.getShiftWeekStartDay() == null || !WeekDayEnum.contains(shiftInfo.getShiftWeekStartDay())
          || shiftInfo.getShiftWeekEndDay() == null || !WeekDayEnum.contains(shiftInfo.getShiftWeekEndDay())) {
        return false;
      } else {
        shiftInfo.setShiftWeekStartDay(shiftInfo.getShiftWeekStartDay().trim().toLowerCase());
        shiftInfo.setShiftWeekEndDay(shiftInfo.getShiftWeekEndDay().trim().toLowerCase());
      }

      if (shiftInfo.getShiftStartClassIndex() < 1 || shiftInfo.getShiftEndClassIndex() < 1
          || shiftInfo.getShiftStartClassIndex() > shiftInfo.getShiftEndClassIndex()
          || !SchoolClassEnum.contains(shiftInfo.getShiftStartClassIndex()) || !SchoolClassEnum.contains( shiftInfo.getShiftEndClassIndex())) {
        return false;
      } else {
        shiftInfo.setShiftStartClassFrom(SchoolClassEnum.of(shiftInfo.getShiftStartClassIndex()));
        shiftInfo.setShiftEndClassTo(SchoolClassEnum.of(shiftInfo.getShiftEndClassIndex()));
      }

      if (shiftInfo.getShiftAttendenceType() == null || !AttendenceTypeEnum.contain(shiftInfo.getShiftAttendenceType())) {
        return false;
      } else {
        shiftInfo.setShiftAttendenceType(shiftInfo.getShiftAttendenceType().trim());
      }

      if (StringUtils.isBlank(shiftInfo.getShiftName())) {
        return false;
      } else {
        shiftInfo.setShiftName(shiftInfo.getShiftName().trim());
      }
    }
    return true;
  }
}
