package jobs;

import javax.inject.Inject;

import com.google.inject.Injector;

import akka.actor.IndirectActorProducer;
import akka.actor.UntypedActor;

public class GenericDependencyInjector implements IndirectActorProducer  {

	@Inject
	Injector injector;
	
	final Class<? extends UntypedActor> actorClass;
	
	public GenericDependencyInjector(Class<? extends UntypedActor> actorClass) {
//		this.injector = injector;
		this.actorClass = actorClass;
	}

	@Override
	public Class<? extends UntypedActor> actorClass() {
		return actorClass;
	}

	@Override
	public UntypedActor produce() {		
		return injector.getInstance(actorClass);
	}

}
