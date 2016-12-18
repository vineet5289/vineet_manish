package controllers;

import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewClass.testPage;
import views.html.viewClass.testAddClass;
import views.html.viewClass.test2;
import views.html.viewClass.test3;
import views.html.viewClass.test4;
import views.html.viewClass.testAcessRight;
import views.html.viewClass.School.timeTable;

import java.util.Map;

import javax.inject.Inject;

public class CustomController extends Controller {
	@Inject
	private FormFactory formFactory;

	public Result testRoute() {
		return ok(timeTable.render());
	}
	
	public Result postRoute() {
	//final Map<String, String[]> data = request().body().asFormUrlEncoded();
	   //String name=data.get("sendToAll")[0];
	   //String message=data.get("messageToSend")[0];
		//return ok("returned "+ name +" "+ message);
		
		final Map<String, String[]> accessright = request().body().asFormUrlEncoded();
		String name=accessright.get("postaccessright")[0];
		String message=accessright.get("members")[0];
		return ok("returned "+ name +" "+ message);
		
	}

	public Result returnShiftDetails(Long shiftId) {
		//List<String> weekList = WeekDayEnum.getWeekDisplayName();
		//List<String> classList = SchoolClassEnum.getClassDisplayName();
		//List<String> attendenceType = AttendenceTypeEnum.getAttendenceTypeDisplayName();
		return ok("returned ");
		
	}
	
}
