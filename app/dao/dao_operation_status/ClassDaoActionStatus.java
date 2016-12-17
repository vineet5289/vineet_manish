package dao.dao_operation_status;

import java.util.HashMap;
import java.util.Map;

public enum ClassDaoActionStatus {
  sectionSuccessfullyDeleted("Section has been successfully deleted from."),
  classSuccessfullyDeleted("Class and it's related section has been successfully deleted."),
  sectionSuccessfullyAdded("Section has been successfully added."),
  classSuccessfullyAdded("Class has been successfully added."),
  norecordfoundforgivenclass("First need to add class then you can add section for given class."),
  invalidRequest("Your request to add Class/Section is invalid. Please try again"),
  serverexception("Some internal error occur during request processing. Please try after sometime.");

  private final static Map<ClassDaoActionStatus, String> statusToValue = new HashMap<ClassDaoActionStatus, String>(ClassDaoActionStatus.values().length);

  static {
    for (ClassDaoActionStatus cdas : ClassDaoActionStatus.values()) {
      statusToValue.put(cdas, cdas.value);
    }
  }

  private String value;

  private ClassDaoActionStatus(String value) {
    this.value = value;
  }

  public static String of(ClassDaoActionStatus key) {
    String statusMessage = statusToValue.get(key);
    if (statusMessage == null)
      return "";
    return statusMessage;
  }

  public String getValue() {
    return value;
  }
}
