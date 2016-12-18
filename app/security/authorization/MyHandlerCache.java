package security.authorization;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dao.impl.RedisSessionDao;
import play.Logger;
import security.authorization.handler.EditEmployeeDeadboltHandler;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.cache.HandlerCache;

public class MyHandlerCache implements HandlerCache {

	private final Map<String, DeadboltHandler> handlers = new HashMap<String, DeadboltHandler>();

	@Inject
	public MyHandlerCache(final ExecutionContextProvider ecProvider, RedisSessionDao redisSessionDao) {
		Logger.info("MyHandlerCache: inside constructor.");
		handlers.put(HandlerKeys.DEFAULT.key, new MyDeadboltHandler(ecProvider));
		handlers.put(HandlerKeys.CUSTOME.key, new MyDeadboltHandler(ecProvider));
		handlers.put(HandlerKeys.EDITEMPLOYEE.key, new EditEmployeeDeadboltHandler(ecProvider, redisSessionDao));
		Logger.info("MyHandlerCache: returning from constructor.");
	}

	@Override
	public DeadboltHandler apply(final String key) {
		return handlers.get(key);
	}

	@Override
	public DeadboltHandler get() {
		return handlers.get(HandlerKeys.DEFAULT.key);
	}

}
