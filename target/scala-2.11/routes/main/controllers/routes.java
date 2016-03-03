
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/vineet/sms/srp/conf/routes
// @DATE:Fri Mar 04 01:19:00 IST 2016

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseRegistrationHandler RegistrationHandler = new controllers.ReverseRegistrationHandler(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseSRPController SRPController = new controllers.ReverseSRPController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseRegistrationHandler RegistrationHandler = new controllers.javascript.ReverseRegistrationHandler(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseSRPController SRPController = new controllers.javascript.ReverseSRPController(RoutesPrefix.byNamePrefix());
  }

}
