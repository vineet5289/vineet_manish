package controllers.institute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.PermissionModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.RoleForm;
import controllers.CustomController;
import dao.PermissionDAO;
import dao.school.RoleDao;
import enum_package.SessionKey;

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
		return ok("");
	}

	public Result postAddRole() {
		RoleForm addNewRole = formFactory.form(RoleForm.class).get();
		Long roleKey = 0l;
		List<PermissionModel> permissions = new ArrayList<PermissionModel>();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(addNewRole != null && instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				roleKey = roleDao.addNewRole(instituteId, userName, addNewRole);
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
		
		return ok("");
	}

	public Result editPermission() {
		RoleForm addNewRole = formFactory.form(RoleForm.class).get();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return ok("");
	}

	public Result disableRole() {
		RoleForm addNewRole = formFactory.form(RoleForm.class).get();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			boolean isRoleDisabled = false;
			if(addNewRole != null && instituteIdFromSession != null) {
				Long roleId = addNewRole.getId();
				Long instituteId = Long.parseLong(instituteIdFromSession);
				isRoleDisabled = roleDao.disableRole(roleId, instituteId, userName);
			}
			if(!isRoleDisabled) {
				flash("error", "Some problem happend during execution of your request. Please try again.");
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return redirect(controllers.institute.routes.RoleController.preAddRole());
	}
}
