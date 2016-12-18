package security.authorization;

import javax.inject.Singleton;

import be.objectify.deadbolt.java.cache.HandlerCache;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

public class CustomDeadboltHook extends Module {

	@Override
	public Seq<Binding<?>> bindings(Environment arg0, Configuration arg1) {
		System.out.println("inside CustomDeadboltHook ");
		return seq(bind(HandlerCache.class).to(MyHandlerCache.class).in(Singleton.class));
	}

}
