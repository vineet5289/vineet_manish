package controllers.school;

import dao.school.SchoolProfileInfoDAO;
import enum_package.SessionKey;
import play.data.Form;
import play.mvc.Result;
import views.forms.school.SchoolGeneralInfoFrom;
import views.forms.school.SchoolHeaderInfoForm;
import views.forms.school.SchoolShiftAndClassTimingInfoForm;
import views.html.viewClass.School.SchoolProfile;
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
			schoolShiftAndClassTimingInfo = schoolProfileInfoDAO.getSchoolShiftAndClassTimingInfoForm(1l);
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

		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolShiftAndClassTimingInfo(schoolShiftAndClassTimingInfo);
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
}
