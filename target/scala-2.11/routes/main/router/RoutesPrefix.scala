
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/vineet/sms/srp/conf/routes
// @DATE:Fri Mar 04 01:19:00 IST 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
