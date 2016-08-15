package actors;

import javax.inject.Inject;

import play.Configuration;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import actors.SchoolRequestActorProtocol.AddInstituteBranch;
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
		} else if(message instanceof AddInstituteBranch) {
			String mailContent = "Dear %s,\n\nThank you for using srp system for your school. Your request for new branch is executed successfully.\n\n"
					+ "Don't not hesitate to contact us for any query. Please find below your user name and password.\n\n UserName : %s \n\nPassword : "
					+ "%s\n\n\nThanks again for you business.\n\nSRP Teams.";
			String mailSubject = "Registration confirmation email";

			AddInstituteBranch addInstituteBranch = (AddInstituteBranch)message;
			String institutePassword = addInstituteBranch.getInstitutePassword();
			String instituteEmailId = addInstituteBranch.getInstituteEmail();
			String receiverName = addInstituteBranch.getInstituteBranchName();
			String userName = addInstituteBranch.getInstituteUserName();

			String mailBody = String.format(mailContent, receiverName, userName, institutePassword);
			Email email = new Email();
			email.setSubject(mailSubject);
			email.setFrom(senderMail);
			email.addTo(instituteEmailId);

			email.setBodyText(mailBody);
			mailerClient.send(email);
		}
	}
}
