package controllers.institute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.GroupForm;
import controllers.CustomController;
import dao.school.GroupDao;
import enum_package.SessionKey;
import views.html.viewClass.School.addGroups;

public class GroupController extends CustomController {

	@Inject private FormFactory formFactory;
	@Inject GroupDao groupDao;

	public Result preAddGroup() {
		Form<GroupForm> addNewGroup = formFactory.form(GroupForm.class);
		List<GroupForm> activeGroup = new ArrayList<GroupForm>();
		List<GroupForm> inactiveGroup = new ArrayList<GroupForm>();
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				Map<Boolean, List<GroupForm>> instituteRoles = groupDao.getAllGroup(instituteId);
				activeGroup = instituteRoles.get(true);
				inactiveGroup = instituteRoles.get(false);
			} else if(instituteIdFromSession == null) {
				flash("error", "Please select an institute you want to see if you are login as group of institute other wise refresh page.");
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}

//		return (rolePresent, activeGroup, inactiveGroup);
		return ok(addGroups.render(activeGroup, inactiveGroup,addNewGroup));
	}

	public Result postAddGroup() {
		Form<GroupForm> addNewRoleForm = formFactory.form(GroupForm.class).bindFromRequest();;
		GroupForm addNewRole = addNewRoleForm.get();
		boolean isGroupAdded = false;
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				isGroupAdded = groupDao.addGroup(instituteId, addNewRole.getGroupName(), addNewRole.getGroupDescription(),
						userName);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			isGroupAdded = false;
		}

		if(!isGroupAdded) {
			flash("error", "Got some error during request processing.");
		} else {
			System.out.println("Yes ..the  group is added");
			flash("success", "Your request for adding group has been completed.");
		}
		return redirect(controllers.institute.routes.GroupController.preAddGroup());
	}

	public Result disableGroup() {
		Form<GroupForm> addNewRoleForm = formFactory.form(GroupForm.class).bindFromRequest();;
		GroupForm addNewRole = addNewRoleForm.get();
		boolean isGroupDisabled = false;
		try {
			String instituteIdFromSession = session().get(SessionKey.of(SessionKey.instituteid));
			String userName = session().get(SessionKey.of(SessionKey.username));
			if(instituteIdFromSession != null) {
				Long instituteId = Long.parseLong(instituteIdFromSession);
				isGroupDisabled = groupDao.addDisabled(instituteId, addNewRole.getId(), userName);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
			isGroupDisabled = false;
		}

		if(!isGroupDisabled) {
			flash("error", "Got some error during request processing.");
		} else {
			flash("success", "Your request for disabled group has been completed.");
		}
		return redirect(controllers.institute.routes.GroupController.preAddGroup());
	}
}
