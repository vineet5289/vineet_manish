package controllers.institute;

import java.util.List;

import play.data.Form;
import play.mvc.Result;
import views.forms.institute.FirstTimeInstituteUpdateForm;
import views.forms.institute.InstituteGeneralInfoForm;
import views.forms.institute.InstituteHeaderInfoForm;
import views.forms.institute.InstituteShiftAndClassTimingInfoForm;
import views.html.viewClass.School.SchoolProfile;
import views.html.viewClass.School.editSchoolInfo;
import views.html.viewClass.School.schoolMandataryInfo;
import controllers.routes;
import dao.school.SchoolProfileInfoDAO;
import enum_package.AttendenceTypeEnum;
import enum_package.SchoolClassEnum;
import enum_package.SessionKey;
import enum_package.WeekDayEnum;

public class InstituteInfoController extends ClassController {

	/*
	 * check usernaem and auth key validation
	 * current shift
	 * userType=School
	 * 
	 * */
	public Result getProfileInfo() {
		InstituteHeaderInfoForm schoolHeaderInfo = null;
		InstituteGeneralInfoForm schoolGeneralInfo = null;
		InstituteShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = null;
		try{
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm(1l);
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
			schoolShiftAndClassTimingInfo = schoolProfileInfoDAO.getSchoolShiftAndClassTimingInfoForm(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolHeaderInfo == null || schoolGeneralInfo == null || schoolShiftAndClassTimingInfo == null) {
			System.out.println("any of the value is null");
		}
		Form<InstituteGeneralInfoForm> schoolGeneralInfoForm = Form.form(InstituteGeneralInfoForm.class).fill(schoolGeneralInfo);
		Form<InstituteHeaderInfoForm> schoolHeaderInfoForm = Form.form(InstituteHeaderInfoForm.class).fill(schoolHeaderInfo);
		Form<InstituteShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(InstituteShiftAndClassTimingInfoForm.class).fill(schoolShiftAndClassTimingInfo);

		System.out.println("schoolGeneralInfoForm=> " + schoolGeneralInfoForm);
		System.out.println("schoolHeaderInfoForm=> " + schoolHeaderInfoForm);
		System.out.println("schoolShiftAndClassTimingInfoForm=> " + schoolShiftAndClassTimingInfoForm);
		return ok(SchoolProfile.render(schoolGeneralInfoForm, schoolHeaderInfoForm, schoolShiftAndClassTimingInfoForm));
	}

	//auth + only superadmin, schoolId must present
	public Result getGeneralInfo() {
		InstituteGeneralInfoForm schoolGeneralInfo = null;
		try{
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolGeneralInfo == null) {
			flash("error", "Error during school information update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		Form<InstituteGeneralInfoForm> schoolGeneralInfoFrom = Form.form(InstituteGeneralInfoForm.class).fill(schoolGeneralInfo);
		System.out.println("general Form errors ******" + schoolGeneralInfoFrom);
		return ok(editSchoolInfo.render(schoolGeneralInfoFrom));
	}

	//auth + only superadmin, schoolId must present
	public Result updateGeneralInfo() {
		Form<InstituteGeneralInfoForm> schoolGeneralInfoFrom = Form.form(InstituteGeneralInfoForm.class).bindFromRequest();
		System.out.println("inside update => " + schoolGeneralInfoFrom);
		if (schoolGeneralInfoFrom == null || schoolGeneralInfoFrom.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		InstituteGeneralInfoForm schoolGeneralInfo = schoolGeneralInfoFrom.get();
		if (schoolGeneralInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
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
		return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
	}

	public Result getShiftInfo() {
		Form<InstituteShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(InstituteShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school class and shift information update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}
		return ok("");
	}

	public Result updateShiftInfo() {
		Form<InstituteShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = Form.form(InstituteShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		InstituteShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = schoolShiftAndClassTimingInfoForm.get();
		if (schoolShiftAndClassTimingInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
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
		return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
	}

	public Result getHeaderInfo() {
		Form<InstituteHeaderInfoForm> schoolHeaderInfoForm = Form.form(InstituteHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school ionformation update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}
		return ok("");
	}

	public Result updateHeaderInfo() {
		Form<InstituteHeaderInfoForm> schoolHeaderInfoForm = Form.form(InstituteHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		InstituteHeaderInfoForm schoolHeaderInfo = schoolHeaderInfoForm.get();
		if (schoolHeaderInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
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
		return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
	}

	//session validation
	public Result getInstituteMandInfo() {
		Form<FirstTimeInstituteUpdateForm> firstTimeUpdateForm = Form.form(FirstTimeInstituteUpdateForm.class);
		List<String> weekList = WeekDayEnum.getWeekDisplayName();
		List<String> classList = SchoolClassEnum.getClassDisplayName();
		List<String> attendenceType = AttendenceTypeEnum.getAttendenceTypeDisplayName();
		return ok(schoolMandataryInfo.render(firstTimeUpdateForm, weekList, classList, attendenceType));
	}

	//session validation
	public Result updateInstituteMandInfo() {
		Form<FirstTimeInstituteUpdateForm> firstTimeSchoolUpdateForm = Form.form(FirstTimeInstituteUpdateForm.class).bindFromRequest();
		System.out.println("************firstTimeSchoolUpdateForm$$$$$$$$" + firstTimeSchoolUpdateForm);
		if (firstTimeSchoolUpdateForm == null || firstTimeSchoolUpdateForm.hasErrors()) {
			flash("error", "Some parameters are missing.");
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		}

		String schoolId = session().get(SessionKey.SCHOOL_ID.name());
		String userName = session().get(SessionKey.USER_NAME.name());
		FirstTimeInstituteUpdateForm firstTimeSchoolUpdate = firstTimeSchoolUpdateForm.get();
		boolean isUpdated = false;
		try {
			SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
			isUpdated = schoolProfileInfoDAO.updateSchoolMandInfo(firstTimeSchoolUpdate, Long.valueOf(schoolId), userName);
		} catch(Exception exception) {
			flash("error", "Some problem occur during update.");
			exception.printStackTrace();
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		}
		if(!isUpdated) {
			flash("error", "Please check values of all mandatory fields.");
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		} else {
			flash("success", "School informations updated successfully.");
			return redirect(routes.SRPController.index());
		}
	}
}