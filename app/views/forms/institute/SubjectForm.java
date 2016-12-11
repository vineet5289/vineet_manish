package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import play.data.validation.ValidationError;
import lombok.Data;

@Data
public class SubjectForm {
  public long classId;
  public long sectionId;
  public String subjectName;
  public String subjectCode;
  public String autoGenerateSCString;
  public String description;
  public String recommendedBook;
  public boolean autoGenerateSC = false;
  public boolean isSection = false;
  public String requestedUsername;

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if (StringUtils.isBlank(subjectName)) {
      errors.add(new ValidationError("subjectName", "Subject name can't be empty."));
    }

    if (StringUtils.isNotBlank(autoGenerateSCString)
        && autoGenerateSCString.equalsIgnoreCase("true")) {
      autoGenerateSC = true;
    }

    if (!autoGenerateSC && StringUtils.isBlank("subjectCode")) {
      errors.add(new ValidationError("subjectCode",
          "Either select auto generate subject code option or enter subject code."));
    }
    if (errors.size() > 0) {
      return errors;
    }
    return null;
  }
}
