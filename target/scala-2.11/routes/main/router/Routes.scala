
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/vineet/sms/srp/conf/routes
// @DATE:Fri Mar 04 01:19:00 IST 2016

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  SRPController_2: controllers.SRPController,
  // @LINE:10
  RegistrationHandler_0: controllers.RegistrationHandler,
  // @LINE:13
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    SRPController_2: controllers.SRPController,
    // @LINE:10
    RegistrationHandler_0: controllers.RegistrationHandler,
    // @LINE:13
    Assets_1: controllers.Assets
  ) = this(errorHandler, SRPController_2, RegistrationHandler_0, Assets_1, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, SRPController_2, RegistrationHandler_0, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.SRPController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """home""", """controllers.SRPController.home()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.SRPController.preLogin()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """registration""", """controllers.RegistrationHandler.registor()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_SRPController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_SRPController_index0_invoker = createInvoker(
    SRPController_2.index(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SRPController",
      "index",
      Nil,
      "GET",
      """ Home page""",
      this.prefix + """"""
    )
  )

  // @LINE:7
  private[this] lazy val controllers_SRPController_home1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("home")))
  )
  private[this] lazy val controllers_SRPController_home1_invoker = createInvoker(
    SRPController_2.home(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SRPController",
      "home",
      Nil,
      "GET",
      """""",
      this.prefix + """home"""
    )
  )

  // @LINE:8
  private[this] lazy val controllers_SRPController_preLogin2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_SRPController_preLogin2_invoker = createInvoker(
    SRPController_2.preLogin(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SRPController",
      "preLogin",
      Nil,
      "GET",
      """""",
      this.prefix + """login"""
    )
  )

  // @LINE:10
  private[this] lazy val controllers_RegistrationHandler_registor3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("registration")))
  )
  private[this] lazy val controllers_RegistrationHandler_registor3_invoker = createInvoker(
    RegistrationHandler_0.registor(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.RegistrationHandler",
      "registor",
      Nil,
      "POST",
      """POST    /login                        controllers.SRPController.postLogin()""",
      this.prefix + """registration"""
    )
  )

  // @LINE:13
  private[this] lazy val controllers_Assets_versioned4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned4_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      """ Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/$file<.+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_SRPController_index0_route(params) =>
      call { 
        controllers_SRPController_index0_invoker.call(SRPController_2.index())
      }
  
    // @LINE:7
    case controllers_SRPController_home1_route(params) =>
      call { 
        controllers_SRPController_home1_invoker.call(SRPController_2.home())
      }
  
    // @LINE:8
    case controllers_SRPController_preLogin2_route(params) =>
      call { 
        controllers_SRPController_preLogin2_invoker.call(SRPController_2.preLogin())
      }
  
    // @LINE:10
    case controllers_RegistrationHandler_registor3_route(params) =>
      call { 
        controllers_RegistrationHandler_registor3_invoker.call(RegistrationHandler_0.registor())
      }
  
    // @LINE:13
    case controllers_Assets_versioned4_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned4_invoker.call(Assets_1.versioned(path, file))
      }
  }
}