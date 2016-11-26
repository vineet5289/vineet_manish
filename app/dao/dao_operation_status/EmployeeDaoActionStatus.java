package dao.dao_operation_status;

import java.util.HashMap;
import java.util.Map;

import enum_package.FileUploadStatus;

public enum EmployeeDaoActionStatus {
  successfullyDeleted("Employee has been successfully deleted."),
  norecordfoundforgivenusername("No record found for given employee."),
  serverexception("Some internal error occur during request processing. Please try after sometime.");

  private String value;
  private final static Map<EmployeeDaoActionStatus, String> statusToValue = new HashMap<EmployeeDaoActionStatus, String>(EmployeeDaoActionStatus.values().length);
  private EmployeeDaoActionStatus(String value) {
      this.value = value;
  }

  static {
      for(EmployeeDaoActionStatus idps : EmployeeDaoActionStatus.values()) {
          statusToValue.put(idps, idps.value);
      }
  }

  public String getValue() {
    return value;
  }

  public static String of(FileUploadStatus key) {
      String statusMessage = statusToValue.get(key);
      if(statusMessage == null)
          return "";
      return statusMessage;
  }
}
