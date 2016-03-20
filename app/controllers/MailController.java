package controllers;

import javax.inject.Inject;

import mailer.MailerService;
import play.mvc.Result;

public class MailController extends CustomController {
	@Inject
	private MailerService mailerService;
	public Result sendMailTest() {
		mailerService.sendEmail();
		return ok("send ...");
	}
	
}
