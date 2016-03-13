package mailer;

import javax.inject.Inject;

import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

public class MailerService {
	@Inject MailerClient mailerClient;

	public void sendEmail() {
		Email email = new Email();
		email.setSubject("Activation Link");
		email.setFrom("vineet5289@gmail.com");
		email.addTo("niet.vineet@gmail.com");
		email.setBodyText("hello");
		mailerClient.send(email);
//		email.setAttachments();
	}
}
