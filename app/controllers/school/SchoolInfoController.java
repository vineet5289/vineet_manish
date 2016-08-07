package controllers.school;

import dao.school.SchoolProfileInfoDAO;
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
	public Result getGeneralInfo() {
		SchoolHeaderInfoForm schoolHeaderInfo = null;
		SchoolGeneralInfoFrom schoolGeneralInfo = null;
		try{
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm(1l);
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolHeaderInfo == null || schoolGeneralInfo == null) {
			// return to some error page
		}
		Form<SchoolGeneralInfoFrom> schoolGeneralInfoForm = Form.form(SchoolGeneralInfoFrom.class).fill(schoolGeneralInfo);
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).fill(schoolHeaderInfo);
		System.out.println("schoolGeneralInfoForm=> " + schoolGeneralInfoForm);
		System.out.println("schoolHeaderInfoForm=> " + schoolHeaderInfoForm);
		return ok(SchoolProfile.render(schoolGeneralInfoForm, schoolHeaderInfoForm));
	}

	/*
	 * check username and auth key validation
	 * current shift
	 * userType=School
	 * 
	 * */
	public Result getShiftInfo() {
		SchoolHeaderInfoForm schoolHeaderInfo = null;
		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = null;
		try{
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm(1l);
			schoolShiftAndClassTimingInfo = schoolProfileInfoDAO.getSchoolShiftAndClassTimingInfoForm(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}

		if(schoolHeaderInfo == null || schoolHeaderInfo == null) {
			// return to some error page
		}
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).fill(schoolHeaderInfo);
		Form<SchoolShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(SchoolShiftAndClassTimingInfoForm.class).fill(schoolShiftAndClassTimingInfo);
		return ok("comming from shift info");
	}

	//auth + only superadmin
	public Result updateGeneralInfo() {
		Form<SchoolGeneralInfoFrom> schoolGeneralInfoFrom = Form.form(SchoolGeneralInfoFrom.class).bindFromRequest();
		if (schoolGeneralInfoFrom == null || schoolGeneralInfoFrom.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
		}

		SchoolGeneralInfoFrom schoolGeneralInfo = schoolGeneralInfoFrom.get();
		if (schoolGeneralInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
		}

		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolGeneralInfo(schoolGeneralInfo);
		} catch(Exception exception) {
			isUpdated = false;
			exception.printStackTrace();
		}

		if(!isUpdated) {
			flash("error", "Error during school info update.");
		} else {
			flash("success", "Successfully updated school info.");
		}
		return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
	}

	public Result updateShiftInfo() {
		Form<SchoolShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(SchoolShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
		}

		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = schoolShiftAndClassTimingInfoForm.get();
		if (schoolShiftAndClassTimingInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
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
		return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
	}

	public Result updateHeaderInfo() {
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
		}

		SchoolHeaderInfoForm schoolHeaderInfo = schoolHeaderInfoForm.get();
		if (schoolHeaderInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
		}

		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolHeaderInfo(schoolHeaderInfo);
		} catch(Exception exception) {
			isUpdated = false;
			exception.printStackTrace();
		}

		if(!isUpdated) {
			flash("error", "Error during school info update.");
		} else {
			flash("success", "Successfully updated school info.");
		}
		return redirect(controllers.school.routes.SchoolInfoController.getGeneralInfo());
	}
}
