package security.authorization.handler.dynamic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import dao.UserLoginDAO;
import play.Logger;
import play.mvc.Http;
import be.objectify.deadbolt.java.AbstractDynamicResourceHandler;
import be.objectify.deadbolt.java.DeadboltAnalyzer;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Permission;
import enum_package.InstituteUserRole;

public class EmployeeDynamicResourceHandler  implements DynamicResourceHandler {

	@Inject private UserLoginDAO userLoginDAO;

	private static final Map<String, Optional<DynamicResourceHandler>> HANDLERS = new HashMap<String, Optional<DynamicResourceHandler>>();

	private static final DynamicResourceHandler DENY = new DynamicResourceHandler()
	{
		@Override
		public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler,
				Http.Context context) {
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
		HANDLERS.put("viewEmployeeProfile", Optional.of(new AbstractDynamicResourceHandler() {
			public CompletionStage<Boolean> isAllowed(final String name,final Optional<String> meta,
					final DeadboltHandler deadboltHandler, final Http.Context context) {

				return deadboltHandler.getSubject(context).thenApplyAsync(subjectOption -> {
					final boolean[] allowed = {false};
					Logger.info("EmployeeDynamicResourceHandler: calling HANDLERS.viewEmployeeProfile");
					DeadboltAnalyzer deadboltAnalyzer = new DeadboltAnalyzer();
					
					if (deadboltAnalyzer.hasRole(subjectOption, InstituteUserRole.instituteadmin.getName())
							|| deadboltAnalyzer.hasRole(subjectOption, InstituteUserRole.institutegroupadmin.getName())
							|| deadboltAnalyzer.hasRole(subjectOption, InstituteUserRole.employee.getName())
							|| deadboltAnalyzer.hasRole(subjectOption, InstituteUserRole.student.getName())
							|| deadboltAnalyzer.hasRole(subjectOption, InstituteUserRole.guardian.getName())) {
						Logger.info("EmployeeDynamicResourceHandler: calling HANDLERS.viewEmployeeProfile. User has rights to view profile");
						allowed[0] = true;
					}
					return allowed[0];
				});
			}
		}));
	}

	@Override
	public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
		Logger.info("EmployeeDynamicResourceHandler: isAllowed method is called.");
		return HANDLERS.get(name).orElseGet(() -> {
			Logger.info("EmployeeDynamicResourceHandler: isAllowed method is called. No handler is present for given name: " + name);
			return DENY;
		}).isAllowed(name, meta, deadboltHandler, ctx);
	}

	@Override
	public CompletionStage<Boolean> checkPermission(String permissionValue, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
		return deadboltHandler.getSubject(ctx).thenApplyAsync(subjectOption -> {
			Logger.info("EmployeeDynamicResourceHandler: checkPermission method is called with permissionValue: ." + permissionValue);
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