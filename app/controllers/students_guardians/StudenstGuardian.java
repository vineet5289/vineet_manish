package controllers.students_guardians;

import play.mvc.Result;
import controllers.CustomController;

public class StudenstGuardian extends CustomController {
	public Result preAddFressStudents() {
		return ok("students registration done.");
	}

	public Result postAddFressStudents() {
		return ok("students registration done.");
	}
}
