package controllers.employee;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.employee.AddEmployeeForm;
import views.forms.employee.EmployeeDetailsForm;
import views.html.Employee.addEmployee;
import views.html.Employee.employeeList;
import controllers.CustomController;
import dao.dao_operation_status.EmployeeDaoActionStatus;
import dao.employee.EmployesDAO;
import enum_package.SessionKey;

public class EmployeeController extends CustomController {
  @Inject
  private FormFactory formFactory;
  @Inject
  EmployesDAO employesDAO;

  // @Security.Authenticated(HeadInstituteBasicAuthCheck.class)
  public Result preAddEmployeeRequest() {
    Form<AddEmployeeForm> addEmployeeForm = formFactory.form(AddEmployeeForm.class);
    /*
     * TODO: 1. Send list of all available job titles with other option
     */
    return ok(addEmployee.render(addEmployeeForm));
  }

  // @Security.Authenticated(HeadInstituteBasicAuthCheck.class)
  public Result postAddEmployeeRequest() {
    Form<AddEmployeeForm> addEmployeeForm =
        formFactory.form(AddEmployeeForm.class).bindFromRequest();
    if (addEmployeeForm == null || addEmployeeForm.hasErrors()) {
      flash("error",
          "Some errors occur either of some fileds are missing or contains invalid value.");
      return redirect(controllers.employee.routes.EmployeeController.viewAllEmployee());
    }

    AddEmployeeForm addEmployee = addEmployeeForm.get();
    if (addEmployee == null) {
      flash("error",
          "Some errors occur either of some fileds are missing or contains invalid value.");
      return redirect(controllers.employee.routes.EmployeeController.viewAllEmployee());
    }

    EmployeeDaoActionStatus employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
    try {
      String userName = session().get(SessionKey.of(SessionKey.username));
      String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
      if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(instituteIdFromSession)) {
        long instituteId = Long.parseLong(instituteIdFromSession);
        employeeDaoActionStatus = employesDAO.addNewEmpRequest(addEmployee, userName, instituteId);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    if (employeeDaoActionStatus != EmployeeDaoActionStatus.successfullyAdded) {
      flash("error", employeeDaoActionStatus.getValue());
    } else {
      flash("success", employeeDaoActionStatus.getValue());
    }

    return redirect(controllers.employee.routes.EmployeeController.viewAllEmployee());
  }

  // @Security.Authenticated(HeadInstituteBasicAuthCheck.class)
  public Result viewAllEmployee() {
    List<EmployeeDetailsForm> employees = new ArrayList<EmployeeDetailsForm>();
    try {
      String userName = session().get(SessionKey.of(SessionKey.username));
      String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
      if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(instituteIdFromSession)) {
        long instituteId = Long.parseLong(instituteIdFromSession);
        employees = employesDAO.getAllEmp(instituteId, true);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    if (employees == null || employees.size() == 0) {
      flash("error", "Some errors occur during details fetch.");
    }
    return ok(employeeList.render(employees));
  }

  public Result deleteEmployee(String empUserName) {
    EmployeeDaoActionStatus employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
    try {
      String userName = session().get(SessionKey.of(SessionKey.username));
      String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
      if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(instituteIdFromSession)) {
        long instituteId = Long.parseLong(instituteIdFromSession);
        employeeDaoActionStatus = employesDAO.enableDisableEmployee(instituteId, empUserName, false, userName);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    if (employeeDaoActionStatus != EmployeeDaoActionStatus.successfullyDeleted) {
      flash("error", employeeDaoActionStatus.getValue());
    } else {
      flash("success", employeeDaoActionStatus.getValue());
    }
    return redirect(controllers.employee.routes.EmployeeController.viewAllEmployee());
  }

  public Result editEmployee(String username, String section, String type) {
    Form<EmployeeDetailsForm> upldateEmpDetailsForm =
        formFactory.form(EmployeeDetailsForm.class).bindFromRequest();
    if (upldateEmpDetailsForm == null || upldateEmpDetailsForm.hasErrors()) {
      flash("error", "Some errors occur either of some fileds are missing or contains invalid value.");
      return redirect(controllers.employee.routes.EmployeeController.showEmployeeInfo(username, section, type));//TODO: send to profile page
    }

    EmployeeDetailsForm upldateEmpDetails = upldateEmpDetailsForm.get();
    if (upldateEmpDetails == null) {
      flash("error",
          "Some errors occur either of some fileds are missing or contains invalid value.");
      return redirect(controllers.employee.routes.EmployeeController.showEmployeeInfo(username, section, type));//TODO: send to profile page
    }

    EmployeeDaoActionStatus employeeDaoActionStatus = EmployeeDaoActionStatus.serverexception;
    try {
        employeeDaoActionStatus = employesDAO.updateEmployeeInfo(upldateEmpDetails, section, type);
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    if (employeeDaoActionStatus != EmployeeDaoActionStatus.successfullyUpdated) {
      flash("error", employeeDaoActionStatus.getValue());
    } else {
      flash("success", employeeDaoActionStatus.getValue());
    }
    return redirect(controllers.employee.routes.EmployeeController.showEmployeeInfo(username, section, type));//TODO: send to profile page
  }

  public Result showEmployeeInfo(String username, String section, String type) {
    return ok("username:" +username + ", section" + section + ",type" + type);
  }
}
