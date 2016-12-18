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
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

public class CustomRestrictAction extends Action<CustomRestrict>{

	  final HandlerCache handlerCache;

	    final Configuration playConfig;

	    final ExecutionContextProvider ecProvider;

	    private final ConstraintLogic constraintLogic;

	    @Inject
	    public CustomRestrictAction(final HandlerCache handlerCache,
	                                final Configuration playConfig,
	                                final ExecutionContextProvider ecProvider,
	                                final ConstraintLogic constraintLogic)
	    {
	    	System.out.println("****** CustomRestrictAction.constructor *********s");
	    	if(handlerCache == null) {
	    		System.out.println("yes ******** handlerCache");
	    	}
	    	if(playConfig == null) {
	    		System.out.println("yes ******** playConfig");
	    	}
	    	if(ecProvider == null) {
	    		System.out.println("yes ******** ecProvider");
	    	}
	    	if(constraintLogic == null) {
	    		System.out.println("yes ******** constraintLogic");
	    	}

	        this.handlerCache = handlerCache;
	        this.playConfig = playConfig;
	        this.ecProvider = ecProvider;
	        this.constraintLogic = constraintLogic;
	    }

	@Override
	public CompletionStage<Result> call(Http.Context context) {
		System.out.println("****** CustomRestrictAction.call *********");
		if(configuration == null) {
			System.out.println("yes ****** configuration");
		}
System.out.println();
		final CustomRestrict outerConfig = configuration;
		RestrictAction restrictionsAction = new RestrictAction(handlerCache, playConfig, configuration.config(),
                this.delegate, ecProvider, constraintLogic) {
			  @Override
	            public List<String[]> getRoleGroups()
	            {
	            	System.out.println("****** CustomRestrictAction.call.getRoleGroups *********");
	                List<String[]> roleGroups = new ArrayList<String[]>();
	                for (RoleGroup roleGroup : outerConfig.value())
	                {
	                    MyRole[] value = roleGroup.value();
	                    String[] group = new String[value.length];
	                    for (int i = 0; i < value.length; i++)
	                    {
	                        group[i] = value[i].getName();
	                    }
	                    roleGroups.add(group);
	                }
	                return roleGroups;
	            }
		};

		if(context == null) {
			System.out.println("yes restrictionsAction is null");
		}
		return restrictionsAction.call(context);
	}

}
