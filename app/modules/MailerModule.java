package modules;

import com.google.inject.AbstractModule;

import play.libs.akka.AkkaGuiceSupport;
import actors.MailerActor;
import actors.MessageActor;

public class MailerModule extends AbstractModule implements AkkaGuiceSupport{
	@Override
	protected void configure() {
//		super.configure();
		bindActor(MailerActor.class, "srp");
//		bindActor(MessageActor.class, "srp");
	}
}
