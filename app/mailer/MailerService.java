package mailer;

import javax.inject.Inject;

import play.Play;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

public class MailerService {
	final String senderMail = Play.application().configuration().getString("play.mailer.user");
	@Inject MailerClient mailerClient;

	public void sendEmail() {
		Email email = new Email();
		email.setSubject("Activation Link");
		System.out.println("senderMail = >" + senderMail);
		email.setFrom(senderMail);
		email.addTo("vineet5289@gmail.com");
		email.setBodyText("hello");
		mailerClient.send(email);
//		email.setAttachments();
	}
}
