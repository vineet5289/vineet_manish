package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import play.data.validation.ValidationError;
import utils.TimeUtils;
import lombok.Data;

@Data
public class ClassForm {
  public long instituteId;
  public long classId;// during display form

  public String className;
  public String classStartTime;
  public String classEndTime;
  public int numberOfPeriod;
  public int numberOfsection; // only during edit or add class
  public String parentClassName;// during display form
  public long parentClassId;// during display form
  private List<String> sectionNames = new ArrayList<String>();
  private boolean isSection = false;
  private String isSectionFormValue;

  private boolean updateSection = false;
  private String updateSectionFormValue;

  private final char[] sectionSuffix = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
      'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if (StringUtils.isBlank(className)) {
      errors.add(new ValidationError("className", "Class/Section name shouldn't be empty."));
    }

    if (numberOfPeriod <= 0) {
      errors.add(new ValidationError("numberOfPeriod", "Class must have atleast one period."));
    }

    boolean isValidTimeFormat = true;
    if (!TimeUtils.isValidTime(classStartTime)) {
      isValidTimeFormat = false;
      errors.add(new ValidationError("classStartTime", "Class start time is mandatory and should be valid."));
    }

    if (!TimeUtils.isValidTime(classEndTime)) {
      isValidTimeFormat = false;
      errors.add(new ValidationError("classEndTime", "Class end time is mandatory and should be valid."));
    }

    if(isValidTimeFormat && !TimeUtils.isValidTimeRange(classStartTime, classEndTime)) {
      errors.add(new ValidationError("error", "You have entered wrong class time information."));
    }

    for (int currentSection = 0; currentSection < numberOfsection
        && currentSection < sectionSuffix.length; currentSection++) {
      String section = className + "-" + sectionSuffix[currentSection];
      sectionNames.add(section);
    }

    if(StringUtils.isNotBlank(isSectionFormValue)) {
      isSection = isSectionFormValue.equalsIgnoreCase("true") ? true : false;
    }

    if(StringUtils.isNotBlank(updateSectionFormValue)) {
      updateSection = updateSectionFormValue.equalsIgnoreCase("true") ? true : false;
    }

    if (errors.size() > 0)
      return errors;
    return null;
  }
}
