package controllers.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.UserInfo;
import controllers.CustomController;
import dao.UserFetchDAO;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;

public class UserController extends CustomController {

	@Security.Authenticated(ActionAuthenticator.class)
	public Result getAllUser(Long schoolId) {
		if(schoolId <= 0) {
			// redirect to error page
		}
		UserFetchDAO userFetchDAO = new UserFetchDAO();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		try {
			userInfos = userFetchDAO.getAllUser(schoolId);
		} catch (SQLException exception) {
			exception.printStackTrace();
			//redirect to particular page
		}
		return ok("hello");
	}
}
