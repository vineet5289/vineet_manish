package views.forms.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import play.data.validation.ValidationError;
import utils.AddressFieldValidationUtils;
import utils.GenderUtil;
import utils.ValidateFields;
import lombok.Data;

@Data
public class EmployeeDetailsForm {

  // inforation used for internal purpose
  public long id;
  public boolean isActiveEmployee;
  public long instituteId;
  public String requestedUserName;

  // public information for every user inside institute
  public String name;// dashboard, can updated by employee, employeer
  public String jobTitles;// dashboard, can updated by employeer


  // information that is visable to only authroized person
  public String gender;// can updated by employee, employeer
  public String userName;// dashboard, can't updated by anyone
  public String phoneNumber;// can be updated by employee, employeer
  public String empCode;// dashboard, // can be updated by employeer
  public String empPreferedName; // can be updated by employee
  public String empAlternativeEmail; // can be updated by employee
  public String alternativeNumber; // can be updated by employee
  public String empEmail;// can be updated by employeer
  public String joiningDate;// can be updated by employeer
  public String dob;// can be updated by employee, employeer
  public String addressLine1;// can be updated by employee, employeer
  public String addressLine2;// can be updated by employee, employeer
  public String city;// can be updated by employee, employeer
  public String state;// can be updated by employee, employeer
  public String pinCode;// can be updated by employee, employeer
  public String country;// can be updated by employee, employeer

  // just only for deleted employee, otherwise show currently working
  public String leavingDate;// can be updated by employeer

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (errors.size() > 0) {
      return errors;
    }

    if (StringUtils.isBlank(name)) {
      errors.add(new ValidationError("name", "Name can't be empty."));
    }

    if (!GenderUtil.isValidGenderValue(gender)) {
      errors.add(new ValidationError("gender", "Select valid gender M for Male or F for Female."));
    }

    if (!ValidateFields.isValidMobileNumber(phoneNumber)) {
      errors.add(new ValidationError("phoneNumber", "Enter valid contract number."));
    }

    if (StringUtils.isNotBlank(empAlternativeEmail)
        && !ValidateFields.isValidEmailId(empAlternativeEmail)) {
      errors.add(new ValidationError("empAlternativeEmail",
          "Enter valid alternative contract number."));
    }

    if (StringUtils.isNotBlank(alternativeNumber)
        && !ValidateFields.isValidAlternativeNumber(alternativeNumber)) {
      errors.add(new ValidationError("alternativeNumber",
          "Enter valid alternative contract number."));
    }

    if (StringUtils.isBlank(addressLine1)) {
      errors.add(new ValidationError("addressLine1", "Address should not be empty."));
    }

    if (!AddressFieldValidationUtils.isValidCity(city)) {
      errors
          .add(new ValidationError("city",
              "City should not be empty. And should not contains any special characters except space."));
    }

    if (!AddressFieldValidationUtils.isValidState(state)) {
      errors
          .add(new ValidationError("state",
              "State should not be empty. And should not contains any special characters except space."));
    }

    if (!AddressFieldValidationUtils.isValidCountry(country)) {
      errors
          .add(new ValidationError("country",
              "Country should not be empty. And should not contains any special characters except space."));
    }

    if (!AddressFieldValidationUtils.isValidPincode(pinCode)) {
      errors.add(new ValidationError("pinCode",
          "Pincode should not be empty. And should not contains any special characters."));
    }

    if (errors.size() > 0) {
      return errors;
    }

    return null;
  }
}
