package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class AccessRightsController extends Controller {
	public Result addAccessRight() {
		return redirect(routes.SRPController.index());
	}
}
