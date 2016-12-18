package views.forms.institute;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;

@Data
public class SubjectForm {
  public long instituteId;
  public long classId;
  public long sectionId;
  public String subjectName;
  public String subjectCode;
  public String description;
  public String recommendedBook;
  public boolean isSection = false;
  public String requestedUsername;

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if (StringUtils.isBlank(subjectName)) {
      errors.add(new ValidationError("subjectName", "Subject name can't be empty."));
    }

    if (errors.size() > 0) {
      return errors;
    }
    return null;
  }
}
