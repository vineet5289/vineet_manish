package controllers.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.UserInfo;
import controllers.CustomController;
import dao.UserFetchDAO;
import enum_package.Role;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.html.viewClass.allUser;
import views.html.user.userProfile;
public class UserController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result getUsers(Long schoolId, String category) {
		if(schoolId <= 0) {
			// redirect to error page
		}
		UserFetchDAO userFetchDAO = new UserFetchDAO();
		Map<String, List<UserInfo>> userInfos = new HashMap<String, List<UserInfo>>();
		try {
			userInfos = userFetchDAO.getAllUser(schoolId);
//			if(category == null || category.isEmpty())
//				userInfos = userFetchDAO.getAllUser(schoolId);
//			else if(category.equalsIgnoreCase(Role.TEACHER.name())) {
//				userInfos = userFetchDAO.getAllTeachers(schoolId);
//			} else if(category.equals(Role.GUARDIAN.name())) {
//				userInfos = userFetchDAO.getAllGuardian(schoolId);
//			} else if(category.equalsIgnoreCase(Role.STUDENT.name())) {
//				userInfos = userFetchDAO.getAllStudents(schoolId);
//			} else {
//				userInfos = userFetchDAO.getAllOtherUser(schoolId);
//			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			//redirect to particular page
		}
		System.out.println("===> " + userInfos);
		return ok(allUser.render(userInfos));
	}

	@Security.Authenticated(ActionAuthenticator.class)
	public Result getUser(Long schoolId, String userName, String category) {
		if(schoolId <= 0 || userName == null || userName.isEmpty() || category == null || category.isEmpty()) {
			// redirect to error page
		}

		UserFetchDAO userFetchDAO = new UserFetchDAO();
		UserInfo userInfos = null;
		try {
			if(category.equals(Role.GUARDIAN.name())) {
//				userInfos = userFetchDAO.getAllGuardian(schoolId);
			} else if(category.equalsIgnoreCase(Role.STUDENT.name())) {
//				userInfos = userFetchDAO.getAllStudents(schoolId);
			} else {
				System.out.println("===========> 1");
				userInfos = userFetchDAO.getEmployeeInfo(userName, schoolId);
				System.out.println("===========> 2" + userInfos);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			//redirect to particular page
		}
//		System.out.println("===> " + userInfos.get);
		return ok(userProfile.render(userInfos));
	}
}
