package controllers.institute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.PermissionModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.PermissionForm;
import views.forms.institute.RoleForm;
import controllers.CustomController;
import dao.PermissionDAO;
import dao.school.RoleDao;
import enum_package.SessionKey;
import views.html.viewClass.School.addRoles;
import views.html.viewClass.School.addRolePermissions;

public class RoleController extends CustomController {

	@Inject private FormFactory formFactory;
	@Inject private RoleDao roleDao;
	@Inject private PermissionDAO permissionDAO;

	public Result preAddRole() {
		Form<RoleForm> addNewRole = formFactory.form(RoleForm.class);
		List<RoleForm> activeRoles = new ArrayList<RoleForm>();
		List<RoleForm> inactiveRoles = new ArrayList<RoleForm>();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				Map<Boolean, List<RoleForm>> instituteRoles = roleDao.getAllRole(instituteId);
				activeRoles = instituteRoles.get(true);
				inactiveRoles = instituteRoles.get(false);
			} else if(instituteIdFromSession == null) {
				flash("error", "Please select an institute you want to see if you are login as group of institute other wise refresh page.");
			}
			
		} catch(Exception exception) {
			exception.printStackTrace();
			
		}

//		return (rolePresent, activeRoles, inactiveRoles);
		return ok(addRoles.render(activeRoles, inactiveRoles,addNewRole));
	}

	public Result postAddRole() {
		Form<RoleForm> addNewRoleForm = formFactory.form(RoleForm.class).bindFromRequest();;
		RoleForm addNewRole = addNewRoleForm.get();
		Long roleKey = 0l;
		List<PermissionModel> permissions = new ArrayList<PermissionModel>();
		String roleName = addNewRole.getRoleName();
		String roleDescription = addNewRole.getRoleDescription();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(addNewRole != null && instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				roleKey = roleDao.addNewRole(instituteId, userName, roleName, roleDescription);
				if(roleKey != 0) {
					permissions = permissionDAO.getAllPermissionInSystem();
				}
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		if(roleKey == 0 || permissions == null || permissions.size() == 0) {
			flash("error", "Please select an institute you want to see if you are login as group of institute other wise refresh page.");
			//redirect to preAddRole;
		}

		Form<PermissionForm> permissionForm = formFactory.form(PermissionForm.class);
		//permissionForm, roleName, roleDescription, roleKey, permissions
		return ok(addRolePermissions.render(permissionForm, roleName, roleDescription, roleKey, permissions));
	}

	public Result editPermission() {
		Form<PermissionForm> permissionsForm = formFactory.form(PermissionForm.class).bindFromRequest();
		PermissionForm permissions = permissionsForm.get();
		boolean isPermissionEdited = false;
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				isPermissionEdited = roleDao.assignPermission(instituteId, permissions.getRoleId(), permissions.getPermissions(), userName);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		if(!isPermissionEdited) {
			flash("error", "Permission request processing error.");
		}
		return redirect(controllers.institute.routes.RoleController.preAddRole());
	}

	public Result disableRole(Long roleId) {
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			boolean isRoleDisabled = false;
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				isRoleDisabled = roleDao.disableEnableRole(roleId, instituteId, userName, true);
			}
			if(!isRoleDisabled) {
				flash("error", "Some problem happend during execution of your request. Please try again.");
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return redirect(controllers.institute.routes.RoleController.preAddRole());
	}

	public Result enableRole(Long roleId) {
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			boolean isRoleEnabled = false;
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				isRoleEnabled = roleDao.disableEnableRole(roleId, instituteId, userName, false);
			}
			if(!isRoleEnabled) {
				flash("error", "Some problem happend during execution of your request. Please try again.");
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return redirect(controllers.institute.routes.RoleController.preAddRole());
	}
}
