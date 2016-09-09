package security.authorization.handler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import dao.impl.RedisSessionDao;
import play.Logger;
import play.libs.concurrent.HttpExecution;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import security.authorization.handler.dynamic.EmployeeDynamicResourceHandler;
import security.authorization.subjects.InstituteAuthorizedUser;
import views.html.defaultpages.unauthorized;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;
import enum_package.SessionKey;

public class EditEmployeeDeadboltHandler extends AbstractDeadboltHandler {
	private RedisSessionDao redisSessionDao;

	@Inject
	public EditEmployeeDeadboltHandler(ExecutionContextProvider ecProvider, RedisSessionDao redisSessionDao) {
		super(ecProvider);
		this.redisSessionDao = redisSessionDao;
		Logger.info("AdminDeadboltHandler: constructor is called");
	}

	@Override
	public CompletionStage<Optional<Result>> beforeAuthCheck(final Http.Context context) {
		Logger.info("AdminDeadboltHandler: beforeAuthCheck method is called");
		return CompletableFuture.completedFuture(Optional.empty());
	}

	@Override
	public CompletionStage<Optional<? extends Subject>> getSubject(final Http.Context context) {
		Logger.info("AdminDeadboltHandler: getSubject method is called. Start fetching subject information");

		String userName = context.session().get(SessionKey.username.name());
		String userAuth = context.session().get(SessionKey.authtoken.name());
		String loginType = context.session().get(SessionKey.logintype.name());
		String loginState = context.session().get(SessionKey.loginstate.name());

		if(userName != null && userAuth != null && loginType != null && loginState != null) {
			Logger.info("AdminDeadboltHandler: getSubject method is called.");
			return CompletableFuture.supplyAsync(() -> {
				InstituteAuthorizedUser authorizedUser= InstituteAuthorizedUser.getAuthorizedUser(userName, userAuth, loginType, loginState, redisSessionDao);
				return Optional.of(authorizedUser);
				});
		} else {
			Logger.info("AdminDeadboltHandler: getSubject method is called. User authentication failed.");
			return CompletableFuture.completedFuture(Optional.empty());
		}
	}

	@Override
	public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(final Http.Context context) {
		return CompletableFuture.completedFuture(Optional.of(new EmployeeDynamicResourceHandler()));
	}

	@Override
	public CompletionStage<Result> onAuthFailure(final Http.Context context, final Optional<String> content) {
		final ExecutionContext executionContext = executionContextProvider.get();
		final ExecutionContextExecutor executor = HttpExecution.fromThread(executionContext);
		return CompletableFuture.supplyAsync(unauthorized::render,
				executor)
				.thenApplyAsync(Results::unauthorized,
						executor);
	}

}
