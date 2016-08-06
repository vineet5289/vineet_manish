package controllers.school;

import dao.school.SchoolProfileInfoDAO;
import play.data.Form;
import play.mvc.Result;
import views.forms.school.SchoolGeneralInfoFrom;
import views.forms.school.SchoolHeaderInfoForm;
import views.forms.school.SchoolShiftAndClassTimingInfoForm;

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
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm();
			schoolGeneralInfo = schoolProfileInfoDAO.getSchoolGeneralInfoFrom();
		} catch(Exception exception) {
			System.out.println("some error happen");
		}
		if(schoolHeaderInfo == null || schoolGeneralInfo == null) {
			// return to some error page
		}
		Form<SchoolGeneralInfoFrom> SchoolGeneralInfoFrom = Form.form(SchoolGeneralInfoFrom.class).fill(schoolGeneralInfo);
		Form<SchoolHeaderInfoForm> schoolHeaderInfoForm = Form.form(SchoolHeaderInfoForm.class).fill(schoolHeaderInfo);
		return ok("comming from general info");
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
			schoolHeaderInfo = schoolProfileInfoDAO.getSchoolHeaderInfoForm();
			schoolShiftAndClassTimingInfo = schoolProfileInfoDAO.getSchoolShiftAndClassTimingInfoForm();
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

}
