package controllers.institute;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import controllers.routes;
import dao.InstituteBoardDAO;
import dao.school.SchoolProfileInfoDAO;
import enum_package.AttendenceTypeEnum;
import enum_package.InstituteDaoProcessStatus;
import enum_package.SchoolClassEnum;
import enum_package.SessionKey;
import enum_package.WeekDayEnum;
import models.SchoolType;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.forms.institute.FirstTimeInstituteUpdateForm;
import views.forms.institute.InstituteFormData;
import views.forms.institute.InstituteGeneralInfoForm;
import views.forms.institute.InstituteHeaderInfoForm;
import views.forms.institute.InstituteShiftAndClassTimingInfoForm;
import views.html.viewClass.School.SchoolProfile;
import views.html.viewClass.School.editSchoolInfo;
import views.html.viewClass.School.schoolMandataryInfo;

public class InstituteInfoController extends ClassController {

	@Inject
	private FormFactory formFactory;

	@Inject private SchoolProfileInfoDAO schoolProfileInfoDAO;
  @Inject private InstituteBoardDAO instituteBoardDAO;
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
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm(1l);
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
			schoolShiftAndClassTimingInfo = schoolProfileInfoDAO.getSchoolShiftAndClassTimingInfoForm(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolHeaderInfo == null || schoolGeneralInfo == null || schoolShiftAndClassTimingInfo == null) {
			System.out.println("any of the value is null");
		}
		Form<InstituteGeneralInfoForm> schoolGeneralInfoForm = formFactory.form(InstituteGeneralInfoForm.class).fill(schoolGeneralInfo);
		Form<InstituteHeaderInfoForm> schoolHeaderInfoForm = formFactory.form(InstituteHeaderInfoForm.class).fill(schoolHeaderInfo);
		Form<InstituteShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = formFactory.form(InstituteShiftAndClassTimingInfoForm.class).fill(schoolShiftAndClassTimingInfo);

