package security.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import be.objectify.deadbolt.java.ConstraintLogic;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.actions.RestrictAction;
import be.objectify.deadbolt.java.cache.HandlerCache;
import play.Configuration;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class CustomPermissionAction extends Action<CustomPermission> {
//	final HandlerCache handlerCache;
//
//    final Configuration playConfig;
//
//    final ExecutionContextProvider ecProvider;
//
//    private final ConstraintLogic constraintLogic;
//
//    @Inject
//    public CustomPermissionAction(final HandlerCache handlerCache,
//                                final Configuration playConfig,
//                                final ExecutionContextProvider ecProvider,
//                                final ConstraintLogic constraintLogic)
//    {
//    	System.out.println("****** CustomPermissionAction.constructor *********s");
//    	if(handlerCache == null) {
//    		System.out.println("yes ******** handlerCache");
//    	}
//    	if(playConfig == null) {
//    		System.out.println("yes ******** playConfig");
//    	}
//    	if(ecProvider == null) {
//    		System.out.println("yes ******** ecProvider");
//    	}
//    	if(constraintLogic == null) {
//    		System.out.println("yes ******** constraintLogic");
//    	}
//
//        this.handlerCache = handlerCache;
//        this.playConfig = playConfig;
//        this.ecProvider = ecProvider;
//        this.constraintLogic = constraintLogic;
//    }

	@Override
	public CompletionStage<Result> call(Context context) {
		System.out.println("******** inside CustomPermissionAction.call ********");
//		if(configuration == null) {
//			System.out.println("yes ****** configuration");
//		}
//		System.out.println("configuration.config() => " + configuration.config());
//		final CustomPermission outerConfig = configuration;
//		RestrictAction restrictionsAction = new RestrictAction(handlerCache, playConfig, configuration.config(),
//                this.delegate, ecProvider, constraintLogic) {
//			  @Override
//	            public List<String[]> getRoleGroups()
//	            {
//	            	System.out.println("****** CustomPermissionAction.call.getRoleGroups *********");
//	                List<String[]> roleGroups = new ArrayList<String[]>();
//	                String[] group = new String[1];
//	                group[0] = outerConfig.value();
//	                roleGroups.add(group);
////	                for (RoleGroup roleGroup : outerConfig.value())
////	                {
////	                    MyRole[] value = roleGroup.value();
////	                    String[] group = new String[value.length];
////	                    for (int i = 0; i < value.length; i++)
////	                    {
////	                        group[i] = value[i].getName();
////	                    }
////	                    roleGroups.add(group);
////	                }
//	                return roleGroups;
//	            }
//		};
//
//		if(context == null) {
//			System.out.println("yes CustomPermissionAction is null");
//		}
//		return restrictionsAction.call(context);
		context.args.put("UpdateContext", configuration.value());
		return delegate.call(context);
	}

}
