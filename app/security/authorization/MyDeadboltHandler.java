package security.authorization;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.libs.concurrent.HttpExecution;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import views.html.defaultpages.unauthorized;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;

public class MyDeadboltHandler extends AbstractDeadboltHandler {

//	private AuthenticationSupport as;
//	private AuthorizedUser authorizedUser;
	
	@Inject
	public MyDeadboltHandler(ExecutionContextProvider ecProvider) {
		super(ecProvider);
		System.out.println("inside MyDeadboltHandler===>");
	}

	@Override
	public CompletionStage<Optional<Result>> beforeAuthCheck(final Http.Context context) {
		System.out.println("=========> fuck come here *******");
		return CompletableFuture.completedFuture(Optional.empty());
	}

	@Override
	public CompletionStage<Optional<? extends Subject>> getSubject(final Http.Context context) {
		System.out.println("inside MyDeadboltHandler.CompletionStage.getSubject===>");
		AuthenticationSupport as = new AuthenticationSupport();
		AuthorizedUser authorizedUser = new AuthorizedUser();

		String username = as.getUsername(context);
		if(username != null) {
			return CompletableFuture.supplyAsync(() -> Optional.of(authorizedUser.getAuthorizedUser(username)));
		} else {
			Result unauthorized = as.onUnauthorized(context);
			return CompletableFuture.completedFuture(Optional.empty());
		}
	}

	@Override
	public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(final Http.Context context) {
		System.out.println("================= MyDeadboltHandler.getDynamicResourceHandler");
        return CompletableFuture.completedFuture(Optional.of(new MyDynamicResourceHandler()));
    }

	 @Override
	    public CompletionStage<Result> onAuthFailure(final Http.Context context,
	                                                 final Optional<String> content)
	    {
	        final ExecutionContext executionContext = executionContextProvider.get();
	        final ExecutionContextExecutor executor = HttpExecution.fromThread(executionContext);
	        return CompletableFuture.supplyAsync(unauthorized::render,
	                                             executor)
	                                .thenApplyAsync(Results::unauthorized,
	                                                executor);
	    }
}
