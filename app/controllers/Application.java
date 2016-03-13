package controllers;

import mailer.MailerService;
import play.mvc.Result;

public class Application extends CustomController {
	public Result sendMailTest() {
		MailerService mailerService = new MailerService();
		mailerService.sendEmail();
		return ok("send ...");
	}
}
