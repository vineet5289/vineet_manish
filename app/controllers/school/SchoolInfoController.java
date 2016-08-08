package controllers.school;

import play.data.Form;
import play.mvc.Result;
import views.forms.school.FirstTimeSchoolUpdateForm;
import views.forms.school.SchoolGeneralInfoFrom;
import views.forms.school.SchoolHeaderInfoForm;
import views.forms.school.SchoolShiftAndClassTimingInfoForm;
import views.html.viewClass.School.SchoolProfile;
import views.html.viewClass.School.editSchoolInfo;
import controllers.routes;
import dao.school.SchoolProfileInfoDAO;
import enum_package.SessionKey;
public class SchoolInfoController extends ClassController {

	/*
	 * check usernaem and auth key validation
	 * current shift
	 * userType=School
	 * 
	 * */
	public Result getProfileInfo() {
		SchoolHeaderInfoForm schoolHeaderInfo = null;
		SchoolGeneralInfoFrom schoolGeneralInfo = null;
		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = null;
		try{
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm(1l);
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
			schoolShiftAndClassTimingInfo = new SchoolShiftAndClassTimingInfoForm();
//			schoolShiftAndClassTimingInfo = schoolProfileInfoDAO.getSchoolShiftAndClassTimingInfoForm(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolHeaderInfo == null || schoolGeneralInfo == null || schoolShiftAndClassTimingInfo == null) {
			// return to some error page
		}
		Form<SchoolGeneralInfoFrom> schoolGeneralInfoForm = Form.form(SchoolGeneralInfoFrom.class).fill(schoolGeneralInfo);
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).fill(schoolHeaderInfo);
		Form<SchoolShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(SchoolShiftAndClassTimingInfoForm.class).fill(schoolShiftAndClassTimingInfo);

		System.out.println("schoolGeneralInfoForm=> " + schoolGeneralInfoForm);
		System.out.println("schoolHeaderInfoForm=> " + schoolHeaderInfoForm);
		System.out.println("schoolShiftAndClassTimingInfoForm=> " + schoolShiftAndClassTimingInfoForm);
		return ok(SchoolProfile.render(schoolGeneralInfoForm, schoolHeaderInfoForm, schoolShiftAndClassTimingInfoForm));
	}

	//auth + only superadmin, schoolId must present
	public Result getGeneralInfo() {
		SchoolGeneralInfoFrom schoolGeneralInfo = null;
		try{
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolGeneralInfo == null) {
			flash("error", "Error during school information update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}

		Form<SchoolGeneralInfoFrom> schoolGeneralInfoFrom = Form.form(SchoolGeneralInfoFrom.class).fill(schoolGeneralInfo);
		System.out.println("general Form errors ******" + schoolGeneralInfoFrom);
		return ok(editSchoolInfo.render(schoolGeneralInfoFrom));
	}

	//auth + only superadmin, schoolId must present
	public Result updateGeneralInfo() {
		Form<SchoolGeneralInfoFrom> schoolGeneralInfoFrom = Form.form(SchoolGeneralInfoFrom.class).bindFromRequest();
		if (schoolGeneralInfoFrom == null || schoolGeneralInfoFrom.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}

		SchoolGeneralInfoFrom schoolGeneralInfo = schoolGeneralInfoFrom.get();
		if (schoolGeneralInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}
		
		String schoolId = session().get(SessionKey.SCHOOL_ID.name());

		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolGeneralInfo(schoolGeneralInfo, Long.valueOf(schoolId));
		} catch(Exception exception) {
			isUpdated = false;
			exception.printStackTrace();
		}

		if(!isUpdated) {
			flash("error", "Error during school info update.");
		} else {
			flash("success", "Successfully updated school info.");
		}
		return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
	}

	public Result getShiftInfo() {
		Form<SchoolShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(SchoolShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school class and shift information update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}
		return ok("");
	}

	public Result updateShiftInfo() {
		Form<SchoolShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(SchoolShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}

		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = schoolShiftAndClassTimingInfoForm.get();
		if (schoolShiftAndClassTimingInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}

		String schoolId = session().get(SessionKey.SCHOOL_ID.name());
		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolShiftAndClassTimingInfo(schoolShiftAndClassTimingInfo, Long.valueOf(schoolId));
		} catch(Exception exception) {
			isUpdated = false;
			exception.printStackTrace();
		}

		if(!isUpdated) {
			flash("error", "Error during school info update.");
		} else {
			flash("success", "Successfully updated school info.");
		}
		return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
	}

	public Result getHeaderInfo() {
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school ionformation update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}
		return ok("");
	}

	public Result updateHeaderInfo() {
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}

		SchoolHeaderInfoForm schoolHeaderInfo = schoolHeaderInfoForm.get();
		if (schoolHeaderInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
		}

		String schoolId = session().get(SessionKey.SCHOOL_ID.name());
		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolHeaderInfo(schoolHeaderInfo, Long.valueOf(schoolId));
		} catch(Exception exception) {
			isUpdated = false;
			exception.printStackTrace();
		}

		if(!isUpdated) {
			flash("error", "Error during school info update.");
		} else {
			flash("success", "Successfully updated school info.");
		}
		return redirect(controllers.school.routes.SchoolInfoController.getProfileInfo());
	}

	//session validation
	public Result getSchoolMandInfo() {
		Form<FirstTimeSchoolUpdateForm> loginForm = Form.form(FirstTimeSchoolUpdateForm.class);
		return ok("inside get mand info");
	}

	//session validation
	public Result updateSchoolMandInfo() {
		Form<FirstTimeSchoolUpdateForm> firstTimeSchoolUpdateForm = Form.form(FirstTimeSchoolUpdateForm.class).bindFromRequest();
		if (firstTimeSchoolUpdateForm == null || firstTimeSchoolUpdateForm.hasErrors()) {
			flash("error", "Some parameters are missing.");
			return redirect(controllers.school.routes.SchoolInfoController.getSchoolMandInfo());
		}

		String schoolId = session().get(SessionKey.SCHOOL_ID.name());
		String userName = session().get(SessionKey.USER_NAME.name());
		FirstTimeSchoolUpdateForm firstTimeSchoolUpdate = firstTimeSchoolUpdateForm.get();
		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolMandInfo(firstTimeSchoolUpdate, Long.valueOf(schoolId), userName);
		} catch(Exception exception) {
			flash("error", "Some problem occur during update.");
			exception.printStackTrace();
			return redirect(controllers.school.routes.SchoolInfoController.getSchoolMandInfo());
		}
		if(!isUpdated) {
			flash("error", "Please check values of all mandatory fields.");
			return redirect(controllers.school.routes.SchoolInfoController.getSchoolMandInfo());
		} else {
			flash("success", "School informations updated successfully.");
			return redirect(routes.SRPController.index());
		}
	}
}
