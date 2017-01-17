package controllers.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.CustomController;
import models.EmployeeModels;
import dao.UserFetchDAO;
import enum_package.InstituteUserRole;
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
		Map<String, List<EmployeeModels>> userInfos = new HashMap<String, List<EmployeeModels>>();
		try {
			userInfos = userFetchDAO.getAllUser(schoolId);
//			if(category == null || category.isEmpty())
//				userInfos = userFetchDAO.getAllUser(instituteId);
//			else if(category.equalsIgnoreCase(Role.TEACHER.name())) {
//				userInfos = userFetchDAO.getAllTeachers(instituteId);
//			} else if(category.equals(Role.GUARDIAN.name())) {
//				userInfos = userFetchDAO.getAllGuardian(instituteId);
//			} else if(category.equalsIgnoreCase(Role.STUDENT.name())) {
//				userInfos = userFetchDAO.getAllStudents(instituteId);
//			} else {
//				userInfos = userFetchDAO.getAllOtherUser(instituteId);
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
		EmployeeModels userInfos = null;
		try {
			if(category.equals(InstituteUserRole.guardian.name())) {
//				userInfos = userFetchDAO.getAllGuardian(instituteId);
			} else if(category.equalsIgnoreCase(InstituteUserRole.student.name())) {
//				userInfos = userFetchDAO.getAllStudents(instituteId);
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