		System.out.println("schoolGeneralInfoForm=> " + schoolGeneralInfoForm);
		System.out.println("schoolHeaderInfoForm=> " + schoolHeaderInfoForm);
		System.out.println("schoolShiftAndClassTimingInfoForm=> " + schoolShiftAndClassTimingInfoForm);
		return ok(SchoolProfile.render(schoolGeneralInfoForm, schoolHeaderInfoForm, schoolShiftAndClassTimingInfoForm));
	}

	//auth + only superadmin, schoolId must present
	public Result getGeneralInfo() {
		InstituteGeneralInfoForm schoolGeneralInfo = null;
		try{
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom(1l);
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolGeneralInfo == null) {
			flash("error", "Error during school information update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		Form<InstituteGeneralInfoForm> schoolGeneralInfoFrom = formFactory.form(InstituteGeneralInfoForm.class).fill(schoolGeneralInfo);
		System.out.println("general Form errors ******" + schoolGeneralInfoFrom);
		return ok(editSchoolInfo.render(schoolGeneralInfoFrom));
	}

	//auth + only superadmin, schoolId must present
	public Result updateGeneralInfo() {
		Form<InstituteGeneralInfoForm> schoolGeneralInfoFrom = formFactory.form(InstituteGeneralInfoForm.class).bindFromRequest();
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
		
		String schoolId = session().get(SessionKey.instituteid.name());

		boolean isUpdated = false;
		try {
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
		Form<InstituteShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = formFactory.form(InstituteShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school class and shift information update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}
		return ok("");
	}

	public Result updateShiftInfo() {
		Form<InstituteShiftAndClassTimingInfoForm> schoolShiftAndClassTimingInfoForm = formFactory.form(InstituteShiftAndClassTimingInfoForm.class).bindFromRequest();
		if (schoolShiftAndClassTimingInfoForm == null || schoolShiftAndClassTimingInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		InstituteShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfo = schoolShiftAndClassTimingInfoForm.get();
		if (schoolShiftAndClassTimingInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		String schoolId = session().get(SessionKey.instituteid.name());
		boolean isUpdated = false;
		try {
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
		Form<InstituteHeaderInfoForm> schoolHeaderInfoForm = formFactory.form(InstituteHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school ionformation update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}
		return ok("");
	}

	public Result updateHeaderInfo() {
		Form<InstituteHeaderInfoForm> schoolHeaderInfoForm = formFactory.form(InstituteHeaderInfoForm.class).bindFromRequest();
		if (schoolHeaderInfoForm == null || schoolHeaderInfoForm.hasErrors()) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		InstituteHeaderInfoForm schoolHeaderInfo = schoolHeaderInfoForm.get();
		if (schoolHeaderInfo == null) {
			flash("error", "Error during school info update.");
			return redirect(controllers.institute.routes.InstituteInfoController.getProfileInfo());
		}

		String schoolId = session().get(SessionKey.instituteid.name());
		boolean isUpdated = false;
		try {
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
		String schoolId = session().get(SessionKey.instituteid.name());
		Form<FirstTimeInstituteUpdateForm> firstTimeUpdateForm = formFactory.form(FirstTimeInstituteUpdateForm.class);
		Map<Integer, String> dayToWeekMap = WeekDayEnum.getDayToWeekMap();
		Map<Integer, String> classList = SchoolClassEnum.seqToClassNameMapping();
		Map<AttendenceTypeEnum, String> attendenceType = AttendenceTypeEnum.getEnumToDisplayNameMap();
		InstituteFormData instituteFormData = null;
		try {
			instituteFormData = schoolProfileInfoDAO.getNumberOfInstituteInGivenGroup(Long.valueOf(schoolId));
		} catch (NumberFormatException | SQLException exception) {
			exception.printStackTrace();
			instituteFormData = null;
		}

		if(instituteFormData != null && instituteFormData.getGroupOfInstitute().equalsIgnoreCase("single")
				&& instituteFormData.getNoOfInstitute() == 1) {
			Map<String, String> schoolBoards = new TreeMap<String, String>();
			schoolBoards.put("cbse", "CBSE");
			schoolBoards.put("icse", "ICSE");
			schoolBoards.put("ib", "International Baccalaureate");
			String affiliatedTo = instituteFormData.getInstituteState();
			String boardDisplayName = instituteBoardDAO.getDisplayNameGivenAffiliatedTo(affiliatedTo);
			String boradCode = instituteBoardDAO.getBoardCodeGivenDisplayName(boardDisplayName);
			schoolBoards.put(boradCode, boardDisplayName);
			session(SessionKey.numerofinstituteingroup.name(), instituteFormData.getNoOfInstitute() + "");
			return ok(schoolMandataryInfo.render(firstTimeUpdateForm, dayToWeekMap, classList, attendenceType, schoolBoards, SchoolType.schoolTypeToValue));
		}

		flash("error", "Some service problem occur during request process. Please login again.");
		return redirect(controllers.login_logout.routes.LoginController.logout());
	}

	//session validation
	public Result updateInstituteMandInfo() {
		System.out.println("======>");
		Form<FirstTimeInstituteUpdateForm> firstTimeSchoolUpdateForm = formFactory.form(FirstTimeInstituteUpdateForm.class).bindFromRequest();
		System.out.println("************firstTimeSchoolUpdateForm$$$$$$$$" + firstTimeSchoolUpdateForm);
		if (firstTimeSchoolUpdateForm == null || firstTimeSchoolUpdateForm.hasErrors()) {
			flash("error", "Some parameters are missing.");
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		}

		String schoolId = session().get(SessionKey.instituteid.name());
		String userName = session().get(SessionKey.username.name());
		FirstTimeInstituteUpdateForm firstTimeSchoolUpdate = firstTimeSchoolUpdateForm.get();
		InstituteDaoProcessStatus instituteDaoProcessStatus = InstituteDaoProcessStatus.invalidschool;
		try {
//			instituteDaoProcessStatus = schoolProfileInfoDAO.updateSchoolMandInfo(firstTimeSchoolUpdate, Long.valueOf(schoolId), userName);
		} catch(Exception exception) {
			flash("error", "Some problem occur during update.");
			exception.printStackTrace();
			instituteDaoProcessStatus = InstituteDaoProcessStatus.servererror;
		}
		if(InstituteDaoProcessStatus.validschool != instituteDaoProcessStatus) {
			flash("error", instituteDaoProcessStatus.name());
			return redirect(controllers.institute.routes.InstituteInfoController.getInstituteMandInfo());
		} else {
			flash("success", "School informations updated successfully.");
			return redirect(routes.SRPController.index());
		}
	}
}
