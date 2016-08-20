package modules;

import play.libs.akka.AkkaGuiceSupport;
import actors.MailerActor;

import com.google.inject.AbstractModule;

public class MailerModule extends AbstractModule implements AkkaGuiceSupport{
	@Override
	protected void configure() {
//		super.configure();
		bindActor(MailerActor.class, "srp");
//		bindActor(MessageActor.class, "srp");
	}
}
