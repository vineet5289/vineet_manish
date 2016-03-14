package controllers;

import mailer.MailerService;
import play.data.Form;
import play.mvc.Result;

import views.forms.*;
import views.html.*;

public class Application extends CustomController {
	public Result sendMailTest() {
		MailerService mailerService = new MailerService();
		mailerService.sendEmail();
		return ok("send ...");
	}
	
	public Result dashBoard(){
		return ok(dashBoard3.render());
	}
	
	
	
}
