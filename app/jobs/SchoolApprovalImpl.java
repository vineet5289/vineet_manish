package jobs;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import scala.concurrent.duration.Duration;
import actors.AlertActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;


@Singleton
public class SchoolApprovalImpl implements SchoolApproval {
	final ActorSystem actorSystem = ActorSystem.create("srp");
	final ActorRef alertActor = actorSystem.actorOf(AlertActor.props);

	public SchoolApprovalImpl() {
		
		System.out.println("start schedule school approval alert job ");
		scheduleJobs();
		System.out.println("done schedule school approval alert job ");
	}

	private Cancellable scheduleJobs() {
		return actorSystem.scheduler().schedule(
				Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
				Duration.create(6, TimeUnit.MINUTES),     //Frequency 30 minutes
				alertActor,
				"alert",
				actorSystem.dispatcher(),
				null
				);
	}
}
