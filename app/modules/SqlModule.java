package modules;

import javax.inject.Singleton;

import dao.MessageDAO;
import dao.impl.SqlMessageDAOImpl;

public class SqlModule extends DependencyModule {
	@Override
	protected void configure() {
		super.configure();
		bind(MessageDAO.class).to(SqlMessageDAOImpl.class).in(Singleton.class);
	}
}
