package security.authorization;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


import play.mvc.Http;
import be.objectify.deadbolt.java.DeadboltAnalyzer;
import be.objectify.deadbolt.java.AbstractDynamicResourceHandler;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Permission;

public class MyDynamicResourceHandler implements DynamicResourceHandler {

	private static final Map<String, Optional<DynamicResourceHandler>> HANDLERS = new HashMap<String, Optional<DynamicResourceHandler>>();

	private static final DynamicResourceHandler DENY = new DynamicResourceHandler()
	{
		@Override
		public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler,
				Http.Context context) {
			System.out.println("=====>MyDynamicResourceHandler.DENY.isAllowed");
			return CompletableFuture.completedFuture(Boolean.FALSE);
		}

		@Override
		public CompletionStage<Boolean> checkPermission(String value, Optional<String> meta, DeadboltHandler deadboltHandler,
				Http.Context context) {
			System.out.println("=====>MyDynamicResourceHandler.DENY.checkPermission");
			return CompletableFuture.completedFuture(Boolean.FALSE);
		}
	};

	static {
		HANDLERS.put("viewProfile", Optional.of(new AbstractDynamicResourceHandler() {
			public CompletionStage<Boolean> isAllowed(final String name,final Optional<String> meta,
					final DeadboltHandler deadboltHandler, final Http.Context context) {

				return deadboltHandler.getSubject(context).thenApplyAsync(subjectOption -> {
					final boolean[] allowed = {false};
					System.out.println("=======> HANDLERS.isAllowed");
					if (new DeadboltAnalyzer().hasRole(subjectOption, "admin1")) {
						allowed[0] = true;
					}
					else {
						System.out.println("=======> HANDLERS.isAllowed.else*******subjectOption" + subjectOption);
						subjectOption.ifPresent(subject -> {
							System.out.println("=======> HANDLERS.isAllowed.else.isPresent");
							// for the purpose of this example, we assume a call to view profile is probably
							// a get request, so the query string is used to provide info
							Map<String, String[]> queryStrings = context.request().queryString();
							System.out.println("queryStrings" + queryStrings);
							String[] requestedNames = queryStrings.get("userName");
							requestedNames = new String[1];
							requestedNames[0] = "vineet";
							allowed[0] = requestedNames != null && requestedNames.length == 1
									&& requestedNames[0].equals(subject.getIdentifier());
						});
					}

					return allowed[0];
				});
			}
		}));
	}

	@Override
	public CompletionStage<Boolean> isAllowed(String name,
			Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
		System.out.println("=====>MyDynamicResourceHandler.isAllowed name=" + name 
				+ ", meta=" + meta);
		return HANDLERS.get(name).orElseGet(() -> {
			System.out.println("No handler available for " + name);
			return DENY;
		}).isAllowed(name, meta, deadboltHandler, ctx);
	}

	@Override
	public CompletionStage<Boolean> checkPermission(String permissionValue,
			Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
		System.out.println("=====>MyDynamicResourceHandler.checkPermission");
		return deadboltHandler.getSubject(ctx).thenApplyAsync(subjectOption -> {
            final boolean[] permissionOk = {false};
            subjectOption.ifPresent(subject -> {
                List<? extends Permission> permissions = subject.getPermissions();
                for (Iterator<? extends Permission> iterator = permissions.iterator(); !permissionOk[0] && iterator.hasNext();) {
                    Permission permission = iterator.next();
                    permissionOk[0] = permission.getValue().contains(permissionValue);
                }
            });
            return permissionOk[0];
        });
	}

}
