package dao.dao_operation_status;

import java.util.HashMap;
import java.util.Map;

public enum SubjectDaoActionStatus {
  successfullyDeletedFromSection("Subject has been successfully deleted from section."),
  successfullyDeletedFromClassAndSection("Subject has been successfully deleted from both class and section."),
  successfullyDeletedFromClass("Subject has been successfully deleted from class."),
  successfullyAddedInSection("Subject has been successfully added in requested section."),
  successfullyAddedInClass("Subject has been successfully added in requested class."),
  successfullyAddedInClassAndSection("Subject has been successfully added in requested class and all section corresponding to class."),
  norecordfoundforgivenclass("First need to add class then you can add subject for given class."),
  invalidRequest("Your request to add subject is invalid. Please try again"),
  serverexception("Some internal error occur during request processing. Please try after sometime.");

  private String value;
  private final static Map<SubjectDaoActionStatus, String> statusToValue = new HashMap<SubjectDaoActionStatus, String>(SubjectDaoActionStatus.values().length);
  private SubjectDaoActionStatus(String value) {
      this.value = value;
  }

  static {
      for(SubjectDaoActionStatus idps : SubjectDaoActionStatus.values()) {
          statusToValue.put(idps, idps.value);
      }
  }

  public String getValue() {
    return value;
  }

  public static String of(SubjectDaoActionStatus key) {
      String statusMessage = statusToValue.get(key);
      if(statusMessage == null)
          return "";
      return statusMessage;
  }
}
