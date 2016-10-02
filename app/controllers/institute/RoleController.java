package controllers.institute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.RoleForm;
import controllers.CustomController;
import dao.school.RoleDao;
import enum_package.SessionKey;

public class RoleController extends CustomController {

	@Inject private FormFactory formFactory;
	@Inject private RoleDao roleDao;

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
				// flash warning, select institute to see all role
			}
			
		} catch(Exception exception) {
			exception.printStackTrace();
			
		}

//		return (rolePresent, activeRoles, inactiveRoles);
		return ok("");
	}

	public Result postAddRole() {
		RoleForm addNewRole = formFactory.form(RoleForm.class).get();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String headInstituteIdFromSession = session().get(SessionKey.of(SessionKey.headinstituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(addNewRole != null && instituteIdFromSession != null && headInstituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				Long headInstituteId = Long.parseLong(headInstituteIdFromSession);
				
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}

//		return (rolePresent, rolePresent);
		return ok("");
	}

	public Result removeRole() {
		RoleForm addNewRole = formFactory.form(RoleForm.class).get();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String headInstituteIdFromSession = session().get(SessionKey.of(SessionKey.headinstituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(addNewRole != null && instituteIdFromSession != null && headInstituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				Long headInstituteId = Long.parseLong(headInstituteIdFromSession);
				
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}

//		return (rolePresent, rolePresent);
		return ok("");
	}
}
