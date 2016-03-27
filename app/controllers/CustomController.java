package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class CustomController extends Controller {
	public Result testRoute() {
		return ok("ok");
	}
	
	public Result postRoute() {
		return ok("ok");
	}
}
