package views.forms.employee;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;
import utils.EmployeeUtil;
import utils.GenderUtil;
import utils.ValidateFields;

@Data
public class AddEmployeeForm {

  // compulsory field
  private String empName;
  private String empPhoneNumber;
  private String gender;
  private String empCode;
  private boolean isAutoGenerate = false;
  // optional field
  private String empEmail;
  private String jobTitle;

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (empName == null || empName.trim().isEmpty()) {
      errors
          .add(new ValidationError("empName",
              "Employee name should not be empty. And should not contains special characters like ;@[]"));
    }

    if (!ValidateFields.isValidMobileNumber(empPhoneNumber)) {
      errors.add(new ValidationError("empPhoneNumber", "Enter valid contract number."));
    }

    if (!GenderUtil.isValidGenderValue(gender)) {
      errors.add(new ValidationError("gender", "Select valid gender M for Male or F for Female."));
    }

    if (!isAutoGenerate && !EmployeeUtil.isValidEmpCodePattern(empCode)) {
      errors.add(new ValidationError("empCode",
          "Only alphanumerics, hypen and dot are allowed in empCode"));

    }

    if (empEmail != null && !ValidateFields.isValidEmailId(empEmail)) {
      errors.add(new ValidationError("empEmail", "Enter valid email id like abcd@xyz.com"));
    }

    if(jobTitle != null && EmployeeUtil.isValidJobTitlePattern(jobTitle)) {
      errors.add(new ValidationError("jobTitle", "Only alphanumerics, space and dot are allowed in jobTitle"));
    }

    if (errors.size() > 0) {
      return errors;
    }

    return null;
  }
}
