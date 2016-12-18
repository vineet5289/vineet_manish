package security.authorization.handler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.Logger;
import play.libs.concurrent.HttpExecution;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import security.authorization.AuthorizedUser;
import security.authorization.MyDynamicResourceHandler;
import views.html.defaultpages.unauthorized;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;
import enum_package.SessionKey;

public class AdminDeadboltHandler extends AbstractDeadboltHandler {

	@Inject
	public AdminDeadboltHandler(ExecutionContextProvider ecProvider, AuthorizedUser authorizedUser) {
		super(ecProvider);
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
			Logger.info("AdminDeadboltHandler: getSubject method is called. User authentication failed.");
			return CompletableFuture.supplyAsync(() -> {
				return Optional.of(AuthorizedUser.getAuthorizedUser(userName, userAuth, loginType, loginState));
				});
		} else {
			Logger.info("AdminDeadboltHandler: getSubject method is called. User authentication failed.");
			return CompletableFuture.completedFuture(Optional.empty());
		}
	}

	@Override
	public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(final Http.Context context) {
		return CompletableFuture.completedFuture(Optional.of(new MyDynamicResourceHandler()));
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
