package controllers.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.UserInfo;
import controllers.CustomController;
import dao.UserFetchDAO;
import enum_package.Role;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import views.html.viewClass.allUser;
public class UserController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result getUsers(Long schoolId, String category) {
		if(schoolId <= 0) {
			// redirect to error page
		}
		UserFetchDAO userFetchDAO = new UserFetchDAO();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		try {
			if(category == null || category.isEmpty())
				userInfos = userFetchDAO.getAllUser(schoolId);
			else if(category.equalsIgnoreCase(Role.TEACHER.name())) {
				userInfos = userFetchDAO.getAllTeachers(schoolId);
			} else if(category.equals(Role.GUARDIAN.name())) {
				userInfos = userFetchDAO.getAllGuardian(schoolId);
			} else if(category.equalsIgnoreCase(Role.STUDENT.name())) {
				userInfos = userFetchDAO.getAllStudents(schoolId);
			} else {
				userInfos = userFetchDAO.getAllOtherUser(schoolId);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			//redirect to particular page
		}
		return ok(allUser.render(userInfos));
	}
}
