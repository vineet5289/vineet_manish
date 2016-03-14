package controllers;

import javax.inject.Inject;

import mailer.MailerService;
import play.data.Form;
import play.mvc.Result;

import views.forms.*;
import views.html.*;

public class Application extends CustomController {

	@Inject
	private MailerService mailerService;
	public Result sendMailTest() {
		mailerService.sendEmail();
		return ok("send ...");
	}
	
	public Result dashBoard(){
		return ok(dashBoard3.render());
	}
	
	
	
}
