package modules;

import javax.inject.Singleton;

import dao.MessageDAO;
import dao.impl.RedisMessageDAO;

public class RedisModule extends DependencyModule {

	@Override
	protected void configure() {
		super.configure();
		bind(MessageDAO.class).to(RedisMessageDAO.class).in(Singleton.class);
	}
}
