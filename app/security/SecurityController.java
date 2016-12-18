package security;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;

public class SecurityController extends Action.Simple {

	@Inject HttpExecutionContext ec;

	@Override
	public CompletableFuture<Result> call(Context ctx) {
		SecurityHelper helper = new SecurityHelper();
		Map<String, String> userHeaderCredential = helper
				.getAuthTokenFromRequest(ctx);
		// TODO: same as ActionAuthenticator

		Result unauthorized = Results.unauthorized("unauthorized user");
		return CompletableFuture.completedFuture(unauthorized);
	}

	private int test() {
		return 1;
	}
}
