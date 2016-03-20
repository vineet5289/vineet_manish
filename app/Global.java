import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import actors.AlertActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Request;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.duration.Duration;


public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		System.out.println("**************in side global start **********");
		initialized();
		System.out.println("**************out side global start **********");
	}

	@Override
	public void onStop(Application app) {
		
	}
	@Override
	public Action onRequest(Request request, Method method) {
		return super.onRequest(request, method);
	}

	@Override
	public Promise<Result> onError(RequestHeader requestHeader, Throwable t) {
	
		return Promise.<Result>pure(Results.internalServerError("get back to you"));
	}

	private void initialized() {
//		System.out.println("**************in side global start **********");
//		scheduleJobs();
//		System.out.println("**************out side global start **********");
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

	private static List<Cancellable> cancellableLists = new ArrayList<Cancellable>();
	final ActorSystem actorSystem = ActorSystem.create("srp");
	final ActorRef alertActor = actorSystem.actorOf(AlertActor.props);
}
