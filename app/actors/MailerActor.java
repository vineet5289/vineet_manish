package actors;

import javax.inject.Inject;

import play.Configuration;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.UntypedActor;

public class MailerActor extends UntypedActor {

	private static String senderMail;
	MailerClient mailerClient;
	@Inject	
	public MailerActor(MailerClient mailerClient, Configuration configure) {
		this.mailerClient = mailerClient;
		senderMail = configure.getString("play.mailer.user");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof NewSchoolRequest) {
			String mailContent = "Dear %s,\n\nThank you for using srp system for your school. Your request number id %s.\n\n"
					+ "We have received your registration request and will verify and process it shortly.\n\n. Don't not hesitate"
					+ " to contact us for any query.\n\nThanks again for you business.\n\nSRP Teams.";
			String mailSubject = "Registration confirmation email";

			NewSchoolRequest newSchoolRequest = (NewSchoolRequest)message;
			String requestRefNumber = newSchoolRequest.getReferenceNumber();
			String schoolEmailId = newSchoolRequest.getSchoolEmailId();
			String receiverName = newSchoolRequest.getSchoolName();
			String mailBody = String.format(mailContent, receiverName, requestRefNumber);
			Email email = new Email();
			email.setSubject(mailSubject);
			email.setFrom(senderMail);
			if(schoolEmailId != null && !schoolEmailId.isEmpty())
				email.addTo(schoolEmailId);

			email.setBodyText(mailBody);
			mailerClient.send(email);
		}
		
	}
}
