package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewClass.testPage;
import views.html.viewClass.testAddClass;
import views.html.viewClass.test2;
import views.html.viewClass.test3;
import views.html.viewClass.test4;
import views.html.viewClass.testAcessRight;
import java.util.Map;

public class CustomController extends Controller {
	public Result testRoute() {
		return ok(testAddClass.render());
	}
	
	public Result postRoute() {
	final Map<String, String[]> data = request().body().asFormUrlEncoded();
	   String name=data.get("sendToAll")[0];
	   String message=data.get("messageToSend")[0];
		return ok("returned "+ name +" "+ message);
	}
}
