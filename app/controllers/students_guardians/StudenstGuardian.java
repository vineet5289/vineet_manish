package controllers.students_guardians;

import controllers.CustomController;
import play.mvc.Result;

public class StudenstGuardian extends CustomController {
	public Result preAddFressStudents() {
		return ok("students registration done.");
	}

	public Result postAddFressStudents() {
		return ok("students registration done.");
	}
}
