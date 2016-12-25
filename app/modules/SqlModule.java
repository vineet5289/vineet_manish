package modules;

import javax.inject.Singleton;

import dao.InstituteBoardDAO;
import dao.MessageDAO;
import dao.impl.SqlMessageDAOImpl;
import models.SchoolBoard;

public class SqlModule extends DependencyModule {
	@Override
	protected void configure() {
		super.configure();
		bind(MessageDAO.class).to(SqlMessageDAOImpl.class).in(Singleton.class);
		bind(InstituteBoardDAO.class).to(SchoolBoard.class).in(Singleton.class);
	}
}
