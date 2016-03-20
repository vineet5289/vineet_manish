package actors;

import javax.inject.Inject;

import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class MailerActor extends UntypedActor {

//	public static Props props = Props.create(new MailerActor());
//	@Inject MailerClient mailerClient;
	MailerClient mailerClient;
	@Inject
	public MailerActor(MailerClient mailerClient) {

		System.out.println("inside MailerActor constructor");

		this.mailerClient = mailerClient;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof NewSchoolRequest) {
			System.out.println("inside mailer sctor 1");
			Email email = new Email();
			System.out.println("inside mailer sctor 2");
			email.setSubject("Activation Link");
			System.out.println("inside mailer sctor 3");
//			email.setFrom("vineet5289@gmail.com");
			System.out.println("inside mailer sctor 4");
			email.addTo("niet.vineet@gmail.com");
			System.out.println("inside mailer sctor 5");
			email.setBodyText("hello");
			mailerClient.send(email);
			System.out.println("inside mailer sctor 6");
		}
		
	}
	

	public static Props props(final MailerClient mailerClient) {
	    return Props.create(new Creator<MailerActor>() {
	      private static final long serialVersionUID = 1L;
	      @Override
	      public MailerActor create() throws Exception {
	        return new MailerActor(mailerClient);
	      }
	    });
	  }

}
